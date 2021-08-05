/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product.impl;

/**
 * Product Image UrlResolver for Amplience hosted product images
 */
public class AmplienceProductImageUrlResolver extends AbstractAmplienceProductImageUrlResolver
{
	private static final String CACHE_KEY_PREFIX = AmplienceProductImageUrlResolver.class.getName();
	private static final String MEDIA_SET_SUFFIX = "-ms";

	public AmplienceProductImageUrlResolver()
	{
		super(CACHE_KEY_PREFIX, MEDIA_SET_SUFFIX, AssetType.SET);
	}
}
