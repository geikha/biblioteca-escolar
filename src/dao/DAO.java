package dao;


import java.sql.*;

public class DAO {
	
	//SUPERCLASE DAO CON METODOS Y DATOS UTILES EN TODO TIPO DE DAOS
	
	/* MÉTODOS A EXISTIR EN LAS SUBCLASES : 
	 * 		baja(Object o) : void
	 * 		alta(Object o) : void
	 * 		modificacion(Object o, Object nuevo) : void
	 * 		ResultSetToArrayList(ResultSet rs) : ArrayList<Object> 
	 *
	 * 			Object -> CLASE CORRESPONDIENTE A LA SUBCLASE
	 */

	private String url = "jdbc:mysql://localhost:3306/biblioteca?useSSL=true&serverTimezone=GMT-3";
	//private String url = "jdbc:sqlite:/home/iglosiggio/experimentos/eclipse-workspace/Biblioteca/db.sqlite3";
	private String user = "root";
	private String password = "admin";
	private String tabla;	
			//NOMBRE DE LA TABLA EN SQL, A USARSE EN SUBCLASES
	protected Connection conexion = conexion(); 
			//ESTABLECE LA CONEXION AL CREARSE UNA INSTANCIA DE ESTA CLASE O SUS SUBCLACES
		
	//QUERIES
	
	/**
	 * @param sql Query SQL
	 * @apiNote Ejecuta un Update (ABM) dada una Query SQL.
	 * @category Queries
	 */
	protected void ejecutarUpdate(String sql) {
		try {
			conexion.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param sql Query SQL
	 * @return Devuelve el ResultSet de una Query SQL.
	 * @category Queries
	 */
	protected ResultSet ejecutarQuery(String sql) {
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param columna La columna por la cual filtrar
	 * @param filtro Filtro a aplicar
	 * @return ResultSet de la Query resultante.
	 * @implNote Ejecuta la Query SELECT * FROM tabla WHERE columna LIKE '%filtro%'
	 * @category Queries
	 */
	protected ResultSet ResultSetFiltro(String columna, String filtro) {
		return ejecutarQuery("SELECT * FROM " + tabla + " WHERE " + columna + " LIKE '%" + filtro + "%'");
	}
	
	/**
	 * @param columna La columna por la cual filtrar
	 * @param filtro Filtro a aplicar
	 * @return ResultSet de la Query resultante.
	 * @apiNote Util para Primary Keys que sean IDs.
	 * @implNote Ejecuta la Query SELECT * FROM tabla WHERE columna = filtro
	 * @category Queries
	 */
	protected ResultSet ResultSetFiltro(String columna, int filtro) {
		return ejecutarQuery("SELECT * FROM " + tabla + " WHERE " + columna + "=" + filtro);
	}

	/**
	 * @return Toda la tabla
	 * @implNote SELECT * FROM tabla LIMIT 1000
	 * @category Queries
	 */
	protected ResultSet ResultSetTodos() {
		return ejecutarQuery("SELECT * FROM " + tabla + " LIMIT 1000");
	}
	
	//ABM
	
	// (ALTAS Y BAJAS EN SUBCLASES)
	
	/**
	 * @param condicion Condicion a aplicarse
	 * @return El DELETE SQL correspondiente a la condición dada.
	 * @implNote DELETE FROM tabla WHERE condicion
	 * @category ABM
	 */
	protected String sqlBaja(String condicion) {
		return "DELETE FROM " + tabla + " WHERE " + condicion;
	}
	
	// CONEXION

	/**
	 * @return Connection
	 * @apiNote Establece y retorna la conexion
	 */
	private Connection conexion() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @apiNote Cierra la conexion
	 * @implNote conexion.close();
	 */
	public void cerrarConexion() {
		try {
			if (conexion != null) 	//Por si la conexión no se estableció
				conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// GETS SETS

	public String getTabla() {
		return tabla;
	}

	/**
	 * @param tabla Tabla correspondiente a la subclase.
	 * @implNote Úsese en los constructores de la ssubclases.
	 */
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public Connection getConexion() {
		return conexion;
	}
}
