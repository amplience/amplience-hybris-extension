/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.addon.web.interceptors.beforeview;

import com.amplience.hybris.dm.addon.constants.AmplienceDmAddonConstants;
import com.amplience.hybris.dm.config.AmplienceConfigService;
import com.amplience.hybris.dm.localization.AmplienceLocaleStringStrategy;
import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.data.RequestContextData;
import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.acceleratorservices.util.SpringHelper;
import de.hybris.platform.addonsupport.interceptors.BeforeViewHandlerAdaptee;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.user.data.PrincipalData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * BeforeViewHandler that Amplience resources to the model for use by views and tags.
 * <p>
 * Adds the following the model:
 * - External URL resources to the lists of CSS and JS resources
 * - Amplience account configuration
 * - The session currency in format expected by the Amplience ecomm bridge
 * - The session local (including fallbacks) in Amplience format
 * - The category code and SEO path if a category page is being viewed
 * - The instagram authentication callback URL
 * - The customer ID from the current order or current session
 * <p>
 * It also changes the view used to render the quickViewPopup to one provided by the amplience addon.
 */
public class AmplienceBeforeViewHandler implements BeforeViewHandlerAdaptee
{
	private Supplier<DecimalFormat> currencyFormatSupplier;
	private SiteConfigService siteConfigService;
	private AmplienceConfigService amplienceConfigService;
	private CategoryService categoryService;
	private BaseSiteService baseSiteService;
	private AmplienceLocaleStringStrategy amplienceLocaleStringStrategy;
	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;
	private UserService userService;

	protected Supplier<DecimalFormat> getCurrencyFormatSupplier()
	{
		return currencyFormatSupplier;
	}

	@Required
	public void setCurrencyFormatSupplier(final Supplier<DecimalFormat> currencyFormatSupplier)
	{
		this.currencyFormatSupplier = currencyFormatSupplier;
	}

	protected SiteConfigService getSiteConfigService()
	{
		return siteConfigService;
	}

	@Required
	public void setSiteConfigService(final SiteConfigService siteConfigService)
	{
		this.siteConfigService = siteConfigService;
	}

	protected AmplienceConfigService getAmplienceConfigService()
	{
		return amplienceConfigService;
	}

	@Required
	public void setAmplienceConfigService(final AmplienceConfigService amplienceConfigService)
	{
		this.amplienceConfigService = amplienceConfigService;
	}

	protected CategoryService getCategoryService()
	{
		return categoryService;
	}

	@Required
	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
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

	protected AmplienceLocaleStringStrategy getAmplienceLocaleStringStrategy()
	{
		return amplienceLocaleStringStrategy;
	}

