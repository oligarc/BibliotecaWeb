<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Buscar Socio</title>
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
        
        <div id="formSocio" class="formulariogeneral">
            <form name="frmSocio" method="post" action="${pageContext.request.contextPath}/ControllerAdmin">
                <fieldset id="datosSocio">
                    <legend><img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Introduzca parte del nombre del Socio a modificar</legend>
                    
                    <!-- Campo para el nombre del socio -->
                    <div class="etiquetas">
                        <label for="nombre">Nombre:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="nombre" name="nombre" />
                    </div>
                    
                    <!-- Operación oculta para indicar la acción en el controlador -->
                    <input name="operacion" type="hidden" id="operacion" value="buscarsocio">
                    
                    <!-- Botón para enviar el formulario -->
                    <div class="botones">    
                        <input type="submit" name="Submit" value="Buscar">
                    </div>
                </fieldset>
            </form>
        </div>


        <c:if test="${listadoSociosNombre != null}">
            <div class="authors">
                <h1>Listado de socios</h1>
                
                <c:if test="${empty listadoSociosNombre}">
                    <h2>Ningún registro coincide con el patrón <c:out value="${nombreSocio}"/></h2>
                </c:if>

                <div class="author-list">
                    <c:forEach items="${listadoSociosNombre}" var="socio">
                        <div class="author-card">
                            <h2><c:out value="${socio.nombre}"/></h2>
                            <p>Dirección: <c:out value="${socio.direccion}"/></p>
                            <p>Email: <c:out value="${socio.email}"/></p>
                            
                            <!-- Formulario para editar el socio -->
                            <form action="${pageContext.request.contextPath}/admin/modificarsocio.jsp" method="get">
                                <input type="hidden" name="idSocio" value="${socio.idSocio}" />
                                <input type="submit" value="Editar" />
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </div>
</body>
</html>
