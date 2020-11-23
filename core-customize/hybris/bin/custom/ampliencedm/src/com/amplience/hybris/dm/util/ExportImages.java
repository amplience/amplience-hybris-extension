/*
 * Copyright (c) 2016-2020 Amplience
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.Objects;
import java.util.Set;

/**
 * Helper class to support exporting images from hybris.
 * <p>
 * Finds products that should have their product images exported and takes the media containers from
 * the Picture and GalleryImages. For each media container the best available image format is
 * selected. Images are exported in gallery order.
 */
public class ExportImages
{
	private static final Logger LOG = LoggerFactory.getLogger(ExportImages.class);

	// Services
	protected final SessionService sessionService;
	protected final UserService userService;
	protected final FlexibleSearchService flexibleSearchService;
	protected final ModelService modelService;
	protected final CatalogVersionService catalogVersionService;
	protected final MediaService mediaService;
	protected final MediaContainerService mediaContainerService;
	protected final MimeService mimeService;
	protected final ImageFormatMapping imageFormatMapping;
	protected final AmplienceIdentifierSanitizer amplienceIdentifierSanitizer;

	// Configuration
	private List<String> imageFormats;
	private String catalogCode;
	private String catalogVersion;
	private String query;

	@SuppressWarnings("java:S107")
	public ExportImages(final SessionService sessionService, final UserService userService, final FlexibleSearchService flexibleSearchService, final ModelService modelService, final CatalogVersionService catalogVersionService, final MediaService mediaService, final MediaContainerService mediaContainerService, final MimeService mimeService, final ImageFormatMapping imageFormatMapping, final AmplienceIdentifierSanitizer amplienceIdentifierSanitizer)
	{
		this.sessionService = sessionService;
		this.userService = userService;
		this.flexibleSearchService = flexibleSearchService;
		this.modelService = modelService;
		this.catalogVersionService = catalogVersionService;
		this.mediaService = mediaService;
		this.mediaContainerService = mediaContainerService;
		this.mimeService = mimeService;
		this.imageFormatMapping = imageFormatMapping;
		this.amplienceIdentifierSanitizer = amplienceIdentifierSanitizer;
	}


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

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(getQuery());
		flexibleSearchQuery.addQueryParameter("catalogVersion", catalogVersionModel);
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
				catch (final UnknownIdentifierException | AmbiguousIdentifierException | ModelNotFoundException ex)
				{
					// Ignore exception, try next imageFormat
					LOG.trace("Ignoring export image [{}] imageFormat [{}] mediaFormat [{}] exception", container.getPk(), imageFormat, mediaFormatQualifier, ex);
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
		final Set<String> seenMedias = new HashSet<>();

		final List<MediaModel> result = new ArrayList<>();

		for (final MediaModel media : medias)
		{
			final String hash = generateHashForMedia(media);
			if (!seenMedias.contains(hash))
			{
				seenMedias.add(hash);
				result.add(media);
			}
		}

		return result;
	}

	protected String generateHashForMedia(final MediaModel media)
	{
		// This hash is not used in a security-sensitive context
		return DigestUtils.md5Hex(mediaService.getDataFromMedia(media));
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

		LOG.info("Exporting product [{}] media [{}] to [{}]", product.getCode(), media.getCode(), outputFilePath);


		try (final InputStream inputStream = mediaService.getStreamFromMedia(media))
		{
			Files.copy(inputStream, outputFilePath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (final IOException ex)
		{
			LOG.error("Failed to export media [{}] to [{}]", media.getCode(), outputFilePath, ex);
		}
	}

	protected String buildImageExportFilename(final ProductModel product, final int position, final String fileExtension)
	{
		return amplienceIdentifierSanitizer.sanitize(product.getCode()) + '-' + intToPaddedString(position + 1) + '.' + fileExtension;
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
		LOG.info("Begin exporting product images to [{}]", outputPath);
		LOG.info("Exporting products from catalog [{}]", getCatalogCode());
		LOG.info("Best media format search order {}", getImageFormats());

		validate(outputPath);

		try
		{
			Files.createDirectories(outputPath);
		}
		catch (final IOException ex)
		{
			LOG.error("Failed to create directory path for [{}]", outputPath, ex);
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

		LOG.info("Done exporting [{}] product images to [{}]", count, outputPath);
	}

	// Called to do the export but as the admin user
	protected int doExportAsAdmin(final Path outputPath)
	{
		int count = 0;
		for (final PK productPK : findProductsToExport())
		{
			final ProductModel product = modelService.get(productPK);
			try
			{
				exportProductImages(product, outputPath);
				count++;
			}
			finally
			{
				modelService.detach(product);
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
