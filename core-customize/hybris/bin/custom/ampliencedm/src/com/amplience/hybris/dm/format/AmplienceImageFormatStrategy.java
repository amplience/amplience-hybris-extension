/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.format;

/**
 * Strategy to get the image format to use when requesting Amplience images ("fmt" query parameter).
 * If set to 'auto' and Amplience Smart Images / Accelerated Media are enabled on the Amplience account,
 * Amplience will automatically serve the most appropriate format for the browser.
 * <p>
 * See the Amplience documentation for more information about supported formats and Amplience Smart Images / Accelerated Media.
 */
public interface AmplienceImageFormatStrategy
{
    /**
     * Get the image format to use when requesting Amplience images.
     *
     * @return The string that contains the Amplience format.
     */
    String getImageFormat();
}
