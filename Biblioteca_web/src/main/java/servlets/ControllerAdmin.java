package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dao.DaoAutor;
import dao.DaoSocio;
import entidades.Autor;
import entidades.Socio;

/**
 * Servlet implementation class ControllerAdmin
 */

public class ControllerAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Obtenemos la operación que han seleccionado en el menuú
		String operacion = request.getParameter("operacion");
		
		DaoAutor daoAutor = new DaoAutor();
		
		switch (operacion) {
		case "insertaautor":
			
			//Me han pedido insertar autor. Lo primero será crear el autor con los campos que vengan en la request
			String nombre = request.getParameter("nombre");
			String strFechaNacimiento = request.getParameter("fechaNacimiento");
			//Transformamos la fecha a java.sql.Date
			
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(strFechaNacimiento,formato);
			Date fechaEnSql= java.sql.Date.valueOf(localDate);
			
			Autor autor = new Autor();
			autor.setNombre(nombre);
			autor.setFechaNacimiento(fechaEnSql);
			
			//Insertamos el autor
			try {
				daoAutor.insertaAutor(autor);
				
				//ofrezco una respuesta
				request.setAttribute("confirmaroperacion", "Autor creado con éxito");
				request.getRequestDispatcher("admin/altaautor.jsp").forward(request, response); //redireccion, recarga la página
			} catch (SQLException e) {
				
				processarError(request, response, e, "admin/altaautor.jsp");
			} catch (Exception e) {
				processarError(request, response, e, "admin/altaautor.jsp");
			}
			
			
			break;

		case "insertasocio":
				
			try {
				Socio socio = new Socio();
				DaoSocio daoSocio = new DaoSocio();
				String nombreSocio = request.getParameter("nombre");
				String direccionSocio = request.getParameter("direccion");
				String emailSocio = request.getParameter("email");
				
				socio.setNombre(nombreSocio);
				socio.setDireccion(direccionSocio);
				socio.setEmail(emailSocio);
				socio.setVersion(1);
				daoSocio.addSocio(socio);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				request.setAttribute("confirmaroperacion", "Socio creado con éxito");
				request.getRequestDispatcher("admin/altasocio.jsp").forward(request, response); //Recarga la página

			break;
			
		case "listadoSociosPaginado":
				
				DaoSocio daoSocio = new DaoSocio();
				ArrayList<Socio> listaSocio;
			try {
				listaSocio = daoSocio.getTodosLosSocios();
				request.setAttribute("listadoSocios", listaSocio);
				request.getRequestDispatcher("admin/listadoSocios.jsp").forward(request, response); //mostrará la lista de socios en la vista listadosocios.jsp
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void processarError(HttpServletRequest request,HttpServletResponse response,Exception e, String url) {
		
		String mensajeError = e.getMessage();
		request.setAttribute("error", mensajeError);
		if(url==null) {
			url = "error.jsp";
		}
		
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	

}
