package test;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoPrestamo;
import dao.DaoSocio;
import entidades.Prestamo;
import entidades.Socio;
import excepciones.PrestamoException;

public class TestDaoPrestamo {

	public static void main(String[] args) {
		
		DaoPrestamo daoPrestamo = new DaoPrestamo();
		Socio socio = new Socio();
		Prestamo prestamo = new Prestamo();
		
		/*prestamo.setIdejemplar(37);
		try {
			daoPrestamo.insertaPrestamo(prestamo);
		} catch (PrestamoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 if(daoEjemplar.findEjemplarById(p.getIdejemplar())==null)
    	   throw new PrestamoException("Ejemplar no existente o dado de baja");  
    	   
    	   Esta primera condición funciona.
		
		
		*/
		
		/*prestamo.setIdejemplar(14);
		try {
			daoPrestamo.insertaPrestamo(prestamo);
		} catch (PrestamoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 Prestamo prestamo = findPrestamoById(p.getIdejemplar());    
	       if(prestamo!=null)
				throw new PrestamoException("Préstamo duplicado. El ejemplar indicado está en préstamo");
	       
	       Esta segunda condición funciona
	       
	       */
		
		/*socio.setIdSocio(54);
		prestamo.setIdsocio(54);
		prestamo.setIdejemplar(2);
		try {
			daoPrestamo.insertaPrestamo(prestamo);
		} catch (PrestamoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Socio socio = new Socio();
	       DaoSocio daoSocio = new DaoSocio();
	       
	       socio = daoSocio.findSocioByID((int) p.getIdsocio());
	       
	       if(socio==null) {
	    	   throw new PrestamoException("El código de socio no es válido");
	       }
	       
	       Esta parte funciona también
	       
	       */
		
		/*
		DaoSocio daoSocio = new DaoSocio();
		ArrayList<Socio> listaSociosMorosos = new ArrayList<Socio>();
		try {
			listaSociosMorosos = daoSocio.listadoSociosMorosos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Socio socio2 : listaSociosMorosos) {
			System.out.println(socio2.toString());
		}
		
		//Para probar, idSocios morosos son el 1,2,3,4,7,9,11,12
		
		prestamo.setIdsocio(2);
		prestamo.setIdejemplar(2);
		try {
			daoPrestamo.insertaPrestamo(prestamo);
		} catch (PrestamoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Socio> listadoSociosMorosos = new ArrayList<Socio>();
	       listadoSociosMorosos = daoSocio.listadoSociosMorosos();
	       
	       for (Socio socioMoroso : listadoSociosMorosos) {
			if(socioMoroso.getIdSocio()==p.getIdsocio()) {
				throw new PrestamoException("El socio tiene ya un préstamo que devolver");
			}
		}
	       
	       Este funciona también
	       
	       */
		
		

	}

}
