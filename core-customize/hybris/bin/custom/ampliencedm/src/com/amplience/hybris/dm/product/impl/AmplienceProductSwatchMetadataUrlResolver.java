/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product.impl;

/**
 * Product Swatch Metadata UrlResolver for Amplience hosted product images
 */
public class AmplienceProductSwatchMetadataUrlResolver extends AbstractAmplienceProductMetadataUrlResolver
{
	private static final String CACHE_KEY_PREFIX = AmplienceProductSwatchMetadataUrlResolver.class.getName();
	private static final String SWATCH_SUFFIX = "-swatch";

	public AmplienceProductSwatchMetadataUrlResolver()
	{
		super(CACHE_KEY_PREFIX, SWATCH_SUFFIX, AssetType.IMAGE);
	}
}
