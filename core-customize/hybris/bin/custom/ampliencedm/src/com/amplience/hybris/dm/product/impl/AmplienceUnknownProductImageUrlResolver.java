/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import com.amplience.hybris.dm.config.AmplienceConfigService;
import com.amplience.hybris.dm.format.AmplienceImageFormatStrategy;
import com.amplience.hybris.dm.localization.AmplienceLocaleStringStrategy;
import de.hybris.platform.commerceservices.url.impl.AbstractUrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static com.amplience.hybris.dm.constants.AmplienceDmConstants.URL_QUERY_PARAM_FORMAT;
import static com.amplience.hybris.dm.constants.AmplienceDmConstants.URL_QUERY_PARAM_LOCALE;

/**
 * UrlResolver used for invalid or unknown products.
 * This resolver is used when the appropriate product for rendering the url cannot be found.
 * This is not an expected situation, but it is the fallback from the
 * AmplienceProductImageUrlResolver.
 */
public class AmplienceUnknownProductImageUrlResolver extends AbstractUrlResolver<ProductModel>
{
	private static final String UNKNOWN_IMAGE = "unknown";

	private AmplienceConfigService amplienceConfigService;
	private AmplienceLocaleStringStrategy amplienceLocaleStringStrategy;
	private AmplienceImageFormatStrategy amplienceImageFormatStrategy;

	protected AmplienceConfigService getAmplienceConfigService()
	{
		return amplienceConfigService;
	}

	@Required
	public void setAmplienceConfigService(final AmplienceConfigService amplienceConfigService)
	{
		this.amplienceConfigService = amplienceConfigService;
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
		return buildMissingProductImageUrl();
	}

	protected String buildMissingProductImageUrl()
	{
		final AmplienceConfigData amplienceConfig = getAmplienceConfigService().getConfigForCurrentSite();

		// No appropriate product with imagery
		// - for example a base product which will have variants, but does not yet have variants, where only the variants have media
		return "//" + amplienceConfig.getImageHostname() + "/i/" + amplienceConfig.getAccountIdentifier() +
			"/" + UNKNOWN_IMAGE + getQueryParameters();
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
}
