<%-- 
    Document   : base
    Created on : 27-Feb-2014, 01:28:14
    Author     : Hudson P. Magalhães <hudson@hmagalhaes.eti.br>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"/>
	<title><spring:message code="app.titulo"/></title>
	<link href="<c:url value="/style/base.css"/>" rel="stylesheet"/>
</head>
<body>
	<header><div class="container">
		<h1><a href="<c:url value="/"/>"><spring:message code="app.titulo"/></a></h1>
		<nav>
			<ul>
				<li><a href='<c:url value="/departamento"/>'><spring:message code="menu.departamentos"/></a></li>
				<li><a href='<c:url value="/departamento/vazios"/>'><spring:message code="menu.departamentos_vazios"/></a></li>
				<li><a href='<c:url value="/colaborador"/>'><spring:message code="menu.colaboradores"/></a></li>
			</ul>
		</nav>
		<form>
			<label><spring:message code="locale.label"/>:</label>
			<select id="lang">
				<c:forEach items="${locales}" var="l">
					<c:choose>
						<c:when test="${localeAtual eq l}">
							<option selected value="${l}">${l.getDisplayLanguage(l)}</option>
						</c:when>
						<c:otherwise>
							<option value="${l}">${l.getDisplayLanguage(l)}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</form>
	</div></header>
	<main><div class="container">
		<header>
			<h1><tiles:insertAttribute name="contentTitle" defaultValue="title"/></h1>
			<c:if test="${not empty msg}"><p class="mensagem">${msg}</p></c:if>
		</header>
		<section>
			<tiles:insertAttribute name="content" defaultValue="content"/>
		</section>
	</div></main>
	<footer><div class="container">
		<span><spring:message code="app.titulo"/></span>
		<span><a href="http://www.hmagalhaes.eti.br"/>Hudson Pena Magalhães</a></span>
	</div></footer>
	<script src="<c:url value="/js/iface.js"/>"></script>
	<script>
		window.iface.setupLang({
			el: document.getElementById("lang"),
			error: "<spring:message code="locale.msg.erro"/>",
			url: "<c:url value="/locale/trocar"/>"
		});
	</script>
</body>
</html>