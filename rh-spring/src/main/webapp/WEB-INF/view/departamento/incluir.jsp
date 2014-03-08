<%-- 
    Document   : novo
    Created on : 28-Feb-2014, 09:15:10
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="contentTitle"><s:message code="depto.incluir.titulo"/></tiles:putAttribute>
	<tiles:putAttribute name="content">

<form:form commandName="formIncluirDepartamento">
	<form:errors element="p"/>
	<ul class="fields">
		<li>
			<form:label path="nome"><s:message code="depto.label.nome"/></form:label>
			<form:input path="nome" id="depto.nome"/>
			<form:errors path="nome" cssClass="error"/>
		</li>
	</ul>
	<div class="footer">
		<input type="submit" value="<s:message code="btn.salvar"/>"/>
		<input type="button" value="<s:message code="btn.cancelar"/>"
				onclick="cancelar()"/>
	</div>
</form:form>
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