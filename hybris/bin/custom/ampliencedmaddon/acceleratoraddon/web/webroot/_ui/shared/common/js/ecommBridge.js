/*
 *	Copyright (c) 2016 Amplience
 */

if (!window.ecommBridge)
{
	window.ecommBridge = {};
}
window.ecommBridge = {

	accountConfig: {},

	capability: {
		quickView: true,
		getProduct: true,
		url: true,
		wishList: false,
		transactional: true
	},

	user: {},

	site: {
		page: {},
		locale: "",
		currency: {},

		/**
		 * Get the URL to a type of resource.
		 *
		 * @param {{type: string, parameter: string?}} urlInfo - the URL to lookup
		 * @param {function({state: string, response:string}|undefined, object)} callback - A callback function that will be called with the result.
		 */
		getUrl: function (urlInfo, callback)
		{
			urlInfo = urlInfo || {};
			var type = urlInfo["type"];
			var param = urlInfo["parameter"];

			if (typeof type !== 'string' || type === '')
			{
				if (typeof callback === 'function')
				{
					return callback.call(ecommBridge, {state: 'error', response: 'Invalid parameters, type missing'}, null);
				}
				return;
			}

			$.ajax({
					method: "GET",
					url: ACC.config.contextPath + '/misc/amplience/resourceUrl',
					data: {type: type, param: param},
					traditional: true,
					dataType: "json"
				})
				// success
				.done(function (response)
				{
					if (typeof callback === 'function')
					{
						callback.call(ecommBridge, null, response.url);
					}
				})
				// failed
				.fail(function (xhr, textStatus)
				{
					if (typeof callback === 'function')
					{
						callback.call(ecommBridge, {state: 'error', response: xhr.responseText, textStatus: textStatus}, null);
					}
				});
		},

		/**
		 * Get the ProductData for a list of products.
		 *
		 * The products parameter should be an array of objects which have an *id* property which specifies the product to lookup.
		 *
		 * The callback parameter should be a function that will be called with the result of the operation. The callback should
		 * have 2 parameters. The first is an error object, or undefined if successful. On success the second parameter contains
		 * the results of the lookup, on error the value depends on the type of error returned.
		 *
		 * @param {{id:string}[]} products - The products to lookup.
		 * @param {function({state: string, response:string}|undefined, object)} callback - A callback function that will be called with the result.
		 */
		getProduct: function (products, callback)
		{
			var ids = [];

			if ($.isArray(products))
			{
				// Handle array
				$.each(products, function (index, item)
				{
					if (typeof item === 'object' && 'id' in item)
					{
						// Array item is object containing id
						ids.push(item['id']);
					}
				});
			}

			// Check we found some IDs to query for
			if (ids.length === 0)
			{
				if (typeof callback === 'function')
				{
					return callback.call(ecommBridge, {state: 'error', response: 'Invalid parameters, no products.ids found!'}, null);
				}
				return;
			}

			$.ajax({
					method: "GET",
					url: ACC.config.contextPath + '/misc/amplience/product',
					data: {code: ids},
					traditional: true,
					dataType: "json"
				})
				// success
				.done(function (response)
				{
					if (typeof callback === 'function')
					{
						callback.call(ecommBridge, null, response);
					}
				})
				// failed
				.fail(function (xhr, textStatus)
				{
					if (typeof callback === 'function')
					{
						callback.call(ecommBridge, {state: 'error', response: xhr.responseText, textStatus: textStatus}, null);
					}
				});
		},

		/**
		 * Bind an event handler to receive events from the ecommBridge.
		 *
		 * Only the productChanged event is supported
		 *
		 * @param {string} eventName - The event to bind to.
		 * @param {function({state: string, response:string}|undefined, object)} callback - A callback function that will be called when the event occurs.
		 */
		bind: function (eventName, callback)
		{
			if (typeof callback == 'function')
			{
				if (eventName == "productChanged")
				{
					$(window).bind("productChanged", function (event, response, textStatus, responseText)
					{
						if (response !== null)
						{
							callback.call(ecommBridge, null, {id: event.productCode, mediaSet: event.mediaSet});
						}
						else
						{
							callback.call(ecommBridge, {state: 'error', response: responseText}, null);
						}
					});
				}
			}
		}
	},

	interaction: {

		/**
		 * Launch a QuickView for the specified product.
		 *
		 * @param {string} productIdentifier - A product identifier or SKU identifier
		 */
		quickview: function (productIdentifier)
		{
			console.log("EcommBridge launchQuickview not supported")

			ecommBridge.site.getUrl({type: 'product', parameter: productIdentifier}, function (error, productUrl)
			{
				if (error)
				{
					console.log("EcommBridge launchQuickview error getting product url. " + error);
				}
				else
				{
					$.colorbox({
						href: productUrl + '/quickView',
						close: '<span class="glyphicon glyphicon-remove"></span>',
						title: $('#quickViewTitle').html(),
						maxWidth: "100%",
						onComplete: function ()
						{
							ACC.quickview.refreshScreenReaderBuffer();
							ACC.quickview.initQuickviewLightbox();
							ACC.ratingstars.bindRatingStars($(".quick-view-stars"));
						},
						onClosed: function ()
						{
							ACC.quickview.refreshScreenReaderBuffer();
						}
					});
				}
			});
		},

		/**
		 * Add products to the Basket.
		 *
		 * @param {{id:string,quantity:number?}[]} products - The products to to add to the basket.
		 * @param {function({state: string, response:string}|undefined, object)} callback - A callback function that will be called when the event occurs.
		 */
		addToBasket: function (products, callback)
		{
			if (!$.isArray(products))
			{
				if (typeof callback === 'function')
				{
					return callback.call(ecommBridge, {state: 'error', response: 'Invalid parameters, no products found!'}, null);
				}
				return;
			}

			// Because this is a POST the CSRFToken will be automatically added, therefore we must use contentType x-www-form-urlencoded
			$.ajax({
					method: "POST",
					url: ACC.config.contextPath + '/misc/amplience/addToBasket',
					data: {products: JSON.stringify(products)},
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					dataType: "json"
				})
				// success
				.done(function (response)
				{
					// process the HTML popup
					ACC.product.displayAddToCartPopup(response);

					if (typeof callback === 'function')
					{
						callback.call(ecommBridge, null, response);
					}
				})
				// failed
				.fail(function (xhr, textStatus)
				{
					if (typeof callback === 'function')
					{
						callback.call(ecommBridge, {state: 'error', response: xhr.responseText, textStatus: textStatus}, null);
					}
				});
		},

		/**
		 * Add products to the WishList.
		 *
		 * Not supported functionality by default. To enable implement this method and set *ecommBridge.capability.wishList* to true.
		 *
		 * @param {{id:string,quantity:number?}[]} products - The products to to add to the wishlist.
		 * @param {function({state: string, response:string}|undefined, object)} callback - A callback function that will be called when the event occurs.
		 */
		addToWishList: function (products, callback)
		{
			if (typeof callback === 'function')
			{
				return callback.call(ecommBridge, {state: 'error', response: 'EcommBridge addToWishList not supported operation.'}, null);
			}
			return;
		}
	}
};
