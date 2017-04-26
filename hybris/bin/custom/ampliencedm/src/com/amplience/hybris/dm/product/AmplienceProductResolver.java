/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.product;

import de.hybris.platform.core.model.product.ProductModel;

/**
 * For a given product resolve the relevant product that holds the images.
 * Products are often organised into hierarchies of products and variants. In this case
 * not all the products will have imagery. For a given product it is necessary to find
 * the appropriate product which holds the images.
 */
public interface AmplienceProductResolver
{
	/**
	 * Resolve the product that has imagery for the specified product.
	 *
	 * @param product the product
	 * @return the product with imagery
	 */
	ProductModel resolveProduct(ProductModel product);
}
