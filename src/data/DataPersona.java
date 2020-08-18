package data;
//orig
import entities.*;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class DataPersona {
	
	public LinkedList<Persona> getAll(){
		DataRol dr=new DataRol();
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Persona> pers= new LinkedList<>();
		
		try {
			stmt= DbConnector.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("select id,nombre,apellido,tipo_doc,nro_doc,email,tel,habilitado from persona");
			//intencionalmente no se recupera la password
			if(rs!=null) {
				while(rs.next()) {
					Persona p=new Persona();
					p.setDocumento(new Documento());
					p.setId(rs.getInt("id"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					p.getDocumento().setTipo(rs.getString("tipo_doc"));
					p.getDocumento().setNro(rs.getString("nro_doc"));
					p.setEmail(rs.getString("email"));
					p.setTel(rs.getString("tel"));
					
					p.setHabilitado(rs.getBoolean("habilitado"));
					
					dr.setRoles(p);
					
					pers.add(p);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return pers;
	}
	
	public Persona getByUser(Persona per) {
		DataRol dr=new DataRol();
		Persona p=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,nombre,apellido,tipo_doc,nro_doc,email,tel,habilitado from persona where email=? and password=?"
					);
			stmt.setString(1, per.getEmail());
			stmt.setString(2, per.getPassword());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				p=new Persona();
				p.setDocumento(new Documento());
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.getDocumento().setTipo(rs.getString("tipo_doc"));
				p.getDocumento().setNro(rs.getString("nro_doc"));
				p.setEmail(rs.getString("email"));
				p.setTel(rs.getString("tel"));
				p.setHabilitado(rs.getBoolean("habilitado"));
				//
				dr.setRoles(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return p;
	}
	
	public Persona getByDocumento(Persona per) {
		DataRol dr=new DataRol();
		Persona p=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,nombre,apellido,tipo_doc,nro_doc,email,tel,habilitado from persona where tipo_doc=? and nro_doc=?"
					);
			stmt.setString(1, per.getDocumento().getTipo());
			stmt.setString(2, per.getDocumento().getNro());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				p=new Persona();
				p.setDocumento(new Documento());
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.getDocumento().setTipo(rs.getString("tipo_doc"));
				p.getDocumento().setNro(rs.getString("nro_doc"));
				p.setEmail(rs.getString("email"));
				p.setTel(rs.getString("tel"));
				p.setHabilitado(rs.getBoolean("habilitado"));
				//
				dr.setRoles(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch( java.lang.NullPointerException je) {
			je.printStackTrace();
			System.out.println(je.getMessage());
		}
		finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return p;
	}
	public LinkedList<Persona> getByApellido(Persona per){
		DataRol dr = new DataRol();
		Persona p = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		LinkedList<Persona> pers = new LinkedList<>();
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,nombre,apellido,tipo_doc,nro_doc,email,tel,habilitado from persona where apellido like ?");
			stmt.setString(1, per.getApellido() + "%");
			rs=stmt.executeQuery();
			if(rs!=null) {
				while (rs.next()) {
					p=new Persona();
					p.setDocumento(new Documento());
					p.setId(rs.getInt("id"));
					p.setNombre(rs.getString("nombre"));
					p.setApellido(rs.getString("apellido"));
					p.getDocumento().setNro(rs.getString("tipo_doc"));
					p.getDocumento().setTipo(rs.getString("nro_doc"));
					p.setEmail(rs.getString("email"));
					p.setTel(rs.getString("tel"));
					p.setHabilitado(rs.getBoolean("habilitado"));
					
					dr.setRoles(p);
					
					pers.add(p);
				}	
			}
		}catch (SQLException e) {
			System.out.println("Error en la clase DataPersona, 1° try " + e.toString());
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}
				DbConnector.getInstancia().releaseConn();
			}catch(SQLException e){
				System.out.println("Error en la clase DataPersona, 2° try " + e.toString());
				e.printStackTrace();
			}
		}
		return pers;
	}	
	public void add(Persona p) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		Data_rol_persona drp = new Data_rol_persona();
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into persona(nombre, apellido, tipo_doc, nro_doc, email, password, habilitado, tel) values(?,?,?,?,?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setString(1, p.getNombre());
			stmt.setString(2, p.getApellido());
			stmt.setString(3, p.getDocumento().getTipo());
			stmt.setString(4, p.getDocumento().getNro());
			stmt.setString(5, p.getEmail());
			stmt.setString(6, p.getPassword());
			stmt.setBoolean(7, p.isHabilitado());
			stmt.setString(8, p.getTel());
			stmt.executeUpdate();	
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                p.setId(keyResultSet.getInt(1));
            }
            
            drp.add_rol_persona(p, p.getRol());
			
		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
    }
	public void updateUser(Persona per, HashMap<Integer, Rol> roles) {
		PreparedStatement stmt= null;
		Data_rol_persona drp = new Data_rol_persona();
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("update persona set nombre=?, apellido=?, tipo_doc=?, nro_doc=?, email=?, password=?, habilitado=?, tel=? where id=?");
			stmt.setString(1, per.getNombre());
			stmt.setString(2, per.getApellido()); 
			stmt.setString(3, per.getDocumento().getTipo());
			stmt.setString(4, per.getDocumento().getNro());
			stmt.setString(5, per.getEmail());
			stmt.setString(6, per.getPassword());
			stmt.setBoolean(7, per.isHabilitado());
			stmt.setString(8, per.getTel());
			stmt.setInt(9, per.getId());
			stmt.executeUpdate();
			
			//drp.update_rol_persona(per, roles);
			drp.delete_rol_persona(per);
			drp.add_rol_persona(per, roles);
			
		}catch (SQLException e) {
            e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteUser(Persona per) {
		Data_rol_persona drp = new Data_rol_persona();
		drp.delete_rol_persona(per);
		
		PreparedStatement stmt = null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("delete from persona where id = ?");
			stmt.setInt(1, per.getId());
			stmt.executeUpdate();
						
		}catch(Exception ex) {
			System.out.println("Error en la clase Data Persona 1° try " + ex.toString());
			ex.printStackTrace();
		}finally {
			try {
				 if(stmt!=null)stmt.close();
	             DbConnector.getInstancia().releaseConn();
			}catch(SQLException e) {
				System.out.println("Error en DatePersona en el catch del finally");
				e.printStackTrace();
			}
		}
	}
	
}
