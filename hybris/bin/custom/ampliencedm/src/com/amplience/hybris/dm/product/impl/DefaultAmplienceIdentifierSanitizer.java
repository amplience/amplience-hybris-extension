package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.product.AmplienceIdentifierSanitizer;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Default implementation of AmplienceIdentifierSanitizer.
 *
 * Removes accents and then removes invalid characters.
 */
public class DefaultAmplienceIdentifierSanitizer implements AmplienceIdentifierSanitizer
{
	private static final Pattern patternNotAscii = Pattern.compile("[^\\p{ASCII}]");
	private static final Pattern patternNotAlphaNum = Pattern.compile("[^A-Za-z0-9-_]");

	@Override
	public String sanitize(final String identifier)
	{
		if (identifier == null)
		{
			return null;
		}

		final StringBuilder decomposed = new StringBuilder(Normalizer.normalize(identifier, Normalizer.Form.NFD));

		convertSpecialCases(decomposed);

		final String asciiSafe = patternNotAscii.matcher(decomposed).replaceAll("");
		return patternNotAlphaNum.matcher(asciiSafe).replaceAll("_");
	}

	protected void convertSpecialCases(final StringBuilder decomposed)
	{
		for (int i = 0; i < decomposed.length(); i++)
		{
			if (decomposed.charAt(i) == '\u0141')
			{
				decomposed.deleteCharAt(i);
				decomposed.insert(i, 'L');
			}
			else if (decomposed.charAt(i) == '\u0142')
			{
				decomposed.deleteCharAt(i);
				decomposed.insert(i, 'l');
			}
		}
	}
}
