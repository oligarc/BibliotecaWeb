<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado Autores</title>
    <jsp:directive.include file="../includes/includefile.jspf" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/estilo.css" />

</head>
<body>

    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

        <div class="authors">
            <h1>Listado de autores</h1>
            <div class="author-list">
                <c:forEach items="${listadoAutores}" var="autor">
                    <div class="author-card">
                        <h2><c:out value="${autor.nombre}"/></h2>
                        <p>Fecha de nacimiento: <c:out value="${autor.fechaNacimiento}"/></p>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

</body>
</html>
