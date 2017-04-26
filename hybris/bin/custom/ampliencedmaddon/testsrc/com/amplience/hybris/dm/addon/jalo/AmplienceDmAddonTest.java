/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.addon.jalo;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.testframework.HybrisJUnit4TransactionalTest;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * JUnit Tests for the Ampliencedmaddon extension
 */
@IntegrationTest
public class AmplienceDmAddonTest extends HybrisJUnit4TransactionalTest
{
	/** Edit the local|project.properties to change logging behaviour (properties log4j.*). */
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AmplienceDmAddonTest.class.getName());

	@Before
	public void setUp()
	{
		// implement here code executed before each test
	}

	@After
	public void tearDown()
	{
		// implement here code executed after each test
	}

	/**
	 * This is a sample test method.
	 */
	@Test
	public void testAmplienceDmAddon()
	{
		final boolean testTrue = true;
		assertTrue("true is not true", testTrue);
	}
}
