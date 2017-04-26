<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="escape" uri="http://amplience.com/hybris/tld/escape" %>

<!--
Begin Amplience ecommBridge
-->

<%-- Populate the dynamic elements of the ecommBridge on every page --%>
<script>
	if (!window.ecommBridge)
	{
		window.ecommBridge = {};
	}

	window.ecommBridge.accountConfig = {
		client: "${escape:js(amplienceConfig.accountIdentifier)}",
		imageBasePath: "//${escape:js(amplienceConfig.imageHostname)}/",
		contentBasePath: "//${escape:js(amplienceConfig.contentHostname)}/"
	};

	window.ecommBridge.site.locale = "${escape:js(amplienceLocales)}";

	window.ecommBridge.site.currency = {
		"code": "${escape:js(currentCurrency.isocode)}",
		"prefix": "${escape:js(currencyPrefix)}",
		"suffix": "${escape:js(currencySuffix)}"
	};

	window.ecommBridge.site.page = {
		<%-- Page Type Specific Data --%>
		<c:choose>

		<c:when test="${pageType == 'PRODUCT'}">
		"type": "product",
		"id": "${escape:js(product.code)}",
		"name": "${escape:js(product.name)}",
		"mediaSet": "${escape:js(product.amplienceMediaSet)}",
		</c:when>

		<c:when test="${pageType == 'CATEGORY'}">
		"type": "category",
		"id": "${escape:js(categoryCode)}",
		"name": "${escape:js(categoryName)}",
		"taxonomy": "${escape:js(categoryPath)}"
		</c:when>

		<c:when test="${pageType == 'PRODUCTSEARCH'}">
		"type": "search",
		"query": "${escape:js(searchPageData.freeTextSearch)}"
		</c:when>

		<c:when test="${pageType == 'CART'}">
		"type": "basket",
		"products": [
			<c:forEach items="${cartData.entries}" var="entry" varStatus="status">
			{
				"id": "${escape:js(entry.product.code)}",
				"qty": "${escape:js(entry.quantity)}"
			}
			${status.last ? '' : ','}
			</c:forEach>
		]
		</c:when>

		<c:when test="${pageType == 'ORDERCONFIRMATION'}">
		"type": "checkout-confirmation",
		"orderNumber": "${escape:js(orderData.code)}",
		"products": [
			<c:forEach items="${orderData.entries}" var="entry" varStatus="status">
			{
				"id": "${escape:js(entry.product.code)}",
				"qty": "${escape:js(entry.quantity)}"
			}
			${status.last ? '' : ','}
			</c:forEach>
		]
		</c:when>

		<c:otherwise>
		"type": "${cmsPage.homepage ? 'home' : 'standalone-page'}",
		"id": "${escape:js(cmsPage.uid)}",
		"name": "${escape:js(cmsPage.name)}"
		</c:otherwise>

		</c:choose>
	};
</script>

<%-- Load Amplience Scripts that require the ecommBridge to be setup before they can be loaded --%>

<c:if test="${requestScope.enableAmplienceCarouselJavascript}">
<%-- Carousel Scripts --%>
<script type="text/javascript" src="${contextPath}/_ui/addons/ampliencedmaddon/shared/common/js/amplience-sdk-client-0.1.2.js"></script>
<script type="text/javascript" src="${contextPath}/_ui/addons/ampliencedmaddon/shared/common/js/amplienceImageCarousel.js"></script>
</c:if>

<c:if test="${requestScope.enableAmplienceStandardPdpViewerJavascript}">
<%-- Standard PDP Viewer Scripts --%>
<script type="text/javascript" src="//${amplienceConfig.scriptHostname}/viewers/standard/pdp/amp-standard-viewer-pdp-libs.min.js"></script>
<script type="text/javascript" src="//${amplienceConfig.scriptHostname}/viewers/standard/pdp-fullscreen/amp-fullscreen-viewer-pdp.min.js"></script>
<script type="text/javascript" src="//${amplienceConfig.scriptHostname}/viewers/standard/pdp/amp-standard-viewer-pdp.min.js"></script>
</c:if>

<c:if test="${requestScope.enableAmplienceUgcJavascript}">
<%-- UGC Carousel & MediaWall Viewer Scripts --%>
<script type="text/javascript" src="//${amplienceConfig.scriptHostname}/viewers/ugc/shared/v2.8.0/shared.min.js"></script>
<script type="text/javascript" src="//${amplienceConfig.scriptHostname}/viewers/ugc/carousel/v2.8.0/viewer.min.js"></script>
<script type="text/javascript" src="//${amplienceConfig.scriptHostname}/viewers/ugc/mediawall/v2.8.0/viewer.min.js"></script>
</c:if>

<c:if test="${requestScope.enableAmplienceRenderKitJavascript}">
<%-- Render Kit Viewer --%>
<script type="text/javascript" src="${contextPath}/_ui/addons/ampliencedmaddon/shared/common/js/rendering-kit/viewer.js"></script>
</c:if>


<!--
End Amplience ecommBridge
-->

<%-- Populate the Amplience analytics tracking on the order confirmation page --%>
<c:if test="${pageType == 'ORDERCONFIRMATION' and not empty amplienceConfig.analyticsCollectorId}">
<c:set var="orderEntries" value="${orderData.entries}"/>

<!--
Begin Amplience Analytics
-->
<script>
	(function(a,m,p,l,n,c,e){a['AmplienceAnalyticsObject']=n;a[n]=a[n]||function(){
			(a[n].q=a[n].q||[]).push(arguments)},a[n].l=1*new Date();c=m.createElement(p),
		e=m.getElementsByTagName(p)[0];c.async=1;c.src=l;e.parentNode.insertBefore(c,e)
	})(window,document,'script','//${amplienceConfig.scriptHostname}/analytics.js','aone');

	aone('create', '${escape:js(amplienceConfig.analyticsCollectorId)}' <c:if test="${not empty customerId}">,{userId: '${escape:js(customerId)}'}</c:if>);
	aone('trackEvents', 'amp:purchase', [
		<c:forEach items="${orderEntries}" var="entry" varStatus="status">
		{
			value: ${escape:js(entry.totalPrice.value)},
			sku: "${escape:js(entry.product.code)}",
			qty: ${escape:js(entry.quantity)},
			currency: "${escape:js(entry.totalPrice.currencyIso)}",
			orderId: "${escape:js(orderData.code)}"
		}
		${status.last ? '' : ','}
		</c:forEach>
	]);
</script>
<!--
End Amplience Analytics
-->
</c:if>
