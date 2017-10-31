/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 31-Oct-2017 15:38:49                        ---
 * ----------------------------------------------------------------
 */
package com.amplience.hybris.dm.jalo;

import com.amplience.hybris.dm.constants.AmplienceDmConstants;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.amplience.hybris.dm.jalo.AmplienceResponsiveImageComponent AmplienceResponsiveImageComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAmplienceResponsiveImageComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>AmplienceResponsiveImageComponent.imageName</code> attribute **/
	public static final String IMAGENAME = "imageName";
	/** Qualifier of the <code>AmplienceResponsiveImageComponent.params</code> attribute **/
	public static final String PARAMS = "params";
	/** Qualifier of the <code>AmplienceResponsiveImageComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>AmplienceResponsiveImageComponent.alt</code> attribute **/
	public static final String ALT = "alt";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(IMAGENAME, AttributeMode.INITIAL);
		tmp.put(PARAMS, AttributeMode.INITIAL);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(ALT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.alt</code> attribute.
	 * @return the alt - The alternate text of the image
	 */
	public String getAlt(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAmplienceResponsiveImageComponent.getAlt requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ALT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.alt</code> attribute.
	 * @return the alt - The alternate text of the image
	 */
	public String getAlt()
	{
		return getAlt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.alt</code> attribute. 
	 * @return the localized alt - The alternate text of the image
	 */
	public Map<Language,String> getAllAlt(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ALT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.alt</code> attribute. 
	 * @return the localized alt - The alternate text of the image
	 */
	public Map<Language,String> getAllAlt()
	{
		return getAllAlt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.alt</code> attribute. 
	 * @param value the alt - The alternate text of the image
	 */
	public void setAlt(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAmplienceResponsiveImageComponent.setAlt requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ALT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.alt</code> attribute. 
	 * @param value the alt - The alternate text of the image
	 */
	public void setAlt(final String value)
	{
		setAlt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.alt</code> attribute. 
	 * @param value the alt - The alternate text of the image
	 */
	public void setAllAlt(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ALT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.alt</code> attribute. 
	 * @param value the alt - The alternate text of the image
	 */
	public void setAllAlt(final Map<Language,String> value)
	{
		setAllAlt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.imageName</code> attribute.
	 * @return the imageName - The name of the image
	 */
	public String getImageName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, IMAGENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.imageName</code> attribute.
	 * @return the imageName - The name of the image
	 */
	public String getImageName()
	{
		return getImageName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.imageName</code> attribute. 
	 * @param value the imageName - The name of the image
	 */
	public void setImageName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, IMAGENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.imageName</code> attribute. 
	 * @param value the imageName - The name of the image
	 */
	public void setImageName(final String value)
	{
		setImageName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.params</code> attribute.
	 * @return the params - Extra parameters to add to the request url
	 */
	public String getParams(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PARAMS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.params</code> attribute.
	 * @return the params - Extra parameters to add to the request url
	 */
	public String getParams()
	{
		return getParams( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.params</code> attribute. 
	 * @param value the params - Extra parameters to add to the request url
	 */
	public void setParams(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PARAMS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.params</code> attribute. 
	 * @param value the params - Extra parameters to add to the request url
	 */
	public void setParams(final String value)
	{
		setParams( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.title</code> attribute.
	 * @return the title - The title of the image
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAmplienceResponsiveImageComponent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.title</code> attribute.
	 * @return the title - The title of the image
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.title</code> attribute. 
	 * @return the localized title - The title of the image
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceResponsiveImageComponent.title</code> attribute. 
	 * @return the localized title - The title of the image
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.title</code> attribute. 
	 * @param value the title - The title of the image
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAmplienceResponsiveImageComponent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.title</code> attribute. 
	 * @param value the title - The title of the image
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.title</code> attribute. 
	 * @param value the title - The title of the image
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceResponsiveImageComponent.title</code> attribute. 
	 * @param value the title - The title of the image
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
}
