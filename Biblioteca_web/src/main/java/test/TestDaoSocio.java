package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoSocio;
import entidades.Socio;

public class TestDaoSocio {

	public static void main(String[] args) {
		
		DaoSocio daoSocio = new DaoSocio();
		try {
			ArrayList<Socio> listaSocios = daoSocio.getBuscarSocioPorNombre("oli");
			for (Socio socio : listaSocios) {
				System.out.println(socio.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			int pruebaNumeroRegistros = daoSocio.getTotalRegistros();
			System.out.println(pruebaNumeroRegistros);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			List<Socio> pruebaMetodo = daoSocio.listadoSocios(0, 5);
			for (Socio socio : pruebaMetodo) {
				System.out.println(socio.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
