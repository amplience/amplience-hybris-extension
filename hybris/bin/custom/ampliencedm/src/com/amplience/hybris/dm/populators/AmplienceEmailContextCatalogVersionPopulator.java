/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.populators;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.catalog.data.CatalogVersionData;
import de.hybris.platform.converters.Populator;


/**
 * Populate the catalog version data with the catalog name and version.
 * Used as part of the product missing images report email generation.
 */
public class AmplienceEmailContextCatalogVersionPopulator implements Populator<CatalogVersionModel, CatalogVersionData>
{
	/**
	 * Populate the catalog version data with the catalog name and version.
	 *
	 * @param source the source catalog version
	 * @param target the target catalog version data
	 */
	@Override
	public void populate(final CatalogVersionModel source, final CatalogVersionData target)
	{
		target.setName(source.getCatalog().getName());
		target.setId(source.getVersion());
	}
}
