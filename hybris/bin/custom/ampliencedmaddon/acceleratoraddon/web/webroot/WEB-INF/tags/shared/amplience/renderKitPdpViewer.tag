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
	"set": "${escape:json(product.amplienceMediaSet)}",
	"secure": "${escape:json(request.secure)}",
	"errImg": "missing_product",
	"product": {
		"name": "${escape:json(product.name)}",
		"description": "${escape:json(product.summary)}",
		"price": "${escape:json(product.price.formattedValue)}"
	}
}
</c:set>

<div class="amplience-renderKit-set-viewer" id="amplience-renderKit-pdp-viewer-${product.code}" data-options="${escape:html(dataOptions)}">
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

<c:set var="enableAmplienceRenderKitJavascript" value="${true}" scope="request"/>
