/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Search Result Populator that adds Amplience product image URLs
 */
public class AmplienceSearchResultVariantProductPopulator extends SearchResultVariantProductPopulator
{
	private List<String> imageFormats;
	private UrlResolver<ProductModel> productImageUrlResolver;

	protected List<String> getImageFormats()
	{
		return imageFormats;
	}

	@Required
	public void setImageFormats(final List<String> imageFormats)
	{
		this.imageFormats = imageFormats;
	}

	protected UrlResolver<ProductModel> getProductImageUrlResolver()
	{
		return productImageUrlResolver;
	}

	@Required
	public void setProductImageUrlResolver(final UrlResolver<ProductModel> productImageUrlResolver)
	{
		this.productImageUrlResolver = productImageUrlResolver;
	}

	/**
	 * Search Result Populator that adds Amplience product image URLs
	 *
	 * @param source the source SearchResultValueData
	 * @param target the target product data
	 */
	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		super.populate(source, target);

		final List<ImageData> images = createAmplienceImageData(source, target);
		if (CollectionUtils.isNotEmpty(images))
		{
			target.setImages(images);
		}
	}

	// Override the createImageData method to disable the default image creation behaviour of the SearchResultProductPopulator superclass.
	@Override
	protected List<ImageData> createImageData(final SearchResultValueData source)
	{
		return Collections.emptyList();
	}

	protected List<ImageData> createAmplienceImageData(final SearchResultValueData source, final ProductData target)
	{
		final List<ImageData> result = new ArrayList<ImageData>();

		final String imageUrl = getProductImageUrl(source);
		if (imageUrl != null)
		{
			final String productImageUrlContextualQuery = getProductImageUrlContextualQuery(source, target);

			for (final String imageFormat : getImageFormats())
			{
				final ImageData imageData = createImageData();
				imageData.setImageType(ImageDataType.PRIMARY);
				imageData.setFormat(imageFormat);
				imageData.setUrl("https:" + imageUrl + "&$" + imageFormat + "$" + productImageUrlContextualQuery);

				result.add(imageData);
			}
		}

		return result;
	}

	/**
	 * Override in sub-class. Get the product image URL for the search result.
	 * This should be the fully qualified URL for the image for the product represented by the
	 * search result. It should not include the image format as that will be added to the URL.
	 * Nor should it include the contextual flags returned by {@link #getProductImageUrlContextualQuery(SearchResultValueData, ProductData)}.
	 * It should include the amplience locale parameters.
	 *
	 * @param source the search result
	 * @return the product image URL
	 */
	protected String getProductImageUrl(final SearchResultValueData source)
	{
		// Ideally a sub-class will be able to retrieve the product image url directly from the
		// search result data rather than loading the product model by code.
		final String productCode = this.getValue(source, "code");
		final ProductModel productModel = getProductService().getProductForCode(productCode);
		if (productModel != null)
		{
			return getProductImageUrlResolver().resolve(productModel);
		}
		return null;
	}

	/**
	 * Override in sub-class. Get search result contextual image URL query parameters.
	 * The default is to return an empty string. If the result is not blank then it should
	 * start with an ampersand (&) as the URL query string parameter separator.
	 * The result will be appended to the URL query and must be a format that can be
	 * interpreted by Amplience.
	 *
	 * @param source the search result
	 * @param target the target product data
	 * @return the URL context query
	 */
	protected String getProductImageUrlContextualQuery(final SearchResultValueData source, final ProductData target)
	{
		// No contextual query by default
		return "";
	}
}
