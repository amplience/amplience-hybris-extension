/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.addon.controllers.misc;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller to handle requests for the Instagram Callback.
 */
@Controller
public class InstagramCallbackController extends AbstractController
{
	/**
	 * Respond with page content required to authenticate instagram.
	 *
	 * @return view name with template content.
	 */
	@RequestMapping(value = "/misc/amplience/instagram.html", method = RequestMethod.GET)
	public String getInstagramAuthHtml()
	{
		return "addon:/ampliencedmaddon/pages/misc/instagramAuth";
	}
}
