package test;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoLibro;
import entidades.Libro;



public class TestDaoLibro {
	
	public static void main(String[] args) {
		
		
		DaoLibro daoLibro = new DaoLibro();
		
		/*ArrayList<Libro> pruebaListaLibros;
		try {
			pruebaListaLibros = daoLibro.listaLibros();
			for (Libro libro : pruebaListaLibros) {
				System.out.println(libro.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
		ArrayList<Libro> pruebaListaLibrosPorTitulo = daoLibro.listaTitulosPorNombre("la");
		for (Libro libro : pruebaListaLibrosPorTitulo) {
            System.out.println(libro.toString());
        }
		
		
	}
	

}
