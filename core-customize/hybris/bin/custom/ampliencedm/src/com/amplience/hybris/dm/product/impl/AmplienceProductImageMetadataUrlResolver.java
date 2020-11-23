/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product.impl;

/**
 * Product Image Metadata UrlResolver for Amplience hosted product images
 */
public class AmplienceProductImageMetadataUrlResolver extends AbstractAmplienceProductMetadataUrlResolver
{
	private static final String CACHE_KEY_PREFIX = AmplienceProductImageMetadataUrlResolver.class.getName();
	private static final String MEDIA_SET_SUFFIX = "-ms";

	public AmplienceProductImageMetadataUrlResolver()
	{
		super(CACHE_KEY_PREFIX, MEDIA_SET_SUFFIX, AssetType.SET);
	}
}
