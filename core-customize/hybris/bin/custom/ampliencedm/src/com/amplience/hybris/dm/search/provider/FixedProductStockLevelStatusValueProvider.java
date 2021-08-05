/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.search.provider;

import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.ProductStockLevelStatusValueProvider;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.AliasRegistry;

import java.util.Collection;
import java.util.List;

/**
 * Fix a couple of issues with the hybris v6 ProductStockLevelStatusValueProvider.
 * <p>
 * 1) The SolrJ client v6 indexes unknown types in a different way to the previous version which
 * breaks the stockLevelStatus field. Here we index the string value rather than the StockLevelStatus instance.
 * <p>
 * 2) The commerceservices-spring-solrfacetsearch.xml sets up an alias for this bean but UI does not support using aliases
 * but requires the canonical ID of the bean.
 */
public class FixedProductStockLevelStatusValueProvider extends ProductStockLevelStatusValueProvider implements InitializingBean, BeanFactoryAware
{
	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(final BeanFactory beanFactory)
	{
		this.beanFactory = beanFactory;
	}

	@Override
	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final Object value)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			// Call value.toString() to index a string rather than an Object
			fieldValues.add(new FieldValue(fieldName, value.toString()));
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (beanFactory instanceof AliasRegistry)
		{
			// Remove the spring alias 'productStockLevelStatusValueProvider' because we have actually
			// defined a new bean called 'productStockLevelStatusValueProvider' and we don't want spring
			// to think that the name refers to an alias.
			((AliasRegistry) beanFactory).removeAlias("productStockLevelStatusValueProvider");
		}
	}
}
