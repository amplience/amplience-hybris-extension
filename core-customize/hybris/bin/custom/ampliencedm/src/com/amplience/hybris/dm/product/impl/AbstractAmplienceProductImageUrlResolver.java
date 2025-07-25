/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import com.amplience.hybris.dm.format.AmplienceImageFormatStrategy;
import com.amplience.hybris.dm.localization.AmplienceLocaleStringStrategy;
import com.amplience.hybris.dm.product.AmplienceProductResolver;
import com.amplience.hybris.dm.product.AmplienceSeoImageNameStrategy;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static com.amplience.hybris.dm.constants.AmplienceDmConstants.URL_QUERY_PARAM_FORMAT;
import static com.amplience.hybris.dm.constants.AmplienceDmConstants.URL_QUERY_PARAM_LOCALE;

/**
 * Base class for the product image url resolvers
 */
public abstract class AbstractAmplienceProductImageUrlResolver extends AbstractAmplienceProductMetadataUrlResolver
{
	private UrlResolver<ProductModel> defaultProductImageUrlResolver;
	private AmplienceSeoImageNameStrategy amplienceSeoImageNameStrategy;
	private AmplienceProductResolver amplienceProductResolver;
	private AmplienceLocaleStringStrategy amplienceLocaleStringStrategy;
	private AmplienceImageFormatStrategy amplienceImageFormatStrategy;

	public AbstractAmplienceProductImageUrlResolver(final String cacheKeyPrefix, final String productSuffix, final AssetType assetType)
	{
		super(cacheKeyPrefix, productSuffix, assetType);
	}

	protected UrlResolver<ProductModel> getDefaultProductImageUrlResolver()
	{
		return defaultProductImageUrlResolver;
	}

	public void setDefaultProductImageUrlResolver(final UrlResolver<ProductModel> defaultProductImageUrlResolver)
	{
		this.defaultProductImageUrlResolver = defaultProductImageUrlResolver;
	}

	protected AmplienceSeoImageNameStrategy getAmplienceSeoImageNameStrategy()
	{
		return amplienceSeoImageNameStrategy;
	}

	@Required
	public void setAmplienceSeoImageNameStrategy(final AmplienceSeoImageNameStrategy amplienceSeoImageNameStrategy)
	{
		this.amplienceSeoImageNameStrategy = amplienceSeoImageNameStrategy;
	}

	public AmplienceProductResolver getAmplienceProductResolver()
	{
		return amplienceProductResolver;
	}

	@Required
	public void setAmplienceProductResolver(final AmplienceProductResolver amplienceProductResolver)
	{
		this.amplienceProductResolver = amplienceProductResolver;
	}

	protected AmplienceLocaleStringStrategy getAmplienceLocaleStringStrategy()
	{
		return amplienceLocaleStringStrategy;
	}

	@Required
	public void setAmplienceLocaleStringStrategy(final AmplienceLocaleStringStrategy amplienceLocaleStringStrategy)
	{
		this.amplienceLocaleStringStrategy = amplienceLocaleStringStrategy;
	}

	protected AmplienceImageFormatStrategy getAmplienceImageFormatStrategy()
	{
		return amplienceImageFormatStrategy;
	}

	public void setAmplienceImageFormatStrategy(final AmplienceImageFormatStrategy amplienceImageFormatStrategy)
	{
		this.amplienceImageFormatStrategy = amplienceImageFormatStrategy;
	}

	// --------

	@Override
	protected String resolveInternal(final ProductModel source)
	{
		if (source != null)
		{
			final ProductModel product = redirectProduct(source);
			if (product != null)
			{
				final String imageUrl = buildProductImageUrl(product);
				if (imageUrl != null)
				{
					return imageUrl;
				}

				return handleFallback(product);
			}

			return handleFallback(source);
		}
		return null;
	}

	protected String handleFallback(final ProductModel source)
	{
		// Fallback is optional
		final UrlResolver<ProductModel> urlResolver = getDefaultProductImageUrlResolver();
		if (urlResolver != null)
		{
			return urlResolver.resolve(source);
		}
		return null;
	}

	protected String buildProductImageUrl(final ProductModel product)
	{
		final AmplienceConfigData amplienceConfig = getAmplienceConfigService().getConfigForCurrentSite();

		return "//" + amplienceConfig.getImageHostname() + urlPathElementForAssetType() + amplienceConfig.getAccountIdentifier() +
				"/" + getImageIdentifierForProduct(product) + "/" + getSeoNameForProduct(product) + getQueryParameters();
	}

	protected String getQueryParameters()
	{
		return UriComponentsBuilder.newInstance()
				.queryParamIfPresent(URL_QUERY_PARAM_LOCALE, getLocaleParameter())
				.queryParamIfPresent(URL_QUERY_PARAM_FORMAT, getFormatParameter())
				.build().toString();
	}

	protected Optional<String> getLocaleParameter()
	{
		return Optional.ofNullable(getAmplienceLocaleStringStrategy().getCurrentLocaleString());
	}

	protected Optional<String> getFormatParameter()
	{
		return Optional.ofNullable(getAmplienceImageFormatStrategy().getImageFormat());
	}

	protected ProductModel redirectProduct(final ProductModel source)
	{
		return getAmplienceProductResolver().resolveProduct(source);
	}

	protected String getSeoNameForProduct(final ProductModel product)
	{
		return getAmplienceSeoImageNameStrategy().getSeoName(product);
	}
}
