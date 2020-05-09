package dao;

import java.sql.*;
import java.util.ArrayList;

public class EditorialDAO extends DAO{

	public EditorialDAO() {
		setTabla("editoriales");
	}
	
	// COLUMNAS EN SQL: |id_editorial|nombre|
	
	public ArrayList<String> ResultSetToArrayList(ResultSet rs){
		ArrayList<String> editoriales = new ArrayList<String>();
		try {
			while(rs.next())
				editoriales.add(rs.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return editoriales;
			
	}
	
	//QUERIES (FROM)
	
	/**
	 * @param id El id de una editorial en la BDDR
	 * @return El nombre de la editorial
	 * @category Queries
	 */
	public String editorialFrom(int id) {
		ResultSet rs = ResultSetFiltro("id_editorial", id);
		String editorial = "";
		
		try {
			rs.next();
			editorial = rs.getString(2);
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		
		return editorial;
	}
	
	/**
	 * @param nombre El nombre de una editorial en la BDDR
	 * @return El ID de dicha editorial
	 * @apiNote Si no encuentra el nombre, crea una nueva editorial
	 * @category Queries
	 */
	public int idFrom(String nombre) {
		ResultSet rs = ResultSetFiltro("nombre", nombre);
		int id = 0;
		
		try {
			if(rs.next())
				id = rs.getInt(1);
			else {
				alta(nombre);
				return idFrom(nombre);
			}
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		
		return id;
	}
	
	// ABM
	
	/**
	 * @param nombre El nombre de la editorial a añadirse
	 * @apiNote Crea una nueva editorial
	 * @category ABM
	 */
	public void alta(String nombre) {
		String sql = "INSERT INTO biblioteca.editoriales VALUES (null,?)";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	/**
	 * @param nombre El nombre de la editorial a modificarse
	 * @apiNote Modifica el nombre de una editorial
	 * @category ABM
	 */
	public void modificacion(String nombre) {
		String sql = "UPDATE `biblioteca`.`editoriales` "
				+ "SET `id_editorial` = " + idFrom(nombre) + ", `nombre` = " + nombre 
				+ "WHERE `id_editorial` = " + idFrom(nombre);
		ejecutarUpdate(sql);
	}
	
	public ArrayList<String> todas(){
		ResultSet rs = ResultSetTodos();
		return ResultSetToArrayList(rs);
	}
	
}
