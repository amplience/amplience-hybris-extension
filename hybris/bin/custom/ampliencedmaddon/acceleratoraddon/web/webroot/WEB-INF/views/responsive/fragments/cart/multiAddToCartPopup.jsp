<%@ page trimDirectiveWhitespaces="true" contentType="application/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/addons/ampliencedmaddon/responsive/cart" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

{"cartData": {
	"total": "${cartData.totalPrice.value}",
	"products": [
		<c:forEach items="${cartData.entries}" var="cartEntry" varStatus="status">
		{
			"sku":		"${cartEntry.product.code}",
			"name": 	"<c:out value='${cartEntry.product.name}' />",
			"qty": 		"${cartEntry.quantity}",
			"price": 	"${cartEntry.basePrice.value}",
			"categories": [
				<c:forEach items="${cartEntry.product.categories}" var="category" varStatus="categoryStatus">
				"<c:out value='${category.name}' />"<c:if test="${not categoryStatus.last}">,</c:if>
				</c:forEach>
			]
		}<c:if test="${not status.last}">,</c:if>
		</c:forEach>
	]
},
	"addToCartLayer":"<spring:escapeBody javaScriptEscape="true">
	<spring:theme code="text.addToCart" var="addToCartText"/>
	<c:url value="/cart" var="cartUrl"/>
	<c:url value="/cart/checkout" var="checkoutUrl"/>
	<ycommerce:testId code="addToCartPopup">
	<div id="addToCartLayer" class="add-to-cart">

	<c:if test="${failedProductCount gt 0}">
	<div class="add-to-cart-errors">
	<spring:theme code="popup.cart.error.product" arguments="${failedProductCount}"/>
	</div>
	</c:if>

	<c:forEach items="${cartModifications}" var="cartModification">
	<cart:addToCartItem cartModification="${cartModification}" />
	</c:forEach>

	<ycommerce:testId code="checkoutLinkInPopup">
	<a href="${cartUrl}" class="btn btn-primary btn-block add-to-cart-button">
	<spring:theme code="checkout.checkout" />
	</a>
	</ycommerce:testId>


	<a href="" class="btn btn-default btn-block js-mini-cart-close-button">
	<spring:theme code="cart.page.continue"/>
	</a>

	</div>
	</ycommerce:testId>
	</spring:escapeBody>"
}
