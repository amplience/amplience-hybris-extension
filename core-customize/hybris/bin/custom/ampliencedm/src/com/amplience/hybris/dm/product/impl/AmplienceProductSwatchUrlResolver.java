/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import de.hybris.platform.core.model.product.ProductModel;

/**
 * Product Swatch Image UrlResolver for Amplience hosted product images
 */
public class AmplienceProductSwatchUrlResolver extends AbstractAmplienceProductImageUrlResolver
{
	private static final String CACHE_KEY_PREFIX = AmplienceProductSwatchUrlResolver.class.getName();
	private static final String SWATCH_SUFFIX = "-swatch";

	public AmplienceProductSwatchUrlResolver()
	{
		super(CACHE_KEY_PREFIX, SWATCH_SUFFIX, AssetType.IMAGE);
	}

	@Override
	protected String buildProductImageUrl(final ProductModel product)
	{
		if (isUseAltSwatch(product))
		{
			final AmplienceConfigData amplienceConfig = getAmplienceConfigService().getConfigForCurrentSite();

			return "//" + amplienceConfig.getImageHostname() + urlPathElementForAssetType() + amplienceConfig.getAccountIdentifier() +
				"/" + getImageIdentifierForProduct(product) + "/" + getSeoNameForProduct(product) + ".jpg" + getLocaleParameter();
		}

		// We want to delegate to the fallback
		return null;
	}

	protected boolean isUseAltSwatch(final ProductModel source)
	{
		return source.isAmplienceAltSwatch();
	}
}
