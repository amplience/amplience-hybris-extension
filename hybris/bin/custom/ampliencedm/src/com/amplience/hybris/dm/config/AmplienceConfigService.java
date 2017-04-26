/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.config;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;

/**
 * Service interface used to retrieve Amplience configuration for a BaseSite.
 */
public interface AmplienceConfigService
{
	/**
	 * Get the Amplience configuration for the specified site.
	 *
	 * @param site The site to get the config for
	 * @return the Amplience configuration
	 */
	AmplienceConfigData getConfigForSite(BaseSiteModel site);

	/**
	 * Get the Amplience configuration for the current site.
	 *
	 * @return the Amplience configuration
	 */
	AmplienceConfigData getConfigForCurrentSite();
}
