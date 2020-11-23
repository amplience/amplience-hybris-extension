<%--
 Copyright (c) 2016-2020 Amplience
  --%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="escape" uri="http://amplience.com/hybris/tld/escape" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="quickView" required="false" type="java.lang.Boolean" %>

<c:set value="${ycommerce:productImage(product, 'product')}" var="productImage"/>

<c:set var="dataOptions">
	{
	"client": "${escape:js(amplienceConfig.accountIdentifier)}",
	"imageBasePath": "//${escape:js(amplienceConfig.imageHostname)}/",
	"locale": "${escape:js(amplienceLocales)}",
	"set": "${escape:json(product.amplienceMediaSet)}",
	"errImg": "missing_product"
	}
</c:set>

<div class="amplience-viewerKit-product-gallery" id="amplience-viewerKit-product-gallery-${fn:escapeXml(product.code)}" data-options='${dataOptions}'>
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


