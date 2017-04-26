/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.addon.controllers.misc;

import com.amplience.hybris.dm.addon.controllers.forms.AddToBasketForm;
import com.amplience.hybris.dm.data.AmplienceProductData;
import com.amplience.hybris.dm.data.AmplienceResourceData;
import com.amplience.hybris.dm.facade.AmplienceProductFacade;
import com.amplience.hybris.dm.facade.AmplienceResourceUrlFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.servicelayer.exceptions.BusinessException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller to handle requests from the Amplience ecommBridge.
 */
@Controller
public class AmplienceBridgeController extends AbstractController
{
	protected static final Logger LOG = LoggerFactory.getLogger(AmplienceBridgeController.class);

	@Resource(name = "amplienceProductFacade")
	private AmplienceProductFacade amplienceProductFacade;

	@Resource(name = "amplienceResourceUrlFacade")
	private AmplienceResourceUrlFacade amplienceResourceUrlFacade;

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	/**
	 * Get Amplience product data for a list of product codes.
	 *
	 * @param codes List of product codes to lookup.
	 * @return List of AmplienceProductData for each product.
	 */
	@RequestMapping(value = "/misc/amplience/product", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<AmplienceProductData> getProducts(@RequestParam(value = "code") final List<String> codes)
	{
		return amplienceProductFacade.getProductsByCode(codes);
	}

	/**
	 * Get Amplience resource data for specified resource.
	 *
	 * @param type    The type of resource to lookup.
	 * @param param   Optional type specific parameter.
	 * @param request The HTTP request
	 * @return The AmplienceResourceData for the requested resource.
	 */
	@RequestMapping(value = "/misc/amplience/resourceUrl", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AmplienceResourceData getResourceUrl(@RequestParam(value = "type") final String type, @RequestParam(value = "param", required = false) final String param, final HttpServletRequest request) throws BusinessException
	{
		return amplienceResourceUrlFacade.getResourceUrl(type, param, request.isSecure());
	}

	/**
	 * Add to basket handler that supports adding multiple products to the basket in a single call.
	 * <p>
	 * Accelerator storefront encodes ajax form posts using application/x-www-form-urlencoded to allow
	 * the XSRF token to be injected into the form. This means that the JSON encoded product data cannot
	 * be automatically deserialized from the post body but appears as a named string parameter.
	 * <p>
	 * The default Accelerator add to basket handler only supports adding a single product to the basket
	 * this handler supports adding different products to the basket in a single call.
	 * <p>
	 * This is achieved by adding each product to the basket in turn and therefore produces a list of
	 * CartModificationData entries. These are then all passed to the ampliencedmaddon/fragments/cart/multiAddToCartPopup
	 * template for them to be rendered into an HTML fragment.
	 *
	 * @param products Products to add to the basket - json encoded string
	 * @param model    The MVC model
	 * @return The view name to use to render the result of adding the products to the basket.
	 * @throws IOException if unable to decode the product data
	 */
	@RequestMapping(value = "/misc/amplience/addToBasket", method = RequestMethod.POST, produces = "application/json")
	public String addToBasket(@RequestParam(value = "products") final String products, final Model model) throws IOException
	{
		final ObjectMapper mapper = new ObjectMapper();
		final List<AddToBasketForm> productList = mapper.readValue(products, mapper.getTypeFactory().constructCollectionType(List.class, AddToBasketForm.class));

		final List<CartModificationData> cartModificationDatas = new ArrayList<>(productList.size());
		int failedProducts = 0;

		for (final AddToBasketForm addToBasketForm : productList)
		{
			try
			{
				final CartModificationData cartModificationData = cartFacade.addToCart(addToBasketForm.getId(), addToBasketForm.getQuantity());
				if (cartModificationData.getQuantityAdded() == 0L)
				{
					cartModificationData.setErrorMessage("basket.information.quantity.noItemsAdded." + cartModificationData.getStatusCode());
				}
				else if (cartModificationData.getQuantityAdded() < addToBasketForm.getQuantity())
				{
					cartModificationData.setErrorMessage("basket.information.quantity.reducedNumberOfItemsAdded." + cartModificationData.getStatusCode());
				}
				cartModificationDatas.add(cartModificationData);

			}
			catch (final CommerceCartModificationException e)
			{
				LOG.info("Cannot add product to basket", e);
				failedProducts++;
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.info("Unknown product [{}]", addToBasketForm.getId(), e);
				failedProducts++;
			}
		}

		model.addAttribute("cartModifications", cartModificationDatas);
		model.addAttribute("failedProductCount", failedProducts);

		return "addon:/ampliencedmaddon/fragments/cart/multiAddToCartPopup";
	}
}
