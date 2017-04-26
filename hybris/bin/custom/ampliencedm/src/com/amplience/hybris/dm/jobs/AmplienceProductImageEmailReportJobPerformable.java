/*
 * Copyright (c) 2016 Amplience
 */

package com.amplience.hybris.dm.jobs;

import com.amplience.hybris.dm.data.AmplienceProductData;
import com.amplience.hybris.dm.data.AmplienceProductImageEmailReportData;
import com.amplience.hybris.dm.model.AmplienceProductImageEmailReportCronJobModel;
import de.hybris.platform.acceleratorservices.process.email.context.EmailContextFactory;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.catalog.data.CatalogVersionData;
import de.hybris.platform.commons.renderer.RendererService;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.mail.MailUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Job Performable that emails a report of products missing images in Amplience.
 */
public class AmplienceProductImageEmailReportJobPerformable extends AbstractJobPerformable<AmplienceProductImageEmailReportCronJobModel>
{
	private static final Logger LOG = LoggerFactory.getLogger(AmplienceProductImageEmailReportJobPerformable.class);

	private RendererService rendererService;
	private EmailContextFactory<BusinessProcessModel> emailContextFactory;
	private Converter<ProductModel, AmplienceProductData> amplienceEmailContextProductConverter;
	private Converter<CatalogVersionModel, CatalogVersionData> amplienceEmailContextCatalogVersionConverter;


	protected Converter<CatalogVersionModel, CatalogVersionData> getAmplienceEmailContextCatalogVersionConverter()
	{
		return amplienceEmailContextCatalogVersionConverter;
	}

	@Required
	public void setAmplienceEmailContextCatalogVersionConverter(final Converter<CatalogVersionModel, CatalogVersionData> amplienceEmailContextCatalogVersionConverter)
	{
		this.amplienceEmailContextCatalogVersionConverter = amplienceEmailContextCatalogVersionConverter;
	}

	protected Converter<ProductModel, AmplienceProductData> getAmplienceEmailContextProductConverter()
	{
		return amplienceEmailContextProductConverter;
	}

	@Required
	public void setAmplienceEmailContextProductConverter(final Converter<ProductModel, AmplienceProductData> amplienceEmailContextProductConverter)
	{
		this.amplienceEmailContextProductConverter = amplienceEmailContextProductConverter;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	protected EmailContextFactory<BusinessProcessModel> getEmailContextFactory()
	{
		return emailContextFactory;
	}

	@Required
	public void setEmailContextFactory(final EmailContextFactory<BusinessProcessModel> emailContextFactory)
	{
		this.emailContextFactory = emailContextFactory;
	}

	protected RendererService getRendererService()
	{
		return rendererService;
	}

	@Required
	public void setRendererService(final RendererService rendererService)
	{
		this.rendererService = rendererService;
	}

	protected FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}


