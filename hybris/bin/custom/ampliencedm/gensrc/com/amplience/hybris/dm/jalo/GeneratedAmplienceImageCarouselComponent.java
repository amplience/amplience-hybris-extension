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
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.amplience.hybris.dm.jalo.AmplienceImageCarouselComponent AmplienceImageCarouselComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAmplienceImageCarouselComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>AmplienceImageCarouselComponent.setName</code> attribute **/
	public static final String SETNAME = "setName";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(SETNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceImageCarouselComponent.setName</code> attribute.
	 * @return the setName - The name of the media set
	 */
	public String getSetName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SETNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceImageCarouselComponent.setName</code> attribute.
	 * @return the setName - The name of the media set
	 */
	public String getSetName()
	{
		return getSetName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceImageCarouselComponent.setName</code> attribute. 
	 * @param value the setName - The name of the media set
	 */
	public void setSetName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SETNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceImageCarouselComponent.setName</code> attribute. 
	 * @param value the setName - The name of the media set
	 */
	public void setSetName(final String value)
	{
		setSetName( getSession().getSessionContext(), value );
	}
	
}
