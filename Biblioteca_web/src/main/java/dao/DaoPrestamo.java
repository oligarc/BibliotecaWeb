package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import conexiones.Conexion;
import entidades.Prestamo;
import entidades.Socio;
import excepciones.PrestamoException;

public class DaoPrestamo {

    public DaoPrestamo() {
    }
/********************************************************************************/
    public void insertaPrestamo(Prestamo p)throws PrestamoException,SQLException,Exception{
       String ordenSQL;
       Connection con=null;
       PreparedStatement sentencia=null;
       DaoEjemplar daoEjemplar=new DaoEjemplar();
       if(daoEjemplar.findEjemplarById(p.getIdejemplar())==null)
    	   throw new PrestamoException("Ejemplar no existente o dado de baja");  
        Prestamo prestamo = findPrestamoById(p.getIdejemplar());    
       if(prestamo!=null)
			throw new PrestamoException("Préstamo duplicado. El ejemplar indicado está en préstamo");
       
       Socio socio = new Socio();
       DaoSocio daoSocio = new DaoSocio();
       
       socio = daoSocio.findSocioByID((int) p.getIdsocio());
       
       if(socio==null) {
    	   throw new PrestamoException("El código de socio no es válido");
       }
       
       ArrayList<Socio> listadoSociosMorosos = new ArrayList<Socio>();
       listadoSociosMorosos = daoSocio.listadoSociosMorosos();
       
       for (Socio socioMoroso : listadoSociosMorosos) {
		if(socioMoroso.getIdSocio()==p.getIdsocio()) {
			throw new PrestamoException("El socio tiene ya un préstamo que devolver");
		}
	}
       
    // Comprobar si el mismo socio ya tiene un préstamo del mismo ejemplar
       Prestamo prestamoRepetido = findPrestamoById(p.getIdejemplar());
       if (prestamoRepetido != null && prestamoRepetido.getIdsocio() == p.getIdsocio()) {
           throw new PrestamoException("El socio no puede tener más de un préstamo del mismo libro");
       }
       
       LocalDate fechalimiteDevolucion = LocalDate.now();
       
       fechalimiteDevolucion =fechalimiteDevolucion.plusDays(5);
       int diaSemana = fechalimiteDevolucion.getDayOfWeek().getValue();
       
       if(diaSemana == 6) {
    	   fechalimiteDevolucion = fechalimiteDevolucion.plusDays(2);
       }else if(diaSemana ==7) {
    	   fechalimiteDevolucion = fechalimiteDevolucion.plusDays(1);
       }
       
       
        try{
            Conexion miconex=new Conexion();
            con=miconex.getConexion();
            ordenSQL="INSERT INTO PRESTAMO(IDEJEMPLAR,IDSOCIO,FECHAPRESTAMO,FECHALIMITEDEVOLUCION) VALUES(?,?,SYSDATE,?)";
            sentencia=con.prepareStatement(ordenSQL);
            sentencia.setInt(1,p.getIdejemplar());
            sentencia.setLong(2,p.getIdsocio());
            sentencia.setObject(3, fechalimiteDevolucion);
            sentencia.executeUpdate();
            sentencia.close();
            con.close();
       }catch(SQLException se){
               throw se;
       }catch(Exception e){
             throw e;
       }
        finally{
        	if(sentencia!=null)
        		sentencia.close();
        	if(con!=null)
               con.close();
        }
    }
    /*********************************************************************************************************************************************************/
    public int devolucionPrestamo(int idejemplar)throws SQLException,Exception{
       String ordenSQL;
       int resultado;
       Connection con=null;
       PreparedStatement sentencia=null;
       DaoEjemplar daoEjemplar = new DaoEjemplar();
       Conexion miconex=new Conexion();
       con=miconex.getConexion();
       try{
    	   
    	   //Comprobar si el ejemplar existe
    	   	
    	   	if(daoEjemplar.findEjemplarById(idejemplar) == null) {
    	   		throw new PrestamoException("Ejemplar no existe o está dado de baja.");
    	   	}
    	   	
    	   	//Comprobar si el ejemplar está prestado
    	   	
    	   	Prestamo prestamo = this.findPrestamoById(idejemplar);
    	   	
    	   	if(prestamo == null) {
    	   		throw new PrestamoException("Imposible hacer la devolución. Este ejemplar no está en préstamo");
    	   	}
    	   	
    	   	//Ahora nos queda comprobar la fecha de devolución por si se ha retrasado
    	   	
    	   	
    	   	if(prestamo.getFechalimitedevolucion().before(java.sql.Date.valueOf(LocalDate.now()))) {
    	   		ordenSQL = "INSERT INTO SOCIOPENALIZADO VALUES(?,SYSDATE+15)";
    	   		sentencia = con.prepareStatement(ordenSQL);
    	   		sentencia.setLong(1, prestamo.getIdsocio());
    	   		sentencia.executeUpdate();
    	   		
    	   	}
            
            ordenSQL="DELETE FROM PRESTAMO WHERE IDEJEMPLAR=?";
            sentencia=con.prepareStatement(ordenSQL);
            sentencia.setInt(1,idejemplar);
            resultado=sentencia.executeUpdate();
            
            //Añadimos una tupla en la tabla devolucion
            
            ordenSQL = "INSERT INTO DEVOLUCION VALUES (?,?,?,SYSDATE)";
            sentencia.setInt(1,idejemplar);
            sentencia.setLong(2, prestamo.getIdsocio());
            sentencia.setObject(3, prestamo.getFechaprestamo());
            sentencia.executeUpdate();
            sentencia.close();
            con.close();
       }catch(SQLException se){
               throw se;
       }catch(Exception e){
       throw e;
       }
       finally{
       	if(sentencia!=null)
       		sentencia.close();
       	if(con!=null)
              con.close();
       }
       return resultado;
    }
    /******************************************************************************************************/
    public ArrayList<Prestamo>listadoPrestamosFueraPlazo(long socio)throws SQLException,Exception{

        String ordenSQL;

        ArrayList<Prestamo> prestamosfueraplazo;

        prestamosfueraplazo = new ArrayList<Prestamo>();

        try{

              Conexion miconex=new Conexion();

              Connection con=miconex.getConexion();

              ordenSQL="SELECT IDEJEMPLAR,IDSOCIO,NOMBRE,TITULO,DIAS_DEMORA,FECHAPRESTAMO "+

                       "FROM(SELECT P.IDEJEMPLAR,P.IDSOCIO,S.NOMBRE,L.TITULO,(TRUNC(SYSDATE)-TRUNC(FECHALIMITEDEVOLUCION))DIAS_DEMORA,P.FECHAPRESTAMO "+

                       "FROM SOCIO S,PRESTAMO P,EJEMPLAR E,LIBRO L "+

                       "WHERE S.IDSOCIO=P.IDSOCIO "+

                       "AND P.IDEJEMPLAR=E.IDEJEMPLAR "+

                       "AND E.ISBN=L.ISBN "+

                       "AND TRUNC(FECHALIMITEDEVOLUCION)<TRUNC(SYSDATE)) "+

                       "WHERE IDSOCIO=?";

              PreparedStatement sentencia=con.prepareStatement(ordenSQL);

              sentencia.setLong(1,socio);

              ResultSet rs=sentencia.executeQuery();

              while(rs.next()){

                 Prestamo miPrestamo=new Prestamo();

                 miPrestamo.setIdejemplar(rs.getInt("IDEJEMPLAR"));

                 miPrestamo.setIdsocio(rs.getLong("IDSOCIO"));

                 miPrestamo.setNombreSocio(rs.getString("NOMBRE"));

                 miPrestamo.setTitulo(rs.getString("TITULO"));

                 miPrestamo.setDiasDemora(rs.getInt("DIAS_DEMORA"));

                 miPrestamo.setFechaprestamo(rs.getDate("FECHAPRESTAMO"));

                 prestamosfueraplazo.add(miPrestamo);

              }

              rs.close();

              sentencia.close();

              con.close();

        } catch (SQLException se) {

                throw se;

        } catch (Exception e) {

                throw e;

        }

        return prestamosfueraplazo;

        }
    
