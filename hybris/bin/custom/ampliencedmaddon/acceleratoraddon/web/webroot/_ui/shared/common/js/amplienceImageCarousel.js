/*
 *	Copyright (c) 2016 Amplience
 */

function createAmplienceHorizontalCarousel(clientId, diBasePath, setName, elementId)
{
	ampx.init({
		client_id: clientId,
		di_basepath: diBasePath
	});

	ampx.get([{"name": setName, "type": "s"}], function (data)
	{
		ampx.genHTML(ampx.di.set(data[setName], {h: 400, w: 780, sm: 'CM'}), $(elementId + " .amp-main")[0], true);
		ampx.genHTML(ampx.di.set(data[setName], {h: 80, w: 150, sm: 'CM'}), $(elementId + " .amp-carousel")[0], true);
		$(elementId + ' .amp-nav img').ampImage();

		$(elementId + ' .amp-nav #' + setName).ampxCarousel({
			height: 100
		});

		$(elementId + ' .amp-main #' + setName).bind('ampxcarouselcreated ampxcarouselchange', function (e, data)
		{
			$(elementId + ' .amp-nav #' + setName + ' div').children().removeClass('current');
			$(elementId + ' .amp-nav #' + setName + ' div').children().eq(data.index - 1).addClass('current');
			$('.message').text((data.index) + " / " + data.count)
		});


		$(elementId + ' .amp-main #' + setName).ampxCarousel({
			width: 370,
			height: 180
		});

		$(elementId + ' .amp-nav #' + setName).ampNav({
			on: "goTo",
			action: "select",
			selector: ".amp-main #" + setName
		});

		$(elementId + ' .amp-main img').ampZoom({
			translations: 'sm=CM'
		});

		$(elementId + ' .amp-nav .prev').bind('click', function (e)
		{
			$('.amp-nav #' + setName).ampxCarousel('prev');
		});

		$(elementId + ' .amp-nav .next').bind('click', function (e)
		{
			$('.amp-nav #' + setName).ampxCarousel('next');
		});

	});
}
