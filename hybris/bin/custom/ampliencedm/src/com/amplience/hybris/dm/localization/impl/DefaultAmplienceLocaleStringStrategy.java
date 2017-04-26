/*
 * Copyright (c) 2016 Amplience
 */

package com.amplience.hybris.dm.localization.impl;

import com.amplience.hybris.dm.localization.AmplienceLocaleStringStrategy;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.site.BaseSiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Default implementation of AmplienceLocaleStringStrategy.
 * <p>
 * Gets the current Amplience locale string for the current site and language.
 * The Amplience locale string is a list of locales which is built from the hybris
 * fallback languages.
 * <p>
 * The locale string is cached per site & language so that it does not need to be
 * regenerated for each request.
 */
public class DefaultAmplienceLocaleStringStrategy implements AmplienceLocaleStringStrategy
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultAmplienceLocaleStringStrategy.class);
	private final Map<String, String> localeStrings = new HashMap<>();

	private CommonI18NService commonI18NService;
	private BaseSiteService baseSiteService;

	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	@Required
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	@Required
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	// ----

	@Override
	public String getCurrentLocaleString()
	{
		return getLocaleString(getBaseSiteService().getCurrentBaseSite(), getCommonI18NService().getCurrentLanguage());
	}

	protected String getLocaleString(final BaseSiteModel site, final LanguageModel language)
	{
		return localeStrings.computeIfAbsent(getKey(site, language), k -> generateLocaleString(site, language));
	}

	protected String getKey(final BaseSiteModel site, final LanguageModel language)
	{
		return site.getPk().toString() + "." + language.getPk().toString();
	}

	protected String generateLocaleString(final BaseSiteModel site, final LanguageModel language)
	{
		final List<String> localesList = addLanguageLocales(site, language);
		final String localeString = localesList.stream()
			.map(s -> s.replace('_', '-'))
			.map(s -> s.length() == 2 ? s + "-*" : s)
			.collect(Collectors.joining(",", "", ",*"));
		LOG.debug("Generated locale string: {}", localeString);
		return localeString;
	}

	protected List<String> addLanguageLocales(final BaseSiteModel site, final LanguageModel language)
	{
		return addLanguageLocales(site, language, new HashSet<>(), new ArrayList<>());
	}

	protected List<String> addLanguageLocales(final BaseSiteModel site, final LanguageModel language, final Set<LanguageModel> seenLanguages, final List<String> locales)
	{
		// Track the hybris languages we have seen to prevent loops & repeats
		// Several languages may have the same fallback and there is no value in repeating a language in the locale list
		seenLanguages.add(language);

		final Locale localeForLanguage = getCommonI18NService().getLocaleForLanguage(language);
		if (localeForLanguage != null)
		{
			final String siteLocale = site.getLocale(localeForLanguage);
			if (siteLocale != null && !siteLocale.isEmpty() && !locales.contains(siteLocale))
			{
				locales.add(siteLocale);
			}

			final String localeForLanguageStr = localeForLanguage.toString();
			if (!locales.contains(localeForLanguageStr))
			{
				locales.add(localeForLanguageStr);
			}
		}

		if (!locales.contains(language.getIsocode()))
		{
			locales.add(language.getIsocode());
		}

		final List<LanguageModel> fallbackLanguages = language.getFallbackLanguages();
		if (fallbackLanguages != null)
		{
			for (final LanguageModel fallbackLanguage : fallbackLanguages)
			{
				// Check if we have already handled this language
				if (!seenLanguages.contains(fallbackLanguage))
				{
					addLanguageLocales(site, fallbackLanguage, seenLanguages, locales);
				}
			}
		}

		return locales;
	}

	/**
	 * Clear the locale strings cache
	 */
	public void reset()
	{
		localeStrings.clear();
	}
}
