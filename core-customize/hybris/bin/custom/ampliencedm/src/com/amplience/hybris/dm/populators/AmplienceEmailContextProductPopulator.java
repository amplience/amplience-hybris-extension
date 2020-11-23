/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;


/**
 * Populate the amplience product data with the product sku and name.
 * Used as part of the product missing images report email generation.
 */
public class AmplienceEmailContextProductPopulator implements Populator<ProductModel, ProductData>
{
	/**
	 * Populate the amplience product data with the product sku and name.
	 *
	 * @param source the source product
	 * @param target the target ProductData
	 */
	@Override
	public void populate(final ProductModel source, final ProductData target)
	{
		target.setCode(source.getCode());
		target.setName(source.getName());
	}
}
