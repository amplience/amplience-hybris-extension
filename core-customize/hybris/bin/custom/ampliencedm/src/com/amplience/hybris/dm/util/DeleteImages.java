/*
 * Copyright (c) 2016-2020 Amplience
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Helper class to support deleting product images from hybris.
 * <p>
 * Given a catalog and version will find all products in that catalog version, and then for each
 * product will find the medias and media containers referenced in the following product attributes:
 * Picture, Thumbnail, Normal, Thumbnails, Detail, Logo, GalleryImages. The medias will
 * then be deleted.
 */
public class DeleteImages
{
	private static final Logger LOG = LoggerFactory.getLogger(DeleteImages.class);

	// Services
	protected final SessionService sessionService;
	protected final UserService userService;
	protected final FlexibleSearchService flexibleSearchService;
	protected final ModelService modelService;
	protected final CatalogVersionService catalogVersionService;
	protected final MediaService mediaService;
	protected final MediaContainerService mediaContainerService;

	// Configuration
	private String catalogCode;
	private String catalogVersion;

	public DeleteImages(final SessionService sessionService, final UserService userService, final FlexibleSearchService flexibleSearchService, final ModelService modelService, final CatalogVersionService catalogVersionService, final MediaService mediaService, final MediaContainerService mediaContainerService)
	{
		this.sessionService = sessionService;
		this.userService = userService;
		this.flexibleSearchService = flexibleSearchService;
		this.modelService = modelService;
		this.catalogVersionService = catalogVersionService;
		this.mediaService = mediaService;
		this.mediaContainerService = mediaContainerService;
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

	// -----

	/**
	 * Delete all the image medias attached to products
	 */
	public void doDeleteImages()
	{
		LOG.info("Begin deleting product images from catalog [{}]", getCatalogCode());

		// Run as admin
		sessionService.executeInLocalView(new SessionExecutionBody()
		{
			@Override
			public void executeWithoutResult()
			{
				for (final PK productPK : findProducts())
				{
					final ProductModel product = modelService.get(productPK);
					try
					{
						deleteProductImages(product);
					}
					finally
					{
						modelService.detach(product);
					}
				}
			}
		}, userService.getAdminUser());

		LOG.info("Done deleting product images");
	}

	protected List<PK> findProducts()
	{
		final CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion(getCatalogCode(), getCatalogVersion());

		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {PK} FROM {Product} WHERE {catalogVersion} = ?catalogVersion ORDER BY {code}");
		query.addQueryParameter("catalogVersion", catalogVersionModel);
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
			LOG.info("Deleting media from product [{}]", product.getCode());
			modelService.save(product);

			if (!mediaToDelete.isEmpty())
			{
				modelService.removeAll(mediaToDelete);
			}
			if (!containersToDelete.isEmpty())
			{
				modelService.removeAll(containersToDelete);
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
