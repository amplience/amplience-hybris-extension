/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 31-Oct-2017 15:38:49                        ---
 * ----------------------------------------------------------------
 */
package com.amplience.hybris.dm.jalo;

import com.amplience.hybris.dm.constants.AmplienceDmConstants;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.commons.jalo.renderer.RendererTemplate;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.amplience.hybris.dm.jalo.AmplienceProductImageEmailReportCronJob AmplienceProductImageEmailReportCronJob}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAmplienceProductImageEmailReportCronJob extends CronJob
{
	/** Qualifier of the <code>AmplienceProductImageEmailReportCronJob.longQuery</code> attribute **/
	public static final String LONGQUERY = "longQuery";
	/** Qualifier of the <code>AmplienceProductImageEmailReportCronJob.emailAddresses</code> attribute **/
	public static final String EMAILADDRESSES = "emailAddresses";
	/** Qualifier of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute **/
	public static final String SENDWHENNONEMISSING = "sendWhenNoneMissing";
	/** Qualifier of the <code>AmplienceProductImageEmailReportCronJob.catalogVersions</code> attribute **/
	public static final String CATALOGVERSIONS = "catalogVersions";
	/** Qualifier of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute **/
	public static final String LIMIT = "limit";
	/** Qualifier of the <code>AmplienceProductImageEmailReportCronJob.subjectTemplate</code> attribute **/
	public static final String SUBJECTTEMPLATE = "subjectTemplate";
	/** Qualifier of the <code>AmplienceProductImageEmailReportCronJob.bodyTemplate</code> attribute **/
	public static final String BODYTEMPLATE = "bodyTemplate";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CronJob.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(LONGQUERY, AttributeMode.INITIAL);
		tmp.put(EMAILADDRESSES, AttributeMode.INITIAL);
		tmp.put(SENDWHENNONEMISSING, AttributeMode.INITIAL);
		tmp.put(CATALOGVERSIONS, AttributeMode.INITIAL);
		tmp.put(LIMIT, AttributeMode.INITIAL);
		tmp.put(SUBJECTTEMPLATE, AttributeMode.INITIAL);
		tmp.put(BODYTEMPLATE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.bodyTemplate</code> attribute.
	 * @return the bodyTemplate - Template to render the email body
	 */
	public RendererTemplate getBodyTemplate(final SessionContext ctx)
	{
		return (RendererTemplate)getProperty( ctx, BODYTEMPLATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.bodyTemplate</code> attribute.
	 * @return the bodyTemplate - Template to render the email body
	 */
	public RendererTemplate getBodyTemplate()
	{
		return getBodyTemplate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.bodyTemplate</code> attribute. 
	 * @param value the bodyTemplate - Template to render the email body
	 */
	public void setBodyTemplate(final SessionContext ctx, final RendererTemplate value)
	{
		setProperty(ctx, BODYTEMPLATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.bodyTemplate</code> attribute. 
	 * @param value the bodyTemplate - Template to render the email body
	 */
	public void setBodyTemplate(final RendererTemplate value)
	{
		setBodyTemplate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.catalogVersions</code> attribute.
	 * @return the catalogVersions - Collection of catalog versions that are passed into the search query.
	 */
	public Collection<CatalogVersion> getCatalogVersions(final SessionContext ctx)
	{
		Collection<CatalogVersion> coll = (Collection<CatalogVersion>)getProperty( ctx, CATALOGVERSIONS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.catalogVersions</code> attribute.
	 * @return the catalogVersions - Collection of catalog versions that are passed into the search query.
	 */
	public Collection<CatalogVersion> getCatalogVersions()
	{
		return getCatalogVersions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.catalogVersions</code> attribute. 
	 * @param value the catalogVersions - Collection of catalog versions that are passed into the search query.
	 */
	public void setCatalogVersions(final SessionContext ctx, final Collection<CatalogVersion> value)
	{
		setProperty(ctx, CATALOGVERSIONS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.catalogVersions</code> attribute. 
	 * @param value the catalogVersions - Collection of catalog versions that are passed into the search query.
	 */
	public void setCatalogVersions(final Collection<CatalogVersion> value)
	{
		setCatalogVersions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.emailAddresses</code> attribute.
	 * @return the emailAddresses - Comma separated list of email addresses to send the report to.
	 */
	public String getEmailAddresses(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILADDRESSES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.emailAddresses</code> attribute.
	 * @return the emailAddresses - Comma separated list of email addresses to send the report to.
	 */
	public String getEmailAddresses()
	{
		return getEmailAddresses( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.emailAddresses</code> attribute. 
	 * @param value the emailAddresses - Comma separated list of email addresses to send the report to.
	 */
	public void setEmailAddresses(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILADDRESSES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.emailAddresses</code> attribute. 
	 * @param value the emailAddresses - Comma separated list of email addresses to send the report to.
	 */
	public void setEmailAddresses(final String value)
	{
		setEmailAddresses( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute.
	 * @return the limit - The maximum number of products retrieved from the database, to display on the email.
	 */
	public Integer getLimit(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, LIMIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute.
	 * @return the limit - The maximum number of products retrieved from the database, to display on the email.
	 */
	public Integer getLimit()
	{
		return getLimit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute. 
	 * @return the limit - The maximum number of products retrieved from the database, to display on the email.
	 */
	public int getLimitAsPrimitive(final SessionContext ctx)
	{
		Integer value = getLimit( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute. 
	 * @return the limit - The maximum number of products retrieved from the database, to display on the email.
	 */
	public int getLimitAsPrimitive()
	{
		return getLimitAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute. 
	 * @param value the limit - The maximum number of products retrieved from the database, to display on the email.
	 */
	public void setLimit(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, LIMIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute. 
	 * @param value the limit - The maximum number of products retrieved from the database, to display on the email.
	 */
	public void setLimit(final Integer value)
	{
		setLimit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute. 
	 * @param value the limit - The maximum number of products retrieved from the database, to display on the email.
	 */
	public void setLimit(final SessionContext ctx, final int value)
	{
		setLimit( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.limit</code> attribute. 
	 * @param value the limit - The maximum number of products retrieved from the database, to display on the email.
	 */
	public void setLimit(final int value)
	{
		setLimit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.longQuery</code> attribute.
	 * @return the longQuery - The query used to get the list of products without images.
	 */
	public String getLongQuery(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LONGQUERY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.longQuery</code> attribute.
	 * @return the longQuery - The query used to get the list of products without images.
	 */
	public String getLongQuery()
	{
		return getLongQuery( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.longQuery</code> attribute. 
	 * @param value the longQuery - The query used to get the list of products without images.
	 */
	public void setLongQuery(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LONGQUERY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.longQuery</code> attribute. 
	 * @param value the longQuery - The query used to get the list of products without images.
	 */
	public void setLongQuery(final String value)
	{
		setLongQuery( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute.
	 * @return the sendWhenNoneMissing - Send and email even if any of the product images are missing
	 */
	public Boolean isSendWhenNoneMissing(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SENDWHENNONEMISSING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute.
	 * @return the sendWhenNoneMissing - Send and email even if any of the product images are missing
	 */
	public Boolean isSendWhenNoneMissing()
	{
		return isSendWhenNoneMissing( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute. 
	 * @return the sendWhenNoneMissing - Send and email even if any of the product images are missing
	 */
	public boolean isSendWhenNoneMissingAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isSendWhenNoneMissing( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute. 
	 * @return the sendWhenNoneMissing - Send and email even if any of the product images are missing
	 */
	public boolean isSendWhenNoneMissingAsPrimitive()
	{
		return isSendWhenNoneMissingAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute. 
	 * @param value the sendWhenNoneMissing - Send and email even if any of the product images are missing
	 */
	public void setSendWhenNoneMissing(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SENDWHENNONEMISSING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute. 
	 * @param value the sendWhenNoneMissing - Send and email even if any of the product images are missing
	 */
	public void setSendWhenNoneMissing(final Boolean value)
	{
		setSendWhenNoneMissing( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute. 
	 * @param value the sendWhenNoneMissing - Send and email even if any of the product images are missing
	 */
	public void setSendWhenNoneMissing(final SessionContext ctx, final boolean value)
	{
		setSendWhenNoneMissing( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.sendWhenNoneMissing</code> attribute. 
	 * @param value the sendWhenNoneMissing - Send and email even if any of the product images are missing
	 */
	public void setSendWhenNoneMissing(final boolean value)
	{
		setSendWhenNoneMissing( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.subjectTemplate</code> attribute.
	 * @return the subjectTemplate - Template to render the email subject
	 */
	public RendererTemplate getSubjectTemplate(final SessionContext ctx)
	{
		return (RendererTemplate)getProperty( ctx, SUBJECTTEMPLATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageEmailReportCronJob.subjectTemplate</code> attribute.
	 * @return the subjectTemplate - Template to render the email subject
	 */
	public RendererTemplate getSubjectTemplate()
	{
		return getSubjectTemplate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.subjectTemplate</code> attribute. 
	 * @param value the subjectTemplate - Template to render the email subject
	 */
	public void setSubjectTemplate(final SessionContext ctx, final RendererTemplate value)
	{
		setProperty(ctx, SUBJECTTEMPLATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageEmailReportCronJob.subjectTemplate</code> attribute. 
	 * @param value the subjectTemplate - Template to render the email subject
	 */
	public void setSubjectTemplate(final RendererTemplate value)
	{
		setSubjectTemplate( getSession().getSessionContext(), value );
	}
	
}
