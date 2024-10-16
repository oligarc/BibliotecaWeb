<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado Socios Morosos</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>

	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="../WEB-INF/menu.jspf" />
		</div>

		<div class="authors">
			<h1>Listado de socios Morosos</h1>
			<div class="author-list">
				<c:forEach items="${listadoSociosMorosos}" var="socio">
					<div class="author-card">
						<h2>
							<c:out value="${socio.nombre}" />
						</h2>
						<p>
							ID:
							<c:out value="${socio.idSocio}" />
							<a
								href="${pageContext.request.contextPath}/ControllerAdmin?operacion=verlibrosfuerasdeplazo&idSocio=${socio.idSocio}">Ver
								libros</a>
					</div>
				</c:forEach>


				<c:if test="${not empty listadoPrestamos}">
				<div class="authors">
					<h1>Préstamos no devueltos del socio : <c:out value="${socio.nombre}"></c:out> </h1>
					<div class="author-card">
					<c:forEach items="${listadoPrestamos}" var="prestamo">
						<p>TÍTULO:
						<c:out value="${prestamo.titulo}"></c:out>
						<p>FECHA DEL PRÉSTAMO:
						<c:out value="${prestamo.fechaprestamo}"></c:out>
						<p>DÍAS DE DEMORA:
						<c:out value="${prestamo.diasDemora}"></c:out>
					</c:forEach>
					</div>
					</div>
				</c:if>

				<c:if test="${empty listadoPrestamos}">
					<p>No hay préstamos fuera de plazo para este socio.</p>
				</c:if>

			</div>
		</div>
	</div>

</body>
</html>