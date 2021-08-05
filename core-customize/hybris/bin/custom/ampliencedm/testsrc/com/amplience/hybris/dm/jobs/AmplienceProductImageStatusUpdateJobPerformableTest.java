/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.jobs;

import com.amplience.hybris.dm.enums.AmplienceProductImageStatus;
import com.amplience.hybris.dm.model.AmplienceProductImageStatusUpdateCronJobModel;
import com.amplience.hybris.dm.product.impl.AmplienceProductImageMetadataUrlResolver;
import com.amplience.hybris.dm.product.impl.AmplienceProductSwatchMetadataUrlResolver;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.impersonation.ImpersonationService;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.type.AttributeDescriptorModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.search.impl.SearchResultImpl;
import de.hybris.platform.servicelayer.type.TypeService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;

@UnitTest
public class AmplienceProductImageStatusUpdateJobPerformableTest
{
	@InjectMocks
	private AmplienceProductImageStatusUpdateJobPerformable amplienceProductImageStatusUpdateJobPerformable;

	@Mock
	private TypeService typeService;

	@Mock
	private ImpersonationService impersonationService;

	@Mock
	private ModelService modelService;

	@Mock
	private FlexibleSearchService flexibleSearchService;

	@Mock
	private AmplienceProductImageMetadataUrlResolver amplienceProductImageMetadataUrlResolver;

	@Mock
	private AmplienceProductSwatchMetadataUrlResolver amplienceProductSwatchMetadataUrlResolver;

	private final AmplienceProductImageStatusUpdateCronJobModel cronJobModel = new AmplienceProductImageStatusUpdateCronJobModel()
	{{
		setCode("cronjob-code-123");
		setProductQuery("cronjob-query-123");
	}};

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		when(typeService.getComposedTypeForCode(any(String.class))).thenAnswer(i -> createComposedTypeModel());

		when(modelService.getAttributeValue(cronJobModel, "attribute1")).thenReturn("value1");
		when(modelService.getAttributeValue(cronJobModel, "attribute2")).thenReturn("value2");
		when(modelService.getAttributeValue(cronJobModel, "attribute3")).thenReturn("value3");
		when(modelService.getAttributeValue(cronJobModel, "attribute4")).thenReturn("value4");
		when(modelService.get(any(PK.class))).thenReturn(createProductModel());

		when(amplienceProductImageMetadataUrlResolver.resolve(any(ProductModel.class))).thenReturn("http://test/metadata/url.json");
		when(amplienceProductSwatchMetadataUrlResolver.resolve(any(ProductModel.class))).thenReturn("http://test/swatch-metadata/url.json");

