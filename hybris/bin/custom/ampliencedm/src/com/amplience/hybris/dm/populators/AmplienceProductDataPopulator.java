/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.populators;

import com.amplience.hybris.dm.data.AmplienceProductData;
import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.basecommerce.enums.StockLevelStatus;
import de.hybris.platform.commercefacades.product.data.BaseOptionData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.data.VariantOptionQualifierData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.site.BaseSiteService;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Populate the AmplienceProductData from the ProductData.
 * Used as part of the response to the ecommBridge.site.getProduct.
 */
public class AmplienceProductDataPopulator implements Populator<ProductData, AmplienceProductData>
{
	private L10NService l10nService;
	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;
	private BaseSiteService baseSiteService;

	protected L10NService getL10nService()
	{
		return l10nService;
	}

	@Required
	public void setL10nService(final L10NService l10nService)
	{
		this.l10nService = l10nService;
	}

	protected SiteBaseUrlResolutionService getSiteBaseUrlResolutionService()
	{
		return siteBaseUrlResolutionService;
	}

	@Required
	public void setSiteBaseUrlResolutionService(final SiteBaseUrlResolutionService siteBaseUrlResolutionService)
	{
		this.siteBaseUrlResolutionService = siteBaseUrlResolutionService;
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

	// ---------

	/**
	 * Populate the AmplienceProductData from the ProductData.
	 *
	 * @param source the source product data
	 * @param target the target AmplienceProductData
	 */
	@Override
	public void populate(final ProductData source, final AmplienceProductData target)
	{
		target.setProductID(source.getCode());
		target.setSku(source.getCode());
		target.setName(source.getName());
		target.setDescription(source.getSummary());
		target.setDescriptionLong(source.getDescription());

		populateUrl(source, target);
		populateImages(source, target);
		populateReviews(target);
		populatePrice(source, target);
		populateAvailability(source, target);
		populateCustom(source, target);
	}

	protected void populateUrl(final ProductData source, final AmplienceProductData target)
	{
		final String productUrl = getSiteBaseUrlResolutionService().getWebsiteUrlForSite(getBaseSiteService().getCurrentBaseSite(), false, source.getUrl());
		target.setUrl(productUrl);
	}

	protected void populateImages(final ProductData source, final AmplienceProductData target)
	{
		final Collection<ImageData> images = source.getImages();
		if (images != null)
		{
			for (final ImageData image : images)
			{
				populateFromImage(image, target);

				if (target.getImage() != null && target.getThumbnail() != null)
				{
					break;
				}
			}
		}
	}

	protected void populateFromImage(final ImageData image, final AmplienceProductData target)
	{
		if (ImageDataType.PRIMARY.equals(image.getImageType()))
		{
			if ("product".equals(image.getFormat()))
			{
				target.setImage(image.getUrl());
			}

			if ("thumbnail".equals(image.getFormat()))
			{
				target.setThumbnail(image.getUrl());
			}
		}
	}

	protected void populateReviews(final AmplienceProductData target)
	{
		// Put review data here
		target.setRatingValue(null);
		target.setRatingsImage(null);
	}

	protected void populatePrice(final ProductData source, final AmplienceProductData target)
	{
		final PriceData price = source.getPrice();
		if (price != null)
		{
			if (price.getValue() != null)
			{
				target.setPrice(Double.valueOf(price.getValue().doubleValue()));
				target.setPriceFormatted(price.getFormattedValue());
			}
			target.setPriceCurrency(price.getCurrencyIso());
			target.setPriceWas(null);
		}
	}

	protected void populateAvailability(final ProductData source, final AmplienceProductData target)
	{
		final StockData stock = source.getStock();
		if (stock != null)
		{
			final StockLevelStatus stockLevelStatus = stock.getStockLevelStatus();
			if (stockLevelStatus != null)
			{
				final String availability = getL10nService().getLocalizedString("AmplienceProductDataPopulator.stock.availability." + stockLevelStatus.getCode());
				target.setAvailability(availability);
			}
		}
	}

	protected void populateCustom(final ProductData source, final AmplienceProductData target)
	{
		final HashMap<String, String> custom = new HashMap<>();
		target.setCustom(custom);

		populateCustomMap(source, custom);
	}

	protected void populateCustomMap(final ProductData source, final Map<String, String> custom)
	{
		// Extract the list of genders for the product
		final Double averageRating = source.getAverageRating();
		if (averageRating != null)
		{
			// Get the rating and ignore very small values
			final double rating = averageRating.doubleValue();
			if (rating > 0.01)
			{
				custom.put("rating", averageRating.toString());
			}
		}

		// Extract the base option qualifiers for the product
		final List<BaseOptionData> baseOptions = source.getBaseOptions();
		if (baseOptions != null)
		{
			for (final BaseOptionData baseOption : baseOptions)
			{
				populateCustomMapFromBaseOptionData(custom, baseOption);
			}
		}
	}

	protected void populateCustomMapFromBaseOptionData(final Map<String, String> custom, final BaseOptionData baseOption)
	{
		final VariantOptionData selected = baseOption.getSelected();
		if (selected != null)
		{
			final Collection<VariantOptionQualifierData> variantOptionQualifiers = selected.getVariantOptionQualifiers();
			if (variantOptionQualifiers != null)
			{
				for (final VariantOptionQualifierData variantOptionQualifier : variantOptionQualifiers)
				{
					custom.put(variantOptionQualifier.getQualifier(), variantOptionQualifier.getValue());
				}
			}
		}
	}
}
