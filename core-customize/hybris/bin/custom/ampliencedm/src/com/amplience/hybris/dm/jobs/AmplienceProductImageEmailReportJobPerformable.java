/*
 * Copyright (c) 2016-2020 Amplience
 */

package com.amplience.hybris.dm.jobs;

import com.amplience.hybris.dm.constants.AmplienceDmConstants;
import com.amplience.hybris.dm.data.AmplienceProductImageEmailReportData;
import com.amplience.hybris.dm.model.AmplienceProductImageEmailReportCronJobModel;
import de.hybris.platform.acceleratorservices.process.email.context.EmailContextFactory;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.catalog.data.CatalogVersionData;
import de.hybris.platform.commercefacades.product.data.ProductData;
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
import de.hybris.platform.util.Config;
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
import java.util.Collection;
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
	private Converter<ProductModel, ProductData> amplienceEmailContextProductConverter;
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

	protected Converter<ProductModel, ProductData> getAmplienceEmailContextProductConverter()
	{
		return amplienceEmailContextProductConverter;
	}

	@Required
	public void setAmplienceEmailContextProductConverter(final Converter<ProductModel, ProductData> amplienceEmailContextProductConverter)
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

		if (!verifyConfiguration(cronJob))
		{
			LOG.warn("CronJob finished. Validation errors.");
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.FINISHED);
		}

		final SearchResult<PK> missingImagesSearchResult = findProductPKs(cronJob);

		final int missingImageCount = missingImagesSearchResult.getTotalCount();
		final List<PK> productPKs = missingImagesSearchResult.getResult();
		LOG.info("Retrieved {}/{} products without images", productPKs.size(), missingImageCount);

		if (shouldSendEmailNotification(cronJob, productPKs, missingImageCount) &&
			!sendEmailNotification(cronJob, productPKs, missingImageCount))
		{
			LOG.warn("CronJob finished. Email sending failure.");
			return new PerformResult(CronJobResult.ERROR, CronJobStatus.FINISHED);
		}

		LOG.info("CronJob finished");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	protected boolean verifyConfiguration(final AmplienceProductImageEmailReportCronJobModel cronJob)
	{
		Assert.notNull(cronJob, "The cronJob cannot be null");
		boolean valid = true;

		if (cronJob.getReportSubjectTemplate() == null)
		{
			LOG.error("[{}] The email subject template cannot be null", cronJob.getCode());
			valid = false;
		}

		if (cronJob.getReportBodyTemplate() == null)
		{
			LOG.error("[{}] The email body template cannot be null", cronJob.getCode());
			valid = false;
		}

		if (cronJob.getReportEmailAddresses() == null)
		{
			LOG.error("[{}] The email address cannot be null", cronJob.getCode());
			valid = false;
		}
		else if (cronJob.getReportEmailAddresses().isBlank())
		{
			LOG.error("[{}] The email address cannot be blank", cronJob.getCode());
			valid = false;
		}
		else
		{
			final List<String> emailAddresses = parseRecipientEmailAddresses(cronJob.getReportEmailAddresses());
			if (emailAddresses == null || emailAddresses.isEmpty())
			{
				LOG.error("[{}] The email address cannot be an empty list", cronJob.getCode());
				valid = false;
			}
		}

		return valid;
	}

	protected boolean sendEmailNotification(final AmplienceProductImageEmailReportCronJobModel cronJob, final List<PK> productPKs, final int missingImageCount)
	{
		Assert.notNull(cronJob.getReportSubjectTemplate(), "The email subject template cannot be null");
		Assert.notNull(cronJob.getReportBodyTemplate(), "The email body template cannot be null");
		Assert.notNull(cronJob.getReportEmailAddresses(), "The email address cannot be null");

		final List<String> emailAddresses = parseRecipientEmailAddresses(cronJob.getReportEmailAddresses());
		final Email email;

		// Create the email and set the to address
		try
		{
			// Create the email
			email = getPreConfiguredEmail();

			// Set the recipient addresses
			for (final String emailAddress : emailAddresses)
			{
				MailUtils.validateEmailAddress(emailAddress, "TO");
				email.addTo(emailAddress);
			}
		}
		catch (final EmailException e)
		{
			LOG.warn("Failed to create notification email: {}", e.getMessage(), e);
			return false;
		}

		// Render the subject and body
		try
		{
			// Create the renderer context
			final AmplienceProductImageEmailReportData templateContext = buildEmailTemplateContext(cronJob, productPKs, missingImageCount);

			// Render the email subject
			final StringWriter emailSubject = new StringWriter();
			getRendererService().render(cronJob.getReportSubjectTemplate(), templateContext, emailSubject);
			email.setSubject(emailSubject.toString());

			// Render the email body
			final StringWriter emailMessage = new StringWriter();
			getRendererService().render(cronJob.getReportBodyTemplate(), templateContext, emailMessage);
			((HtmlEmail) email).setHtmlMsg(emailMessage.toString());
		}
		catch (final EmailException e)
		{
			LOG.warn("Failed to render notification email contents: {}", e.getMessage(), e);
			return false;
		}

		// Send the email
		try
		{
			LOG.info("Sending Amplience product images report to: {}", emailAddresses);
			email.send();
		}
		catch (final EmailException e)
		{
			LOG.warn("Failed to send notification email: {}", e.getMessage(), e);
			return false;
		}

		return true;
	}

	protected Email getPreConfiguredEmail() throws EmailException
	{
		final Email email = MailUtils.getPreConfiguredEmail();

		// Support SMTPS SSL/TLS (Port 465) as well as the default STARTTLS (Port 587)
		final boolean useSmtps = Config.getBoolean(AmplienceDmConstants.MAIL_USE_SMTPS, false);
		if (useSmtps)
		{
			email.setSSLOnConnect(true);
		}

		// Optionally enforce check on the server identity
		final boolean checkServerIdentity = Config.getBoolean(AmplienceDmConstants.MAIL_CHECK_SERVER_IDENTITY, false);
		if (checkServerIdentity)
		{
			email.setSSLCheckServerIdentity(true);
		}

		return email;
	}

	protected List<String> parseRecipientEmailAddresses(final String emailAddresses)
	{
		return emailAddresses == null ? null : Arrays.asList(emailAddresses.split("\\s*,\\s*"));
	}

	@SuppressWarnings("java:S1172")
	protected boolean shouldSendEmailNotification(final AmplienceProductImageEmailReportCronJobModel cronJob, final List<PK> productPKs, final int missingImageCount)
	{
		return missingImageCount > 0 || cronJob.isSendReportWhenNoneMissing();
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
		final Collection<CatalogVersionModel> productCatalogVersions = cronJob.getProductCatalogVersions();
		final List<CatalogVersionData> catalogVersionDatas = new ArrayList<>(productCatalogVersions.size());
		for (final CatalogVersionModel catalogVersionModel : productCatalogVersions)
		{
			catalogVersionDatas.add(getAmplienceEmailContextCatalogVersionConverter().convert(catalogVersionModel));
		}
		context.setCatalogVersions(catalogVersionDatas);
	}

	protected void populateProducts(final AmplienceProductImageEmailReportData context, final List<PK> products)
	{
		final List<ProductData> productDatas = new ArrayList<>(products.size());
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

		final FlexibleSearchQuery query = new FlexibleSearchQuery(cronJobModel.getProductQuery(), queryParams);
		query.setNeedTotal(true);
		query.setResultClassList(Collections.singletonList(PK.class));

		final Integer limit = cronJobModel.getReportLimit();
		if (limit != null)
		{
			query.setCount(limit.intValue());
		}

		return getFlexibleSearchService().search(query);
	}


	protected Map<String, Object> getQueryParameters(final AmplienceProductImageEmailReportCronJobModel cronJobModel)
	{
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("productCatalogVersions", cronJobModel.getProductCatalogVersions());
		return parameters;
	}
}
