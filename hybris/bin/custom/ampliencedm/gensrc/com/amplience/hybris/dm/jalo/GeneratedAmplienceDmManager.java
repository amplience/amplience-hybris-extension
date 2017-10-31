/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 31-Oct-2017 15:38:49                        ---
 * ----------------------------------------------------------------
 */
package com.amplience.hybris.dm.jalo;

import com.amplience.hybris.dm.constants.AmplienceDmConstants;
import com.amplience.hybris.dm.jalo.AmplienceImageCarouselComponent;
import com.amplience.hybris.dm.jalo.AmpliencePageScriptComponent;
import com.amplience.hybris.dm.jalo.AmplienceProductImageEmailReportCronJob;
import com.amplience.hybris.dm.jalo.AmplienceProductImageStatusUpdateCronJob;
import com.amplience.hybris.dm.jalo.AmplienceResponsiveImageComponent;
import com.amplience.hybris.dm.jalo.AmplienceUGCCarouselComponent;
import com.amplience.hybris.dm.jalo.AmplienceUGCMediaWallComponent;
import de.hybris.platform.basecommerce.jalo.site.BaseSite;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>AmplienceDmManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAmplienceDmManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("amplienceImageStatus", AttributeMode.INITIAL);
		tmp.put("amplienceAltSwatch", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.product.Product", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("amplienceAccountIdentifier", AttributeMode.INITIAL);
		tmp.put("amplienceImageHostname", AttributeMode.INITIAL);
		tmp.put("amplienceContentHostname", AttributeMode.INITIAL);
		tmp.put("amplienceAnalyticsCollectorId", AttributeMode.INITIAL);
		tmp.put("amplienceAnalyticsHostname", AttributeMode.INITIAL);
		tmp.put("amplienceScriptHostname", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.basecommerce.jalo.site.BaseSite", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceAccountIdentifier</code> attribute.
	 * @return the amplienceAccountIdentifier - The Amplience Account Identifier to use for this CMS Site.
	 */
	public String getAmplienceAccountIdentifier(final SessionContext ctx, final BaseSite item)
	{
		return (String)item.getProperty( ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCEACCOUNTIDENTIFIER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceAccountIdentifier</code> attribute.
	 * @return the amplienceAccountIdentifier - The Amplience Account Identifier to use for this CMS Site.
	 */
	public String getAmplienceAccountIdentifier(final BaseSite item)
	{
		return getAmplienceAccountIdentifier( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceAccountIdentifier</code> attribute. 
	 * @param value the amplienceAccountIdentifier - The Amplience Account Identifier to use for this CMS Site.
	 */
	public void setAmplienceAccountIdentifier(final SessionContext ctx, final BaseSite item, final String value)
	{
		item.setProperty(ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCEACCOUNTIDENTIFIER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceAccountIdentifier</code> attribute. 
	 * @param value the amplienceAccountIdentifier - The Amplience Account Identifier to use for this CMS Site.
	 */
	public void setAmplienceAccountIdentifier(final BaseSite item, final String value)
	{
		setAmplienceAccountIdentifier( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.amplienceAltSwatch</code> attribute.
	 * @return the amplienceAltSwatch - Flag that tells whether this product has a separate swatch image or if the primary image should be used.
	 */
	public Boolean isAmplienceAltSwatch(final SessionContext ctx, final Product item)
	{
		return (Boolean)item.getProperty( ctx, AmplienceDmConstants.Attributes.Product.AMPLIENCEALTSWATCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.amplienceAltSwatch</code> attribute.
	 * @return the amplienceAltSwatch - Flag that tells whether this product has a separate swatch image or if the primary image should be used.
	 */
	public Boolean isAmplienceAltSwatch(final Product item)
	{
		return isAmplienceAltSwatch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.amplienceAltSwatch</code> attribute. 
	 * @return the amplienceAltSwatch - Flag that tells whether this product has a separate swatch image or if the primary image should be used.
	 */
	public boolean isAmplienceAltSwatchAsPrimitive(final SessionContext ctx, final Product item)
	{
		Boolean value = isAmplienceAltSwatch( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.amplienceAltSwatch</code> attribute. 
	 * @return the amplienceAltSwatch - Flag that tells whether this product has a separate swatch image or if the primary image should be used.
	 */
	public boolean isAmplienceAltSwatchAsPrimitive(final Product item)
	{
		return isAmplienceAltSwatchAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.amplienceAltSwatch</code> attribute. 
	 * @param value the amplienceAltSwatch - Flag that tells whether this product has a separate swatch image or if the primary image should be used.
	 */
	public void setAmplienceAltSwatch(final SessionContext ctx, final Product item, final Boolean value)
	{
		item.setProperty(ctx, AmplienceDmConstants.Attributes.Product.AMPLIENCEALTSWATCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.amplienceAltSwatch</code> attribute. 
	 * @param value the amplienceAltSwatch - Flag that tells whether this product has a separate swatch image or if the primary image should be used.
	 */
	public void setAmplienceAltSwatch(final Product item, final Boolean value)
	{
		setAmplienceAltSwatch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.amplienceAltSwatch</code> attribute. 
	 * @param value the amplienceAltSwatch - Flag that tells whether this product has a separate swatch image or if the primary image should be used.
	 */
	public void setAmplienceAltSwatch(final SessionContext ctx, final Product item, final boolean value)
	{
		setAmplienceAltSwatch( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.amplienceAltSwatch</code> attribute. 
	 * @param value the amplienceAltSwatch - Flag that tells whether this product has a separate swatch image or if the primary image should be used.
	 */
	public void setAmplienceAltSwatch(final Product item, final boolean value)
	{
		setAmplienceAltSwatch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceAnalyticsCollectorId</code> attribute.
	 * @return the amplienceAnalyticsCollectorId - The Amplience analytics collector ID to use for this CMS Site.
	 */
	public String getAmplienceAnalyticsCollectorId(final SessionContext ctx, final BaseSite item)
	{
		return (String)item.getProperty( ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCEANALYTICSCOLLECTORID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceAnalyticsCollectorId</code> attribute.
	 * @return the amplienceAnalyticsCollectorId - The Amplience analytics collector ID to use for this CMS Site.
	 */
	public String getAmplienceAnalyticsCollectorId(final BaseSite item)
	{
		return getAmplienceAnalyticsCollectorId( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceAnalyticsCollectorId</code> attribute. 
	 * @param value the amplienceAnalyticsCollectorId - The Amplience analytics collector ID to use for this CMS Site.
	 */
	public void setAmplienceAnalyticsCollectorId(final SessionContext ctx, final BaseSite item, final String value)
	{
		item.setProperty(ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCEANALYTICSCOLLECTORID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceAnalyticsCollectorId</code> attribute. 
	 * @param value the amplienceAnalyticsCollectorId - The Amplience analytics collector ID to use for this CMS Site.
	 */
	public void setAmplienceAnalyticsCollectorId(final BaseSite item, final String value)
	{
		setAmplienceAnalyticsCollectorId( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceAnalyticsHostname</code> attribute.
	 * @return the amplienceAnalyticsHostname - The Amplience Analytics Hostname to use for this CMS Site.
	 */
	public String getAmplienceAnalyticsHostname(final SessionContext ctx, final BaseSite item)
	{
		return (String)item.getProperty( ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCEANALYTICSHOSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceAnalyticsHostname</code> attribute.
	 * @return the amplienceAnalyticsHostname - The Amplience Analytics Hostname to use for this CMS Site.
	 */
	public String getAmplienceAnalyticsHostname(final BaseSite item)
	{
		return getAmplienceAnalyticsHostname( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceAnalyticsHostname</code> attribute. 
	 * @param value the amplienceAnalyticsHostname - The Amplience Analytics Hostname to use for this CMS Site.
	 */
	public void setAmplienceAnalyticsHostname(final SessionContext ctx, final BaseSite item, final String value)
	{
		item.setProperty(ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCEANALYTICSHOSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceAnalyticsHostname</code> attribute. 
	 * @param value the amplienceAnalyticsHostname - The Amplience Analytics Hostname to use for this CMS Site.
	 */
	public void setAmplienceAnalyticsHostname(final BaseSite item, final String value)
	{
		setAmplienceAnalyticsHostname( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceContentHostname</code> attribute.
	 * @return the amplienceContentHostname - The Amplience Content Hostname to use for this CMS Site.
	 */
	public String getAmplienceContentHostname(final SessionContext ctx, final BaseSite item)
	{
		return (String)item.getProperty( ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCECONTENTHOSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceContentHostname</code> attribute.
	 * @return the amplienceContentHostname - The Amplience Content Hostname to use for this CMS Site.
	 */
	public String getAmplienceContentHostname(final BaseSite item)
	{
		return getAmplienceContentHostname( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceContentHostname</code> attribute. 
	 * @param value the amplienceContentHostname - The Amplience Content Hostname to use for this CMS Site.
	 */
	public void setAmplienceContentHostname(final SessionContext ctx, final BaseSite item, final String value)
	{
		item.setProperty(ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCECONTENTHOSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceContentHostname</code> attribute. 
	 * @param value the amplienceContentHostname - The Amplience Content Hostname to use for this CMS Site.
	 */
	public void setAmplienceContentHostname(final BaseSite item, final String value)
	{
		setAmplienceContentHostname( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceImageHostname</code> attribute.
	 * @return the amplienceImageHostname - The Amplience Image Hostname to use for this CMS Site.
	 */
	public String getAmplienceImageHostname(final SessionContext ctx, final BaseSite item)
	{
		return (String)item.getProperty( ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCEIMAGEHOSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceImageHostname</code> attribute.
	 * @return the amplienceImageHostname - The Amplience Image Hostname to use for this CMS Site.
	 */
	public String getAmplienceImageHostname(final BaseSite item)
	{
		return getAmplienceImageHostname( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceImageHostname</code> attribute. 
	 * @param value the amplienceImageHostname - The Amplience Image Hostname to use for this CMS Site.
	 */
	public void setAmplienceImageHostname(final SessionContext ctx, final BaseSite item, final String value)
	{
		item.setProperty(ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCEIMAGEHOSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceImageHostname</code> attribute. 
	 * @param value the amplienceImageHostname - The Amplience Image Hostname to use for this CMS Site.
	 */
	public void setAmplienceImageHostname(final BaseSite item, final String value)
	{
		setAmplienceImageHostname( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.amplienceImageStatus</code> attribute.
	 * @return the amplienceImageStatus - The Amplience image status for this product.
	 */
	public EnumerationValue getAmplienceImageStatus(final SessionContext ctx, final Product item)
	{
		return (EnumerationValue)item.getProperty( ctx, AmplienceDmConstants.Attributes.Product.AMPLIENCEIMAGESTATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.amplienceImageStatus</code> attribute.
	 * @return the amplienceImageStatus - The Amplience image status for this product.
	 */
	public EnumerationValue getAmplienceImageStatus(final Product item)
	{
		return getAmplienceImageStatus( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.amplienceImageStatus</code> attribute. 
	 * @param value the amplienceImageStatus - The Amplience image status for this product.
	 */
	public void setAmplienceImageStatus(final SessionContext ctx, final Product item, final EnumerationValue value)
	{
		item.setProperty(ctx, AmplienceDmConstants.Attributes.Product.AMPLIENCEIMAGESTATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.amplienceImageStatus</code> attribute. 
	 * @param value the amplienceImageStatus - The Amplience image status for this product.
	 */
	public void setAmplienceImageStatus(final Product item, final EnumerationValue value)
	{
		setAmplienceImageStatus( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceScriptHostname</code> attribute.
	 * @return the amplienceScriptHostname - The Amplience Script Hostname to use for this CMS Site.
	 */
	public String getAmplienceScriptHostname(final SessionContext ctx, final BaseSite item)
	{
		return (String)item.getProperty( ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCESCRIPTHOSTNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BaseSite.amplienceScriptHostname</code> attribute.
	 * @return the amplienceScriptHostname - The Amplience Script Hostname to use for this CMS Site.
	 */
	public String getAmplienceScriptHostname(final BaseSite item)
	{
		return getAmplienceScriptHostname( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceScriptHostname</code> attribute. 
	 * @param value the amplienceScriptHostname - The Amplience Script Hostname to use for this CMS Site.
	 */
	public void setAmplienceScriptHostname(final SessionContext ctx, final BaseSite item, final String value)
	{
		item.setProperty(ctx, AmplienceDmConstants.Attributes.BaseSite.AMPLIENCESCRIPTHOSTNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BaseSite.amplienceScriptHostname</code> attribute. 
	 * @param value the amplienceScriptHostname - The Amplience Script Hostname to use for this CMS Site.
	 */
	public void setAmplienceScriptHostname(final BaseSite item, final String value)
	{
		setAmplienceScriptHostname( getSession().getSessionContext(), item, value );
	}
	
	public AmplienceImageCarouselComponent createAmplienceImageCarouselComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AmplienceDmConstants.TC.AMPLIENCEIMAGECAROUSELCOMPONENT );
			return (AmplienceImageCarouselComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AmplienceImageCarouselComponent : "+e.getMessage(), 0 );
		}
	}
	
	public AmplienceImageCarouselComponent createAmplienceImageCarouselComponent(final Map attributeValues)
	{
		return createAmplienceImageCarouselComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public AmpliencePageScriptComponent createAmpliencePageScriptComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AmplienceDmConstants.TC.AMPLIENCEPAGESCRIPTCOMPONENT );
			return (AmpliencePageScriptComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AmpliencePageScriptComponent : "+e.getMessage(), 0 );
		}
	}
	
	public AmpliencePageScriptComponent createAmpliencePageScriptComponent(final Map attributeValues)
	{
		return createAmpliencePageScriptComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public AmplienceProductImageEmailReportCronJob createAmplienceProductImageEmailReportCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AmplienceDmConstants.TC.AMPLIENCEPRODUCTIMAGEEMAILREPORTCRONJOB );
			return (AmplienceProductImageEmailReportCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AmplienceProductImageEmailReportCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public AmplienceProductImageEmailReportCronJob createAmplienceProductImageEmailReportCronJob(final Map attributeValues)
	{
		return createAmplienceProductImageEmailReportCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public AmplienceProductImageStatusUpdateCronJob createAmplienceProductImageStatusUpdateCronJob(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AmplienceDmConstants.TC.AMPLIENCEPRODUCTIMAGESTATUSUPDATECRONJOB );
			return (AmplienceProductImageStatusUpdateCronJob)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AmplienceProductImageStatusUpdateCronJob : "+e.getMessage(), 0 );
		}
	}
	
	public AmplienceProductImageStatusUpdateCronJob createAmplienceProductImageStatusUpdateCronJob(final Map attributeValues)
	{
		return createAmplienceProductImageStatusUpdateCronJob( getSession().getSessionContext(), attributeValues );
	}
	
	public AmplienceResponsiveImageComponent createAmplienceResponsiveImageComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AmplienceDmConstants.TC.AMPLIENCERESPONSIVEIMAGECOMPONENT );
			return (AmplienceResponsiveImageComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AmplienceResponsiveImageComponent : "+e.getMessage(), 0 );
		}
	}
	
	public AmplienceResponsiveImageComponent createAmplienceResponsiveImageComponent(final Map attributeValues)
	{
		return createAmplienceResponsiveImageComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public AmplienceUGCCarouselComponent createAmplienceUGCCarouselComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AmplienceDmConstants.TC.AMPLIENCEUGCCAROUSELCOMPONENT );
			return (AmplienceUGCCarouselComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AmplienceUGCCarouselComponent : "+e.getMessage(), 0 );
		}
	}
	
	public AmplienceUGCCarouselComponent createAmplienceUGCCarouselComponent(final Map attributeValues)
	{
		return createAmplienceUGCCarouselComponent( getSession().getSessionContext(), attributeValues );
	}
	
	public AmplienceUGCMediaWallComponent createAmplienceUGCMediaWallComponent(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AmplienceDmConstants.TC.AMPLIENCEUGCMEDIAWALLCOMPONENT );
			return (AmplienceUGCMediaWallComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AmplienceUGCMediaWallComponent : "+e.getMessage(), 0 );
		}
	}
	
	public AmplienceUGCMediaWallComponent createAmplienceUGCMediaWallComponent(final Map attributeValues)
	{
		return createAmplienceUGCMediaWallComponent( getSession().getSessionContext(), attributeValues );
	}
	
	@Override
	public String getName()
	{
		return AmplienceDmConstants.EXTENSIONNAME;
	}
	
}
