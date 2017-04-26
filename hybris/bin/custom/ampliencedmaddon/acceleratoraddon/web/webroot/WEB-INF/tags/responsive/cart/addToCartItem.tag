<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="cartModification" required="true" type="de.hybris.platform.commercefacades.order.data.CartModificationData" %>

<c:set var="quantity" value="${cartModification.quantityAdded}"/>
<c:set var="entry" value="${cartModification.entry}"/>
<c:set var="cartCode" value="${cartModification.cartCode}"/>
<c:set var="errorMsg" value="${cartModification.errorMessage}"/>

<div class="add-to-cart-item">
	<div class="thumb">
		<a href="${entryProductUrl}">
			<product:productPrimaryImage product="${entry.product}" format="cartIcon"/>
		</a>
	</div>
	<div class="details">
		<div class="cart_popup_error_msg"><spring:theme code="${errorMsg}" /></div>
		<a class="name" href="${entryProductUrl}">${entry.product.name}</a>
		<div class="qty"><span><spring:theme code="popup.cart.quantity.added"/></span>&nbsp;${quantity}</div>
		<c:forEach items="${product.baseOptions}" var="baseOptions">
			<c:forEach items="${baseOptions.selected.variantOptionQualifiers}" var="baseOptionQualifier">
				<c:if test="${baseOptionQualifier.qualifier eq 'style' and not empty baseOptionQualifier.image.url}">
					<div class="itemColor">
						<span class="label"><spring:theme code="product.variants.colour"/></span>
						<img src="${baseOptionQualifier.image.url}"  alt="${baseOptionQualifier.value}" title="${baseOptionQualifier.value}"/>
					</div>
				</c:if>
				<c:if test="${baseOptionQualifier.qualifier eq 'size'}">
					<div class="itemSize">
						<span class="label"><spring:theme code="product.variants.size"/></span>
							${baseOptionQualifier.value}
					</div>
				</c:if>
			</c:forEach>
		</c:forEach>
		<c:if test="${not empty entry.deliveryPointOfService.name}">
			<div class="itemPickup"><span class="itemPickupLabel"><spring:theme code="popup.cart.pickup"/></span>&nbsp;${entry.deliveryPointOfService.name}</div>
		</c:if>
		<div class="price"><format:price priceData="${entry.basePrice}"/></div>
	</div>
</div>
