<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuevo Socio</title>
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
		<div id="formSocio" class="formulariogeneral">
			<form name="frmSocio" method="post" action="${pageContext.request.contextPath}/ControllerAdmin">
				<fieldset id="datosSocio">
					<legend><img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Nuevo Socio</legend>
					
					<!-- Campo para el nombre del socio -->
					<div class="etiquetas">
						<label for="nombre">Nombre:</label>
					</div>
					<div class="campos">
						<input type="text" 
						       id="nombre" 
						       name="nombre"
						       value="${nuevoSocio.nombre}" 
						       required />
					</div>
					
					<!-- Campo para el email del socio -->
					<div class="etiquetas">
						<label for="email">Email:</label>
					</div>
					<div class="campos">
						<input type="email" 
						       id="email" 
						       name="email" 
						       value="${nuevoSocio.email}" 
						       required/>
					</div>
					
					<!-- Campo para la dirección del socio -->
					<div class="etiquetas">
						<label for="direccion">Dirección:</label>
					</div>
					<div class="campos">
						<input type="text" 
						       id="direccion" 
						       name="direccion" 
						       value="${nuevoSocio.direccion}" 
						       required/>
					</div>

					<!-- Operación oculta para indicar la acción en el controlador -->
					<input name="operacion" 
					       type="hidden" 
					       id="operacion" 
					       value="insertasocio">

					<!-- Botón para enviar el formulario -->
					<div class="botones">	
						<input type="submit" name="Submit" value="Guardar">
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>
