package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import conexiones.Conexion;
import entidades.Socio;
import entidades.Token;
import tools.Tools;
import util.Hash;

public class DaoSocio {
	
	public DaoSocio() {
		
	}
	
	
	public void insertarSocio(Socio socio,String clave) throws SQLException { //Éste es el bueno
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = null;
		
		try {
			con = miconex.getConexion();
			con.setAutoCommit(false); //Esto lo hacemos para que no se guarden los cambios hasta que yo lo decida
			
			//Antes haciamos los 3 pasos de golpe, pero ahora lo vamos a hacer con validación
			
			/*query = "INSERT INTO USUARIOS VALUES(?,?)";
			ps = con.prepareStatement(query);
			ps.setString(1, socio.getEmail());
			ps.setString(2, Hash.getSha256(socio.getClave())); //Utilizamos la clase Hash para encriptar la contraseña
			ps.executeUpdate();
			ps.close();
			query = "INSERT INTO GRUPOS VALUES(?,?)";
			ps = con.prepareStatement(query);
			ps.setString(1, "sociosbiblioteca");
			ps.setString(2, socio.getEmail());
			ps.executeUpdate();
			ps.close();
			*/
			query = "INSERT INTO SOCIO (idSocio,email,nombre,direccion,version,telefono) VALUES(S_SOCIO.NEXTVAL,?,?,?,?,?)";
			ps = con.prepareStatement(query);
			ps.setString(1, socio.getEmail());
			ps.setString(2, socio.getNombre());
			ps.setString(3, socio.getDireccion());
			ps.setInt(4, 1);
			ps.setString(5, socio.getTelefono());
			ps.executeUpdate();
			ps.close();
			
			//Segundo paso, creamos el objeto Token para poder enviar por correo
			
			Token token = new Token();
			token.setEmail(socio.getEmail());
			token.setValue(Tools.generaToken()); //Generamos un valor para el Token
			token.setTelefono(socio.getTelefono());
			token.setFecha(java.sql.Timestamp.valueOf(LocalDateTime.now()));
			token.setClave(Hash.getSha256(clave));
			DaoToken daoToken = new DaoToken();
			daoToken.addToken(token);
			
			//Tercer paso: Enviar un correo de validación con el mail que se han registrado
			
			String asunto = "Validación de alta de usuario en la Biblioteca";
			String cuerpo = Tools.creaCuerpoCorreo(token.getEmail(), token.getValue());
			Tools.enviarConGMail(token.getEmail(), asunto, cuerpo);
			System.out.println("Correo enviado");
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
	
	public Socio findSocioByEmail(String email) throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Socio socio = null;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			String ordenSQL = "SELECT IDSOCIO,NOMBRE,EMAIL,DIRECCION FROM SOCIO WHERE email=?";
			st = con.prepareStatement(ordenSQL);
			st.setString(1, email);
			rs = st.executeQuery();
			if (rs.next()) {
				socio = new Socio();
				socio.setIdSocio(rs.getInt("IDSOCIO"));
				socio.setNombre(rs.getString("NOMBRE"));
				socio.setEmail(rs.getString("EMAIL"));
				socio.setDireccion(rs.getString("DIRECCION"));
			}
		} catch (SQLException se) {
			throw se;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();

		}
		return socio;

	}
	
