package com.amplience.hybris.dm.search.provider;

import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.VariantProductStockLevelStatusValueProvider;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.Collection;
import java.util.List;

/**
 * Fix an issues with the hybris v6 ProductStockLevelStatusValueProvider.
 * <p>
 * The SolrJ client v6 indexes unknown types in a different way to the previous version which
 * breaks the stockLevelStatus field. Here we index the string value rather than the StockLevelStatus instance.
 */
public class FixedVariantProductStockLevelStatusValueProvider extends VariantProductStockLevelStatusValueProvider
{
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
}
