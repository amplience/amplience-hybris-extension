/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.addon.web.interceptors.beforeview;

import com.amplience.hybris.dm.config.AmplienceConfigService;
import com.amplience.hybris.dm.format.AmplienceImageFormatStrategy;
import com.amplience.hybris.dm.localization.AmplienceLocaleStringStrategy;
import de.hybris.platform.addonsupport.interceptors.BeforeViewHandlerAdaptee;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BeforeViewHandler that adds Amplience resources to the model for use by views and tags.
 * <p>
 * Adds the following the model:
 * - Amplience account configuration
 * - The session locale (including fallbacks) in Amplience format
 * - The image format value to use when requesting Amplience images
 * <p>
 * It also changes the view used to render the quickViewPopup to one provided by the amplience addon.
 */
public class AmplienceBeforeViewHandlerAdaptee implements BeforeViewHandlerAdaptee
{
	private AmplienceConfigService amplienceConfigService;
	private AmplienceLocaleStringStrategy amplienceLocaleStringStrategy;
	private AmplienceImageFormatStrategy amplienceImageFormatStrategy;

	protected AmplienceConfigService getAmplienceConfigService()
	{
		return amplienceConfigService;
	}

	@Required
	public void setAmplienceConfigService(final AmplienceConfigService amplienceConfigService)
	{
		this.amplienceConfigService = amplienceConfigService;
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

	protected AmplienceImageFormatStrategy getAmplienceImageFormatStrategy()
	{
		return amplienceImageFormatStrategy;
	}

	public void setAmplienceImageFormatStrategy(final AmplienceImageFormatStrategy amplienceImageFormatStrategy)
	{
		this.amplienceImageFormatStrategy = amplienceImageFormatStrategy;
	}

	// ----------

	@Override
	public String beforeView(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model, final String viewName) throws Exception
	{
		addAmplienceConfigToModel(model);
		addLocaleToModel(model);
		addFormatToModel(model);

		return adjustView(viewName);
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

	protected void addAmplienceConfigToModel(final ModelMap model)
	{
		model.addAttribute("amplienceConfig", getAmplienceConfigService().getConfigForCurrentSite());
	}

	protected void addLocaleToModel(final ModelMap model)
	{
		model.addAttribute("amplienceLocales", getAmplienceLocaleStringStrategy().getCurrentLocaleString());
	}

	protected void addFormatToModel(final ModelMap model)
	{
		model.addAttribute("amplienceImageFormat", getAmplienceImageFormatStrategy().getImageFormat());
	}
}
