package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.DaoAutor;
import dao.DaoPrestamo;
import dao.DaoSocio;
import entidades.Autor;
import entidades.Prestamo;
import entidades.Socio;
import excepciones.PrestamoException;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		// Obtenemos la operación que han seleccionado en el menuú
		String operacion = request.getParameter("operacion");

		DaoAutor daoAutor = new DaoAutor();

		switch (operacion) {
		case "insertaautor":

			// Me han pedido insertar autor. Lo primero será crear el autor con los campos
			// que vengan en la request
			String nombre = request.getParameter("nombre");
			String strFechaNacimiento = request.getParameter("fechaNacimiento");
			// Transformamos la fecha a java.sql.Date

			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(strFechaNacimiento, formato);
			Date fechaEnSql = java.sql.Date.valueOf(localDate);

			Autor autor = new Autor();
			autor.setNombre(nombre);
			autor.setFechaNacimiento(fechaEnSql);

			// Insertamos el autor
			try {
				daoAutor.insertaAutor(autor);

				// ofrezco una respuesta
				request.setAttribute("confirmaroperacion", "Autor creado con éxito");
				request.getRequestDispatcher("admin/altaautor.jsp").forward(request, response); // redireccion, recarga
																								// la página
			} catch (SQLException e) {

				procesarError(request, response, e, "admin/altaautor.jsp");
			} catch (Exception e) {
				procesarError(request, response, e, "admin/altaautor.jsp");
			}

			break;

		case "insertasocio":
			
			
			try {
				Socio socio = new Socio();
				DaoSocio daoSocio = new DaoSocio();
				String nombreSocio = request.getParameter("nombre");
				String direccionSocio = request.getParameter("direccion");
				String emailSocio = request.getParameter("email");
				String passwordSocio = request.getParameter("password");

				socio.setNombre(nombreSocio);
				socio.setDireccion(direccionSocio);
				socio.setEmail(emailSocio);
				socio.setVersion(1);
				socio.setClave(passwordSocio);
				daoSocio.insertarSocio(socio);
				//Recuperamos el socio para mostrar el id que se ha creado en el mensaje de confirmación
				socio = daoSocio.findSocioByEmail(socio.getEmail());
				request.setAttribute("confirmaroperacion", "Socio " +socio.getIdSocio() + " creado satisfactoriamente.");
				request.getRequestDispatcher("admin/altasocio.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("confirmaroperacion", "Socio creado con éxito");
			request.getRequestDispatcher("admin/altasocio.jsp").forward(request, response); // Recarga la página

			break;

		case "listadoSociosPaginado":
			
		    DaoSocio daoSocio = new DaoSocio();
		    int totalRegistros = 0;
		    int pagina = 0; // Por defecto muestro la página 0
		    int numregpag = 5; // Por defecto le pongo 5
		    int paginamasalta = 0;
		    List<Socio> listadoSocios = null;
		    
		    // Preguntar si tengo parámetros en la request
		    if (request.getParameter("pag") != null) { // si nos han pedido una página concreta
		        pagina = Integer.parseInt(request.getParameter("pag"));
		    }
		    if (request.getParameter("nrp") != null) { 
		        numregpag = Integer.parseInt(request.getParameter("nrp"));
		    }
		    
		    try {
		        // Averiguar cuántos registros hay
		        totalRegistros = daoSocio.getTotalRegistros();
		        // Calcular cuál es la última página (página más alta)
		        paginamasalta = totalRegistros / numregpag;
		        // Obtener el listado de socios
		        listadoSocios = daoSocio.listadoSocios(pagina, numregpag);
		        
		        // añadir todos los datos a la request para mandárselos a la vista
		        request.setAttribute("pagina", pagina);
		        request.setAttribute("numregpag", numregpag);
		        request.setAttribute("paginamasalta", paginamasalta);
		        request.setAttribute("totalregistros", totalRegistros);
		        request.setAttribute("listadoSocios", listadoSocios);
		        request.getRequestDispatcher("admin/listadoSocios.jsp").forward(request, response);
		    } catch (SQLException e) {
		        procesarError(request, response, e, "admin/listadoSocios.jsp");
		    } catch (Exception e) {
		        procesarError(request, response, e, "admin/listadoSocios.jsp");
		    }
		    break;


		case "buscarsocio":

			ArrayList<Socio> listaSocios;

			String nombreABuscar = request.getParameter("nombre");
			try {
				DaoSocio daoSocio2 = new DaoSocio();
				listaSocios = daoSocio2.getBuscarSocioPorNombre(nombreABuscar);
				request.setAttribute("listadoSociosNombre", listaSocios);
				request.setAttribute("nombreSocio", nombreABuscar);
				request.getRequestDispatcher("admin/getsocio.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		case "modificardatosocio":

			String nuevoNombre = request.getParameter("nombre");
			String nuevaDireccion = request.getParameter("direccion");
			int idSocioAModificar = Integer.parseInt(request.getParameter("idSocio"));

			DaoSocio daoSocio3 = new DaoSocio();
			daoSocio3.updateSocio(idSocioAModificar, nuevoNombre, nuevaDireccion);
			request.setAttribute("confirmaroperacion", "Los datos del socio han sido modificados con éxito.");
			request.getRequestDispatcher("admin/getsocio.jsp").forward(request, response);
			break;
			
		case "socioslibrosfueraplazo":
			System.out.println("prueba");
			DaoSocio daoSocio4 = new DaoSocio();
			ArrayList<Socio> listadoSociosMorosos = new ArrayList<Socio>();
			
			try {
				listadoSociosMorosos = daoSocio4.listadoSociosMorosos();
				session.setAttribute("listadoSociosMorosos", listadoSociosMorosos); //Con el session lo guardas en la sesión y ya no tienes que volver a enviarlo
				request.getRequestDispatcher("admin/listadoSociosMorosos.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
		case "verlibrosfuerasdeplazo":
			
			DaoSocio daoSocio6 = new DaoSocio();
		    DaoPrestamo daoPrestamo = new DaoPrestamo();
		    String idSocio = request.getParameter("idSocio");
		    ArrayList<Prestamo> listadoPrestamos;
		    Socio socio = new Socio();
		    
		    
		    
		    Long idSocioLong = Long.parseLong(idSocio);
		    int idSocioInt = Integer.parseInt(idSocio);
		    socio = daoSocio6.getSocio(idSocioInt);
		    System.out.println("ID Socio: " + idSocioLong); // Añadir un log para depuración

		    try {
		        listadoPrestamos = daoPrestamo.listadoPrestamosFueraPlazo(idSocioLong);
		        if (listadoPrestamos == null || listadoPrestamos.isEmpty()) {
		            System.out.println("No se encontraron préstamos fuera de plazo para el socio: " + idSocioLong);
		        }
		        request.setAttribute("socio", socio);
		        request.setAttribute("listadoPrestamos", listadoPrestamos);
		        request.getRequestDispatcher("admin/listadoSociosMorosos.jsp").forward(request, response);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de base de datos");
		    } catch (Exception e) {
		        e.printStackTrace();
		        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error inesperado");
		    }
		    break;
		    
		case "nuevoPrestamo":
			
			DaoPrestamo daoPrestamo2 = new DaoPrestamo();
			Prestamo prestamo = new Prestamo();
			
			int idEjemplar = Integer.parseInt(request.getParameter("idEjemplar"));
			//String tituloEjemplar = request.getParameter("titulo");
			int idSocioParaEjemplar = Integer.parseInt(request.getParameter("idSocio"));
			//String nombreSocio = request.getParameter("nombreSocio");
			/*String fechaPrestamo = request.getParameter("fechaPrestamo");
			
			DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Patrón para la BBDD
			LocalDate localDate2 = LocalDate.parse(fechaPrestamo, formato2);
			Date fechaPrestamoSQL = java.sql.Date.valueOf(localDate2); //Valor para la fecha de préstamo realizada que se introduce en la BBDD
			
			
			LocalDate fechaPrestamoLocal = LocalDate.parse(fechaPrestamo,formato2);
			
			
			
			if(fechaPrestamoLocal.getDayOfWeek() == DayOfWeek.SATURDAY) {
				fechaPrestamoLocal = fechaPrestamoLocal.plusDays(2);
			}else if(fechaPrestamoLocal.getDayOfWeek() == DayOfWeek.SUNDAY) {
				fechaPrestamoLocal = fechaPrestamoLocal.plusDays(1);
			}
			

			LocalDate fechaLimiteDevolucion = fechaPrestamoLocal;
			int dias=0;
			while (dias < 5) {
			    fechaLimiteDevolucion = fechaLimiteDevolucion.plusDays(1);
			    
			    // Incrementa solo en días que no sean sábados ni domingos
			    if (!(fechaLimiteDevolucion.getDayOfWeek() == DayOfWeek.SATURDAY || 
			          fechaLimiteDevolucion.getDayOfWeek() == DayOfWeek.SUNDAY)) {
			        dias++;
			    }
			}
			
			Date fechaLimiteDevolucionSQL = java.sql.Date.valueOf(fechaLimiteDevolucion);
			*/
			
			
			prestamo.setIdejemplar(idEjemplar);
			//prestamo.setTitulo(tituloEjemplar);
			prestamo.setIdsocio(idSocioParaEjemplar);
			//prestamo.setNombreSocio(nombreSocio);
			//prestamo.setFechaprestamo(fechaPrestamoSQL);
			//prestamo.setFechalimitedevolucion(fechaLimiteDevolucionSQL);
			
			try {
				daoPrestamo2.insertaPrestamo(prestamo);
				request.setAttribute("confirmaroperacion", "Préstamo realizado con éxito.");
			} catch (PrestamoException e) {
				request.setAttribute("error", e.getMessage());
				request.setAttribute("idEjemplar", idEjemplar);
				request.setAttribute("idSocio", idSocioParaEjemplar);
				e.printStackTrace();
			} catch (SQLException e) {
				request.setAttribute("error", "Error en la base de datos: " + e.getMessage());
				request.setAttribute("idEjemplar", idEjemplar);
				request.setAttribute("idSocio", idSocioParaEjemplar);
				e.printStackTrace();
			} catch (Exception e) {
				request.setAttribute("error", "Error inesperado: " +e.getMessage());
				request.setAttribute("idEjemplar", idEjemplar);
				request.setAttribute("idSocio", idSocioParaEjemplar);
				e.printStackTrace();
			}
			
			
			request.getRequestDispatcher("admin/prestamo.jsp").forward(request, response);
			
			break;
			
		case "borrarprestamo":
			
			int idEjemplarParaDevolucion = Integer.parseInt(request.getParameter("idejemplar"));
			DaoPrestamo daoPrestamoDev = new DaoPrestamo();
			
			try {
				daoPrestamoDev.devolucionPrestamo(idEjemplarParaDevolucion);
				request.setAttribute("confirmaroperacion", "Devolución realizada con éxito.");
			} catch (SQLException e) {
				request.setAttribute("error", "Error en la base de datos: " +e.getMessage());
				e.printStackTrace();
			} catch (PrestamoException e) {
				request.setAttribute("error", e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				request.setAttribute("error", "Error inesperado: " +e.getMessage());
				e.printStackTrace();
			}
			
			request.getRequestDispatcher("admin/devolucion.jsp").forward(request, response);
			
			
			
			
			break;
			
			


		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
