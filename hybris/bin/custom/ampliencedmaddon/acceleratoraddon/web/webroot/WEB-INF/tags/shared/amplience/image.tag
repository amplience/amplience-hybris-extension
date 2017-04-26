<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="escape" uri="http://amplience.com/hybris/tld/escape" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="code" required="true" type="java.lang.String" %>
<%@ attribute name="alt" required="false" type="java.lang.String" %>
<%@ attribute name="title" required="false" type="java.lang.String" %>
<%@ attribute name="params" required="false" type="java.lang.String" %>

<c:set var="imgUrl" value="//${amplienceConfig.imageHostname}/i/${amplienceConfig.accountIdentifier}/${code}?locale=${amplienceLocales}&${params}"/>

<img
	src="${escape:html(imgUrl)}"
	srcset="${escape:html(imgUrl)}&amp;w=320 320w,
		${escape:html(imgUrl)}&amp;w=480 480w,
		${escape:html(imgUrl)}&amp;w=640 640w,
		${escape:html(imgUrl)}&amp;w=800 800w,
		${escape:html(imgUrl)}&amp;w=1024 1024w,
		${escape:html(imgUrl)}&amp;w=1400 1400w,
		${escape:html(imgUrl)}&amp;w=1600 1600w"
	alt="${escape:html(alt)}"
	title="${escape:html(title)}">
