/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.addon.setup;

import de.hybris.platform.commerceservices.setup.SetupImpexService;
import de.hybris.platform.commerceservices.setup.events.SampleDataImportedEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * Event listener that listens for the SampleDataImportedEvent raised when accelerator sample data has been imported.
 * If the event is raised by the apparelstore extension then this listener runs the impex file ApparelStoreSampleData.impex
 */
public class ApparelStoreSampleDataEventListener extends AbstractEventListener<SampleDataImportedEvent>
{
	private static final Logger LOG = LoggerFactory.getLogger(ApparelStoreSampleDataEventListener.class);
	private static final String APPARELSTORE = "apparelstore";

	private SetupImpexService setupImpexService;

	protected SetupImpexService getSetupImpexService()
	{
		return setupImpexService;
	}

	@Required
	public void setSetupImpexService(final SetupImpexService setupImpexService)
	{
		this.setupImpexService = setupImpexService;
	}

	@Override
	protected void onEvent(final SampleDataImportedEvent event)
	{
		if (APPARELSTORE.equals(event.getContext().getExtensionName()))
		{
			loadApparelStoreSampleData();
		}
	}

	protected void loadApparelStoreSampleData()
	{
		LOG.info("Load Amplience sample data for apparelstore");
		getSetupImpexService().importImpexFile("/ampliencedmaddon/import/sampleData/apparelstore.impex", false);
	}

}
