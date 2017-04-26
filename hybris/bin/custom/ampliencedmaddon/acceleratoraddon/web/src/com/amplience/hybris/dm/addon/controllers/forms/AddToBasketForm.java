/*
 * Copyright (c) 2016 Amplience
 */

package com.amplience.hybris.dm.addon.controllers.forms;

/**
 * Form used to encode product data to add to the basket for the
 * AmplienceBridgeController#addToBasket call.
 *
 * Represents product SKU and Quantity.
 */
public class AddToBasketForm
{
	private String id;
	private int quantity = 1;

	public String getId()
	{
		return id;
	}

	public void setId(final String id)
	{
		this.id = id;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(final int quantity)
	{
		this.quantity = quantity;
	}
}
