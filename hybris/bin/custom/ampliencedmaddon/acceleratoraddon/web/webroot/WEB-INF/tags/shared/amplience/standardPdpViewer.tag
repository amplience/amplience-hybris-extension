<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="escape" uri="http://amplience.com/hybris/tld/escape" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<c:set value="${ycommerce:productImage(product, 'product')}" var="productImage"/>

<c:set var="dataOptions">
{
	"playerLayout": {
		"thumbLocation": "bottom",
		"numThumbs": 4,
		"enableFullscreenMode": false
	},
	"video": {
		"autoplay": true,
		"loopVideo": true
	},
	"spin": {
		"autoplay": true,
		"momentum": true
	},
	"zoom": {
		"zoomType": "inner",
		"zoomActivation": "click"
	},
	"transformations": {
		"main": "$product$",
		"thumb": "$thumbnail$",
		"zoom": "$zoom$"
	}
}
</c:set>

<div class="amplience-standard-pdp-viewer" id="amplience-standard-pdp-viewer-${product.code}" data-options="${escape:html(dataOptions)}">
	<noscript>
		<%-- No Script fallback. --%>
		<c:choose>
			<c:when test="${not empty fallbackImage.altText}">
				<img src="${fallbackImage.url}" alt="${fn:escapeXml(primaryImage.altText)}" title="${fn:escapeXml(primaryImage.altText)}"/>
			</c:when>
			<c:otherwise>
				<img src="${fallbackImage.url}" alt="${fn:escapeXml(product.name)}" title="${fn:escapeXml(product.name)}"/>
			</c:otherwise>
		</c:choose>
	</noscript>
</div>

<c:set var="enableAmplienceStandardPdpViewerJavascript" value="${true}" scope="request"/>
