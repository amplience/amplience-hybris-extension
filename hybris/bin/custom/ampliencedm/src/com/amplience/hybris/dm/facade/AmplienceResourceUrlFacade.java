/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.facade;

import com.amplience.hybris.dm.data.AmplienceResourceData;
import de.hybris.platform.servicelayer.exceptions.BusinessException;

/**
 * AmplienceProductFacade used to support the EcommBridge
 */
public interface AmplienceResourceUrlFacade
{
	/**
	 * Get the Amplience resource data for a specific type of resource.
	 *
	 * @param type The resource type - specified in the amplience ecommBride
	 * @param param The optional parameter. Type specific.
	 * @param forceSecure Force the resource URL to be secure
	 * @return The Amplience resource data
	 */
	AmplienceResourceData getResourceUrl(String type, String param, boolean forceSecure) throws BusinessException;
}
