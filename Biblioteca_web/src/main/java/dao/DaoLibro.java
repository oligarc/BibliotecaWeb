package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexiones.Conexion;
import entidades.Autor;
import entidades.Libro;

public class DaoLibro {
	
	public DaoLibro() {
		
	}
	
	
	public void insertaLibro(Libro l, int numejemplares) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = null;
		int maxidejemplar = 0;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			con.setAutoCommit(false);
			String ordenSQL = "INSERT INTO LIBRO VALUES(?,?,?)";
			st = con.prepareStatement(ordenSQL);
			st.setString(1, l.getIsbn());
			st.setString(2, l.getTitulo());
			st.setInt(3, l.getIdautor());
			st.execute();
			st.close();
			if (numejemplares > 0) {
				ordenSQL = "select max(idejemplar) maxidejemplar from ejemplar";
				st = con.prepareStatement(ordenSQL);
				rs = st.executeQuery();
				if (rs.next())
					maxidejemplar = rs.getInt("maxidejemplar");
				rs.close();
				st.close();
				ordenSQL = "insert into ejemplar(idejemplar,isbn,baja) values(?,?,?)";
				for (int i = 1; i <= numejemplares; i++) {
					st = con.prepareStatement(ordenSQL);
					st.setInt(1, maxidejemplar + i);
					st.setString(2, l.getIsbn());
					st.setString(3, "N");
					st.execute();
					st.close();
				}
				
			}
			con.commit();
		} catch (SQLException se) {
			con.rollback();
			throw se;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		}
	}
	
	/*
	 * Métodos obviados por cambiar al código del profesor
	 * En mi código original en Libro tenía un atributo Autor, Javier hace un arreglo que no se debe utilizar pero que nos salva la papeleta sin conocer JPA
	 * También mete en la clase Libro atributos que directamente no están en la BBDD
	 * public void addLibro(Libro libro) {
	 
		
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "INSERT INTO LIBRO VALUES(?,?,?)";
		
		try {
			con = miconex.getConexion();
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);
			
			ps.setString(1, libro.getISBN());
			ps.setString(2, libro.getTitulo());
			ps.setInt(3, libro.getAutor().getIdAutor());
			
			con.commit();
			System.out.println("Libro añadido correctamente.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Libro> listaLibros() throws SQLException{
		
		ArrayList<Libro> listaLibros = new ArrayList<Libro>();
		
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "SELECT LIBRO.ISBN, LIBRO.TITULO, LIBRO.IDAUTOR, AUTOR.NOMBRE,AUTOR.FECHANACIMIENTO " +
                "FROM LIBRO " +
                "JOIN AUTOR ON LIBRO.IDAUTOR = AUTOR.IDAUTOR " +
                "ORDER BY LIBRO.TITULO";

		ResultSet resultadoConsulta = null;
		
		try {
			con = miconex.getConexion();
			ps = con.prepareStatement(query);
			resultadoConsulta = ps.executeQuery();
			
			while(resultadoConsulta.next()) {
				
				Libro libro = new Libro();
				Autor autor = new Autor();
				libro.setISBN(resultadoConsulta.getString("ISBN"));
				libro.setTitulo(resultadoConsulta.getString("TITULO"));
				autor.setIdAutor(resultadoConsulta.getInt("IDAUTOR"));
				autor.setNombre(resultadoConsulta.getString("NOMBRE"));
				autor.setFechaNacimiento(resultadoConsulta.getDate("FECHANACIMIENTO"));
				libro.setAutor(autor);
				listaLibros.add(libro);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}if(resultadoConsulta != null) {
					resultadoConsulta.close();
				}if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaLibros;
	}
	
	public ArrayList<Libro> listaTitulosPorNombre(String nombre){
		
		ArrayList<Libro> listaTitulos = new ArrayList<Libro>();
		
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "SELECT LIBRO.ISBN, LIBRO.TITULO, LIBRO.IDAUTOR, AUTOR.NOMBRE, AUTOR.FECHANACIMIENTO " +
	               "FROM LIBRO " +
	               "JOIN AUTOR ON LIBRO.IDAUTOR = AUTOR.IDAUTOR " +
	               "WHERE UPPER(LIBRO.TITULO) LIKE ? " +
	               "ORDER BY LIBRO.TITULO";


				
		ResultSet resultadoConsulta = null;
		
		try {
			con = miconex.getConexion();
			ps = con.prepareStatement(query);
			ps.setString(1, "%" + nombre.toUpperCase() + "%");
			resultadoConsulta = ps.executeQuery();
			
			
			
			while(resultadoConsulta.next()) {
				
				Libro libro = new Libro();
				Autor autor = new Autor();
				libro.setISBN(resultadoConsulta.getString("ISBN"));
				libro.setTitulo(resultadoConsulta.getString("TITULO"));
				autor.setIdAutor(resultadoConsulta.getInt("IDAUTOR"));
				autor.setNombre(resultadoConsulta.getString("NOMBRE"));
				autor.setFechaNacimiento(resultadoConsulta.getDate("FECHANACIMIENTO"));
				libro.setAutor(autor);
				listaTitulos.add(libro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}if(resultadoConsulta != null) {
					resultadoConsulta.close();
				}if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listaTitulos;
		
	}
	
	public ArrayList<Libro> listaLibrosPorISBN(String ISBN){
		
		ArrayList<Libro> listaLibros = new ArrayList<Libro>();
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		
		String query = "SELECT LIBRO.ISBN, LIBRO.TITULO, LIBRO.IDAUTOR, AUTOR.NOMBRE, AUTOR.FECHANACIMIENTO " +
	               "FROM LIBRO " +
	               "JOIN AUTOR ON LIBRO.IDAUTOR = AUTOR.IDAUTOR " +
	               "WHERE UPPER(LIBRO.ISBN) LIKE ? " +
	               "ORDER BY LIBRO.TITULO";


				
		ResultSet resultadoConsulta = null;
		
		try {
			
			con = miconex.getConexion();
			ps = con.prepareStatement(query);
			ps.setString(1, "%" + ISBN.toUpperCase() + "%");
			resultadoConsulta = ps.executeQuery();
			
			while(resultadoConsulta.next()) {
				
				Libro libro = new Libro();
				Autor autor = new Autor();
				
				libro.setISBN(resultadoConsulta.getString("ISBN"));
				libro.setTitulo(resultadoConsulta.getString("TITULO"));
				autor.setIdAutor(resultadoConsulta.getInt("IDAUTOR"));
				autor.setNombre(resultadoConsulta.getString("NOMBRE"));
				autor.setFechaNacimiento(resultadoConsulta.getDate("FECHANACIMIENTO"));
				libro.setAutor(autor);
				listaLibros.add(libro);
				System.out.println("Libro añadido correctamente.");
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(ps !=null) {
					ps.close();
				}if(resultadoConsulta != null) {
					resultadoConsulta.close();
				}if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaLibros;
		
	}
	
	public ArrayList<Libro> listaTitulosPorNombreAutor(String nombreAutor){
		
		ArrayList<Libro> listaTitulos = new ArrayList<Libro>();
		
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		
		String query = "SELECT LIBRO.ISBN, LIBRO.TITULO, LIBRO.IDAUTOR, AUTOR.NOMBRE, AUTOR.FECHANACIMIENTO " +
	               "FROM LIBRO " +
	               "JOIN AUTOR ON LIBRO.IDAUTOR = AUTOR.IDAUTOR " +
	               "WHERE UPPER(AUTOR.NOMBRE) LIKE ? " +
	               "ORDER BY LIBRO.TITULO";


				
		ResultSet resultadoConsulta = null;
		
		try {
			
			con = miconex.getConexion();
			ps = con.prepareStatement(query);
			ps.setString(1, "%" + nombreAutor.toUpperCase() + "%");
			resultadoConsulta = ps.executeQuery();
			
			while(resultadoConsulta.next()) {
				
				Libro libro = new Libro();
				Autor autor = new Autor();
				
				libro.setISBN(resultadoConsulta.getString("ISBN"));
				libro.setTitulo(resultadoConsulta.getString("TITULO"));
				autor.setIdAutor(resultadoConsulta.getInt("IDAUTOR"));
				autor.setNombre(resultadoConsulta.getString("NOMBRE"));
				autor.setFechaNacimiento(resultadoConsulta.getDate("FECHANACIMIENTO"));
				libro.setAutor(autor);
				listaTitulos.add(libro);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
		
		return listaTitulos;
	}
	
	*/
	
	public ArrayList<Libro> listadoLibros(String criteriobusqueda, String valorbusqueda)
			throws SQLException, Exception {
		ArrayList<Libro> listalibros;
		listalibros = new ArrayList<Libro>();
		ResultSet rs;
		PreparedStatement st;
		String titulo, isbn, autor = null;
		if (criteriobusqueda.equals("autor")) {
			titulo = "%";
			isbn = "%";
			autor = "%" + valorbusqueda + "%";
		} else if (criteriobusqueda.equals("titulo")) {
			autor = "%";
			isbn = "%";
			titulo = "%" + valorbusqueda + "%";
		} else {
			autor = "%";
			titulo = "%";
			isbn = valorbusqueda;
		}
		try {
			Conexion miconex = new Conexion();
			Connection con = miconex.getConexion();
			String ordenSQL ="SELECT L.ISBN,L.TITULO,A.NOMBRE AUTOR,EJEMPLARESTOTALES,EJEMPLARESENPRESTAMO, " + 
					"      (EJEMPLARESTOTALES-EJEMPLARESENPRESTAMO)EJEMPLARESDISPONIBLES " + 
					"FROM LIBRO L,AUTOR A, " + 
					"(" + 
					"SELECT A.ISBN,EJEMPLARESTOTALES,NVL(EJEMPLARESENPRESTAMO,0)EJEMPLARESENPRESTAMO " + 
					"FROM " + 
					"  ( " + 
					"    SELECT L.ISBN,COUNT(*)EJEMPLARESTOTALES " + 
					"    FROM LIBRO L,EJEMPLAR E " + 
					"    WHERE L.ISBN=E.ISBN " + 
					"    AND E.BAJA='N' " + 
					"    GROUP BY L.ISBN " + 
					"  )A LEFT JOIN " + 
					"    ( SELECT ISBN,COUNT(*) EJEMPLARESENPRESTAMO " + 
					"      FROM PRESTAMO P,EJEMPLAR E " + 
					"      WHERE P.IDEJEMPLAR=E.IDEJEMPLAR "  + 
					"      GROUP BY ISBN)B " + 
					"ON A.ISBN=B.ISBN)B " + 
					"WHERE L.ISBN=B.ISBN " + 
					"AND L.IDAUTOR=A.IDAUTOR " + 
					"AND TRANSLATE(UPPER(A.NOMBRE),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') " + 
					"AND TRANSLATE(UPPER(L.TITULO),'Á,É,Í,Ó,Ú','A,E,I,O,U') LIKE  TRANSLATE(UPPER(?),'Á,É,Í,Ó,Ú','A,E,I,O,U') " + 
					"AND L.ISBN LIKE ? " + 
					"ORDER BY AUTOR,TITULO ";
			st = con.prepareStatement(ordenSQL);
			st.setString(1, autor);
			st.setString(2, titulo);
			st.setString(3, isbn);
			rs = st.executeQuery();
			while (rs.next()) {
				Libro libro = new Libro();
				libro.setIsbn(rs.getString("ISBN"));
				libro.setTitulo(rs.getString("TITULO"));
				libro.setNombreAutor(rs.getString("AUTOR"));
				libro.setEjemplaresTotales(rs.getInt("EJEMPLARESTOTALES"));
				libro.setEjemplaresEnPrestamo(rs.getInt("EJEMPLARESENPRESTAMO"));
				libro.setEjemplaresDisponibles(rs.getInt("EJEMPLARESDISPONIBLES"));
				listalibros.add(libro);
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		System.out.println(listalibros.size());
		return listalibros;
	}

}
