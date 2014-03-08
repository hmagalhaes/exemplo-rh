<%-- 
    Document   : remover
    Created on : 28-Feb-2014, 11:39:13
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="contentTitle"><spring:message code="depto.remover.titulo"/></tiles:putAttribute>
	<tiles:putAttribute name="content">

<style>
	main .lista {margin-top: 60px; border-top: solid 1px #666}
	main form ul {display: table}
	main form ul > li {
		float: left; 
		height: 130px; 
		width: 350px;
		background: #ededed;
		margin-right: 20px;
		border: solid 1px transparent;
		padding: 3px;
	}
	main form ul li {cursor: pointer}
	main form ul li:hover {border-color: orange}
	main form label {font-weight: bold}
	main form .footer {padding: 0 0 0 36px}
</style>
<section>
	<form:form commandName="formRemoverDepartamento">
		<form:errors element="p"/>
		<p>
			<spring:message code="depto.remover.situacao"
					arguments="${formRemoverDepartamento.nome}"/><br/>
			<spring:message code="depto.remover.situacao2"/>
		</p>
			<ul>
				<li id="option1">
					<c:if test="${not empty departamentos}">
					<p>
						<form:radiobutton path="transferirColaboradores" value="true"
								id="transfColab-true"/>
						<form:label path="transferirColaboradores" for="transfColab-true">
							<spring:message code="depto.remover.label.transferir"/>
						</form:label>
					</p>
					<p>
						<form:label path="novoDepartamento">
							<spring:message code="depto.remover.label.escolher"/>:
						</form:label>
						<form:select path="novoDepartamento"
								items="${departamentos}" itemLabel="nome"/>
					</p>
					</c:if>
					<c:if test="${empty departamentos}">
						<p><spring:message code="depto.remover.transferir.impossivel"/></p>
					</c:if>
				</li>
				<li id="option2">
					<p>
						<form:radiobutton path="transferirColaboradores" value="false"
								id="transfColab-false"/>
						<form:label path="transferirColaboradores" for="transfColab-false">
							<spring:message code="depto.remover.label.remover"/>
						</form:label>
					</p>
				</li>
			</ul>
		<div class="footer">
			<input type="submit" value="<spring:message code="btn.confirmar"/>"/>
			<input type="button" value="<spring:message code="btn.cancelar"/>" onclick="cancelar()"/>
		</div>
	</form:form>
</section>
<section class="lista">
	<header>
		<h2><spring:message code="depto.remover.lista.titulo"/></h2>
	</header>
	<table>
		<thead>
			<tr>
				<th><spring:message code="depto.label.id"/></th>
				<th><spring:message code="depto.label.nome"/></th>
			<tr>
		</thead>
		<tbody>
			<c:forEach items="${colaboradores}" var="colab">
				<tr>
					<td>${colab.id}</td>
					<td>${colab.nome}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
<script>
	function cancelar() {
		location.href = "<c:url value="/departamento"/>";
	}
	function setupOpt(o, r) {
		o = document.getElementById(o);
		r = document.getElementById(r);
		if (o != null && r != null) {
			o.onclick = function() { r.click(); };
		}
	}
	setupOpt("option1", "transfColab-true");
	setupOpt("option2", "transfColab-false");
</script>

	</tiles:putAttribute>
</tiles:insertDefinition>