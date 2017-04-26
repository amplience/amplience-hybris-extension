<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="amplienceDmAddonProduct" tagdir="/WEB-INF/tags/addons/ampliencedmaddon/responsive/product" %>
<%@ taglib prefix="amplience" tagdir="/WEB-INF/tags/addons/ampliencedmaddon/shared/amplience" %>

<template:page pageTitle="${pageTitle}">

	<cms:pageSlot position="Section1" var="comp" element="div" class="productDetailsPageSection1">
		<cms:component component="${comp}"/>
	</cms:pageSlot>

	<amplienceDmAddonProduct:productDetailsPanel/>

	<cms:pageSlot position="CrossSelling" var="comp" element="div" class="productDetailsPageSectionCrossSelling">
		<cms:component component="${comp}"/>
	</cms:pageSlot>
	<cms:pageSlot position="Section3" var="comp" element="div" class="productDetailsPageSection3">
		<cms:component component="${comp}"/>
	</cms:pageSlot>
	<cms:pageSlot position="UpSelling" var="comp" element="div" class="productDetailsPageSectionUpSelling">
		<cms:component component="${comp}"/>
	</cms:pageSlot>
	<product:productPageTabs/>
	<cms:pageSlot position="Section4" var="comp" element="div" class="productDetailsPageSection4">
		<cms:component component="${comp}"/>
	</cms:pageSlot>

	<%-- The quick view popup requires the RenderKit Javascript --%>
	<c:set var="enableAmplienceRenderKitJavascript" value="${true}" scope="request"/>

</template:page>
