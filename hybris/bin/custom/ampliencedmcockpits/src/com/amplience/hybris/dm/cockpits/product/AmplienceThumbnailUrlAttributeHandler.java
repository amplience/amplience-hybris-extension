/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.cockpits.product;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cockpit.session.UISession;
import de.hybris.platform.cockpit.session.UISessionUtils;
import de.hybris.platform.commerceservices.impersonation.ImpersonationContext;
import de.hybris.platform.commerceservices.impersonation.ImpersonationService;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DynamicAttributeHandler for the Product.amplienceThumbnailUrl attribute.
 *
 * This handler is readonly and generates a fully qualified URL to a preview thumbnail image for
 * the product. This needs to be done as an item attribute because the product cockpit viewers
 * that shown the product preview thumbnails consume the URL from an item attribute.
 *
 * The image URL needs to be generated using the context of a website. The Amplience
 * configuration is per website, therefore the product must be in a product catalog that is
 * linked to a base store that is linked to a base site that is configured to work with Amplience.
 *
 * The URL locale is dependant on the DataLocale rather than the SessionLocale.
 */
public class AmplienceThumbnailUrlAttributeHandler implements DynamicAttributeHandler<String, ProductModel>
{
	private static final String SESSION_KEY = AmplienceThumbnailUrlAttributeHandler.class.getName();

	private CommonI18NService commonI18NService;
	private SessionService sessionService;
	private BaseSiteService baseSiteService;
	private ImpersonationService impersonationService;
	private UrlResolver<ProductModel> amplienceProductImageUrlResolver;
	private UserService userService;
	private String imageFormat;

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	@Required
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	protected SessionService getSessionService()
	{
		return sessionService;
	}

	@Required
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	@Required
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	protected UrlResolver<ProductModel> getAmplienceProductImageUrlResolver()
	{
		return amplienceProductImageUrlResolver;
	}

	@Required
	public void setAmplienceProductImageUrlResolver(final UrlResolver<ProductModel> amplienceProductImageUrlResolver)
	{
		this.amplienceProductImageUrlResolver = amplienceProductImageUrlResolver;
	}

	protected ImpersonationService getImpersonationService()
	{
		return impersonationService;
	}

	@Required
	public void setImpersonationService(final ImpersonationService impersonationService)
	{
		this.impersonationService = impersonationService;
	}

	protected UserService getUserService()
	{
		return userService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	protected String getImageFormat()
	{
		return imageFormat;
	}

	@Required
	public void setImageFormat(final String imageFormat)
	{
		this.imageFormat = imageFormat;
	}

	@Override
	public String get(final ProductModel productModel)
	{
		final BaseSiteModel baseSiteModel = getCachedBestBaseSiteForCatalogVersionAndLanguage(productModel.getCatalogVersion(), getCurrentDataLanguage());
		if (baseSiteModel != null)
		{
			String url = getImpersonationService().executeInContext(createImpersonationContext(baseSiteModel), () ->
				getAmplienceProductImageUrlResolver().resolve(productModel)
			);

			// Will only return a non-null value when executed within zkoss (in the product cockpit, for instance)
			final Execution execution = Executions.getCurrent();
			if (execution != null)
			{
				url = execution.getScheme() + ":" + url;
			}

			// Use image format & backoffice transformation template & add the product type (not the resolved product type)
			return url + "&$" + getImageFormat() + "$&$backoffice$&productType=" + productModel.getItemtype();
		}
		return null;
	}

	@Override
	public void set(final ProductModel model, final String s)
	{
		throw new UnsupportedOperationException();
	}


	protected BaseSiteModel getCachedBestBaseSiteForCatalogVersionAndLanguage(final CatalogVersionModel catalogVersionModel, final LanguageModel languageModel)
	{
		final String key = catalogVersionModel.getPk().toString() + "." + languageModel.getPk().toString();
		BaseSiteModel baseSiteModel = null;

		// Try to lookup in cache map
		final String baseSiteUid = getCacheValue(key);
		if (baseSiteUid != null)
		{
			baseSiteModel = getBaseSiteService().getBaseSiteForUID(baseSiteUid);
		}

		if (baseSiteModel == null)
		{
			// Try to compute the best site to use
			baseSiteModel = getBestBaseSiteForCatalogVersionAndLanguage(catalogVersionModel, languageModel);
			if (baseSiteModel != null)
			{
				putCacheValue(key, baseSiteModel.getUid());
			}
		}

		return baseSiteModel;
	}

	protected BaseSiteModel getBestBaseSiteForCatalogVersionAndLanguage(final CatalogVersionModel catalogVersionModel, final LanguageModel languageModel)
	{
		final List<BaseSiteModel> allBaseSites = catalogVersionModel.getCatalog().getBaseStores().stream().
			map(BaseStoreModel::getCmsSites).
			filter(Objects::nonNull).
			flatMap(Collection::stream).
			filter(Objects::nonNull).
			collect(Collectors.toList());

		final Locale targetLocale = getCommonI18NService().getLocaleForLanguage(languageModel);
		BaseSiteModel bestSite = null;

		for (final BaseSiteModel baseSite : allBaseSites)
		{
			if (baseSite.getDefaultLanguage().equals(languageModel))
			{
				// Matched default language exactly
				return baseSite;
			}

			if (bestSite == null && !StringUtils.isBlank(baseSite.getLocale(targetLocale)))
			{
				// Site has an entry in its locale map for the language we are looking for - so its a possible match
				bestSite = baseSite;
			}
		}

		// If we haven't worked out a bestSite yet then just try the first one
		if (bestSite == null && !allBaseSites.isEmpty())
		{
			bestSite = allBaseSites.get(0);
		}
		return bestSite;
	}

	protected LanguageModel getCurrentDataLanguage()
	{
		final UISession currentSession = UISessionUtils.getCurrentSession();
		if (currentSession != null)
		{
			final String dataLanguageIsoCode = currentSession.getGlobalDataLanguageIso();
			if (dataLanguageIsoCode != null)
			{
				return getCommonI18NService().getLanguage(dataLanguageIsoCode);
			}
		}

		// Fallback to the current session language rather than returning null
		return getCommonI18NService().getCurrentLanguage();
	}

	protected ImpersonationContext createImpersonationContext(final BaseSiteModel site)
	{
		final ImpersonationContext impersonationContext = new ImpersonationContext();
		impersonationContext.setSite(site);
		impersonationContext.setLanguage(getCurrentDataLanguage());
		impersonationContext.setUser(getUserService().getAdminUser());
		return impersonationContext;
	}

	protected String getCacheValue(final String key)
	{
		final Map<String,String> cache = getSessionService().getAttribute(SESSION_KEY);
		if (cache != null)
		{
			return cache.get(key);
		}
		return null;
	}

	protected void putCacheValue(final String key, final String value)
	{
		final Map<String,String> cache = getSessionService().getAttribute(SESSION_KEY);
		final HashMap<String,String> newCache = cache != null ? new HashMap<>(cache) : new HashMap<>();
		newCache.put(key, value);
		getSessionService().setAttribute(SESSION_KEY, newCache);
	}

	protected void clearCache()
	{
		getSessionService().removeAttribute(SESSION_KEY);
	}
}
