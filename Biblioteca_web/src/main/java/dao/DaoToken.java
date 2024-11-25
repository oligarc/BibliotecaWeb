package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexiones.Conexion;
import entidades.Token;

public class DaoToken {
	
	public DaoToken() {
		
	}
	
	public void addToken(Token t) {
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = null;
		
		try {
			con = miconex.getConexion();
			con.setAutoCommit(false);
			query = "INSERT INTO TOKEN (EMAIL,CLAVE,VALUE,TELEFONO,FECHA_INICIO) VALUES (?,?,?,?,?)";
			ps = con.prepareStatement(query);
			ps.setString(1, t.getEmail());
			ps.setString(2, t.getClave());
			ps.setString(3, t.getValue());
			ps.setString(4, t.getTelefono());
			ps.setTimestamp(5, t.getFecha());
			ps.executeUpdate();
			ps.close();
			con.commit();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public Token findTokenByEmail(String email) {
		
		Token t = null;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Conexion miconex = new Conexion();
		
		String query = "SELECT EMAIL,CLAVE,VALUE,TELEFONO,FECHA_INICIO FROM TOKEN WHERE EMAIL=?";
		
		try {
			con = miconex.getConexion();
			st = con.prepareStatement(query);
			st.setString(1, email);
			rs = st.executeQuery();
			
			if(rs.next()) {
				t = new Token();
				t.setEmail(rs.getString("EMAIL"));
				t.setClave(rs.getString("CLAVE"));
				t.setValue(rs.getString("VALUE"));
				t.setTelefono(rs.getString("TELEFONO"));
				t.setFecha(rs.getTimestamp("FECHA_INICIO"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
		
	}

}
