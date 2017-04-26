/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.util;

import de.hybris.platform.commercefacades.product.impl.DefaultPriceDataFactory;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.c2l.LanguageModel;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * CurrencyFormatSupplier creates a currency DecimalFormat for the current currency and locale.
 * Extend DefaultPriceDataFactory as it has the standard logic for creating a java currency formatter
 */
public class CurrencyFormatSupplier extends DefaultPriceDataFactory implements Supplier<DecimalFormat>
{
	@Override
	public DecimalFormat get()
	{
		final CurrencyModel currentCurrency = getCommonI18NService().getCurrentCurrency();
		final LanguageModel currentLanguage = getCommonI18NService().getCurrentLanguage();
		Locale locale = getCommerceCommonI18NService().getLocaleForLanguage(currentLanguage);
		if (locale == null)
		{
			// Fallback to session locale
			locale = getI18NService().getCurrentLocale();
		}

		return (DecimalFormat) createCurrencyFormat(locale, currentCurrency);
	}
}
