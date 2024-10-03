package test;

import java.sql.SQLException;
import java.util.ArrayList;

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

	}

}
