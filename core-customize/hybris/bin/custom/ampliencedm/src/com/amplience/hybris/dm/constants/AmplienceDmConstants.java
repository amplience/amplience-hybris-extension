/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.constants;

/**
 * Global class for all AmplienceDm constants. You can add global constants for your extension into this class.
 */
public final class AmplienceDmConstants extends GeneratedAmplienceDmConstants
{
	public static final String EXTENSIONNAME = "ampliencedm";

	private AmplienceDmConstants()
	{
		// Empty to avoid instantiating this constant class
	}

	// Implement here constants used by this extension
	public static final String MAIL_USE_SMTPS = "mail.use.smtps";
	public static final String MAIL_CHECK_SERVER_IDENTITY = "mail.check.server.identity";

	// Query parameters used when requesting Amplience media
	public static final String URL_QUERY_PARAM_LOCALE = "locale";
	public static final String URL_QUERY_PARAM_FORMAT = "fmt";
}
