/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import com.amplience.hybris.dm.config.AmplienceConfigService;
import com.amplience.hybris.dm.localization.AmplienceLocaleStringStrategy;
import com.amplience.hybris.dm.product.AmplienceIdentifierSanitizer;
import com.amplience.hybris.dm.product.AmplienceProductResolver;
import com.amplience.hybris.dm.product.AmplienceSeoImageNameStrategy;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.product.ProductModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@UnitTest
public class AmplienceProductSwatchUrlResolverTest
{
	private static final String SWATCH_SUFFIX = "-swatch";

	@InjectMocks
	private AmplienceProductSwatchUrlResolver amplienceProductSwatchUrlResolver;

	@Mock
	private UrlResolver<ProductModel> defaultProductImageUrlResolver;

	@Mock
	private AmplienceConfigService amplienceConfigService;

	@Mock
	private AmplienceLocaleStringStrategy localeStringStrategy;

	@Mock
	private AmplienceSeoImageNameStrategy amplienceSeoImageNameStrategy;

	@Mock
	private AmplienceProductResolver amplienceProductResolver;

	@Mock
	private AmplienceIdentifierSanitizer amplienceIdentifierSanitizer;

	@Mock
	private ProductModel productModel;


	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		when(productModel.getPk()).thenReturn(PK.parse("12345"));
		when(productModel.getCode()).thenReturn("image-junit");
		when(productModel.isAmplienceAltSwatch()).thenReturn(Boolean.TRUE);
		when(amplienceIdentifierSanitizer.sanitize(eq("image-junit"))).thenReturn("image-junit");

		when(localeStringStrategy.getCurrentLocaleString()).thenReturn("foo");
		when(amplienceProductResolver.resolveProduct(any(ProductModel.class))).thenReturn(productModel);
		when(amplienceConfigService.getConfigForCurrentSite()).thenReturn(initAmplienceConfigData());
		when(amplienceSeoImageNameStrategy.getSeoName(any(ProductModel.class))).thenReturn("seo-name-junit");

		when(defaultProductImageUrlResolver.resolve(any(ProductModel.class))).thenReturn("//imageHostname-junit/i/account-junit/image-junit/seo-name-junit.jpg?locale=foo");
	}

	private AmplienceConfigData initAmplienceConfigData()
	{
		return new AmplienceConfigData()
		{{
			setAccountIdentifier("account-junit");
			setImageHostname("imageHostname-junit");
		}};
	}


	@Test
	public void testGetKey() throws Exception
	{
		final String expectedResult = AmplienceProductSwatchUrlResolver.class.getName() + ".12345";
		final String result = amplienceProductSwatchUrlResolver.getKey(productModel);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testResolveInternalAltSwatch() throws Exception
	{
		final String expectedResult = "//imageHostname-junit/i/account-junit/image-junit" + SWATCH_SUFFIX + "/seo-name-junit.jpg?locale=foo";
		final String result = amplienceProductSwatchUrlResolver.resolveInternal(productModel);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testResolveInternalNoAltSwatch() throws Exception
	{
		when(productModel.isAmplienceAltSwatch()).thenReturn(Boolean.FALSE);

		final String expectedResult = "//imageHostname-junit/i/account-junit/image-junit/seo-name-junit.jpg?locale=foo";
		final String result = amplienceProductSwatchUrlResolver.resolveInternal(productModel);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testGetImageIdentifierForProduct() throws Exception
	{
		final String expectedResult = "image-junit" + SWATCH_SUFFIX;
		final String result = amplienceProductSwatchUrlResolver.getImageIdentifierForProduct(productModel);
		Assert.assertEquals(expectedResult, result);
	}
}
