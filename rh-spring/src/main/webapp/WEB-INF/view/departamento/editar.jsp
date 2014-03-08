<%-- 
    Document   : editar
    Created on : 28-Feb-2014, 09:58:00
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="base">
	<tiles:putAttribute name="contentTitle"><s:message code="depto.editar.titulo"/></tiles:putAttribute>
	<tiles:putAttribute name="content">
		
<f:form commandName="formEditarDepartamento">
	<f:errors element="p"/>
	<ul class="fields">
		<li>
			<label><s:message code="depto.label.id"/></label>
			<span>${formEditarDepartamento.id}</span>
		</li>
		<li>
			<f:label path="nome"><s:message code="depto.label.nome"/></f:label>
			<f:input path="nome" id="depto.nome"/>
			<f:errors path="nome" cssClass="error"/>
		</li>
	</ul>
	<div class="footer">
		<input type="submit" value="<s:message code="btn.salvar"/>"/>
		<input type="button" value="<s:message code="btn.cancelar"/>"
				onclick="cancelar()"/>
	</div>
</f:form>
<script>
	window.onload = function() {
		document.getElementById("depto.nome").focus();
	};
	function cancelar() {
		location.href = "<c:url value="/departamento"/>";
	}
</script>

	</tiles:putAttribute>
</tiles:insertDefinition>