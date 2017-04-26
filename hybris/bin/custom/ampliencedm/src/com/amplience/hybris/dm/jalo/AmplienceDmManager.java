/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.jalo;

import com.amplience.hybris.dm.constants.AmplienceDmConstants;
import de.hybris.platform.core.Registry;

/**
 * This is the extension manager of the AmplienceDm extension.
 */
public class AmplienceDmManager extends GeneratedAmplienceDmManager
{
	/**
	 * Get the valid instance of this manager.
	 *
	 * @return the current instance of this manager
	 */
	public static AmplienceDmManager getInstance()
	{
		return (AmplienceDmManager) Registry.getCurrentTenant().getJaloConnection().getExtensionManager().getExtension(AmplienceDmConstants.EXTENSIONNAME);
	}
}