	@Override
	public PerformResult perform(final AmplienceProductImageEmailReportCronJobModel cronJob)
	{
		LOG.info("CronJob started");

		final SearchResult<PK> missingImagesSearchResult = findProductPKs(cronJob);

		final int missingImageCount = missingImagesSearchResult.getTotalCount();
		final List<PK> productPKs = missingImagesSearchResult.getResult();
		LOG.info("Retrieved {}/{} products without images", productPKs.size(), missingImageCount);

		if (shouldSendEmailNotification(cronJob, productPKs, missingImageCount))
		{
			sendEmailNotification(cronJob, productPKs, missingImageCount);
		}

		LOG.info("CronJob finished");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	protected void sendEmailNotification(final AmplienceProductImageEmailReportCronJobModel cronJob, final List<PK> productPKs, final int missingImageCount)
	{
		final Email email;
		try
		{
			Assert.notNull(cronJob.getSubjectTemplate(), "The email subject template cannot be null");
			Assert.notNull(cronJob.getBodyTemplate(), "The email body template cannot be null");
			Assert.notNull(cronJob.getEmailAddresses(), "The email address cannot be null");

			//create the renderer context
			final AmplienceProductImageEmailReportData templateContext = buildEmailTemplateContext(cronJob, productPKs, missingImageCount);

			//create the email
			email = MailUtils.getPreConfiguredEmail();

			//set the recipient addresses
			final List<String> emailAddresses = parseRecipientEmailAddresses(cronJob.getEmailAddresses());
			for (final String emailAddress : emailAddresses)
			{
				MailUtils.validateEmailAddress(emailAddress, "TO");
				email.addTo(emailAddress);
			}

			//render the email subject
			final StringWriter emailSubject = new StringWriter();
			getRendererService().render(cronJob.getSubjectTemplate(), templateContext, emailSubject);
			email.setSubject(emailSubject.toString());

			//render the email body
			final StringWriter emailMessage = new StringWriter();
			getRendererService().render(cronJob.getBodyTemplate(), templateContext, emailMessage);
			((HtmlEmail) email).setHtmlMsg(emailMessage.toString());
		}
		catch (final EmailException e)
		{
			LOG.warn("Failed to create notification email", e);
			return;
		}

		try
		{
			//send the email
			email.send();
		}
		catch (final EmailException e)
		{
			LOG.warn("Failed to send notification email", e);
		}
	}

	protected List<String> parseRecipientEmailAddresses(final String emailAddresses)
	{
		return emailAddresses == null ? null : Arrays.asList(emailAddresses.split("\\s*,\\s*"));
	}

	protected boolean shouldSendEmailNotification(final AmplienceProductImageEmailReportCronJobModel cronJob, final List<PK> productPKs, final int missingImageCount)
	{
		return missingImageCount > 0 || cronJob.isSendWhenNoneMissing();
	}

	/**
	 * Generates the context to be used to render the email templates
	 *
	 * @param cronJob
	 * @param products
	 * @param missingImageCount
	 * @return
	 */
	protected AmplienceProductImageEmailReportData buildEmailTemplateContext(final AmplienceProductImageEmailReportCronJobModel cronJob, final List<PK> products, final int missingImageCount)
	{
		final AmplienceProductImageEmailReportData context = createEmailTemplateContext();
		populateProducts(context, products);
		populateCatalogVersions(context, cronJob);
		context.setMissingImageCount(missingImageCount);
		return context;
	}

	protected AmplienceProductImageEmailReportData createEmailTemplateContext()
	{
		return new AmplienceProductImageEmailReportData();
	}

	protected void populateCatalogVersions(final AmplienceProductImageEmailReportData context, final AmplienceProductImageEmailReportCronJobModel cronJob)
	{
		final List<CatalogVersionData> catalogVersionDatas = new ArrayList<>(cronJob.getCatalogVersions().size());
		for (final CatalogVersionModel catalogVersionModel : cronJob.getCatalogVersions())
		{
			catalogVersionDatas.add(getAmplienceEmailContextCatalogVersionConverter().convert(catalogVersionModel));
		}
		context.setCatalogVersions(catalogVersionDatas);
	}

	protected void populateProducts(final AmplienceProductImageEmailReportData context, final List<PK> products)
	{
		final List<AmplienceProductData> productDatas = new ArrayList<>(products.size());
		for (final PK pk : products)
		{
			final ProductModel productModel = getModelService().get(pk);
			productDatas.add(getAmplienceEmailContextProductConverter().convert(productModel));
			getModelService().detach(productModel);
		}
		context.setProducts(productDatas);
	}


	protected SearchResult<PK> findProductPKs(final AmplienceProductImageEmailReportCronJobModel cronJobModel)
	{
		final Map<String, Object> queryParams = getQueryParameters(cronJobModel);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(cronJobModel.getLongQuery(), queryParams);
		query.setNeedTotal(true);
		query.setResultClassList(Collections.singletonList(PK.class));

		final Integer limit = cronJobModel.getLimit();
		if (limit != null)
		{
			query.setCount(limit.intValue());
		}

		final SearchResult<PK> searchResult = getFlexibleSearchService().search(query);
		return searchResult;
	}


	protected Map<String, Object> getQueryParameters(final AmplienceProductImageEmailReportCronJobModel cronJobModel)
	{
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("catalogVersions", cronJobModel.getCatalogVersions());
		return parameters;
	}
}
