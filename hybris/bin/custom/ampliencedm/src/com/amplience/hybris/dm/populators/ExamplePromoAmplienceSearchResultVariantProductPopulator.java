/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.populators;

import de.hybris.platform.basecommerce.enums.StockLevelStatus;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.variants.model.VariantProductModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Search Result Populator that adds Amplience product image URLs which also adds contextual promotion roundels.
 * This example displays 3 optional roundels on product image URLs. The 3 triggers are 'new', 'stock' and 'sale'.
 * <p>
 * The 'new' trigger is contrived as in this example all sample products are imported at the same time therefore
 * we are just taking the product's PK mod 3 and using that. This means that about a third of the products will
 * have this roundel.
 * <p>
 * The 'stock' trigger uses the lowStock condition in the product's StockData.
 * <p>
 * The 'sale' trigger looks at the categories a product is in to see if any of them are called 'sale'.
 * <p>
 * These 2 examples are contrived but show the potential functionality. For performance reasons the data for the
 * trigger conditions should be indexed into Solr and not require separate database lookups.
 */
public class ExamplePromoAmplienceSearchResultVariantProductPopulator extends AmplienceSearchResultVariantProductPopulator
{
	private CommerceCategoryService commerceCategoryService;

	protected CommerceCategoryService getCommerceCategoryService()
	{
		return commerceCategoryService;
	}

	@Required
	public void setCommerceCategoryService(final CommerceCategoryService commerceCategoryService)
	{
		this.commerceCategoryService = commerceCategoryService;
	}

	@Override
	protected String getProductImageUrlContextualQuery(final SearchResultValueData source, final ProductData target)
	{
		// We use the product code to lookup the product model. It would be preferable to have all these flags indexed
		// into the search result so that we don't need to lookup the product model
		final String productCode = this.<String>getValue(source, "code");
		final ProductModel productModel = getProductService().getProductForCode(productCode);
		
		
		if (productModel != null)
		{
			final boolean isNew = isProductNew(productModel);
			final boolean isLowStock = isProductLowStock(target);
			final boolean isOnSale = isProductOnSale(productModel);
			final boolean isMrhi = isProductMrhi(productModel);

			if (isMrhi) {
				final StringBuilder query = new StringBuilder("&$MRHI_v2_UK$");
				return query.toString();
			}
			else if (isNew || isLowStock || isOnSale)
			{
				final StringBuilder query = new StringBuilder("&$roundel$");
				if (isNew)
				{
					query.append("&new=1");
				}
				if (isLowStock)
				{
					query.append("&stock=1");
				}
				if (isOnSale)
				{
					query.append("&sale=1");
				}
				return query.toString();
			}
		}
		
		return "";
	}

	protected boolean isProductNew(final ProductModel product)
	{
		// Note that this is a contrived algorithm for making 1/3 of products 'new'
		return 0 == (product.getPk().getCounter() % 3);
	}

	protected boolean isProductLowStock(final ProductData target)
	{
		return StockLevelStatus.LOWSTOCK.equals(target.getStock().getStockLevelStatus());
	}

	protected boolean isProductOnSale(final ProductModel product)
	{
		return getCategoryPathsForProduct(product).stream()
			.anyMatch(categories -> categories.stream()
				.anyMatch(this::isSaleCategory));
	}

	protected boolean isProductMrhi(final ProductModel product)
	{
		return getCategoryPathsForProduct(product).stream()
			.anyMatch(categories -> categories.stream()
				.anyMatch(this::isMrhiCategory));
	}

	protected Collection<List<CategoryModel>> getCategoryPathsForProduct(final ProductModel product)
	{
		// Get the product & all base products and find the direct categories that the products are in
		final Set<CategoryModel> productCategories = new HashSet<>();
		collectProductWithBaseProducts(product).stream()
			.map(ProductModel::getSupercategories)
			.forEachOrdered(productCategories::addAll);

		// Filter to only supported categories, get the category paths for each product category and merge the collections into a single collection
		return productCategories.stream()
			.filter(this::isNavigationCategory)
			.flatMap(category -> getCommerceCategoryService().getPathsForCategory(category).stream())
			.collect(Collectors.toList());
	}

	protected List<ProductModel> collectProductWithBaseProducts(final ProductModel product)
	{
		final List<ProductModel> result = new ArrayList<>();
		result.add(product);

		ProductModel currentProduct = product;
		while (currentProduct instanceof VariantProductModel)
		{
			currentProduct = ((VariantProductModel) currentProduct).getBaseProduct();
			if (currentProduct == null)
			{
				break;
			}
			result.add(currentProduct);
		}

		return result;
	}

	protected boolean isNavigationCategory(final CategoryModel category)
	{
		// There are different types of category, and we only want to consider the navigation categories
		// so ignore subtypes for example ClassificationClass.
		return CategoryModel._TYPECODE.equals(category.getItemtype());
	}

	protected boolean isSaleCategory(final CategoryModel category)
	{
		return category.getCode().contains("sale");
	}

	protected boolean isMrhiCategory(final CategoryModel category)
	{
		return category.getCode().contains("hairandbeauty");
	}
}
