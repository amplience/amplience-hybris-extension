/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.populators;

import com.amplience.hybris.dm.data.AmplienceProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;


/**
 * Populate the amplience product data with the product sku and name.
 * Used as part of the product missing images report email generation.
 */
public class AmplienceEmailContextProductPopulator implements Populator<ProductModel, AmplienceProductData>
{
	/**
	 * Populate the amplience product data with the product sku and name.
	 *
	 * @param source the source product
	 * @param target the target AmplienceProductData
	 */
	@Override
	public void populate(final ProductModel source, final AmplienceProductData target)
	{
		target.setSku(source.getCode());
		target.setName(source.getName());
	}
}
