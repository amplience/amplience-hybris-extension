/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.util;

import com.amplience.hybris.dm.product.AmplienceIdentifierSanitizer;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.product.ImageFormatMapping;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.media.services.MimeService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.media.MediaContainerService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Helper class to support exporting images from hybris.
 *
 * Finds products that should have their product images exported and takes the media containers from
 * the Picture and GalleryImages. For each media container the best available image format is
 * selected. Images are exported in gallery order.
 */
public class ExportImages
{
	private static final Logger LOG = Logger.getLogger(ExportImages.class);

	// Services
	private SessionService sessionService;
	private UserService userService;
	private FlexibleSearchService flexibleSearchService;
	private ModelService modelService;
	private CatalogVersionService catalogVersionService;
	private MediaService mediaService;
	private MediaContainerService mediaContainerService;
	private MimeService mimeService;
	private ImageFormatMapping imageFormatMapping;
	private AmplienceIdentifierSanitizer amplienceIdentifierSanitizer;

	// Configuration
	private List<String> imageFormats;
	private String catalogCode;
	private String catalogVersion;
	private String query;

	// -----

	public SessionService getSessionService()
	{
		return sessionService;
	}

	@Required
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	public ModelService getModelService()
	{
		return modelService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	@Required
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	public MediaService getMediaService()
	{
		return mediaService;
	}

	@Required
	public void setMediaService(final MediaService mediaService)
	{
		this.mediaService = mediaService;
	}

	public MediaContainerService getMediaContainerService()
	{
		return mediaContainerService;
	}

	@Required
	public void setMediaContainerService(final MediaContainerService mediaContainerService)
	{
		this.mediaContainerService = mediaContainerService;
	}

	public MimeService getMimeService()
	{
		return mimeService;
	}

	@Required
	public void setMimeService(final MimeService mimeService)
	{
		this.mimeService = mimeService;
	}

	public ImageFormatMapping getImageFormatMapping()
	{
		return imageFormatMapping;
	}

	@Required
	public void setImageFormatMapping(final ImageFormatMapping imageFormatMapping)
	{
		this.imageFormatMapping = imageFormatMapping;
	}

	protected AmplienceIdentifierSanitizer getAmplienceIdentifierSanitizer()
	{
		return amplienceIdentifierSanitizer;
	}

	@Required
	public void setAmplienceIdentifierSanitizer(final AmplienceIdentifierSanitizer amplienceIdentifierSanitizer)
	{
		this.amplienceIdentifierSanitizer = amplienceIdentifierSanitizer;
	}

	// -----

	public String getCatalogCode()
	{
		return catalogCode;
	}

	public void setCatalogCode(final String catalogCode)
	{
		this.catalogCode = catalogCode;
	}

	public String getCatalogVersion()
	{
		return catalogVersion;
	}

	public void setCatalogVersion(final String catalogVersion)
	{
		this.catalogVersion = catalogVersion;
	}

	public List<String> getImageFormats()
	{
		return imageFormats;
	}

	public void setImageFormats(final List<String> imageFormats)
	{
		this.imageFormats = imageFormats;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(final String query)
	{
		this.query = query;
	}

	// -----

	protected List<PK> findProductsToExport()
	{
		final CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion(getCatalogCode(), getCatalogVersion());

		final Map<String, Object> params = Collections.singletonMap("catalogVersion", catalogVersionModel);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(getQuery(), params);
		flexibleSearchQuery.setNeedTotal(false);
		flexibleSearchQuery.setResultClassList(Collections.singletonList(PK.class));

		final SearchResult<PK> search = flexibleSearchService.search(flexibleSearchQuery);
		return search.getResult();
	}

	protected MediaModel getBestMediaFromContainer(final MediaContainerModel container)
	{
		if (container == null)
		{
			return null;
		}

		for (final String imageFormat : imageFormats)
		{
			final String mediaFormatQualifier = imageFormatMapping.getMediaFormatQualifierForImageFormat(imageFormat);
			if (mediaFormatQualifier != null)
			{
				try
				{
					final MediaFormatModel mediaFormat = mediaService.getFormat(mediaFormatQualifier);
					return mediaContainerService.getMediaForFormat(container, mediaFormat);
				}
				catch (UnknownIdentifierException | AmbiguousIdentifierException | ModelNotFoundException ex)
				{
					// Ignore exception, try next imageFormat
					LOG.trace("Ignoring export image [" + container.getPk() + "] imageFormat [" + imageFormat + "] mediaFormat [" + mediaFormatQualifier + "] exception", ex);
				}
			}
		}
		return null;
	}

	protected List<MediaContainerModel> getProductImageMediaContainersForProduct(final ProductModel product)
	{
		final List<MediaContainerModel> result = new ArrayList<>();

		if (product == null)
		{
			return result;
		}

		final MediaModel picture = product.getPicture();
		if (picture != null)
		{
			final MediaContainerModel mediaContainer = picture.getMediaContainer();
			if (mediaContainer != null)
			{
				result.add(mediaContainer);
			}
		}

		final List<MediaContainerModel> galleryImages = product.getGalleryImages();
		if (galleryImages != null)
		{
			galleryImages.stream().filter(Objects::nonNull).forEachOrdered(result::add);
		}

		return result;
	}

	protected List<MediaModel> uniqueMedias(final List<MediaModel> medias)
	{
		final Set<String> seenMd5s = new HashSet<>();

		final List<MediaModel> result = new ArrayList<>();

		for (final MediaModel media : medias)
		{
			final String md5 = DigestUtils.md5Hex(mediaService.getDataFromMedia(media));
			if (!seenMd5s.contains(md5))
			{
				seenMd5s.add(md5);
				result.add(media);
			}
		}

		return result;

	}

	protected List<MediaModel> getImagesToExportForProduct(final ProductModel product)
	{
		final List<MediaModel> bestMedias = new ArrayList<>();
		for (final MediaContainerModel mediaContainer : getProductImageMediaContainersForProduct(product))
		{
			final MediaModel bestMedia = getBestMediaFromContainer(mediaContainer);
			if (bestMedia != null)
			{
				bestMedias.add(bestMedia);
			}
		}

		return uniqueMedias(bestMedias);
	}

	protected void exportProductImages(final ProductModel product, final Path outputPath)
	{
		final List<MediaModel> imagesToExport = getImagesToExportForProduct(product);
		for (int i = 0; i < imagesToExport.size(); i++)
		{
			exportProductImage(product, outputPath, imagesToExport.get(i), i);
		}
	}

	protected void exportProductImage(final ProductModel product, final Path outputPath, final MediaModel media, final int position)
	{
		final String fileExtension = mimeService.getFileExtensionFromMime(media.getMime());
		final String outputFileName = buildImageExportFilename(product, position, fileExtension);

		final Path outputFilePath = outputPath.resolve(outputFileName);

		LOG.info("Exporting product [" + product.getCode() + "] media [" + media.getCode() + "] to [" + outputFilePath + "]");


		try (final InputStream inputStream = mediaService.getStreamFromMedia(media))
		{
			Files.copy(inputStream, outputFilePath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (final IOException ex)
		{
			LOG.error("Failed to export media [" + media.getCode() + "] to [" + outputFilePath + "]", ex);
		}
	}

	protected String buildImageExportFilename(final ProductModel product, final int position, final String fileExtension)
	{
		return getAmplienceIdentifierSanitizer().sanitize(product.getCode()) + '-' + intToPaddedString(position + 1) + '.' + fileExtension;
	}

	protected String intToPaddedString(final int value)
	{
		final DecimalFormat format = new DecimalFormat("00");
		return format.format(value);
	}

	/**
	 * Export the images related to the product to files in the path specified
	 *
	 * @param outputPath the output path
	 */
	public void doExport(final Path outputPath)
	{
		LOG.info("Begin exporting product images to [" + outputPath + "]");
		LOG.info("Exporting products from catalog [" + getCatalogCode() + "]");
		LOG.info("Best media format search order " + getImageFormats());

		validate(outputPath);

		try
		{
			Files.createDirectories(outputPath);
		}
		catch (final IOException ex)
		{
			LOG.error("Failed to create directory path for [" + outputPath + "]", ex);
			return;
		}

		// Run export as admin
		final Integer count = sessionService.executeInLocalView(new SessionExecutionBody()
		{
			@Override
			public Object execute()
			{
				return Integer.valueOf(doExportAsAdmin(outputPath));
			}
		}, userService.getAdminUser());

		LOG.info("Done exporting [" + count + "] product images to [" + outputPath + "]");
	}

	// Called to do the export but as the admin user
	protected int doExportAsAdmin(final Path outputPath)
	{
		int count = 0;
		for (final PK productPK : findProductsToExport())
		{
			final ProductModel product = getModelService().get(productPK);
			try
			{
				exportProductImages(product, outputPath);
				count++;
			}
			finally
			{
				getModelService().detach(product);
			}
		}
		return count;
	}

	protected void validate(final Path outputPath)
	{
		Objects.requireNonNull(outputPath, "outputPath is required");
		Objects.requireNonNull(this.catalogCode, "catalogCode is required");
		Objects.requireNonNull(this.imageFormats, "imageFormats is required");
		Objects.requireNonNull(this.query, "query is required");
	}

}
