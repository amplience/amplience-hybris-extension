package com.amplience.hybris.dm.populators;

import com.amplience.hybris.dm.product.AmplienceIdentifierSanitizer;
import com.amplience.hybris.dm.product.AmplienceProductResolver;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.beans.factory.annotation.Required;

/**
 * Populate the product data with the amplience media set name
 */
public class ProductAmplienceMediaSetPopulator implements Populator<ProductModel, ProductData>
{
	private static final String MEDIA_SET_SUFFIX = "-ms";

	private AmplienceProductResolver amplienceProductResolver;
	private AmplienceIdentifierSanitizer amplienceIdentifierSanitizer;

	protected AmplienceProductResolver getAmplienceProductResolver()
	{
		return amplienceProductResolver;
	}

	@Required
	public void setAmplienceProductResolver(final AmplienceProductResolver amplienceProductResolver)
	{
		this.amplienceProductResolver = amplienceProductResolver;
	}

	protected AmplienceIdentifierSanitizer getAmplienceIdentifierSanitizer()
	{
		return amplienceIdentifierSanitizer;
	}

	@Required
	public void setAmplienceIdentifierSanitizer(final AmplienceIdentifierSanitizer amplienceIdentifierSanitizer)
	{
		this.amplienceIdentifierSanitizer = amplienceIdentifierSanitizer;
	}

	/**
	 * Populate the product data with the amplience media set name
	 *
	 * @param productModel the source product model
	 * @param productData the target product data
	 */
	@Override
	public void populate(final ProductModel productModel, final ProductData productData)
	{
		final ProductModel resolvedProduct = getAmplienceProductResolver().resolveProduct(productModel);
		if (resolvedProduct != null)
		{
			productData.setAmplienceMediaSet(getAmplienceIdentifierSanitizer().sanitize(resolvedProduct.getCode()) + MEDIA_SET_SUFFIX);
		}
	}
}
