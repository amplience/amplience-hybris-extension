/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.localization;

/**
 * Strategy to the current Amplience locale string.
 * <p>
 * The Amplience locale string is a list of locales which Amplience will try to use in turn when resolving a resource.
 * See the Amplience documentation for the format of the locale string.
 */
public interface AmplienceLocaleStringStrategy
{
	/**
	 * Get the current Amplience locale string.
	 *
	 * @return The string that contains the Amplience locale string for the current context.
	 */
	String getCurrentLocaleString();
}
