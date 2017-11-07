/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.product.AmplienceSeoImageNameStrategy;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.commerceservices.url.impl.DefaultProductModelUrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of AmplienceSeoImageNameStrategy.
 * <p>
 * Builds an SEO path using the categories the product it in.
 */
public class DefaultAmplienceSeoImageNameStrategy implements AmplienceSeoImageNameStrategy
{
	private static final Logger LOG = Logger.getLogger(DefaultAmplienceSeoImageNameStrategy.class);
	private final String CACHE_KEY = DefaultProductModelUrlResolver.class.getName();

	private CommerceCategoryService commerceCategoryService;
	private ThreadContextService threadContextService;

	protected CommerceCategoryService getCommerceCategoryService()
	{
		return commerceCategoryService;
	}

	@Required
	public void setCommerceCategoryService(final CommerceCategoryService commerceCategoryService)
	{
		this.commerceCategoryService = commerceCategoryService;
	}

	protected ThreadContextService getThreadContextService()
	{
		return threadContextService;
	}

	@Required
	public void setThreadContextService(final ThreadContextService threadContextService)
	{
		this.threadContextService = threadContextService;
	}

	// ------

	@Override
	public String getSeoName(final ProductModel product)
	{
		final String key = getKey(product);
		if (key == null)
		{
			// No key, just return the value
			return resolveInternal(product);
		}

		// Lookup the cached value by key
		final String cachedValue = getThreadContextService().getAttribute(key);
		if (cachedValue != null)
		{
			return cachedValue;
		}

		final String url = resolveInternal(product);

		// Store the value in the thread cache
		getThreadContextService().setAttribute(key, url);

		return url;
	}

	protected String getKey(final ProductModel source)
	{
		return CACHE_KEY + "." + source.getPk().toString();
	}

	protected String resolveInternal(final ProductModel product)
	{
		final String path = buildPathString(getCategoryPath(product));
		final String name = urlSafe(product.getName());
		return path + "/" + name;
	}

	protected String buildPathString(final List<CategoryModel> path)
	{
		if (path == null || path.isEmpty())
		{
			return "c"; // Default category part of path when missing category
		}

		final StringBuilder result = new StringBuilder();

		for (int i = 0; i < path.size(); i++)
		{
			if (i != 0)
			{
				result.append('/');
			}
			result.append(urlSafe(path.get(i).getName()));
		}

		return result.toString();
	}

	protected List<CategoryModel> getCategoryPath(final ProductModel product)
	{
		final CategoryModel category = getPrimaryCategoryForProduct(product);
		if (category != null)
		{
			return getCategoryPath(category);
		}
		return Collections.emptyList();
	}

	protected CategoryModel getPrimaryCategoryForProduct(final ProductModel product)
	{
		// Get the first super-category from the product that isn't a classification category
		for (final CategoryModel category : product.getSupercategories())
		{
			if (!(category instanceof ClassificationClassModel))
			{
				return category;
			}
		}
		return null;
	}

	protected List<CategoryModel> getCategoryPath(final CategoryModel category)
	{
		final Collection<List<CategoryModel>> paths = getCommerceCategoryService().getPathsForCategory(category);
		// Return first - there will always be at least 1
		return paths.iterator().next();
	}

	/**
	 * Encode string with UTF8 encoding and then convert a string into a URL safe version of the string. Only upper and
	 * lower case letters and numbers are retained. All other characters are replaced by a hyphen (-).
	 *
	 * @param text the text to sanitize
	 * @return the safe version of the text
	 */
	protected String urlSafe(final String text)
	{
		if (text == null || text.isEmpty())
		{
			return "";
		}

		String encodedText;
		try
		{
			encodedText = URLEncoder.encode(text, "utf-8");
		}
		catch (final UnsupportedEncodingException encodingException)
		{
			encodedText = text;
			LOG.error(encodingException.getMessage());
		}

		// Cleanup the text
		String cleanedText = encodedText;
		cleanedText = cleanedText.replaceAll("%2F", "/");
		cleanedText = cleanedText.replaceAll("[^%A-Za-z0-9\\-]+", "-");
		return cleanedText;
	}
}
