/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.populators;

import de.hybris.platform.commercefacades.product.converters.populator.ProductPrimaryImagePopulator;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

/**
 * Populate the primary product image with the URL to the Amplience product image.
 * Uses the amplienceProductImageUrlResolver to create the URL to the image.
 *
 * @param <SOURCE> Source is a ProductModel or subtype
 * @param <TARGET> Target is a ProductData or subtype
 */
public class AmplienceProductPrimaryImagePopulator<SOURCE extends ProductModel, TARGET extends ProductData> extends ProductPrimaryImagePopulator<SOURCE, TARGET>
{
	private UrlResolver<ProductModel> amplienceProductImageUrlResolver;

	protected UrlResolver<ProductModel> getAmplienceProductImageUrlResolver()
	{
		return amplienceProductImageUrlResolver;
	}

	@Required
	public void setAmplienceProductImageUrlResolver(final UrlResolver<ProductModel> amplienceProductImageUrlResolver)
	{
		this.amplienceProductImageUrlResolver = amplienceProductImageUrlResolver;
	}

	/**
	 * Populate the primary product image with the URL to the Amplience product image.
	 *
	 * @param productModel the source product instance
	 * @param productData the target product data instance
	 */
	@Override
	public void populate(final SOURCE productModel, final TARGET productData)
	{
		final String imageUrl = getAmplienceProductImageUrlResolver().resolve(productModel);

		final List<ImageData> imageList = new ArrayList<>();
		for (final String imageFormat : getImageFormats())
		{
			final ImageData imageData = new ImageData();
			imageData.setFormat(imageFormat);
			imageData.setImageType(ImageDataType.PRIMARY);
			imageData.setUrl("https:" + imageUrl + "&$" + imageFormat + "$");
			imageData.setAltText(productModel.getName());
			imageList.add(imageData);
		}

		productData.setImages(imageList);
	}
}
