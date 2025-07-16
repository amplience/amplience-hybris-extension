/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import de.hybris.platform.core.model.product.ProductModel;

/**
 * UrlResolver used to preview Amplience product images in Backoffice.
 * Backoffice preview requires the image URL to have an extension (for example, .jpg).
 */
public class AmplienceBackofficeProductImageUrlResolver extends AbstractAmplienceProductImageUrlResolver
{
	private static final String CACHE_KEY_PREFIX = AmplienceBackofficeProductImageUrlResolver.class.getName();
	private static final String MEDIA_SET_SUFFIX = "-ms";

	public AmplienceBackofficeProductImageUrlResolver()
	{
		super(CACHE_KEY_PREFIX, MEDIA_SET_SUFFIX, AssetType.SET);
	}

	@Override
	protected String buildProductImageUrl(final ProductModel product)
	{
		final AmplienceConfigData amplienceConfig = getAmplienceConfigService().getConfigForCurrentSite();

		return "//" + amplienceConfig.getImageHostname() + urlPathElementForAssetType() + amplienceConfig.getAccountIdentifier() +
				"/" + getImageIdentifierForProduct(product) + "/" + getSeoNameForProduct(product) + ".jpg" + getQueryParameters();
	}
}
