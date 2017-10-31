/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 31-Oct-2017 15:38:49                        ---
 * ----------------------------------------------------------------
 */
package com.amplience.hybris.dm.jalo;

import com.amplience.hybris.dm.constants.AmplienceDmConstants;
import de.hybris.platform.basecommerce.jalo.site.BaseSite;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.amplience.hybris.dm.jalo.AmplienceProductImageStatusUpdateCronJob AmplienceProductImageStatusUpdateCronJob}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAmplienceProductImageStatusUpdateCronJob extends CronJob
{
	/** Qualifier of the <code>AmplienceProductImageStatusUpdateCronJob.longQuery</code> attribute **/
	public static final String LONGQUERY = "longQuery";
	/** Qualifier of the <code>AmplienceProductImageStatusUpdateCronJob.amplienceProductImageStatuses</code> attribute **/
	public static final String AMPLIENCEPRODUCTIMAGESTATUSES = "amplienceProductImageStatuses";
	/** Qualifier of the <code>AmplienceProductImageStatusUpdateCronJob.catalogVersions</code> attribute **/
	public static final String CATALOGVERSIONS = "catalogVersions";
	/** Qualifier of the <code>AmplienceProductImageStatusUpdateCronJob.site</code> attribute **/
	public static final String SITE = "site";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CronJob.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(LONGQUERY, AttributeMode.INITIAL);
		tmp.put(AMPLIENCEPRODUCTIMAGESTATUSES, AttributeMode.INITIAL);
		tmp.put(CATALOGVERSIONS, AttributeMode.INITIAL);
		tmp.put(SITE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageStatusUpdateCronJob.amplienceProductImageStatuses</code> attribute.
	 * @return the amplienceProductImageStatuses - Collection of product image statuses that are passed into the search query.
	 */
	public Collection<EnumerationValue> getAmplienceProductImageStatuses(final SessionContext ctx)
	{
		Collection<EnumerationValue> coll = (Collection<EnumerationValue>)getProperty( ctx, AMPLIENCEPRODUCTIMAGESTATUSES);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageStatusUpdateCronJob.amplienceProductImageStatuses</code> attribute.
	 * @return the amplienceProductImageStatuses - Collection of product image statuses that are passed into the search query.
	 */
	public Collection<EnumerationValue> getAmplienceProductImageStatuses()
	{
		return getAmplienceProductImageStatuses( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageStatusUpdateCronJob.amplienceProductImageStatuses</code> attribute. 
	 * @param value the amplienceProductImageStatuses - Collection of product image statuses that are passed into the search query.
	 */
	public void setAmplienceProductImageStatuses(final SessionContext ctx, final Collection<EnumerationValue> value)
	{
		setProperty(ctx, AMPLIENCEPRODUCTIMAGESTATUSES,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageStatusUpdateCronJob.amplienceProductImageStatuses</code> attribute. 
	 * @param value the amplienceProductImageStatuses - Collection of product image statuses that are passed into the search query.
	 */
	public void setAmplienceProductImageStatuses(final Collection<EnumerationValue> value)
	{
		setAmplienceProductImageStatuses( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageStatusUpdateCronJob.catalogVersions</code> attribute.
	 * @return the catalogVersions - Collection of catalog versions that are passed into the search query.
	 */
	public Collection<CatalogVersion> getCatalogVersions(final SessionContext ctx)
	{
		Collection<CatalogVersion> coll = (Collection<CatalogVersion>)getProperty( ctx, CATALOGVERSIONS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageStatusUpdateCronJob.catalogVersions</code> attribute.
	 * @return the catalogVersions - Collection of catalog versions that are passed into the search query.
	 */
	public Collection<CatalogVersion> getCatalogVersions()
	{
		return getCatalogVersions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageStatusUpdateCronJob.catalogVersions</code> attribute. 
	 * @param value the catalogVersions - Collection of catalog versions that are passed into the search query.
	 */
	public void setCatalogVersions(final SessionContext ctx, final Collection<CatalogVersion> value)
	{
		setProperty(ctx, CATALOGVERSIONS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageStatusUpdateCronJob.catalogVersions</code> attribute. 
	 * @param value the catalogVersions - Collection of catalog versions that are passed into the search query.
	 */
	public void setCatalogVersions(final Collection<CatalogVersion> value)
	{
		setCatalogVersions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageStatusUpdateCronJob.longQuery</code> attribute.
	 * @return the longQuery - The query used to get the products to check for images.
	 */
	public String getLongQuery(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LONGQUERY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageStatusUpdateCronJob.longQuery</code> attribute.
	 * @return the longQuery - The query used to get the products to check for images.
	 */
	public String getLongQuery()
	{
		return getLongQuery( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageStatusUpdateCronJob.longQuery</code> attribute. 
	 * @param value the longQuery - The query used to get the products to check for images.
	 */
	public void setLongQuery(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LONGQUERY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageStatusUpdateCronJob.longQuery</code> attribute. 
	 * @param value the longQuery - The query used to get the products to check for images.
	 */
	public void setLongQuery(final String value)
	{
		setLongQuery( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageStatusUpdateCronJob.site</code> attribute.
	 * @return the site - Site to use to load Amplience configuration.
	 */
	public BaseSite getSite(final SessionContext ctx)
	{
		return (BaseSite)getProperty( ctx, SITE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceProductImageStatusUpdateCronJob.site</code> attribute.
	 * @return the site - Site to use to load Amplience configuration.
	 */
	public BaseSite getSite()
	{
		return getSite( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageStatusUpdateCronJob.site</code> attribute. 
	 * @param value the site - Site to use to load Amplience configuration.
	 */
	public void setSite(final SessionContext ctx, final BaseSite value)
	{
		setProperty(ctx, SITE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceProductImageStatusUpdateCronJob.site</code> attribute. 
	 * @param value the site - Site to use to load Amplience configuration.
	 */
	public void setSite(final BaseSite value)
	{
		setSite( getSession().getSessionContext(), value );
	}
	
}
