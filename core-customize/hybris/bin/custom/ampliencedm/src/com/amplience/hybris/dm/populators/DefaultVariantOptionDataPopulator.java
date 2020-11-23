/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.populators;

import de.hybris.platform.commercefacades.product.converters.populator.VariantOptionDataPopulator;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.data.VariantOptionQualifierData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.variants.model.VariantAttributeDescriptorModel;
import de.hybris.platform.variants.model.VariantProductModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Extends the VariantOptionDataPopulator to limit the variant options which are inherited from the base product.
 */
public class DefaultVariantOptionDataPopulator extends VariantOptionDataPopulator
{
	private TypeService typeService;

	protected TypeService getTypeService()
	{
		return typeService;
	}

	@Required
	public void setTypeService(final TypeService typeService)
	{
		this.typeService = typeService;
	}

	/**
	 * Populate the VariantOptionData for a VariantProductModel
	 *
	 * @param source the source VariantProductModel
	 * @param target the target VariantOptionData
	 */
	@Override
	public void populate(final VariantProductModel source, final VariantOptionData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		if (source.getBaseProduct() != null)
		{
			// Call getVariantAttributeDescriptorModels rather than getVariantsService().getVariantAttributesForVariantType()
			final List<VariantAttributeDescriptorModel> descriptorModels = getVariantAttributeDescriptorModels(source);

			final Collection<VariantOptionQualifierData> variantOptionQualifiers = new ArrayList<>();
			for (final VariantAttributeDescriptorModel descriptorModel : descriptorModels)
			{
				// Create the variant qualifier
				final VariantOptionQualifierData variantOptionQualifier = new VariantOptionQualifierData();
				final String qualifier = descriptorModel.getQualifier();
				variantOptionQualifier.setQualifier(qualifier);
				variantOptionQualifier.setName(descriptorModel.getName());

				// Lookup the value
				final Object variantAttributeValue = lookupVariantAttributeName(source, qualifier);
				variantOptionQualifier.setValue(variantAttributeValue == null ? "" : variantAttributeValue.toString());

				// Add to list of variants
				variantOptionQualifiers.add(variantOptionQualifier);
			}

			target.setVariantOptionQualifiers(variantOptionQualifiers);
			target.setCode(source.getCode());
			target.setUrl(getProductModelUrlResolver().resolve(source));
			target.setStock(getStockConverter().convert(source));

			final PriceDataType priceType;
			final PriceInformation info;
			if (CollectionUtils.isEmpty(source.getVariants()))
			{
				priceType = PriceDataType.BUY;
				info = getCommercePriceService().getWebPriceForProduct(source);
			}
			else
			{
				priceType = PriceDataType.FROM;
				info = getCommercePriceService().getFromPriceForProduct(source);
			}

			if (info != null)
			{
				final PriceData priceData = getPriceDataFactory().create(priceType, BigDecimal.valueOf(info.getPriceValue().getValue()), info.getPriceValue().getCurrencyIso());
				target.setPriceData(priceData);
			}
		}
	}

	/**
	 * Get the variant attribute descriptors for the variant product.
	 * Exclude any variant attributes that are duplicates of variant attributes provided by the base product(s).
	 * <p>
	 * The Apparel data model is slightly odd because the ApparelSizeVariantProduct extends the ApparelStyleVariantProduct
	 * and therefore an ApparelSizeVariantProduct has a _style_ attribute and potentially has a base product which
	 * is a ApparelStyleVariantProduct which also has a _style_ attribute. This allows the Apparel data model to
	 * be constructed very flexibly but it does mean that the _style_ attribute could be duplicated when traversing
	 * the variant hierarchy for a product. This method filters out those duplicate occurrences.
	 */
	protected List<VariantAttributeDescriptorModel> getVariantAttributeDescriptorModels(final VariantProductModel source)
	{
		final List<VariantAttributeDescriptorModel> result = new ArrayList<>();

		final List<VariantAttributeDescriptorModel> attributes = getVariantsService().getVariantAttributesForVariantType(source.getBaseProduct().getVariantType());
		for (final VariantAttributeDescriptorModel attribute : attributes)
		{
			if (attribute.getEnclosingType().equals(attribute.getDeclaringEnclosingType()) ||
				!isBaseProductOfType(source.getBaseProduct(), attribute.getDeclaringEnclosingType()))
			{
				// Found it where it is declared, so it must be ok, or
				// It isn't on any of our base products so we need to add it here
				result.add(attribute);
			}
		}

		return result;
	}

	/**
	 * Check if a product is a sub-type of the specified type, or if any of its base products are.
	 *
	 * @param baseProduct the product to check, and who's base products to check
	 * @param matchType   the type to match against the product
	 * @return true if the product or any of its base products matches the type specified
	 */
	protected boolean isBaseProductOfType(final ProductModel baseProduct, final ComposedTypeModel matchType)
	{
		ProductModel currentProduct = baseProduct;
		while (currentProduct != null)
		{
			final ComposedTypeModel baseProductType = getTypeService().getComposedTypeForCode(currentProduct.getItemtype());
			if (getTypeService().isAssignableFrom(matchType, baseProductType))
			{
				return true;
			}

			if (currentProduct instanceof VariantProductModel)
			{
				currentProduct = ((VariantProductModel) currentProduct).getBaseProduct();
			}
			else
			{
				currentProduct = null;
			}
		}

		return false;
	}
}
