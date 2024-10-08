package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexiones.Conexion;
import entidades.Autor;
import entidades.Socio;

public class DaoAutor {
	public DaoAutor() {

	}

	public ArrayList<Autor> listadoAutores() throws SQLException, Exception {
		ArrayList<Autor> listaautores;
		listaautores = new ArrayList<Autor>();
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			st = con.createStatement();
			String ordenSQL = "SELECT * FROM AUTOR ORDER BY NOMBRE";
			rs = st.executeQuery(ordenSQL);
			while (rs.next()) {
				Autor miAutor = new Autor();
				miAutor.setIdAutor(rs.getInt("IDAUTOR"));
				miAutor.setNombre(rs.getString("NOMBRE"));
				miAutor.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
				listaautores.add(miAutor);
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
		return listaautores;
	}
/*******************************************************************************/	
/*
 *     Nuevo autor
 *     Procedimiento para dar de alta un autor mediante un PreparedStatement
 *
 ********************************************************************************/
	   public void insertaAutor(Autor a) throws SQLException, Exception {
	        Connection con=null;
	        PreparedStatement st=null;
	        try {
	            Conexion miconex = new Conexion();
	            con = miconex.getConexion();
	            con.setAutoCommit(false);
	            String ordenSQL = "INSERT INTO AUTOR VALUES(S_AUTOR.NEXTVAL,?,?)";
	            st = con.prepareStatement(ordenSQL);
	            st.setString(1, a.getNombre());
	            st.setDate(2,a.getFechaNacimiento());
	            st.executeUpdate();
	            con.commit();
	            st.close();
	            con.close();
	        } catch (SQLException se) {
	            throw se;
	        } catch (Exception e) {
	            throw e;
	        }
	        finally{
	         	if(st!=null)
	                st.close();
	         	if(con!=null)
	                con.close();
	        }
	    }
/**************************************************************************************/
	   public void addAutorPLSQL(Autor a) throws SQLException, Exception{
	        Connection con=null;
	        CallableStatement st=null;
	        try {
	            Conexion miconex = new Conexion();
	            con = miconex.getConexion();
	            String ordenSQL = "{call ADDAUTOR(?,?)}";
	            st = con.prepareCall(ordenSQL);
	            st.setString(1, a.getNombre());
	            st.setDate(2,a.getFechaNacimiento());
	            st.executeUpdate();
	            st.close();
	            con.close();
	        } catch (SQLException se) {
	            throw se;
	        } catch (Exception e) {
	            throw e;
	        }
	        finally{
	         	if(st!=null)
	                st.close();
	         	if(con!=null)
	                con.close();
	        }		   
		   
	   }
/**************************************************/
	public Autor findAutorById(int idautor)throws SQLException, Exception {
		Autor a=null;
        Connection con=null;
        PreparedStatement st=null;
        ResultSet rs = null;
        try {
            Conexion miconex = new Conexion();
            con = miconex.getConexion();
            con.setAutoCommit(false);
            String ordenSQL = "SELECT IDAUTOR,NOMBRE,FECHANACIMIENTO FROM AUTOR"+
            				  " WHERE IDAUTOR=?";
            st = con.prepareStatement(ordenSQL);
            st.setInt(1, idautor);
            rs=st.executeQuery();  // no se pasa la orden como parámetro porque ya
            if(rs.next()) {        // lo hemos hecho aqui con.prepareStatement(ordenSQL);
            	a=new Autor();
            	a.setIdAutor(rs.getInt("IDAUTOR"));
            	a.setNombre(rs.getString("NOMBRE"));
            	a.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
            }
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        }
        finally{
         	if(st!=null)
                st.close();
         	if(con!=null)
                con.close();
        }
        return a;
	}
	
	public List<Autor> listadoAutores(int pagina, int numregpag)
			throws SQLException, Exception {

		List<Autor> listaAutores;
		listaAutores = new ArrayList<Autor>();
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
			
			String ordenSql = "SELECT IDAUTOR,NOMBRE,FECHANACIMIENTO FROM AUTOR";
			System.out.println("La orden lanzada es: " + ordenSql);
			st = con.prepareStatement(ordenSql);
			/*st.setInt(1, (pagina * numregpag) + 1);
			st.setInt(2, (pagina * numregpag) + numregpag);
			*/
			//Ahora tengo todas las tuplas de socio
			
			rs = st.executeQuery();
			while (rs.next()) {
				Autor miAutor = new Autor();
				miAutor.setIdAutor(rs.getInt("IDAUTOR"));
				miAutor.setNombre(rs.getString("NOMBRE"));
				miAutor.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
				listaAutores.add(miAutor);
			}
			
			//Ya tengo la lista con todos los socios
			//Tengo que seleccionar solo los que incluyen en la página pedida
			
			List <Autor> listaParcial = new ArrayList<Autor>();
			/*for (int i = (pagina*numregpag); i < (((pagina*numregpag)+numregpag<listasocios.size())?(pagina*numregpag)+numregpag : listasocios.size()) ; i++) {
				listaParcial.add(listasocios.get(i));
			}
			
			*/
			
			//Otra solución
			
			listaParcial = listaAutores.subList((pagina*numregpag),(((pagina*numregpag)+numregpag<listaAutores.size())?(pagina*numregpag)+numregpag : listaAutores.size()));
			//sublist obtiene un rango especifico de elementos de la lista completa
			//coge 2 paramatros, el indice inicial y el indice final
			//el inicial es pagina * numero de registros de pagina, por ejemplo si pag es 0 y registros 10, empiezas desde el indice 0
			//si la pagina es la 1 y los elementos 5, empiezas en la pagina 5
			
			//luego para el indice final usamos una ternaria, si pag*numregistros es menor que el tamaño, usamos ese valor como indice final
			//sino, hemos llegado al final y utilizaremos el tamaño como indice final
			listaAutores=listaParcial;
			
			
			
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
		return listaAutores;
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
			String ordenSQL = "SELECT COUNT(*) NUMEROREGISTROS FROM AUTOR";
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
}
