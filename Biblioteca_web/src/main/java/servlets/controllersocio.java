package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoAutor;
import dao.DaoLibro;
import dao.DaoSocio;
import entidades.Autor;
import entidades.Libro;
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
			
			int totalRegistros = 0;
		    int pagina = 0; // Por defecto muestro la página 0
		    int numregpag = 5; // Por defecto le pongo 5
		    int paginamasalta = 0;
		    List <Autor> listaAutores = new ArrayList<Autor>();
		    
		    if (request.getParameter("pag") != null) { // si nos han pedido una página concreta
		        pagina = Integer.parseInt(request.getParameter("pag"));
		    }
		    if (request.getParameter("nrp") != null) { 
		        numregpag = Integer.parseInt(request.getParameter("nrp"));
		    }
		    
		    try {
		        // Averiguar cuántos registros hay
		        totalRegistros = daoAutor.getTotalRegistros();
		        // Calcular cuál es la última página (página más alta)
		        paginamasalta = totalRegistros / numregpag;
		        // Obtener el listado de socios
		        listaAutores = daoAutor.listadoAutores(pagina, numregpag);
		        
		        // añadir todos los datos a la request para mandárselos a la vista
		        request.setAttribute("pagina", pagina);
		        request.setAttribute("numregpag", numregpag);
		        request.setAttribute("paginamasalta", paginamasalta);
		        request.setAttribute("totalregistros", totalRegistros);
		        request.setAttribute("listadoAutores", listaAutores);
		        request.getRequestDispatcher("socios/listadoautores.jsp").forward(request, response);
		    } catch (SQLException e) {
		        procesarError(request, response, e, "socios/listadoautores.jsp");
		    } catch (Exception e) {
		        procesarError(request, response, e, "socios/listadoautores.jsp");
		    }
			
			break;
			
			
		case "listarLibros":
			
			DaoLibro daoLibro = new DaoLibro();
			ArrayList<Libro> listaLibros = new ArrayList<Libro>();
			String nombre = request.getParameter("nombre");
			String opcionSelect = request.getParameter("opcionbusqueda");
			
			
			switch(opcionSelect) {
			
			case "titulo":
				listaLibros = daoLibro.listaTitulosPorNombre(nombre);
			break;
			
			case "isbn":
				listaLibros = daoLibro.listaLibrosPorISBN(nombre);
				break;
			case "autor":
				listaLibros = daoLibro.listaTitulosPorNombreAutor(nombre);
				
			break;
			}
			
		
			
			
			request.setAttribute("listadoTitulos", listaLibros);
			request.getRequestDispatcher("socios/getlibros.jsp").forward(request, response);
			
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
