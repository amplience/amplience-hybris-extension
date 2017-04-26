package com.amplience.hybris.dm.addon.web.renderer;

import com.amplience.hybris.dm.addon.constants.AmplienceDmAddonConstants;
import de.hybris.platform.addonsupport.renderer.impl.DefaultAddOnCMSComponentRenderer;
import de.hybris.platform.cms2.model.contents.components.AbstractCMSComponentModel;

import javax.servlet.jsp.PageContext;
import java.util.Map;

/**
 * Custom DefaultAddOnCMSComponentRenderer where the ampliencedmaddon extension contains the CMS component render template.
 *
 * The DefaultAddOnCMSComponentRenderer assumes that a cms component will be rendered by the same
 * extension that defined the component type. In this case the cms component types are defined in the
 * ampliencedm extension but the renderer is the ampliencedmaddon.
 *
 * The DefaultAddOnCMSComponentRenderer exposes all the component's attributes to the renderer template
 * however it does not expose the cms component itself to the template. The component is added to the
 * list of variables to expose to the template here.
 */
public class AmplienceAddOnCMSComponentRenderer extends DefaultAddOnCMSComponentRenderer
{
	@Override
	protected String getAddonUiExtensionName(final AbstractCMSComponentModel component)
	{
		return AmplienceDmAddonConstants.EXTENSIONNAME;
	}

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final AbstractCMSComponentModel component)
	{
		final Map<String, Object> variablesToExpose = super.getVariablesToExpose(pageContext, component);
		variablesToExpose.put("component", component);
		return variablesToExpose;
	}
}
