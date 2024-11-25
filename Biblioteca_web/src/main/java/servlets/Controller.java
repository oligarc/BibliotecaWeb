package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import dao.DaoSocio;
import entidades.Socio;

/**
 * Controller que únicamente se encarga de los registros de los usuarios
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacion = request.getParameter("operacion");
		
		switch (operacion) {
		
		case "registrarse":
			
			//Recogemos los datos del formulario
			
			String nombre = request.getParameter("nombre");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String telefono = request.getParameter("telefono");
			String direccion = request.getParameter("direccion");
			
			//Insertar tupla en socio
			
			Socio socio = new Socio();
			
			socio.setNombre(nombre);
			socio.setDireccion(direccion);
			socio.setClave(password);
			socio.setEmail(email);
			socio.setTelefono(telefono);
			
			DaoSocio daoS = new DaoSocio();
			try {
				//Insertamos el socio pero hay que cambiar el dao
				daoS.insertarSocio(socio,password);
				Socio socioparaID = daoS.findSocioByEmail(email);
				request.setAttribute("socio", socioparaID);
				request.getRequestDispatcher("socioregistrado.jsp").forward(request, response);
			} catch (SQLException e) {
				procesarError(request, response, e, "error.jsp");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
		case "validacion":
			//recoger los parámetros
			String token = request.getParameter("token");
			email = request.getParameter("email");
			//ahora hay que validar el email y el token, eso lo hacemos en DAOSocio
			daoS = new DaoSocio();
			try {
				daoS.activarCuenta(email, token);
				response.sendRedirect("cuentaactivada.jsp");
			} catch (SQLException e) {
				procesarError(request, response, e, "error.jsp");
			} catch (Exception e) {
				procesarError(request, response, e, "error.jsp");
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
	
	protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String url) throws ServletException, IOException {
		String mensajeError = e.getMessage();
		request.setAttribute("error", mensajeError);
		if(url == null) {
			url = "error.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
