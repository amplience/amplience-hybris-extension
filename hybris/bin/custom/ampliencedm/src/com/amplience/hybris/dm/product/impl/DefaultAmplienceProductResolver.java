/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.product.AmplienceProductResolver;
import de.hybris.platform.core.model.product.ProductModel;

/**
 * Default implementation of AmplienceProductResolver.
 * Assumes that each hybris product has a media set in Amplience.
 * This implementation knows nothing about the product model and
 * assumes that every product should have its own imagery.
 */
public class DefaultAmplienceProductResolver implements AmplienceProductResolver
{
	/**
	 * Resolve the product that has imagery for the specified product.
	 *
	 * @param product the product
	 * @return the product with imagery
	 */
	@Override
	public ProductModel resolveProduct(final ProductModel product)
	{
		return product;
	}
}
