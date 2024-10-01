package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoAutor;
import dao.DaoSocio;
import entidades.Autor;
import entidades.Socio;

/**
 * Servlet implementation class ControllerSocio
 */
public class controllersocio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public controllersocio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String operacion = request.getParameter("operacion");
		DaoAutor daoAutor = new DaoAutor();
		switch (operacion) {

		case "listarAutores":
			
			ArrayList<Autor> listadoAutores;
			try {
				listadoAutores = daoAutor.listadoAutores();
				request.setAttribute("listadoAutores", listadoAutores);
				request.getRequestDispatcher("socios/listadoautores.jsp").forward(request, response); //mostrará la lista de autores en la vista listadoAutores.jsp
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
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

}
