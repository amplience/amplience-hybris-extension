/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.facade.impl;

import com.amplience.hybris.dm.data.AmplienceProductData;
import com.amplience.hybris.dm.facade.AmplienceProductFacade;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default implementation of AmplienceProductFacade
 */
public class DefaultAmplienceProductFacade implements AmplienceProductFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultAmplienceProductFacade.class);

	private ProductFacade productFacade;
	private Converter<ProductData, AmplienceProductData> amplienceProductDataConverter;

	protected ProductFacade getProductFacade()
	{
		return productFacade;
	}

	@Required
	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	protected Converter<ProductData, AmplienceProductData> getAmplienceProductDataConverter()
	{
		return amplienceProductDataConverter;
	}

	@Required
	public void setAmplienceProductDataConverter(final Converter<ProductData, AmplienceProductData> amplienceProductDataConverter)
	{
		this.amplienceProductDataConverter = amplienceProductDataConverter;
	}

	// ----------

	/**
	 * Get list of AmplienceProductData for specified list of products.
	 *
	 * Gets the ProductData for each product code and then converts the ProductData into AmplienceProductData
	 * using the injected amplienceProductDataConverter.
	 *
	 * @param productCodes the list of product codes
	 * @return the list of AmplienceProductData
	 */
	@Override
	public List<AmplienceProductData> getProductsByCode(final List<String> productCodes)
	{
		final List<AmplienceProductData> result = new ArrayList<>();

		for (final String productCode : productCodes)
		{
			try
			{
				final ProductData productData = getProductFacade().getProductForCodeAndOptions(productCode, Arrays
					.asList(ProductOption.BASIC, ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.PRICE, ProductOption.URL));
				if (productData != null)
				{
					final AmplienceProductData amplienceProductData = getAmplienceProductDataConverter().convert(productData);
					if (amplienceProductData != null)
					{
						result.add(amplienceProductData);
					}
				}
			}
			catch (final UnknownIdentifierException | AmbiguousIdentifierException | IllegalArgumentException ex)
			{
				// Skip the product code
				LOG.debug("Failed to lookup product by code [" + productCode + "]", ex);
			}
		}
		return result;
	}
}
