/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.product;

import de.hybris.platform.core.model.product.ProductModel;

/**
 * Strategy to build the SEO name for a product.
 */
public interface AmplienceSeoImageNameStrategy
{
	/**
	 * Generate an optimized name for product images. This name is independent from the actual image names on the Amplience account.
	 * It will be used for SEO purposes and is also the default name for the images when the used saves them with the browser.
	 *
	 * @param product
	 * @return The name of the image as a String.
	 */
	String getSeoName(final ProductModel product);
}
