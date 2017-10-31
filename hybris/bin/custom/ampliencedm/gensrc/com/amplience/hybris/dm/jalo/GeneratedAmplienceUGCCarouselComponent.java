/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 31-Oct-2017 15:38:49                        ---
 * ----------------------------------------------------------------
 */
package com.amplience.hybris.dm.jalo;

import com.amplience.hybris.dm.constants.AmplienceDmConstants;
import com.amplience.hybris.dm.jalo.AbstractAmplienceUGCComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.amplience.hybris.dm.jalo.AmplienceUGCCarouselComponent AmplienceUGCCarouselComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAmplienceUGCCarouselComponent extends AbstractAmplienceUGCComponent
{
	/** Qualifier of the <code>AmplienceUGCCarouselComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute **/
	public static final String CALLTOACTION = "callToAction";
	/** Qualifier of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute **/
	public static final String AUTOPLAY = "autoPlay";
	/** Qualifier of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute **/
	public static final String NUMBEROFSLIDES = "numberOfSlides";
	/** Qualifier of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute **/
	public static final String ENABLEMODAL = "enableModal";
	/** Qualifier of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute **/
	public static final String SHOWMODALTEXT = "showModalText";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(AbstractAmplienceUGCComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(CALLTOACTION, AttributeMode.INITIAL);
		tmp.put(AUTOPLAY, AttributeMode.INITIAL);
		tmp.put(NUMBEROFSLIDES, AttributeMode.INITIAL);
		tmp.put(ENABLEMODAL, AttributeMode.INITIAL);
		tmp.put(SHOWMODALTEXT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute.
	 * @return the autoPlay
	 */
	public Boolean isAutoPlay(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, AUTOPLAY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute.
	 * @return the autoPlay
	 */
	public Boolean isAutoPlay()
	{
		return isAutoPlay( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute. 
	 * @return the autoPlay
	 */
	public boolean isAutoPlayAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isAutoPlay( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute. 
	 * @return the autoPlay
	 */
	public boolean isAutoPlayAsPrimitive()
	{
		return isAutoPlayAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute. 
	 * @param value the autoPlay
	 */
	public void setAutoPlay(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, AUTOPLAY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute. 
	 * @param value the autoPlay
	 */
	public void setAutoPlay(final Boolean value)
	{
		setAutoPlay( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute. 
	 * @param value the autoPlay
	 */
	public void setAutoPlay(final SessionContext ctx, final boolean value)
	{
		setAutoPlay( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.autoPlay</code> attribute. 
	 * @param value the autoPlay
	 */
	public void setAutoPlay(final boolean value)
	{
		setAutoPlay( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute.
	 * @return the callToAction - The call to action text component
	 */
	public String getCallToAction(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAmplienceUGCCarouselComponent.getCallToAction requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CALLTOACTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute.
	 * @return the callToAction - The call to action text component
	 */
	public String getCallToAction()
	{
		return getCallToAction( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute. 
	 * @return the localized callToAction - The call to action text component
	 */
	public Map<Language,String> getAllCallToAction(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CALLTOACTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute. 
	 * @return the localized callToAction - The call to action text component
	 */
	public Map<Language,String> getAllCallToAction()
	{
		return getAllCallToAction( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute. 
	 * @param value the callToAction - The call to action text component
	 */
	public void setCallToAction(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAmplienceUGCCarouselComponent.setCallToAction requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CALLTOACTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute. 
	 * @param value the callToAction - The call to action text component
	 */
	public void setCallToAction(final String value)
	{
		setCallToAction( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute. 
	 * @param value the callToAction - The call to action text component
	 */
	public void setAllCallToAction(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CALLTOACTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.callToAction</code> attribute. 
	 * @param value the callToAction - The call to action text component
	 */
	public void setAllCallToAction(final Map<Language,String> value)
	{
		setAllCallToAction( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute.
	 * @return the enableModal
	 */
	public Boolean isEnableModal(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ENABLEMODAL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute.
	 * @return the enableModal
	 */
	public Boolean isEnableModal()
	{
		return isEnableModal( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute. 
	 * @return the enableModal
	 */
	public boolean isEnableModalAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isEnableModal( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute. 
	 * @return the enableModal
	 */
	public boolean isEnableModalAsPrimitive()
	{
		return isEnableModalAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute. 
	 * @param value the enableModal
	 */
	public void setEnableModal(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ENABLEMODAL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute. 
	 * @param value the enableModal
	 */
	public void setEnableModal(final Boolean value)
	{
		setEnableModal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute. 
	 * @param value the enableModal
	 */
	public void setEnableModal(final SessionContext ctx, final boolean value)
	{
		setEnableModal( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.enableModal</code> attribute. 
	 * @param value the enableModal
	 */
	public void setEnableModal(final boolean value)
	{
		setEnableModal( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute.
	 * @return the numberOfSlides
	 */
	public Integer getNumberOfSlides(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, NUMBEROFSLIDES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute.
	 * @return the numberOfSlides
	 */
	public Integer getNumberOfSlides()
	{
		return getNumberOfSlides( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute. 
	 * @return the numberOfSlides
	 */
	public int getNumberOfSlidesAsPrimitive(final SessionContext ctx)
	{
		Integer value = getNumberOfSlides( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute. 
	 * @return the numberOfSlides
	 */
	public int getNumberOfSlidesAsPrimitive()
	{
		return getNumberOfSlidesAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute. 
	 * @param value the numberOfSlides
	 */
	public void setNumberOfSlides(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, NUMBEROFSLIDES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute. 
	 * @param value the numberOfSlides
	 */
	public void setNumberOfSlides(final Integer value)
	{
		setNumberOfSlides( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute. 
	 * @param value the numberOfSlides
	 */
	public void setNumberOfSlides(final SessionContext ctx, final int value)
	{
		setNumberOfSlides( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.numberOfSlides</code> attribute. 
	 * @param value the numberOfSlides
	 */
	public void setNumberOfSlides(final int value)
	{
		setNumberOfSlides( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute.
	 * @return the showModalText
	 */
	public Boolean isShowModalText(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOWMODALTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute.
	 * @return the showModalText
	 */
	public Boolean isShowModalText()
	{
		return isShowModalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute. 
	 * @return the showModalText
	 */
	public boolean isShowModalTextAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShowModalText( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute. 
	 * @return the showModalText
	 */
	public boolean isShowModalTextAsPrimitive()
	{
		return isShowModalTextAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute. 
	 * @param value the showModalText
	 */
	public void setShowModalText(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOWMODALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute. 
	 * @param value the showModalText
	 */
	public void setShowModalText(final Boolean value)
	{
		setShowModalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute. 
	 * @param value the showModalText
	 */
	public void setShowModalText(final SessionContext ctx, final boolean value)
	{
		setShowModalText( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.showModalText</code> attribute. 
	 * @param value the showModalText
	 */
	public void setShowModalText(final boolean value)
	{
		setShowModalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.title</code> attribute.
	 * @return the title - The title for this component
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAmplienceUGCCarouselComponent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.title</code> attribute.
	 * @return the title - The title for this component
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.title</code> attribute. 
	 * @return the localized title - The title for this component
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AmplienceUGCCarouselComponent.title</code> attribute. 
	 * @return the localized title - The title for this component
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.title</code> attribute. 
	 * @param value the title - The title for this component
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAmplienceUGCCarouselComponent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.title</code> attribute. 
	 * @param value the title - The title for this component
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.title</code> attribute. 
	 * @param value the title - The title for this component
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AmplienceUGCCarouselComponent.title</code> attribute. 
	 * @param value the title - The title for this component
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
}
