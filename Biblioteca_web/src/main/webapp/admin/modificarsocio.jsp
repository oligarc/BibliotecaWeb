<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@page import="dao.DaoSocio"%>
<%@page import="entidades.Socio"%>

<%

    String idSocio = request.getParameter("idSocio");
    DaoSocio daoSocio = new DaoSocio();
    Socio socio = daoSocio.getSocio(Integer.parseInt(idSocio));
    
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar Socio</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

        <!-- Mostrar mensajes de error o confirmación -->
        <c:if test="${error != null}">
            <div class="diverror">
                <p>
                    <strong><c:out value="Error" /></strong><br>
                    <c:out value="${error}" />
                </p>
            </div>
        </c:if>
        <c:if test="${confirmaroperacion != null}">
            <div class="divconfirmacion">
                <p>
                    <strong><c:out value="Mensaje" /></strong><br>
                    <c:out value="${confirmaroperacion}" />
                </p>
            </div>
        </c:if>

        <!-- Formulario para modificar los datos del socio -->
        <div id="formSocio" class="formulariogeneral">
            <form name="frmSocio" method="post" action="${pageContext.request.contextPath}/ControllerAdmin">
                <fieldset id="datosSocio">
                    <legend>
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Modifique los datos
                    </legend>

                    <!-- Campo para el nombre del socio -->
                    <div class="etiquetas">
                        <label for="nombre">Nombre:</label>
                    </div>
                    <div class="campos">
                        <input type="text" 
                               id="nombre" 
                               name="nombre"
                               required />
                    </div>

                    <!-- Campo para la dirección del socio -->
                    <div class="etiquetas">
                        <label for="direccion">Dirección:</label>
                    </div>
                    <div class="campos">
                        <input type="text" 
                               id="direccion" 
                               name="direccion"
                               required />
                    </div>

                    <!-- Campo oculto para el ID del socio -->
                    <input type="hidden" name="idSocio" value="<%= socio != null ? socio.getIdSocio() : "" %>" />
                    <input type="hidden" name="operacion" value="modificardatosocio">
                    <div class="botones">
                        <input type="submit" name="Submit" value="Modificar">
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</body>
</html>
