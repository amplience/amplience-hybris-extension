<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="escape" uri="http://amplience.com/hybris/tld/escape" %>

<c:set var="cid" value="${component.pk}"/>

<c:set var="dataOptions">
{
	"clientId": "${escape:json(amplienceConfig.accountIdentifier)}",
	"diBasePath": "//${escape:json(amplienceConfig.imageHostname)}/",
	"setName": "${escape:json(setName)}"
}
</c:set>

<div id="carousel-${cid}" class="amplience-carousel-viewer" data-options="${escape:html(dataOptions)}">
	<div class="amp-main">
	</div>
	<div class="amp-nav">
		<div class="other">
			<div class="prev fa fa-chevron-left"></div>
			<div class="message"></div>
			<div class="next fa fa-chevron-right"></div>
		</div>
		<div class="amp-carousel"></div>
	</div>
</div>

<c:set var="enableAmplienceCarouselJavascript" value="${true}" scope="request"/>
