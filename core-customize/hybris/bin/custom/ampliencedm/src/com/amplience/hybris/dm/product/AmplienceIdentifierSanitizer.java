/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.product;

/**
 * Sanitizer that creates valid Amplience Identifiers.
 * <p>
 * Asset identifiers in Amplience need to use a subset of characters which are safe to use
 * in URLs, in local file name on Windows, Mac, and Unix, and acceptable to FTP.
 * <p>
 * The implementation should take the input identifier and map it to a string which
 * only contains characters that are appropriate for Amplience while at the same time
 * trying to maintain differentiation between distinct identifiers.
 */
public interface AmplienceIdentifierSanitizer
{
	/**
	 * Sanitize the identifier for Amplience
	 *
	 * @param identifier the identifier
	 * @return the sanitized version of the identifier
	 */
	String sanitize(String identifier);
}
