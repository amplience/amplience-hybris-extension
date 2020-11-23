/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.accelerator.demo.product;

import com.amplience.hybris.dm.product.AmplienceProductResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.yacceleratorcore.model.ApparelProductModel;
import de.hybris.platform.yacceleratorcore.model.ApparelSizeVariantProductModel;
import de.hybris.platform.yacceleratorcore.model.ApparelStyleVariantProductModel;

import java.util.Collection;

/**
 * AmplienceProductResolver for the Apparel product model.
 * <p>
 * Finds the appropriate product in the product hierarchy that has
 * imagery. Typically this will be the ApparelStyleVariantProduct
 * however there are some ApparelProducts which are sold without
 * variations and therefore the ApparelProducts has the imagery.
 */
public class ApparelAmplienceProductResolver implements AmplienceProductResolver
{
	@Override
	public ProductModel resolveProduct(final ProductModel product)
	{
		if (product == null)
		{
			return null;
		}
		else if (!(product instanceof VariantProductModel) && product.getVariantType() == null)
		{
			// Not a variant and has no variant type, therefore try resolving to the product
			return product;
		}
		return resolveProductInHierarchy(product);
	}

	protected ProductModel resolveProductInHierarchy(final ProductModel product)
	{
		if (product instanceof ApparelSizeVariantProductModel)
		{
			return resolveApparelSizeVariantProduct((ApparelSizeVariantProductModel) product);
		}
		else if (product instanceof ApparelStyleVariantProductModel)
		{
			return resolveApparelStyleVariantProduct((ApparelStyleVariantProductModel) product);
		}
		else if (product instanceof ApparelProductModel)
		{
			return resolveApparelProduct((ApparelProductModel) product);
		}

		// Unknown just try resolving to the product
		return product;
	}

	protected ProductModel resolveApparelProduct(final ApparelProductModel product)
	{
		// Look at the variants
		final Collection<VariantProductModel> variants = product.getVariants();
		if (variants != null && !variants.isEmpty())
		{
			final VariantProductModel firstVariant = variants.iterator().next();

			if (firstVariant instanceof ApparelSizeVariantProductModel)
			{
				if (((ApparelSizeVariantProductModel) firstVariant).getStyle() != null)
				{
					// First variant is a Size not a Style, but it has a style set
					return firstVariant;
				}
			}
			else if (firstVariant instanceof ApparelStyleVariantProductModel)
			{
				// First variant is a style, lets go with that
				return firstVariant;
			}
		}
		return product;
	}

	protected ProductModel resolveApparelStyleVariantProduct(final ApparelStyleVariantProductModel product)
	{
		// If we were passed a Style product then that is probably the one to go with
		return product;
	}

	protected ProductModel resolveApparelSizeVariantProduct(final ApparelSizeVariantProductModel product)
	{
		// Look at base product
		final ProductModel baseProduct = product.getBaseProduct();

		if (baseProduct == null)
		{
			return product;
		}
		else if (baseProduct instanceof ApparelStyleVariantProductModel)
		{
			// Our base is a Style product, that is probably the right thing to go for
			return baseProduct;
		}
		else if (product.getStyle() != null)
		{
			// Base is not a Style product, and the Size has a style set, so lets go with the Size product
			return product;
		}
		// Looks like the base product is the right one to go with
		return baseProduct;
	}
}
