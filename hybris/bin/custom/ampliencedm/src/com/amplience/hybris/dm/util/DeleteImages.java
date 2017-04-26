/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.util;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.media.MediaContainerService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Helper class to support deleting product images from hybris.
 *
 * Given a catalog and version will find all products in that catalog version, and then for each
 * product will find the medias and media containers referenced in the following product attributes:
 * Picture, Thumbnail, Normal, Thumbnails, Detail, Logo, GalleryImages. The medias will
 * then be deleted.
 */
public class DeleteImages
{
	private static final Logger LOG = Logger.getLogger(DeleteImages.class);

	// Services
	private SessionService sessionService;
	private UserService userService;
	private FlexibleSearchService flexibleSearchService;
	private ModelService modelService;
	private CatalogVersionService catalogVersionService;
	private MediaService mediaService;
	private MediaContainerService mediaContainerService;

	// Configuration
	private String catalogCode;
	private String catalogVersion;

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

	// -----

	/**
	 * Delete all the image medias attached to products
	 */
	public void doDeleteImages()
	{
		LOG.info("Begin deleting product images from catalog [" + getCatalogCode() + "]");

		// Run as admin
		sessionService.executeInLocalView(new SessionExecutionBody()
		{
			@Override
			public void executeWithoutResult()
			{
				for (final PK productPK : findProducts())
				{
					final ProductModel product = getModelService().get(productPK);
					try
					{
						deleteProductImages(product);
					}
					finally
					{
						getModelService().detach(product);
					}
				}
			}
		}, userService.getAdminUser());

		LOG.info("Done deleting product images");
	}

	protected List<PK> findProducts()
	{
		final CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion(getCatalogCode(), getCatalogVersion());

		final String queryString = "SELECT {PK} FROM {Product} WHERE {catalogVersion} = ?catalogVersion ORDER BY {code}";
		final Map<String, Object> params = Collections.singletonMap("catalogVersion", catalogVersionModel);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString, params);
		query.setNeedTotal(false);
		query.setResultClassList(Collections.singletonList(PK.class));

		final SearchResult<PK> search = flexibleSearchService.search(query);
		return search.getResult();
	}

	protected void deleteProductImages(final ProductModel product)
	{
		final Set<MediaModel> mediaToDelete = new HashSet<>();
		final Set<MediaContainerModel> containersToDelete = new HashSet<>();

		collectProductMedia(product, mediaToDelete);
		collectProductMediaContainers(product, containersToDelete);

		// Go through the media and find all the containers & add to the containers set
		mediaToDelete.stream().map(MediaModel::getMediaContainer).filter(Objects::nonNull).forEachOrdered(containersToDelete::add);

		// Go through the containers and find all the media
		containersToDelete.stream().flatMap(container -> container.getMedias().stream()).forEachOrdered(mediaToDelete::add);

		if (!mediaToDelete.isEmpty() || !containersToDelete.isEmpty())
		{
			LOG.info("Deleting media from product [" + product.getCode() + "]");
			getModelService().save(product);

			if (!mediaToDelete.isEmpty())
			{
				getModelService().removeAll(mediaToDelete);
			}
			if (!containersToDelete.isEmpty())
			{
				getModelService().removeAll(containersToDelete);
			}
		}
	}

	protected void collectProductMedia(final ProductModel product, final Set<MediaModel> mediaToDelete)
	{
		collectProductMediaPicture(product, mediaToDelete);
		collectProductMediaThumbnail(product, mediaToDelete);
		collectProductMediaNormal(product, mediaToDelete);
		collectProductMediaThumbnails(product, mediaToDelete);
		collectProductMediaDetail(product, mediaToDelete);
		collectProductMediaLogo(product, mediaToDelete);
	}

	protected void collectProductMediaPicture(final ProductModel product, final Set<MediaModel> mediaToDelete)
	{
		if (product.getPicture() != null)
		{
			mediaToDelete.add(product.getPicture());
			product.setPicture(null);
		}
	}

	protected void collectProductMediaThumbnail(final ProductModel product, final Set<MediaModel> mediaToDelete)
	{
		if (product.getThumbnail() != null)
		{
			mediaToDelete.add(product.getThumbnail());
			product.setThumbnail(null);
		}
	}

	protected void collectProductMediaNormal(final ProductModel product, final Set<MediaModel> mediaToDelete)
	{
		if (product.getNormal() != null && !product.getNormal().isEmpty())
		{
			mediaToDelete.addAll(product.getNormal());
			product.setNormal(Collections.emptyList());
		}
	}

	protected void collectProductMediaThumbnails(final ProductModel product, final Set<MediaModel> mediaToDelete)
	{
		if (product.getThumbnails() != null && !product.getThumbnails().isEmpty())
		{
			mediaToDelete.addAll(product.getThumbnails());
			product.setThumbnails(Collections.emptyList());
		}
	}

	protected void collectProductMediaDetail(final ProductModel product, final Set<MediaModel> mediaToDelete)
	{
		if (product.getDetail() != null && !product.getDetail().isEmpty())
		{
			mediaToDelete.addAll(product.getDetail());
			product.setDetail(Collections.emptyList());
		}
	}

	protected void collectProductMediaLogo(final ProductModel product, final Set<MediaModel> mediaToDelete)
	{
		if (product.getLogo() != null && !product.getLogo().isEmpty())
		{
			mediaToDelete.addAll(product.getLogo());
			product.setLogo(Collections.emptyList());
		}
	}

	protected void collectProductMediaContainers(final ProductModel product, final Set<MediaContainerModel> containersToDelete)
	{
		if (product.getGalleryImages() != null && !product.getGalleryImages().isEmpty())
		{
			containersToDelete.addAll(product.getGalleryImages());
			product.setGalleryImages(Collections.emptyList());
		}
	}
}
