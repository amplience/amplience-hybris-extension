<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="escape" uri="http://amplience.com/hybris/tld/escape" %>

<c:set var="cid" value="${component.pk}"/>

<c:set var="dataOptions">
{
	"account": {
		"client": "${escape:json(amplienceConfig.accountIdentifier)}",
		"imageBasePath": "//${escape:json(amplienceConfig.imageHostname)}/",
		"contentBasePath": "//${escape:json(amplienceConfig.contentHostname)}/",
		"ampAnalyticsCollectorId": "${escape:json(amplienceConfig.analyticsCollectorId)}",
		"ampAnalyticsBeacon": "https://${escape:json(amplienceConfig.analyticsHostname)}/"
	},
	"viewer": {
		"stream": "${escape:json(streamID)}",
		"clientName": "${escape:json(title)}",
		"layout": "${escape:json(fn:toLowerCase(wallLayout))}",
		"heroSeparation": [3, 5, 4],
		"heroStart": 2,
		"heroEnd": 102,
		"columnWidth": 200,
		"padding": 10,
		"transitionDuration": "0.4s",
		"lazyLoading": true,
		"lazyLoadPagesNum": 1,
		"diOptions": "qlt=95&amp;w=640",
		"reportpath": "https://social-live-api.adis.ws/report",
		"xd": "https://social-live-api.adis.ws/crossdomain.xml",
		"callToActionText": "${escape:json(callToAction)}"
	},
	"modal": {
		"showModal": ${enableModal},
		"displayText": ${showModalText},
		"displayUsername": true,
		"diOptions": "w=400"
	},
	"uploadModal": {
		"content": "#${escape:json(cid)}-upload",
		"width": 600,
		"height": 680
	},
	"upload": {
		"endpoint": "https://social-live-api.adis.ws/upload",
		"stream": "${escape:json(streamID)}",
		"hashtag": "#${escape:json(hashtag)}",
		"tags": [
		<c:forEach items="${tags}" var="tag" varStatus="loopStatus">
			"${escape:json(tag)}" ${loopStatus.last ? '' : ','}
		</c:forEach>
		],
		"client": "${escape:json(amplienceConfig.accountIdentifier)}",
		"area": "#${escape:json(cid)}-upload",
		"tcsLink": "<c:url value="/termsAndConditions"/>",
		"gnipTcsLink": "http://support.gnip.com/faq/"
	},
	"instagramUpload": {
		"client": "${escape:json(amplienceConfig.accountIdentifier)}",
		"stream": "${escape:json(streamID)}",
		"tags": [
		<c:forEach items="${tags}" var="tag" varStatus="loopStatus">
			"${escape:json(tag)}" ${loopStatus.last ? '' : ','}
		</c:forEach>
		],
		"clientId": "e721d5f2d1a94438a64a42a0c8c4002a",
		"callbackUrl": "//${amplienceConfig.scriptHostname}/instagram/router.html?localCallback=${escape:json(instagramAuthCallbackUrl)}",
		"basepath": "https://social-live-api.adis.ws/upload",
		"xd": "https://social-live-api.adis.ws/crossdomain.xml"
	}
}
</c:set>

<div id="${cid}-mediawall" class="amplience-ugc-mediawall-viewer" data-options="${escape:html(dataOptions)}">
</div>
<div id="${cid}-upload"></div>

<c:set var="enableAmplienceUgcJavascript" value="${true}" scope="request"/>
