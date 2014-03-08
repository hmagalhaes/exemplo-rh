<%-- 
    Document   : listar
    Created on : 28-Feb-2014, 09:08:03
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<tiles:insertDefinition name="base">
	<tiles:putAttribute name="contentTitle"><spring:message code="depto.lista.titulo"/></tiles:putAttribute>
	<tiles:putAttribute name="content">

<nav>
	<input type="button" value="<spring:message code="depto.btn.novo"/>"
		   onclick="location.href='<c:url value="/departamento/incluir"/>'"/>
</nav>
<table>
	<thead>
		<tr>
			<th><spring:message code="depto.label.id"/></th>
			<th><spring:message code="depto.label.nome"/></th>
			<th><spring:message code="label.opcoes"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${lista}" var="depto">
			<tr>
				<td>${depto.id}</td>
				<td>${depto.nome}</td>
				<td><input type="button" value="<spring:message code="btn.editar"/>"
						   onclick="depto.edt(${depto.id})"/>
					<input type="button" value="<spring:message code="btn.remover"/>"
						   onclick="depto.rem(${depto.id})"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script>
	window.depto = {
		edt: function(id) {
			location.href = "<c:url value="/departamento/{1}/editar"/>".replace("{1}", id);
		},
		rem: function(id) {
			if (confirm("<spring:message code="msg.confirmar_excluir"/>")) {
				location.href = "<c:url value="/departamento/{1}/remover"/>".replace("{1}", id);
			}
		}
	};
</script>

	</tiles:putAttribute>
</tiles:insertDefinition>