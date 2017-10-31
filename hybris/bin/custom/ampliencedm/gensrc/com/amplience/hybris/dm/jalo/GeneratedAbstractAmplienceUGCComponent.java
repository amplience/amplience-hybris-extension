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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.amplience.hybris.dm.jalo.AbstractAmplienceUGCComponent AbstractAmplienceUGCComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAbstractAmplienceUGCComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>AbstractAmplienceUGCComponent.streamID</code> attribute **/
	public static final String STREAMID = "streamID";
	/** Qualifier of the <code>AbstractAmplienceUGCComponent.hashtag</code> attribute **/
	public static final String HASHTAG = "hashtag";
	/** Qualifier of the <code>AbstractAmplienceUGCComponent.tags</code> attribute **/
	public static final String TAGS = "tags";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(STREAMID, AttributeMode.INITIAL);
		tmp.put(HASHTAG, AttributeMode.INITIAL);
		tmp.put(TAGS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractAmplienceUGCComponent.hashtag</code> attribute.
	 * @return the hashtag - The primary hashtag for uploaded content
	 */
	public String getHashtag(final SessionContext ctx)
	{
		return (String)getProperty( ctx, HASHTAG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractAmplienceUGCComponent.hashtag</code> attribute.
	 * @return the hashtag - The primary hashtag for uploaded content
	 */
	public String getHashtag()
	{
		return getHashtag( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractAmplienceUGCComponent.hashtag</code> attribute. 
	 * @param value the hashtag - The primary hashtag for uploaded content
	 */
	public void setHashtag(final SessionContext ctx, final String value)
	{
		setProperty(ctx, HASHTAG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractAmplienceUGCComponent.hashtag</code> attribute. 
	 * @param value the hashtag - The primary hashtag for uploaded content
	 */
	public void setHashtag(final String value)
	{
		setHashtag( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractAmplienceUGCComponent.streamID</code> attribute.
	 * @return the streamID - The Amplience UGC Stream ID to show in this component.
	 */
	public String getStreamID(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STREAMID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractAmplienceUGCComponent.streamID</code> attribute.
	 * @return the streamID - The Amplience UGC Stream ID to show in this component.
	 */
	public String getStreamID()
	{
		return getStreamID( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractAmplienceUGCComponent.streamID</code> attribute. 
	 * @param value the streamID - The Amplience UGC Stream ID to show in this component.
	 */
	public void setStreamID(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STREAMID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractAmplienceUGCComponent.streamID</code> attribute. 
	 * @param value the streamID - The Amplience UGC Stream ID to show in this component.
	 */
	public void setStreamID(final String value)
	{
		setStreamID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractAmplienceUGCComponent.tags</code> attribute.
	 * @return the tags - The secondary tags for uploaded content
	 */
	public Collection<String> getTags(final SessionContext ctx)
	{
		Collection<String> coll = (Collection<String>)getProperty( ctx, TAGS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbstractAmplienceUGCComponent.tags</code> attribute.
	 * @return the tags - The secondary tags for uploaded content
	 */
	public Collection<String> getTags()
	{
		return getTags( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractAmplienceUGCComponent.tags</code> attribute. 
	 * @param value the tags - The secondary tags for uploaded content
	 */
	public void setTags(final SessionContext ctx, final Collection<String> value)
	{
		setProperty(ctx, TAGS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbstractAmplienceUGCComponent.tags</code> attribute. 
	 * @param value the tags - The secondary tags for uploaded content
	 */
	public void setTags(final Collection<String> value)
	{
		setTags( getSession().getSessionContext(), value );
	}
	
}
