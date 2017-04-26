<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="escape" uri="http://amplience.com/hybris/tld/escape" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="code" required="true" type="java.lang.String" %>
<%@ attribute name="alt" required="false" type="java.lang.String" %>
<%@ attribute name="title" required="false" type="java.lang.String" %>
<%@ attribute name="params" required="false" type="java.lang.String" %>

<c:set var="imgUrl" value="//${amplienceConfig.imageHostname}/i/${amplienceConfig.accountIdentifier}/${code}?locale=${amplienceLocales}"/>

<picture>
	<!--[if IE 9]><video style="display: none;"><![endif]-->
	<source media="(min-width: 1400px)" srcset="${imgUrl}&$poi$&amp;w=1400&amp;aspect=5:1&amp;${params} 1x, ${imgUrl}&$poi$&amp;w=2800&amp;aspect=5:1&amp;${params} 2x">
	<source media="(min-width: 1024px)" srcset="${imgUrl}&$poi$&amp;w=1400&amp;aspect=3:1&amp;${params} 1x, ${imgUrl}&$poi$&amp;w=2800&amp;aspect=3:1&amp;${params} 2x">
	<source media="(min-width: 800px)" srcset="${imgUrl}&$poi$&amp;w=1024&amp;aspect=5:2&amp;${params} 1x, ${imgUrl}&$poi$&amp;w=2048&amp;aspect=5:2&amp;${params} 2x">
	<source media="(min-width: 640px)" srcset="${imgUrl}&$poi$&amp;w=800&amp;aspect=3:2&amp;${params} 1x, ${imgUrl}&$poi$&amp;w=1600&amp;aspect=3:2&amp;${params} 2x">
	<source media="(min-width: 480px)" srcset="${imgUrl}&$poi$&amp;w=640&amp;aspect=1:1&amp;${params} 1x, ${imgUrl}&$poi$&amp;w=1280&amp;aspect=1:1&amp;${params} 2x">
	<source media="(min-width: 320px)" srcset="${imgUrl}&$poi$&amp;w=480&amp;aspect=2:3&amp;${params} 1x, ${imgUrl}&$poi$&amp;w=960&amp;aspect=2:3&amp;${params} 2x">
	<source media="(min-width: 0px)" srcset="${imgUrl}&$poi$&amp;w=320&amp;aspect=1:2&amp;${params} 1x, ${imgUrl}&$poi$&amp;w=640&amp;aspect=1:2&amp;${params} 2x">
	<!--[if IE 9]></video><![endif]-->
	<img src="${imgUrl}&${params}" title="${escape:html(title)}" alt="${escape:html(alt)}">
</picture>
