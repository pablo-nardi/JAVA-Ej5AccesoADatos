package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import entities.Persona;
import entities.Rol;

public class Data_rol_persona {
	
	public void add_rol_persona(Persona per, HashMap<Integer, Rol>roles) {
		PreparedStatement stmt = null;
		try {
			for(int i = 1 ; i <= roles.size() ; i++) {
				Rol r = roles.get(i); 	// ¿COMO MEJORO ESTA PARTE?
				if(r == null) {			// LA IDEA ES TENER UNA FORMA DE OBTENER EL ROL QUE CORRESPONDA
					r = roles.get(2);	//
				}
				stmt=DbConnector.getInstancia().getConn().prepareStatement("insert into rol_persona (id_persona, id_rol) values (?, ?)");
				stmt.setInt(1, per.getId());
				stmt.setInt(2, r.getId());
				stmt.executeUpdate();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}
	public void update_rol_persona(Persona per, HashMap<Integer, Rol>roles) {
		PreparedStatement stmt = null;
		try {
			
			stmt=DbConnector.getInstancia().getConn().prepareStatement("delete from rol_persona where id_persona = ?");
			stmt.setInt(1, per.getId());
			stmt.executeUpdate();
			//no realiza el delete de los roles que no se necesitan
			
			this.add_rol_persona(per, roles);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				 if(stmt!=null)stmt.close();
	             DbConnector.getInstancia().releaseConn();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void delete_rol_persona(Persona per) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("select id from persona where tipo_doc = ? and nro_doc = ?");
			stmt.setString(1, per.getDocumento().getTipo());
			stmt.setString(2, per.getDocumento().getNro());
			
			rs = stmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				per.setId(rs.getInt("id"));// 
			}
		}catch(Exception ex) {
			System.out.println("Error en la clase Data_rol_persona, 1° try " + ex.toString());
			ex.printStackTrace();
		}
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("delete from rol_persona where id_persona = ?");
			stmt.setInt(1, per.getId());
			stmt.executeUpdate();
						
			
		}catch(Exception ex) {
			System.out.println("Error en la clase Data_rol_persona, 2° try " + ex.toString());
			ex.printStackTrace();
		}finally {
			try {
				 if(stmt!=null)stmt.close();
	             if(rs!=null)rs.close();
	             DbConnector.getInstancia().releaseConn();
			}catch(SQLException e) {
				System.out.println("Error en Data_rol_persona en el catch del finally");
				e.printStackTrace();
			}
		}
	}
}