		when(flexibleSearchService.search(any(FlexibleSearchQuery.class))).thenReturn(createSearchResult());
	}

	private SearchResult<Object> createSearchResult()
	{
		return new SearchResultImpl<>(Arrays.asList(PK.parse("100"), PK.parse("101"), PK.parse("102")), 3, 3, 0);
	}


	private CloseableHttpClient createHttpClient(final int status) throws IOException
	{
		final CloseableHttpClient httpClient = Mockito.mock(CloseableHttpClient.class);
		if (status < 0)
		{
			when(httpClient.execute(isNotNull(HttpUriRequest.class))).thenThrow(IOException.class);
		}
		else
		{
			final CloseableHttpResponse httpResponse = createHttpResponse(status);
			when(httpClient.execute(isNotNull(HttpUriRequest.class))).thenReturn(httpResponse);
		}
		return httpClient;
	}

	private CloseableHttpResponse createHttpResponse(final int code)
	{
		final CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);
		when(response.getStatusLine()).thenReturn(new BasicStatusLine(new org.apache.http.ProtocolVersion("http", 1, 1), code, ""));
		return response;
	}


	private ComposedTypeModel createComposedTypeModel()
	{
		final ComposedTypeModel composedTypeModel = Mockito.mock(ComposedTypeModel.class);
		when(composedTypeModel.getDeclaredattributedescriptors()).thenReturn(createAttributeDescriptorModels());
		return composedTypeModel;
	}

	private List<AttributeDescriptorModel> createAttributeDescriptorModels()
	{
		final List<AttributeDescriptorModel> attributeDescriptorModels = new ArrayList<>();
		attributeDescriptorModels.add(new AttributeDescriptorModel()
		{{
			setQualifier("attribute1");
		}});
		attributeDescriptorModels.add(new AttributeDescriptorModel()
		{{
			setQualifier("attribute2");
		}});
		attributeDescriptorModels.add(new AttributeDescriptorModel()
		{{
			setQualifier("attribute3");
		}});
		attributeDescriptorModels.add(new AttributeDescriptorModel()
		{{
			setQualifier("attribute4");
		}});
		return attributeDescriptorModels;
	}


	private ProductModel createProductModel()
	{
		final ProductModel productModel = new ProductModel();
		productModel.setCode("junit-product");
		return productModel;
	}

	@Test
	public void testPerform() throws Exception
	{
		final PerformResult result = amplienceProductImageStatusUpdateJobPerformable.perform(cronJobModel);

		Assert.assertNotNull(result);
		Assert.assertEquals(CronJobResult.SUCCESS, result.getResult());
	}


	@Test
	public void testUpdateProductImageStatus_found() throws Exception
	{
		final ProductModel productModel = createProductModel();
		final AmplienceProductImageStatus result = amplienceProductImageStatusUpdateJobPerformable.updateProductImageStatus(createHttpClient(200), productModel);

		Assert.assertEquals(AmplienceProductImageStatus.FOUND, result);
		Assert.assertEquals(AmplienceProductImageStatus.FOUND, productModel.getAmplienceImageStatus());
	}

	@Test
	public void testUpdateProductImageStatus_missing() throws Exception
	{
		final ProductModel productModel = createProductModel();
		final AmplienceProductImageStatus result = amplienceProductImageStatusUpdateJobPerformable.updateProductImageStatus(createHttpClient(404), productModel);

		Assert.assertEquals(AmplienceProductImageStatus.MISSING, result);
		Assert.assertEquals(AmplienceProductImageStatus.MISSING, productModel.getAmplienceImageStatus());
	}

	@Test
	public void testUpdateProductSwatchStatus_found() throws Exception
	{
		final ProductModel productModel = createProductModel();
		amplienceProductImageStatusUpdateJobPerformable.updateProductSwatchStatus(createHttpClient(200), productModel);

		Assert.assertEquals(true, productModel.isAmplienceAltSwatch());
	}

	@Test
	public void testUpdateProductSwatchStatus_missing() throws Exception
	{
		final ProductModel productModel = createProductModel();
		amplienceProductImageStatusUpdateJobPerformable.updateProductSwatchStatus(createHttpClient(404), productModel);

		Assert.assertEquals(false, productModel.isAmplienceAltSwatch());
	}

	@Test
	public void testCheckImageSetExists_true() throws Exception
	{
		final boolean result = amplienceProductImageStatusUpdateJobPerformable.checkAmplienceMediaAssetExists(createHttpClient(200), "http://test/request/success");
		Assert.assertEquals(true, result);
	}

	@Test
	public void testCheckImageSetExists_false() throws Exception
	{
		final boolean result = amplienceProductImageStatusUpdateJobPerformable.checkAmplienceMediaAssetExists(createHttpClient(404), "http://test/request/failure");
		Assert.assertEquals(false, result);
	}

	@Test
	public void testCheckImageSetExists_exception() throws Exception
	{
		final boolean result = amplienceProductImageStatusUpdateJobPerformable.checkAmplienceMediaAssetExists(createHttpClient(-1), "http://test/request/failure");
		Assert.assertEquals(false, result);
	}

	@Test
	public void testGetQueryParameters_noExclusions() throws Exception
	{
		final Map<String, Object> expectedResult = new HashMap<>()
		{{
			put("attribute1", "value1");
			put("attribute2", "value2");
			put("attribute3", "value3");
			put("attribute4", "value4");
		}};
		final Map<String, Object> result = amplienceProductImageStatusUpdateJobPerformable.getQueryParameters(cronJobModel, new HashSet<>());
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testGetQueryParameters_exclusions() throws Exception
	{
		final Map<String, Object> expectedResult = new HashMap<>()
		{{
			put("attribute1", "value1");
			put("attribute2", "value2");
		}};
		final Map<String, Object> result = amplienceProductImageStatusUpdateJobPerformable
			.getQueryParameters(cronJobModel, new HashSet<>(Arrays.asList("attribute3", "attribute5", "attribute4")));
		Assert.assertEquals(expectedResult, result);
	}

}
