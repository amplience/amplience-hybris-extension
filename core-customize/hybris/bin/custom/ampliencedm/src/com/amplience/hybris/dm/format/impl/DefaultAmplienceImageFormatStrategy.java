/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.format.impl;

import com.amplience.hybris.dm.format.AmplienceImageFormatStrategy;
import de.hybris.platform.servicelayer.config.ConfigurationService;

/**
 * Default implementation of AmplienceFormatStrategy.
 * <p>
 * Gets the image format value from a configuration property.
 */
public class DefaultAmplienceImageFormatStrategy implements AmplienceImageFormatStrategy {

    private static final String IMAGE_FORMAT_PROPERTY = "amplience.dm.image.format";

    private ConfigurationService configurationService;

    protected ConfigurationService getConfigurationService()
    {
        return configurationService;
    }

    public void setConfigurationService(ConfigurationService configurationService)
    {
        this.configurationService = configurationService;
    }

    @Override
    public String getImageFormat()
    {
        return getConfigurationService().getConfiguration().getString(IMAGE_FORMAT_PROPERTY, null);
    }
}
