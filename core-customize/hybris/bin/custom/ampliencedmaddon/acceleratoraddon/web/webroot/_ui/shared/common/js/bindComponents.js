/*
 *	Copyright (c) 2016-2020 Amplience
 */

/*
 * Script that makes the Amplience components start
 */

Amplience = {};

Amplience.Dm = {
	bindComponents: function (context) {
		$(".amplience-viewerKit-product-gallery", context).each(function (index, value) {
			const options = $(value).data("options");
			options.target = "#" + $(value).attr('id');
			options.cacheControl = Date.now();
			options.secure = ('https:' === document.location.protocol);
			options.errCallback = function () {
				console.log('viewer-kit something went wrong loading the set');
			};
			new amp.Viewer(options);
		});

	}
};

$(function () {
	// Bind all the components that are available now
	Amplience.Dm.bindComponents(null);

	// Bind to any components loaded into the colorbox popup
	$(document).bind('cbox_complete', function () {
		Amplience.Dm.bindComponents($("#colorbox"));
	});
});
