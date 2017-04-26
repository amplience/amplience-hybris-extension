/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.backoffice.widgets;

import com.hybris.cockpitng.util.DefaultWidgetController;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Iframe;

/**
 * IFrame Widget Controller.
 *
 * Widget that uses an iframe to load externally hosted content into the backoffice.
 * The iframe src is loaded from the property named with the *urlkey* setting on the widget.
 */
public class IFrameController extends DefaultWidgetController
{
	@WireVariable
	private ConfigurationService configurationService;

	private Iframe widgetContent;

	@Override
	public void initialize(final Component comp)
	{
		super.initialize(comp);

		final String urlKey = getWidgetSettings().getString("urlkey");
		final String url = configurationService.getConfiguration().getString(urlKey);
		widgetContent.setSrc(url);
	}
}
