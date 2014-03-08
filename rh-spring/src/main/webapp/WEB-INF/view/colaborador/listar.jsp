<%-- 
    Document   : listar
    Created on : 28-Feb-2014, 08:51:19
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="contentTitle"><spring:message code="colab.lista.titulo"/></tiles:putAttribute>
	<tiles:putAttribute name="content">

<nav>
	<input type="button" value="<spring:message code="colab.btn.novo"/>"
		   onclick="location.href='<c:url value="/colaborador/incluir"/>'"/>
</nav>
<table>
	<thead>
		<tr>
			<th><spring:message code="colab.label.id"/></th>
			<th><spring:message code="colab.label.nome"/></th>
			<th><spring:message code="colab.label.email"/></th>
			<th><spring:message code="colab.label.depto"/></th>
			<th><spring:message code="colab.label.dataAdmissao"/></th>
			<th><spring:message code="label.opcoes"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${lista}" var="colab">
			<tr>
				<td>${colab.id}</td>
				<td>${colab.nome}</td>
				<td>${colab.email}</td>
				<td>${colab.departamento.nome}</td>
				<td><fmt:formatDate dateStyle="MEDIUM" value="${colab.dataAdmissao}"/></td>
				<td><input type="button" value="<spring:message code="btn.editar"/>"
						   onclick="colab.edt(${colab.id})"/>
					<input type="button" value="<spring:message code="btn.remover"/>"
						   onclick="colab.rem(${colab.id})"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script>
	window.colab = {
		edt: function(id) {
			location.href = "<c:url value="/colaborador/{1}/editar"/>".replace("{1}", id);
		},
		rem: function(id) {
			if (confirm("<spring:message code="msg.confirmar_excluir"/>")) {
				location.href = "<c:url value="/colaborador/{1}/remover"/>".replace("{1}", id);
			}
		}
	};
</script>

	</tiles:putAttribute>
</tiles:insertDefinition>