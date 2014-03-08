<%-- 
    Document   : editar
    Created on : 28-Feb-2014, 11:12:40
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="contentTitle"><spring:message code="colab.editar.titulo"/></tiles:putAttribute>
	<tiles:putAttribute name="content">

<form:form commandName="formEditarColaborador">
	<form:errors element="p"/>
	<ul class="fields">
		<li>
			<label><spring:message code="colab.label.id"/></label>
			${formEditarColaborador.id}
		</li>
		<li>
			<form:label path="nome"><spring:message code="colab.label.nome"/></form:label>
			<form:input path="nome" id="colab.nome"/>
			<form:errors path="nome" cssClass="error"/>
		</li>
		<li>
			<form:label path="email"><spring:message code="colab.label.email"/></form:label>
			<form:input path="email"/>
			<form:errors path="email" cssClass="error"/>
		</li>
		<li>
			<form:label path="dataAdmissao"><spring:message code="colab.label.dataAdmissao"/></form:label>
			<form:input path="dataAdmissao"/>
			<form:errors path="dataAdmissao" cssClass="error"/>
		</li>
		<li>
			<form:label path="departamento"><spring:message code="colab.label.depto"/></form:label>
			<form:select path="departamento" items="${departamentos}" itemLabel="nome"/>
			<form:errors path="departamento" cssClass="error"/>
		</li>
	</ul>
	<div class="footer">
		<input type="submit" value="<spring:message code="btn.salvar"/>"/>
		<input type="button" value="<spring:message code="btn.cancelar"/>" onclick="cancelar()"/>
	</div>
</form:form>
<script>
	window.onload = function() {
		document.getElementById("colab.nome").focus();
	};
	function cancelar() {
		location.href = "<c:url value="/colaborador"/>";
	}
</script>

	</tiles:putAttribute>
</tiles:insertDefinition>