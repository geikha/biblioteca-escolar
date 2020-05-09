package dao;

import java.sql.*;
import java.util.ArrayList;

import biblioteca.Libro;

public class AutorxDAO extends DAO {

	public AutorxDAO() {
		setTabla("autorxs");
	}

	// COLUMNAS EN SQL: |id_autorx|nombre|
	// COLUMNAS DE libros_autorxs: |ISBN_libro|id_autorx|
	
	public ArrayList<String> ResultSetToArrayList(ResultSet rs){
		ArrayList<String> autorxs = new ArrayList<String>();
		try {
			while(rs.next())
				autorxs.add(rs.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return autorxs;
	}

	// QUERIES

	/**
	 * @param ISBN El ISBN de un Libro
	 * @return Una lista de autorxs en formato String
	 * @category Queries
	 */
	public ArrayList<String> autorxsFromLibro(String ISBN) {
		String sql = "SELECT a.nombre FROM libros_autorxs AS la JOIN autorxs AS a ON la.id_autorx = a.id_autorx WHERE la.ISBN_libro = "
				+ "'" + ISBN + "'";
		ResultSet rs = ejecutarQuery(sql);

		ArrayList<String> autorxs = new ArrayList<String>();

		try {
			while (rs.next()) {
				autorxs.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		return autorxs;
	}

	/**
	 * @param nombre El nombre de unx Autorx
	 * @return El ID de dicho autorx
	 * @apiNote Si el nombre no se encuentra en la BDDR, crea unx nuevx autorx
	 * @category Queries
	 */
	public int idFrom(String nombre) {
		ResultSet rs = ResultSetFiltro("nombre", nombre);
		int id = 0;

		try {
			if (rs.next())
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
	 * @param nombre El nombre de lx autorx a crearse
	 * @apiNote Le da de alta a unx autorx
	 * @category ABM
	 */
	public void alta(String nombre) {
		String sql = "INSERT INTO biblioteca.autorxs VALUES (null,?)";
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
	 * @param nombre El nombre de lx autorx a modificarse
	 * @apiNote Modifica el nombre de unx autorx
	 * @category ABM
	 */
	public void modificacion(String nombre) {
		String sql = "UPDATE `biblioteca`.`autorxs` " + "SET `id_autorx` = " + idFrom(nombre) + ", `nombre` = " + nombre
				+ "WHERE `id_autorx` = " + idFrom(nombre);
		ejecutarUpdate(sql);
	}

	public ArrayList<String> todxs() {
		ResultSet rs = ResultSetTodos();
		return ResultSetToArrayList(rs);
	}
	
	public void altaLibroAutor(Libro libro, String nombre) {
		String sql = "INSERT IGNORE INTO libros_autorxs VALUES (?,?);";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, libro.getISBN());
			ps.setInt(2, idFrom(nombre));
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void bajaLibroAutor(Libro libro, Libro nuevo) {
		String sql = "DELETE FROM libros_autorxs WHERE ISBN_libro = ? AND id_autorx = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, libro.getISBN());
			for(String autor : libro.getAutorxs()) {
				if(!nuevo.getAutorxs().contains(autor)) {
					ps.setInt(2, idFrom(autor));
					ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
