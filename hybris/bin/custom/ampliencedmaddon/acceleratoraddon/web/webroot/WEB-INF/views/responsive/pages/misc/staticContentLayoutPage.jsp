<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="amplienceDmAddonProduct" tagdir="/WEB-INF/tags/addons/ampliencedmaddon/responsive/product" %>
<%@ taglib prefix="amplience" tagdir="/WEB-INF/tags/addons/ampliencedmaddon/shared/amplience" %>

<%-- DEMO Static Content Page to show useage of amplience:image and amplience:responsiveImage tags --%>
<template:page pageTitle="${pageTitle}">

	<style>
		.test-image img{
			width:100%;
			margin:30px auto;
			display:block
		}
	</style>

	<cms:pageSlot position="Section1" var="comp" element="div" class="productDetailsPageSection1">
		<cms:component component="${comp}"/>
	</cms:pageSlot>

	<div class="row test-image">
		<amplience:responsiveImage code="womanwall" alt="Responsive Image" title="Responsive Image"/>
	</div>

	<div class="row test-image">
		<amplience:image code="womanwall" alt="Responsive Image" title="Responsive Image"/>
	</div>

</template:page>
