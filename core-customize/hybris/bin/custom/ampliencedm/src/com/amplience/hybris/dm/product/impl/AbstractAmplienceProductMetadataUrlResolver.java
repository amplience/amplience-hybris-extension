/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import com.amplience.hybris.dm.config.AmplienceConfigService;
import com.amplience.hybris.dm.product.AmplienceIdentifierSanitizer;
import de.hybris.platform.commerceservices.url.impl.AbstractUrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.beans.factory.annotation.Required;

/**
 * Product metadata Url resolver base class
 */
public abstract class AbstractAmplienceProductMetadataUrlResolver extends AbstractUrlResolver<ProductModel>
{
	protected enum AssetType
	{
		IMAGE,
		SET
	}

	private final String cacheKeyPrefix;
	private final String productSuffix;
	private final AssetType assetType;

	private AmplienceConfigService amplienceConfigService;
	private AmplienceIdentifierSanitizer amplienceIdentifierSanitizer;

	public AbstractAmplienceProductMetadataUrlResolver(final String cacheKeyPrefix, final String productSuffix, final AssetType assetType)
	{
		this.cacheKeyPrefix = cacheKeyPrefix;
		this.productSuffix = productSuffix;
		this.assetType = assetType;
	}

	protected AmplienceConfigService getAmplienceConfigService()
	{
		return amplienceConfigService;
	}

	@Required
	public void setAmplienceConfigService(final AmplienceConfigService amplienceConfigService)
	{
		this.amplienceConfigService = amplienceConfigService;
	}

	protected AmplienceIdentifierSanitizer getAmplienceIdentifierSanitizer()
	{
		return amplienceIdentifierSanitizer;
	}

	@Required
	public void setAmplienceIdentifierSanitizer(final AmplienceIdentifierSanitizer amplienceIdentifierSanitizer)
	{
		this.amplienceIdentifierSanitizer = amplienceIdentifierSanitizer;
	}

	// --------

	@Override
	protected String getKey(final ProductModel source)
	{
		return cacheKeyPrefix + "." + source.getPk().toString();
	}

	@Override
	protected String resolveInternal(final ProductModel product)
	{
		if (product != null)
		{
			final AmplienceConfigData amplienceConfig = getAmplienceConfigService().getConfigForCurrentSite();
			return "https://" + amplienceConfig.getImageHostname() + urlPathElementForAssetType() +
				amplienceConfig.getAccountIdentifier() + "/" + getImageIdentifierForProduct(product) + ".json";
		}
		return null;
	}

	protected String urlPathElementForAssetType()
	{
		if (assetType == AssetType.SET)
		{
			return "/s/";
		}
		return "/i/";
	}

	protected String getImageIdentifierForProduct(final ProductModel product)
	{
		return getAmplienceIdentifierSanitizer().sanitize(product.getCode()) + productSuffix;
	}
}
