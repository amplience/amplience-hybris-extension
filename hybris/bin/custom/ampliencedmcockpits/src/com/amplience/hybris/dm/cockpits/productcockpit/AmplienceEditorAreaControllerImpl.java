/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.cockpits.productcockpit;

import de.hybris.platform.cockpit.components.sectionpanel.AbstractSectionPanelModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.productcockpit.session.impl.EditorAreaControllerImpl;
import de.hybris.platform.servicelayer.exceptions.ModelLoadingException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

/**
 * Amplience specific version of the product cockpit's EditorAreaControllerImpl that uses
 * the product's AmplienceThumbnailUrl to get the preview image URL to display.
 */
public class AmplienceEditorAreaControllerImpl extends EditorAreaControllerImpl
{
	private static final Logger LOG = Logger.getLogger(AmplienceEditorAreaControllerImpl.class);

	@Override
	protected void updateLabelForProduct(final Product product, final AbstractSectionPanelModel model)
	{
		super.updateLabelForProduct(product, model);

		final String imageUrl = getProductThumbnailUrl(product);
		model.setImageUrl(StringUtils.isBlank(imageUrl) ? null : imageUrl);
	}

	protected String getProductThumbnailUrl(final Product product)
	{
		try
		{
			//get the thumbnail URL
			final ModelService modelService = Registry.getCoreApplicationContext().getBean("modelService", ModelService.class);
			final ProductModel productModel = modelService.get(product);
			try
			{
				return productModel.getAmplienceThumbnailUrl();
			}
			finally
			{
				modelService.detach(productModel);
			}
		}
		catch (final BeansException | ModelLoadingException e)
		{
			LOG.warn("Cannot get the thumbnail URL", e);
		}
		return null;
	}
}
