/*
 *	Copyright (c) 2016 Amplience
 */

/*
 * Script that makes the Amplience components start
 */

Amplience = {};

Amplience.Dm = {
	bindComponents: function (context)
	{
		$(".amplience-standard-pdp-viewer", context).each(function (index, value)
		{
			var options = $(value).data("options");

			new amp.StandardViewerPdp(
				"#" + $(value).attr('id'),
				window.ecommBridge.accountConfig,
				options.playerLayout,
				options.video,
				options.spin,
				options.zoom,
				options.transformations);
		});

		$(".amplience-renderKit-set-viewer", context).each(function (index, value)
		{
			var options = $(value).data("options");

			options.target = "#" + $(value).attr('id');
			options.client = window.ecommBridge.accountConfig.client;
			options.imageBasePath = window.ecommBridge.accountConfig.imageBasePath;
			options.locale = window.ecommBridge.site.locale;
			options.cacheControl = Date.now();
			options.ecommBridge = false;

			options.errCallback = function () {
				console.log('renderKit something went wrong loading the set');
			};

			new amp.Viewer(options);
		});

		$(".amplience-ugc-carousel-viewer", context).each(function (index, value)
		{
			var options = $(value).data("options");

			options.viewer.appendTo = $(value);

			new amp.Carousel(options.account, options.viewer, options.modal, options.uploadModal, options.upload, options.instagramUpload);
		});

		$(".amplience-ugc-mediawall-viewer", context).each(function (index, value)
		{
			var options = $(value).data("options");

			options.viewer.appendTo = $(value);

			new amp.Mediawall(options.account, options.viewer, options.modal, options.uploadModal, options.upload, options.instagramUpload);
		});

		$(".amplience-carousel-viewer", context).each(function (index, value)
		{
			var options = $(value).data("options");

			var elementId = "#" + $(value).attr('id');

			createAmplienceHorizontalCarousel(options.clientId, options.diBasePath, options.setName, elementId);
		});
	}
};

$(function ()
{
	// Bind all the components that are available now
	Amplience.Dm.bindComponents(null);

	// Bind to any components loaded into the colorbox popup
	$(document).bind('cbox_complete', function ()
	{
		Amplience.Dm.bindComponents($("#colorbox"));
	});
});
