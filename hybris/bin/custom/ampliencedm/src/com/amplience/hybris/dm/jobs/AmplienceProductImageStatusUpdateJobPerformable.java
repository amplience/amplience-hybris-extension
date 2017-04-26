/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.jobs;

import com.amplience.hybris.dm.enums.AmplienceProductImageStatus;
import com.amplience.hybris.dm.model.AmplienceProductImageStatusUpdateCronJobModel;
import de.hybris.platform.commerceservices.impersonation.ImpersonationContext;
import de.hybris.platform.commerceservices.impersonation.ImpersonationService;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.type.AttributeDescriptorModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.type.TypeService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Job Performable to check the Amplience image status for products that match a query.
 */
public class AmplienceProductImageStatusUpdateJobPerformable extends AbstractJobPerformable<AmplienceProductImageStatusUpdateCronJobModel>
{
	private static final Logger LOG = LoggerFactory.getLogger(AmplienceProductImageStatusUpdateJobPerformable.class);

	private UrlResolver<ProductModel> amplienceProductImageMetadataUrlResolver;
	private UrlResolver<ProductModel> amplienceProductSwatchMetadataUrlResolver;
	private TypeService typeService;
	private ImpersonationService impersonationService;

	protected TypeService getTypeService()
	{
		return typeService;
	}

	@Required
	public void setTypeService(final TypeService typeService)
	{
		this.typeService = typeService;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	protected UrlResolver<ProductModel> getAmplienceProductImageMetadataUrlResolver()
	{
		return amplienceProductImageMetadataUrlResolver;
	}

	@Required
	public void setAmplienceProductImageMetadataUrlResolver(final UrlResolver<ProductModel> amplienceProductImageMetadataUrlResolver)
	{
		this.amplienceProductImageMetadataUrlResolver = amplienceProductImageMetadataUrlResolver;
	}

	protected UrlResolver<ProductModel> getAmplienceProductSwatchMetadataUrlResolver()
	{
		return amplienceProductSwatchMetadataUrlResolver;
	}

	@Required
	public void setAmplienceProductSwatchMetadataUrlResolver(final UrlResolver<ProductModel> amplienceProductSwatchMetadataUrlResolver)
	{
		this.amplienceProductSwatchMetadataUrlResolver = amplienceProductSwatchMetadataUrlResolver;
	}

	protected FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	protected ImpersonationService getImpersonationService()
	{
		return impersonationService;
	}

	@Required
	public void setImpersonationService(final ImpersonationService impersonationService)
	{
		this.impersonationService = impersonationService;
	}

	// ------

	@Override
	public boolean isAbortable()
	{
		return true;
	}

	@Override
	public PerformResult perform(final AmplienceProductImageStatusUpdateCronJobModel cronJob)
	{
		LOG.info("CronJob started");
		final List<PK> productPKs = findProductPKs(cronJob);
		LOG.info("Processing {} products", productPKs.size());


		try (final CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			final PerformResult abortResult = getImpersonationService().executeInContext(createImpersonationContext(cronJob), () -> {
				for (final PK productPK : productPKs)
				{
					final ProductModel product = getModelService().get(productPK);
					try
					{
						processProduct(httpClient, product);
					}
					finally
					{
						getModelService().detach(product);
					}

					if (clearAbortRequestedIfNeeded(cronJob))
					{
						return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
					}
				}
				return null;
			});

			if (abortResult != null)
			{
				LOG.info("CronJob aborted");
				return abortResult;
			}
		}
		catch (final IOException ex)
		{
			LOG.warn("Error closing the http client", ex);
		}

		LOG.info("CronJob finished");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	protected void processProduct(final CloseableHttpClient httpClient, final ProductModel product)
	{
		final AmplienceProductImageStatus imageStatus = updateProductImageStatus(httpClient, product);
		if (imageStatus == AmplienceProductImageStatus.FOUND)
		{
			updateProductSwatchStatus(httpClient, product);
		}
		getModelService().save(product);
	}

	protected ImpersonationContext createImpersonationContext(final AmplienceProductImageStatusUpdateCronJobModel cronJob)
	{
		final ImpersonationContext impersonationContext = new ImpersonationContext();
		impersonationContext.setSite(cronJob.getSite());
		return impersonationContext;
	}

	protected AmplienceProductImageStatus updateProductImageStatus(final CloseableHttpClient httpClient, final ProductModel product)
	{
		final String metadataUrl = getAmplienceProductImageMetadataUrlResolver().resolve(product);
		final boolean setExists = checkAmplienceMediaAssetExists(httpClient, metadataUrl);

		if (setExists)
		{
			product.setAmplienceImageStatus(AmplienceProductImageStatus.FOUND);
		}
		else
		{
			product.setAmplienceImageStatus(AmplienceProductImageStatus.MISSING);
		}
		return product.getAmplienceImageStatus();
	}

	protected void updateProductSwatchStatus(final CloseableHttpClient httpClient, final ProductModel product)
	{
		final String metadataUrl = getAmplienceProductSwatchMetadataUrlResolver().resolve(product);
		final boolean swatchExists = checkAmplienceMediaAssetExists(httpClient, metadataUrl);

		product.setAmplienceAltSwatch(swatchExists);
	}

	protected boolean checkAmplienceMediaAssetExists(final CloseableHttpClient httpClient, final String url)
	{
		int statusCode = -1;
		try
		{
			final HttpUriRequest request = new HttpHead(url);
			final CloseableHttpResponse response = httpClient.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
		}
		catch (final IOException e)
		{
			LOG.warn("Error getting the url [{}]", url);
			LOG.warn("Error getting an url", e);
		}

		LOG.debug("{} {}", statusCode, url);
		return statusCode == HttpStatus.SC_OK;
	}

	protected List<PK> findProductPKs(final AmplienceProductImageStatusUpdateCronJobModel cronJobModel)
	{
		final Set<String> excludeAttributes = Collections.singleton(AmplienceProductImageStatusUpdateCronJobModel.LONGQUERY);
		final Map<String, Object> queryParams = getQueryParameters(cronJobModel, excludeAttributes);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(cronJobModel.getLongQuery(), queryParams);
		query.setNeedTotal(false);
		query.setResultClassList(Collections.singletonList(PK.class));

		final SearchResult<PK> searchResult = getFlexibleSearchService().search(query);
		return searchResult.getResult();
	}

	protected Map<String, Object> getQueryParameters(final AmplienceProductImageStatusUpdateCronJobModel cronJobModel, final Set<String> excludeAttributes)
	{
		final Map<String, Object> parameters = new HashMap<>();
		final ComposedTypeModel composedType = getTypeService().getComposedTypeForCode(cronJobModel.getCode());
		final Collection<AttributeDescriptorModel> descriptors = composedType.getDeclaredattributedescriptors();
		for (final AttributeDescriptorModel descriptor : descriptors)
		{
			final String name = descriptor.getQualifier();
			if (!excludeAttributes.contains(name))
			{
				final Object value = getModelService().getAttributeValue(cronJobModel, name);
				parameters.put(name, value);
			}
		}

		if (LOG.isDebugEnabled())
		{
			LOG.debug("attributes found on the cronjob model:");
			for (final Map.Entry<String, Object> entry : parameters.entrySet())
			{
				LOG.debug("{}={}", entry.getKey(), entry.getValue());
			}
		}

		return parameters;
	}


}
