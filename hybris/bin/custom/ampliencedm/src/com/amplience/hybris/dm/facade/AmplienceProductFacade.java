/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.facade;

import com.amplience.hybris.dm.data.AmplienceProductData;

import java.util.List;

/**
 * AmplienceProductFacade used to support the EcommBridge
 */
public interface AmplienceProductFacade
{
	/**
	 * Get list of AmplienceProductData for specified list of products.
	 *
	 * @param productCodes the list of product codes
	 * @return the list of AmplienceProductData
	 */
	List<AmplienceProductData> getProductsByCode(List<String> productCodes);
}
