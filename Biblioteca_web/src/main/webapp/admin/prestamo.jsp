<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuevo Préstamo</title>
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
		
		<div id="formPrestamo" class="formulariogeneral">
		<form action="${pageContext.request.contextPath}/ControllerAdmin" method="get">
		<fieldset id="datosPrestamo">
		<legend><img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Nuevo Préstamo</legend>
      <div class="etiquetas">
        <label for="idEjemplar">ID EJEMPLAR: </label>
        </div>
        <div class="campos">
        <input type="number" name="idEjemplar" id="idEjemplar" required />
        </div>
        <div class="etiquetas">
        <label for="titulo">TÍTULO: </label>
         </div>
         <div class="campos">
        <input type="text" name="titulo" id="titulo" required />
        </div>
        <div class="etiquetas">
        <label for="idSocio">ID SOCIO:</label>
        </div>
         <div class="campos">
        <input type="number" name="idSocio" id="idSocio" required />
        </div>
        <div class="etiquetas">
        <label for="nombreSocio">NOMBRE SOCIO:</label>
        </div>
        <div class="campos">
        <input type="text" name="nombreSocio" id="nombreSocio" required />
        </div>
        <div class="etiquetas">
        <label for="fechaPrestamo">FECHA PRÉSTAMO:</label>
        </div>
        <div class="campos">
        <input type="date" name="fechaPrestamo" id="fechaPrestamo" required />
        </div>
        
        <input name="operacion" type="hidden" id="operacion" value="nuevoPrestamo">

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