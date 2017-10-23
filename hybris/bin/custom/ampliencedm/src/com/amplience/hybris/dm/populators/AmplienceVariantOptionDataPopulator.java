/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.data.VariantOptionQualifierData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.variants.model.VariantProductModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.Map;

/**
 * Accelerator specific variant option data converter implementation.
 */
public class AmplienceVariantOptionDataPopulator extends DefaultVariantOptionDataPopulator
{
	private TypeService typeService;
	private Map<String, String> variantAttributeMapping;
	private UrlResolver<ProductModel> defaultProductImageUrlResolver;
	private Map<String, UrlResolver<ProductModel>> imageFormatProductImageUrlResolverMap;

	/**
	 * Accelerator specific variant option data converter implementation.
	 *
	 * @param source the source variant product model
	 * @param target the target variant option data
	 */
	@Override
	public void populate(final VariantProductModel source, final VariantOptionData target)
	{
		super.populate(source, target);

		final ComposedTypeModel productType = getTypeService().getComposedTypeForClass(source.getClass());
		for (final VariantOptionQualifierData variantOptionQualifier : target.getVariantOptionQualifiers())
		{
			final String imageFormat = lookupImageFormat(productType, variantOptionQualifier.getQualifier());
			if (imageFormat != null)
			{
				final UrlResolver<ProductModel> formatUrlResolver = getImageFormatProductImageUrlResolverMap().get(imageFormat);

				final String imageUrl = formatUrlResolver != null ? formatUrlResolver.resolve(source) : getDefaultProductImageUrlResolver().resolve(source);
				if (imageUrl != null)
				{
					final ImageData imageData = new ImageData();
					imageData.setFormat(imageFormat);
					imageData.setImageType(ImageDataType.PRIMARY);
					imageData.setUrl("https:" + imageUrl + "&$" + imageFormat + "$");
					imageData.setAltText(source.getName());

					variantOptionQualifier.setImage(imageData);
				}
			}
		}
	}

	protected String lookupImageFormat(final ComposedTypeModel productType, final String attributeQualifier)
	{
		if (productType == null)
		{
			return null;
		}

		// Lookup the image format mapping
		final String key = productType.getCode() + "." + attributeQualifier;
		final String imageFormat = getVariantAttributeMapping().get(key);

		if (imageFormat != null)
		{
			return imageFormat;
		}

		// Try super type of not found for this type

		if (VariantProductModel._TYPECODE.equals(productType.getCode()))
		{
			// If the type we have reached is VariantProduct then there is no point continuing up the type hierarchy
			// the super types Product, GenericItem, LocalizableItem, ExtensibleItem, and Item can't hold variant attributes
			// therefore the attribute is not found on this type.
			return null;
		}

		// Ok, now try the super type

		return lookupImageFormat(productType.getSuperType(), attributeQualifier);
	}


	@Override
	protected TypeService getTypeService()
	{
		return typeService;
	}

	@Override
	@Required
	public void setTypeService(final TypeService typeService)
	{
		this.typeService = typeService;
	}

	protected Map<String, String> getVariantAttributeMapping()
	{
		return variantAttributeMapping;
	}

	@Required
	public void setVariantAttributeMapping(final Map<String, String> variantAttributeMapping)
	{
		this.variantAttributeMapping = variantAttributeMapping;
	}

	protected UrlResolver<ProductModel> getDefaultProductImageUrlResolver()
	{
		return defaultProductImageUrlResolver;
	}

	@Required
	public void setDefaultProductImageUrlResolver(final UrlResolver<ProductModel> defaultProductImageUrlResolver)
	{
		this.defaultProductImageUrlResolver = defaultProductImageUrlResolver;
	}

	protected Map<String, UrlResolver<ProductModel>> getImageFormatProductImageUrlResolverMap()
	{
		return imageFormatProductImageUrlResolverMap;
	}

	@Required
	public void setImageFormatProductImageUrlResolverMap(final Map<String, UrlResolver<ProductModel>> imageFormatProductImageUrlResolverMap)
	{
		this.imageFormatProductImageUrlResolverMap = imageFormatProductImageUrlResolverMap;
	}
}
