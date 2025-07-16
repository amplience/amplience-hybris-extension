/*
 * Copyright (c) 2016-2020 Amplience
 */

package com.amplience.hybris.dm.product.impl;

import com.amplience.hybris.dm.config.AmplienceConfigData;
import com.amplience.hybris.dm.config.AmplienceConfigService;
import com.amplience.hybris.dm.format.AmplienceImageFormatStrategy;
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

import java.util.Optional;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@UnitTest
public class AmplienceProductImageUrlResolverTest
{
	private static final String MEDIA_SET_SUFFIX = "-ms";
	private static final String UNKNOWN_IMAGE = "unknown";

	@InjectMocks
	private final AmplienceProductImageUrlResolver amplienceProductImageUrlResolver = new AmplienceProductImageUrlResolver();

	@Mock
	private AmplienceConfigService amplienceConfigService;

	@Mock
	private AmplienceLocaleStringStrategy localeStringStrategy;

	@Mock
	private AmplienceImageFormatStrategy amplienceImageFormatStrategy;

	@Mock
	private AmplienceSeoImageNameStrategy amplienceSeoImageNameStrategy;

	@Mock
	private AmplienceProductResolver amplienceProductResolver;

	@Mock
	private AmplienceIdentifierSanitizer amplienceIdentifierSanitizer;

	@Mock
	private UrlResolver<ProductModel> defaultProductImageUrlResolver;

	@Mock
	private ProductModel productModel;


	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		when(localeStringStrategy.getCurrentLocaleString()).thenReturn("foo");
		when(amplienceImageFormatStrategy.getImageFormat()).thenReturn("auto");
		when(productModel.getPk()).thenReturn(PK.parse("12345"));
		when(productModel.getCode()).thenReturn("image-junit");
		when(amplienceProductResolver.resolveProduct(any(ProductModel.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
		when(amplienceConfigService.getConfigForCurrentSite()).thenReturn(initAmplienceConfigData());
		when(amplienceSeoImageNameStrategy.getSeoName(notNull(ProductModel.class))).thenReturn("seo-name-junit");
		when(amplienceIdentifierSanitizer.sanitize(eq("image-junit"))).thenReturn("image-junit");
		when(defaultProductImageUrlResolver.resolve(any(ProductModel.class))).thenReturn("//imageHostname-junit/i/account-junit/" + UNKNOWN_IMAGE + "?locale=foo&fmt=auto");
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
		final String expectedResult = AmplienceProductImageUrlResolver.class.getName() + ".12345";
		final String result = amplienceProductImageUrlResolver.getKey(productModel);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testResolveInternal() throws Exception
	{
		final String expectedResult = "//imageHostname-junit/s/account-junit/image-junit" + MEDIA_SET_SUFFIX + "/seo-name-junit?locale=foo&fmt=auto";
		final String result = amplienceProductImageUrlResolver.resolveInternal(productModel);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testResolveInternal_nullProduct() throws Exception
	{
		final String result = amplienceProductImageUrlResolver.resolveInternal(null);
		Assert.assertNull(result);
	}

	@Test
	public void testResolveInternal_unresolvedProduct() throws Exception
	{
		when(amplienceProductResolver.resolveProduct(any(ProductModel.class))).thenReturn(null);

		final String expectedResult = "//imageHostname-junit/i/account-junit/" + UNKNOWN_IMAGE + "?locale=foo&fmt=auto";
		final String result = amplienceProductImageUrlResolver.resolveInternal(productModel);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testGetLocaleParameter() throws Exception
	{
		final Optional<String> expectedResult = Optional.of("foo");
		final Optional<String> result = amplienceProductImageUrlResolver.getLocaleParameter();
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testGetFormatParameter() throws Exception
	{
		final Optional<String> expectedResult = Optional.of("auto");
		final Optional<String> result = amplienceProductImageUrlResolver.getFormatParameter();
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testRedirectProduct() throws Exception
	{
		final ProductModel expectedResult = productModel;
		final ProductModel result = amplienceProductImageUrlResolver.redirectProduct(productModel);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testGetImageIdentifierForProduct() throws Exception
	{
		final String expectedResult = "image-junit" + MEDIA_SET_SUFFIX;
		final String result = amplienceProductImageUrlResolver.getImageIdentifierForProduct(productModel);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testGetSeoNameForProduct() throws Exception
	{
		final String expectedResult = "seo-name-junit";
		final String result = amplienceProductImageUrlResolver.getSeoNameForProduct(productModel);
		Assert.assertEquals(expectedResult, result);
	}
}
