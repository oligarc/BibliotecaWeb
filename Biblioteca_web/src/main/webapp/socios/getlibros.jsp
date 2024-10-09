<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consulta de libros</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>

	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="../WEB-INF/menu.jspf" />
		</div>
		<c:if test="${error != null}">
			<div class="diverror">
				<p>
					<strong><c:out value="Error" /></strong> <br>
					<c:out value="${error}" />
				</p>
			</div>
		</c:if>
		<c:if test="${confirmaroperacion != null}">
			<div class="divconfirmacion">
				<p>
					<strong><c:out value="Mensaje" /></strong> <br>
					<c:out value="${confirmaroperacion}" />
				</p>
			</div>
		</c:if>
		<form name="frmBusquedaLibro" method="post"
			action="${pageContext.request.contextPath}/controllersocio">
			<fieldset id="busquedalibro">
				<legend>
					<img
						src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Búsqueda
					sencilla
				</legend>
				<input type="text" id="nombre" name="nombre" /> <label
					for="tituloLibro"></label> <span><select
					name="opcionbusqueda" id="opcionbusqueda">
						<option value="autor">Autor</option>
						<option value="titulo">Titulo</option>
						<option value="isbn">ISBN</option>
				</select> <!-- Operación oculta para indicar la acción en el controlador -->
					<input name="operacion" type="hidden" id="operacion"
					value="listarLibros"> </span><span><input type="submit"
					value="Buscar" /></span>
			</fieldset>
		</form>

		<c:choose>
    <c:when test="${not empty listadoTitulos}">
        <div class="authors">
            <h1>Listado de Libros</h1>
            <div class="author-list">
                <c:forEach items="${listadoTitulos}" var="libro">
                    <div class="author-card">
                        <h2>Título: <c:out value="${libro.titulo}"/></h2>
                        <p>ISBN: <c:out value="${libro.ISBN}"/></p>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty nombre}">
            <p>No se encontraron libros con el patrón <c:out value="${nombre}"/></p>
        </c:if>
    </c:otherwise>
</c:choose>


	</div>
</body>
</html>