	public Socio findSocioByID(int idSocio) {
		
		Socio socio = null;
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Conexion miconex = new Conexion();
		
		String query = "SELECT IDSOCIO,NOMBRE,EMAIL,DIRECCION FROM SOCIO WHERE IDSOCIO=?";
		
		try {
			con = miconex.getConexion();
			st = con.prepareStatement(query);
			st.setInt(1, idSocio);
			rs = st.executeQuery();
			
			if(rs.next()) {
				socio = new Socio();
				socio.setIdSocio(rs.getInt("IDSOCIO"));
				socio.setNombre(rs.getString("NOMBRE"));
				socio.setEmail(rs.getString("EMAIL"));
				socio.setDireccion(rs.getString("DIRECCION"));
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
	
	public List<Socio> listadoSocios(int pagina, int numregpag)
			throws SQLException, Exception {

		List<Socio> listasocios;
		listasocios = new ArrayList<Socio>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			/*String ordenSql = "SELECT IDSOCIO,EMAIL,NOMBRE,DIRECCION "
					+ "FROM( SELECT ROWNUM FILA ,IDSOCIO,EMAIL,NOMBRE,DIRECCION "
					+ "FROM( SELECT IDSOCIO,EMAIL,NOMBRE,DIRECCION "
					+ "FROM SOCIO "
					+ "ORDER BY NOMBRE))"
					+ "WHERE FILA >=? AND FILA<=?";
			*/
			
			String ordenSql = "SELECT IDSOCIO,EMAIL,NOMBRE,DIRECCION FROM SOCIO";
			System.out.println("La orden lanzada es: " + ordenSql);
			st = con.prepareStatement(ordenSql);
			/*st.setInt(1, (pagina * numregpag) + 1);
			st.setInt(2, (pagina * numregpag) + numregpag);
			*/
			//Ahora tengo todas las tuplas de socio
			
			rs = st.executeQuery();
			while (rs.next()) {
				Socio miSocio = new Socio();
				miSocio.setIdSocio(rs.getInt("IDSOCIO"));
				miSocio.setEmail(rs.getString("EMAIL"));
				miSocio.setNombre(rs.getString("NOMBRE"));
				miSocio.setDireccion(rs.getString("DIRECCION"));
				listasocios.add(miSocio);
			}
			
			//Ya tengo la lista con todos los socios
			//Tengo que seleccionar solo los que incluyen en la página pedida
			
			List <Socio> listaParcial = new ArrayList<Socio>();
			/*for (int i = (pagina*numregpag); i < (((pagina*numregpag)+numregpag<listasocios.size())?(pagina*numregpag)+numregpag : listasocios.size()) ; i++) {
				listaParcial.add(listasocios.get(i));
			}
			
			*/
			
			//Otra solución
			
			listaParcial = listasocios.subList((pagina*numregpag),(((pagina*numregpag)+numregpag<listasocios.size())?(pagina*numregpag)+numregpag : listasocios.size()));
			//sublist obtiene un rango especifico de elementos de la lista completa
			//coge 2 paramatros, el indice inicial y el indice final
			//el inicial es pagina * numero de registros de pagina, por ejemplo si pag es 0 y registros 10, empiezas desde el indice 0
			//si la pagina es la 1 y los elementos 5, empiezas en la pagina 5
			
			//luego para el indice final usamos una ternaria, si pag*numregistros es menor que el tamaño, usamos ese valor como indice final
			//sino, hemos llegado al final y utilizaremos el tamaño como indice final
			listasocios=listaParcial;
			
			
			
		} catch (SQLException se) {
			throw se;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();

		}
		return listasocios;
	}
	
	public ArrayList<Socio> getBuscarSocioPorNombre(String nombre) throws SQLException{
		
		ArrayList<Socio> listaSociosPorNombre = new ArrayList<Socio>();
		
        Connection con = null;
        Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "SELECT * FROM SOCIO WHERE LOWER(NOMBRE) LIKE ?";
		
		try {
			con=miconex.getConexion();
			ps=con.prepareStatement(query);
			ps.setString(1, "%" + nombre.toLowerCase() + "%"); 
			ResultSet rc = ps.executeQuery();
			
			while(rc.next()) {
				
				int idSocio = rc.getInt("IDSOCIO");
				String email = rc.getString("EMAIL");
				String nombreSocio = rc.getString("NOMBRE");
				String direccion = rc.getString("DIRECCION");
				int version = rc.getInt("VERSION");
				
				Socio socio = new Socio(idSocio, email, nombreSocio, direccion, version);
				listaSociosPorNombre.add(socio);
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
		
		
		return listaSociosPorNombre;
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
	
	public void updateSocio(int idSocio,String nombre,String direccion) {
		
		Connection con = null;
		Conexion miconex = new Conexion();
		PreparedStatement ps = null;
		String query = "UPDATE SOCIO SET NOMBRE=?,DIRECCION=? WHERE IDSOCIO=?";
		
		try {
			con=miconex.getConexion();
			con.setAutoCommit(false);
			ps=con.prepareStatement(query);
			ps.setString(1, nombre);
			ps.setString(2, direccion);
			ps.setInt(3, idSocio);
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
	
	public int getTotalRegistros() throws SQLException, Exception {
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		int numeroRegistros = 0;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			st = con.createStatement();
			String ordenSQL = "SELECT COUNT(*) NUMEROREGISTROS FROM SOCIO";
			rs = st.executeQuery(ordenSQL);
			rs.next();
			numeroRegistros = rs.getInt("NUMEROREGISTROS");
		} catch (SQLException se) {
			throw se;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();

		}
		return numeroRegistros;
	}
	
	public ArrayList<Socio> listadoSociosMorosos()
			throws SQLException, Exception {

		ArrayList<Socio> listasocios;
		listasocios = new ArrayList<Socio>();
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			String ordenSql ="SELECT  IDSOCIO,NOMBRE "+
                             "FROM SOCIO S "+
                             "WHERE IDSOCIO IN(SELECT IDSOCIO "+
                                               "FROM PRESTAMO "+
                                                "WHERE FECHALIMITEDEVOLUCION<SYSDATE) "+
                             "ORDER BY S.IDSOCIO";
			System.out.println("La orden lanzada es: " + ordenSql);
			st = con.createStatement();
			rs = st.executeQuery(ordenSql);
			while (rs.next()) {
				Socio miSocio = new Socio();
				miSocio.setIdSocio(rs.getInt("IDSOCIO"));
				miSocio.setNombre(rs.getString("NOMBRE"));
				listasocios.add(miSocio);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();

		}
		return listasocios;
		
	}
	
	//método que activa la cuenta cuando recibimos la solicitud desde el email
		public void activarCuenta(String email, String valortoken) throws Exception {
			Connection con = null;
			PreparedStatement st = null;
			//lo primero debería ser comprobar el token
			DaoToken daoToken = new DaoToken();
			Conexion miconex = new Conexion();
			try {
				con = miconex.getConexion();
				con.setAutoCommit(false); //como voy a hacer varios cambios, los valído al final
				Token token = daoToken.findTokenByEmail(email);
				if (token.getValue().equals(valortoken)) {
					String ordenSQL = "INSERT INTO USUARIOS VALUES(?,?)";
					st = con.prepareStatement(ordenSQL);
					st.setString(1, email);
					st.setString(2, token.getClave() );
					st.executeUpdate();
					st.close();
					ordenSQL = "INSERT INTO GRUPOS VALUES(?,?)";
					st = con.prepareStatement(ordenSQL);
					st.setString(1, "sociosbiblioteca");
					st.setString(2, email);
					st.executeUpdate();
					st.close();
					ordenSQL = "DELETE FROM TOKEN WHERE EMAIL=?";
					st = con.prepareStatement(ordenSQL);
					st.setString(1, email);
					st.executeUpdate();
					st.close();
					con.commit(); //valído los 3 cambios
				}
			} catch (SQLException e) {
				con.rollback();
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				con.rollback();
				e.printStackTrace();
				throw e;
			}finally {
				if(con != null) {
					con.close();
				}
			}
		}

}
