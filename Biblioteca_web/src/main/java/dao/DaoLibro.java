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
	
	public void addLibro(Libro libro) {
		
		
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

}
