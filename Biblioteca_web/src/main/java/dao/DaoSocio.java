package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexiones.Conexion;
import entidades.Socio;

public class DaoSocio {
	
	public DaoSocio() {
		
	}
	
	public void addSocio(Socio socio) throws SQLException {
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "INSERT INTO SOCIO (idSocio,email,nombre,direccion,version) VALUES(S_SOCIO.NEXTVAL,?,?,?,?)";
		
		
		try {
			con = miconex.getConexion();
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);
			ps.setString(1, socio.getEmail());
			ps.setString(2, socio.getNombre());
			ps.setString(3, socio.getDireccion());
			ps.setInt(4, socio.getVersion());
			ps.execute();
			con.commit();
			System.out.println("Socio añadido correctamente.");
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(ps!=null) {
				ps.close();
			}if(con!=null) {
				con.close();
			}
		}
		
		
	}
	
	public void deleteSocio(int idSocio) {
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "DELETE FROM SOCIO WHERE IDSOCIO=?";
		
		try {
			con = miconex.getConexion();
			con.setAutoCommit(false);
			ps=con.prepareStatement(query);
			ps.setInt(1, idSocio);
			ps.executeUpdate();
			con.commit();
			System.out.println("Socio eliminado correctamente.");
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public ArrayList<Socio> getTodosLosSocios() throws SQLException{
		
		ArrayList<Socio> listaSocios = new ArrayList<Socio>();
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "SELECT * FROM SOCIO ORDER BY NOMBRE";
		
		try {
			con=miconex.getConexion();
			ps=con.prepareStatement(query);
			ResultSet rc = ps.executeQuery();
			
			while(rc.next()) {
				
				int idSocio = rc.getInt("IDSOCIO");
				String email = rc.getString("EMAIL");
				String nombre = rc.getString("NOMBRE");
				String direccion = rc.getString("DIRECCION");
				int version = rc.getInt("VERSION");
				
				Socio socio = new Socio(idSocio, email, nombre, direccion, version);
				listaSocios.add(socio);
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(ps!=null) {
				ps.close();
			}if(con!=null) {
				con.close();
			}
		}
		
		return listaSocios;
		
	}
	
	public Socio getSocio(int idSocio) {
		
		Socio socio = null;
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "SELECT * FROM SOCIO WHERE IDSOCIO=?";
		
		try {
			con = miconex.getConexion();
			
			ps=con.prepareStatement(query);
			ps.setInt(1, idSocio);
			ResultSet rc = ps.executeQuery();
			
			if(rc.next()) {
				
				int idSocio2 = rc.getInt("IDSOCIO");
				String email = rc.getString("EMAIL");
				String nombre = rc.getString("NOMBRE");
				String direccion = rc.getString("DIRECCION");
				int version = rc.getInt("VERSION");
				
				socio = new Socio(idSocio2, email, nombre, direccion, version);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return socio;
		
	}
	
	public void updateSocio(Socio socio) {
		
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "UPDATE SOCIO SET EMAIL=?,NOMBRE=?,DIRECCION=?,VERSION=? WHERE IDSOCIO=?";
		
		try {
			con=miconex.getConexion();
			con.setAutoCommit(false);
			ps=con.prepareStatement(query);
			ps.setString(1, socio.getEmail());
			ps.setString(2, socio.getNombre());
			ps.setString(3, socio.getDireccion());
			ps.setInt(4, socio.getVersion());
			ps.setInt(5, socio.getIdSocio());
			ps.executeUpdate();
			con.commit();
			System.out.println("Socio actualizado correctamente.");
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
