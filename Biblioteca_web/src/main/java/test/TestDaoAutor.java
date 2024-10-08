package test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import conexiones.Conexion;
import dao.DaoAutor;
import entidades.Autor;

public class TestDaoAutor {

	public static void main(String[] args) {
		
		DaoAutor dao=new DaoAutor();
		Autor a=new Autor();
		/*a.setNombre("Arturo Pérez Reverte");	
		LocalDate fecha_nacimiento=LocalDate.of(1951, 11, 25);
		java.sql.Date fechacimiento=java.sql.Date.valueOf(fecha_nacimiento);
		a.setFechaNacimiento(fechacimiento);
		try {
			  dao.insertaAutor(a);
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
		e.printStackTrace();
		}
		*/
		
		
		try {
			int pruebaRegistros;
			pruebaRegistros = dao.getTotalRegistros();
			System.out.println(pruebaRegistros);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<Autor> pruebaMetodo;
		try {
			pruebaMetodo = dao.listadoAutores(0, 5);
			for (Autor autor : pruebaMetodo) {
				System.out.println(autor.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//		try {
//			ArrayList<Autor>listadoAutores=dao.listadoAutores();
//			for(Autor a: listadoAutores) {
//				System.out.println(a.toString());
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	

}
