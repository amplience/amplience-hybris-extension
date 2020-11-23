/*
 * Copyright (c) 2016-2020 Amplience
 */
package com.amplience.hybris.dm.accelerator.demo.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Bean that sets system properties to allow Java SMTP to use modern TLS protocols on init
 */
public class EmailPropertiesSetupBean implements InitializingBean
{
	private static final Logger LOG = LoggerFactory.getLogger(EmailPropertiesSetupBean.class);

	@Override
	public void afterPropertiesSet() throws Exception
	{
		// Set system properties to allow Java SMTP to use modern TLS protocols
		// Update this to include future TLS versions when they are supported
		LOG.info("Setting system properties to to allow Java SMTP to use modern TLS protocols");
		System.setProperty("mail.smtp.ssl.protocols", "TLSv1.1 TLSv1.2");
		System.setProperty("mail.smtps.ssl.protocols", "TLSv1.1 TLSv1.2");
	}
}
