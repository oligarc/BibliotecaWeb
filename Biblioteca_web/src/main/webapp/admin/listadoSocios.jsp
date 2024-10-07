<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado Socios</title>
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
            <h1>Listado de socios</h1>
            <div class="author-list">
                <c:forEach items="${listadoSocios}" var="socio">
                    <div class="author-card">
                        <h2><c:out value="${socio.nombre}"/></h2>
                        <p>ID: <c:out value="${socio.idSocio}"/></p>
                        <p>Dirección: <c:out value="${socio.direccion}"/></p>
                        <p>Email: <c:out value="${socio.email}"/></p>
                    </div>
                </c:forEach>
            </div>
            <div class="w-75 ma py-2">
				<c:set var="totalregistros" value="${totalregistros}"></c:set>
				<c:set var="paginaactual" value="${pagina}"></c:set>
				<c:set var="registrosporpagina" value="${numregpag}"></c:set>
				<c:set var="paginamasalta" value="${paginamasalta}"></c:set>
				<c:out value="Total Registros: ${totalregistros}"></c:out>
				<c:out
					value="Mostrando desde ${(registrosporpagina*paginaactual)+1} a ${(registrosporpagina*paginaactual)+registrosporpagina < totalregistros?(registrosporpagina*paginaactual)+registrosporpagina:totalregistros}"></c:out>
				<a
					href="${pageContext.request.contextPath}/ControllerAdmin?operacion=listadoSociosPaginado&pag=${paginaactual+1>paginamasalta?0:paginaactual+1}&nrp=${registrosporpagina} ">
					Sig</a> <a
					href="${pageContext.request.contextPath}/ControllerAdmin?operacion=listadoSociosPaginado&pag=${paginaactual-1>=0?paginaactual-1:paginamasalta}&nrp=${registrosporpagina} ">Ant</a>
			</div>
        </div>
    </div>

</body>
</html>