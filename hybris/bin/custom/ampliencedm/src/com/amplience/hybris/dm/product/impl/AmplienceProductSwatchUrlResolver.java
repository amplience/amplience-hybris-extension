/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import com.amplience.hybris.dm.config.AmplienceConfigService;
import com.amplience.hybris.dm.localization.AmplienceLocaleStringStrategy;
import com.amplience.hybris.dm.product.AmplienceIdentifierSanitizer;
import com.amplience.hybris.dm.product.AmplienceProductResolver;
import com.amplience.hybris.dm.product.AmplienceSeoImageNameStrategy;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.commerceservices.url.impl.AbstractUrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.beans.factory.annotation.Required;

/**
 * Product Swatch Image UrlResolver for Amplience hosted product images
 */
public class AmplienceProductSwatchUrlResolver extends AbstractUrlResolver<ProductModel>
{
	private static final String CACHE_KEY = AmplienceProductSwatchUrlResolver.class.getName();
	private static final String SWATCH_SUFFIX = "-swatch";

	private UrlResolver<ProductModel> defaultProductImageUrlResolver;
	private AmplienceConfigService amplienceConfigService;
	private AmplienceSeoImageNameStrategy amplienceSeoImageNameStrategy;
	private AmplienceProductResolver amplienceProductResolver;
	private AmplienceLocaleStringStrategy amplienceLocaleStringStrategy;
	private AmplienceIdentifierSanitizer amplienceIdentifierSanitizer;

	protected UrlResolver<ProductModel> getDefaultProductImageUrlResolver()
	{
		return defaultProductImageUrlResolver;
	}

	@Required
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

	protected AmplienceConfigService getAmplienceConfigService()
	{
		return amplienceConfigService;
	}

	@Required
	public void setAmplienceConfigService(final AmplienceConfigService amplienceConfigService)
	{
		this.amplienceConfigService = amplienceConfigService;
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
		return CACHE_KEY + "." + source.getPk().toString();
	}

	@Override
	protected String resolveInternal(final ProductModel source)
	{
		if (isUseAltSwatch(source))
		{
			final AmplienceConfigData amplienceConfig = getAmplienceConfigService().getConfigForCurrentSite();

			final ProductModel product = redirectProduct(source);
			if (product != null)
			{
				return "//" + amplienceConfig.getImageHostname() + "/i/" + amplienceConfig.getAccountIdentifier() +
					"/" + getImageIdentifierForProduct(product) + "/" + getSeoNameForProduct(product) + ".jpg?" + getLocaleParameter();
			}
		}
		return getDefaultProductImageUrlResolver().resolve(source);
	}

	protected boolean isUseAltSwatch(final ProductModel source)
	{
		return source.isAmplienceAltSwatch();
	}

	protected String getLocaleParameter()
	{
		return "locale=" + getAmplienceLocaleStringStrategy().getCurrentLocaleString();
	}

	protected ProductModel redirectProduct(final ProductModel source)
	{
		return getAmplienceProductResolver().resolveProduct(source);
	}

	protected String getImageIdentifierForProduct(final ProductModel product)
	{
		return getAmplienceIdentifierSanitizer().sanitize(product.getCode()) + SWATCH_SUFFIX;
	}

	protected String getSeoNameForProduct(final ProductModel product)
	{
		return getAmplienceSeoImageNameStrategy().getSeoName(product);
	}
}
