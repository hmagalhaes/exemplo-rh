<%-- 
    Document   : listar-vazios
    Created on : 28-Feb-2014, 18:34:25
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="contentTitle"><spring:message code="depto.vazios.lista.titulo"/></tiles:putAttribute>
	<tiles:putAttribute name="content">

	<table>
		<thead>
			<tr>
				<th><spring:message code="depto.label.id"/></th>
				<th><spring:message code="depto.label.nome"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${lista}" var="depto">
				<tr>
					<td>${depto.id}</td>
					<td>${depto.nome}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	</tiles:putAttribute>
</tiles:insertDefinition>