    /******************************************************************************************************/
    public Prestamo findPrestamoById(int idejemplar)throws SQLException,Exception {
    	
        Connection con=null;
        PreparedStatement ps=null;	
        Conexion conexion=new Conexion();
        ResultSet rs= null;
        Prestamo prestamo=null;
        try {
			con=conexion.getConexion();
			ps=con.prepareStatement("SELECT IDEJEMPLAR,IDSOCIO,FECHAPRESTAMO,FECHALIMITEDEVOLUCION "+
			          				"FROM PRESTAMO "+
					  				"WHERE IDEJEMPLAR=?");
			ps.setInt(1, idejemplar);
			rs=ps.executeQuery();
			if(rs.next()) {
				prestamo=new Prestamo();
				prestamo.setIdejemplar(rs.getInt("IDEJEMPLAR"));
				prestamo.setIdsocio(rs.getLong("IDSOCIO"));
				prestamo.setFechaprestamo(rs.getDate("FECHAPRESTAMO"));
				prestamo.setFechalimitedevolucion(rs.getDate("FECHALIMITEDEVOLUCION"));	
			}
					
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		}
        finally{
           	if(rs!=null)
           		rs.close();
           	if(con!=null)
                  con.close();
           }        
        return prestamo;
    }
    
    /***************************FIN DE LA CLASE DaoPrestamo **********************************************************************/
}