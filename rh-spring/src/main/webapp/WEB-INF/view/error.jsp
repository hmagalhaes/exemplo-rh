<%-- 
    Document   : error
    Created on : 28-Feb-2014, 01:53:34
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<tiles:insertDefinition name="base">
	<tiles:putAttribute name="contentTitle"><spring:message code="error.titulo"/></tiles:putAttribute>
	<tiles:putAttribute name="content" type="string">

<h2><spring:message code="error.subtitulo"/></h2>
<c:if test="${not empty error}">
	<p><strong><spring:message code="error.label.dados"/>:</strong> ${error}</p>
</c:if>

	</tiles:putAttribute>
</tiles:insertDefinition>