	@Required
	public void setAmplienceLocaleStringStrategy(final AmplienceLocaleStringStrategy amplienceLocaleStringStrategy)
	{
		this.amplienceLocaleStringStrategy = amplienceLocaleStringStrategy;
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

	protected UserService getUserService()
	{
		return userService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

// ----------

	@Override
	public String beforeView(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model, final String viewName) throws Exception
	{
		updateModel(request, model);

		return adjustView(viewName);
	}

	protected void updateModel(final HttpServletRequest request, final ModelMap model)
	{
		addOrAppendListAttribute(model, getCommonCssPathKey(), getCssUrls());
		addOrAppendListAttribute(model, getJavaScriptPathsKey(), getJsUrls());

		addAmplienceConfigToModel(model);
		addCurrencyToModel(model);
		addLocaleToModel(model);

		addCategoryInfoToModel(request, model);

		addInstagramToModel(model);
		addCustomerIdToModel(model);
	}

	protected String adjustView(final String viewName)
	{
		// Replace the quick view popup template path here to use the template from
		// the ampliencedmaddon instead. This supports replacing the product image
		// gallery with an amplience viewer.
		// If this behaviour is not required then override this method to return
		// the viewName without any modification.
		if ("fragments/product/quickViewPopup".equals(viewName))
		{
			return "addon:/ampliencedmaddon/fragments/product/quickViewPopup";
		}
		return viewName;
	}

	protected String getCommonCssPathKey()
	{
		return "addOnCommonCssPaths";
	}

	protected String getJavaScriptPathsKey()
	{
		return "addOnJavaScriptPaths";
	}

	protected List<String> getCssUrls()
	{
		return getSplitPropertyValues(AmplienceDmAddonConstants.EXTENSIONNAME + ".css.urls");
	}

	protected List<String> getJsUrls()
	{
		return getSplitPropertyValues(AmplienceDmAddonConstants.EXTENSIONNAME + ".js.urls");
	}

	protected void addAmplienceConfigToModel(final ModelMap model)
	{
		model.addAttribute("amplienceConfig", getAmplienceConfigService().getConfigForCurrentSite());
	}

	protected void addCurrencyToModel(final ModelMap model)
	{
		final DecimalFormat currencyFormat = getCurrencyFormatSupplier().get();

		model.addAttribute("currencyPrefix", currencyFormat.getPositivePrefix());
		model.addAttribute("currencySuffix", currencyFormat.getPositiveSuffix());
	}

	protected void addLocaleToModel(final ModelMap model)
	{
		model.addAttribute("amplienceLocales", getAmplienceLocaleStringStrategy().getCurrentLocaleString());
	}

	protected void addCategoryInfoToModel(final HttpServletRequest request, final ModelMap model)
	{
		final RequestContextData requestContextData = getRequestContextData(request);
		if (requestContextData != null && requestContextData.getCategory() != null)
		{
			model.addAttribute("categoryCode", requestContextData.getCategory().getCode());
			model.addAttribute("categoryPath", getCategoryPath(requestContextData.getCategory()));
		}
	}

	protected String getCategoryPath(final CategoryModel category)
	{
		final Collection<List<CategoryModel>> paths = getCategoryService().getPathsForCategory(category);
		if (!paths.isEmpty())
		{
			return paths.iterator().next().stream().map(CategoryModel::getName).collect(Collectors.joining("/"));
		}
		return "";
	}

	protected void addInstagramToModel(final ModelMap model)
	{
		final BaseSiteModel currentBaseSite = getBaseSiteService().getCurrentBaseSite();
		final String instagramAuthCallbackUrl = getSiteBaseUrlResolutionService().getWebsiteUrlForSite(currentBaseSite, true, "/misc/amplience/instagram.html");
		model.addAttribute("instagramAuthCallbackUrl", instagramAuthCallbackUrl);
	}

	protected void addCustomerIdToModel(final ModelMap model)
	{
		final CustomerModel anonymousUser = getUserService().getAnonymousUser();

		// Look on the order first
		final CustomerModel customerForOrder = getCustomerForOrder(model);
		if (customerForOrder != null)
		{
			if (!anonymousUser.equals(customerForOrder))
			{
				model.addAttribute("customerId", customerForOrder.getCustomerID());
			}
			return;
		}

		// Look at the session user
		final UserModel currentUser = getUserService().getCurrentUser();
		if (currentUser instanceof CustomerModel && !anonymousUser.equals(currentUser))
		{
			model.addAttribute("customerId", ((CustomerModel) currentUser).getCustomerID());
		}
	}

	protected CustomerModel getCustomerForOrder(final ModelMap model)
	{
		if (model.containsAttribute("orderData"))
		{
			final Object orderData = model.get("orderData");
			if (orderData instanceof OrderData)
			{
				final PrincipalData orderUser = ((OrderData) orderData).getUser();
				if (orderUser != null)
				{
					return getUserService().getUserForUID(orderUser.getUid(), CustomerModel.class);
				}
			}
		}
		return null;
	}

	protected List<String> getSplitPropertyValues(final String key)
	{
		final String propertyValue = getSiteConfigService().getProperty(key);
		if (propertyValue != null)
		{
			return Arrays.asList(propertyValue.split(";"));
		}
		return Collections.emptyList();
	}

	protected <E> void addOrAppendListAttribute(final ModelMap model, final String key, final List<E> values)
	{
		if (model.containsAttribute(key))
		{
			final Object modelEntry = model.get(key);
			if (modelEntry instanceof List)
			{
				final List<E> existingValueList = (List<E>) modelEntry;
				existingValueList.addAll(values);
			}
		}
		else
		{
			model.addAttribute(key, values);
		}
	}

	protected RequestContextData getRequestContextData(final HttpServletRequest request)
	{
		return SpringHelper.getSpringBean(request, "requestContextData", RequestContextData.class, true);
	}
}
