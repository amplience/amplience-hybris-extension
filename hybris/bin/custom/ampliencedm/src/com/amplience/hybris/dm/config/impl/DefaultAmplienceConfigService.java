/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.config.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import com.amplience.hybris.dm.config.AmplienceConfigService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.site.BaseSiteService;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Required;

/**
 * Default implementation of AmplienceConfigService.
 *
 * Loads the config from attributes on the BaseSite. Default values loaded from the following configuration properties.
 *
 * - amplience.dm.config.accountIdentifier
 * - amplience.dm.config.analyticsCollectorId
 * - amplience.dm.config.imageHostname
 * - amplience.dm.config.contentHostname
 * - amplience.dm.config.analyticsHostname
 * - amplience.dm.config.scriptHostname
 */
public class DefaultAmplienceConfigService implements AmplienceConfigService
{
	private BaseSiteService baseSiteService;
	private ConfigurationService configurationService;


	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	@Required
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	protected ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	@Required
	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	// -----------

	@Override
	public AmplienceConfigData getConfigForSite(final BaseSiteModel site)
	{
		final AmplienceConfigData result = new AmplienceConfigData();

		final Configuration configuration = getConfigurationService().getConfiguration();

		if (site != null)
		{
			// Load config from the CMS Site
			result.setAccountIdentifier(site.getAmplienceAccountIdentifier());
			result.setAnalyticsCollectorId(site.getAmplienceAnalyticsCollectorId());
			result.setImageHostname(site.getAmplienceImageHostname());
			result.setContentHostname(site.getAmplienceContentHostname());
			result.setAnalyticsHostname(site.getAmplienceAnalyticsHostname());
			result.setScriptHostname(site.getAmplienceScriptHostname());
		}

		// Fallback to system properties

		if (result.getAccountIdentifier() == null)
		{
			result.setAccountIdentifier(configuration.getString("amplience.dm.config.accountIdentifier"));
		}

		if (result.getAnalyticsCollectorId() == null)
		{
			result.setAnalyticsCollectorId(configuration.getString("amplience.dm.config.analyticsCollectorId"));
		}

		if (result.getImageHostname() == null)
		{
			result.setImageHostname(configuration.getString("amplience.dm.config.imageHostname"));
		}

		if (result.getContentHostname() == null)
		{
			result.setContentHostname(configuration.getString("amplience.dm.config.contentHostname"));
		}

		if (result.getAnalyticsHostname() == null)
		{
			result.setAnalyticsHostname(configuration.getString("amplience.dm.config.analyticsHostname"));
		}

		if (result.getScriptHostname() == null)
		{
			result.setScriptHostname(configuration.getString("amplience.dm.config.scriptHostname"));
		}

		return result;
	}

	@Override
	public AmplienceConfigData getConfigForCurrentSite()
	{
		return getConfigForSite(getBaseSiteService().getCurrentBaseSite());
	}
}
