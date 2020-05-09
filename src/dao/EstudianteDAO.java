package dao;

import java.sql.*;
import dao.UserDAO;
import java.util.ArrayList;
import java.time.LocalDate;
import biblioteca.*;

public class EstudianteDAO extends DAO { 

	public EstudianteDAO() {
		setTabla("estudiantes");
	}
	
	// COLUMNAS EN SQL: |DNI|nombre_apellido|curso|telefono|fecha_alta|user|
	
	/**
	 * @param rs ResultSet
	 * @return Formato ArrayList de Estudiantes del RS recibido
	 * @implNote Usa UserDAO para obtener los datos correspondientes
	 */
	private ArrayList<Estudiante> ResultSetToArrayList(ResultSet rs) {
		ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
		UserDAO udao = new UserDAO();

		try {
			while (rs.next()) {
				int DNI = rs.getInt(1);
				String nombreApellido = rs.getString(2);
				int curso = rs.getInt(3);
				String telefono = rs.getString(4);
				LocalDate fechaAlta = rs.getDate(5).toLocalDate();
				User user = udao.userFrom(rs.getInt(6));

				estudiantes.add(new Estudiante(user, DNI, nombreApellido, curso, telefono, fechaAlta));
			}
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		udao.cerrarConexion();
		return estudiantes;
	}
	
	//QUERIES
	
	/**
	 * @param DNI (o parte) de unx Estudiante
	 * @return Una lista de Estudiantes compatibles.
	 * @category Queries
	 */
	public ArrayList<Estudiante> estudiantesPorDNI(String DNI){
		ResultSet rs = ResultSetFiltro("DNI", DNI);
		return ResultSetToArrayList(rs);
	}
	
	/**
	 * @param nombre Nombre de unx Estudiante
	 * @return Una lista de estudiantes compatibles
	 * @category Queries
	 */
	public ArrayList<Estudiante> estudiantesPorNombre(String nombre){
		ResultSet rs = ResultSetFiltro("nombre_apellido", nombre);
		return ResultSetToArrayList(rs);
	}
	
	/**
	 * @return Todxs lxs estudiantes
	 * @category Queries
	 */
	public ArrayList<Estudiante> todxs(){
		ResultSet rs = ResultSetTodos();
		return ResultSetToArrayList(rs);
	}
	
	//FROM
	
	/**
	 * @param idReserva El ID de una reserva
	 * @return El estudiante correspondiente a esa Reserva
	 * @category Queries
	 */
	public Estudiante estudianteFromReserva(int idReserva) {
		String sql = "SELECT e.* FROM reservas AS r JOIN estudiantes AS e ON r.estudiante = e.DNI WHERE r.id_reserva = " + idReserva;
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs).get(0);
	}
	
	public Estudiante estudianteFromEntrega(int idReserva) {
		String sql = "SELECT e.* FROM entregas AS en JOIN estudiantes AS e ON en.estudiante = e.DNI WHERE en.id_entrega = " + idReserva;
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs).get(0);
	}
	
	// ABM
	
	/**
	 * @param e Unx Estudiante
	 * @apiNote Le da de baja a un Estudiante y a su usuario
	 * @implNote Usa UserDAO y le da de baja al usuario correspondiete
	 * @category ABM
	 */
	public void baja(Estudiante e) {
		UserDAO udao = new UserDAO();
		udao.baja(e.usuario());
		
		String condicion = "DNI = '" + e.getDNI() + "'";
		String sql = sqlBaja(condicion);
		
		ejecutarUpdate(sql);
	}

	/**
	 * @param e Estudiante original
	 * @param nuevx Estudiante actualizadx
	 * @apiNote Actualiza la información de un Estudiante (su usuario incluido)
	 * @implNote Usa UserDAO para actualizar la información del usuario
	 * @category ABM
	 */
	public void modificacion(Estudiante e, Estudiante nuevx) {
		String sql = "UPDATE `biblioteca`.`estudiantes` "
				+ "SET `DNI` = ?, `nombre_apellido` = ?, `curso` = ?, `telefono` = ?, `fecha_alta` = ?, `user` = ? "
				+ "WHERE `DNI` = ?;";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			UserDAO udao = new UserDAO();

			udao.modificacion(e.usuario(), nuevx.usuario());

			ps.setInt(1, nuevx.getDNI());
			ps.setString(2, nuevx.getNombre());
			ps.setInt(3, nuevx.getCurso());
			ps.setString(4, nuevx.getTelefono());
			ps.setDate(5, Date.valueOf(LocalDate.now()));
			ps.setInt(6, udao.idFrom(nuevx.getUsername()));
			ps.setInt(7, e.getDNI());

			ps.executeUpdate();

			udao.cerrarConexion();
		} catch (SQLException exc) {
			// TODO Bloque catch generado automáticamente
			exc.printStackTrace();
		}
	}

	/**
	 * @param e Unx estudiante
	 * @apiNote Le da de alta a unx Estudiante y su usuario en la BDDR
	 * @implNote Usa UserDAO para dar de alta a su usuario
	 * @category ABM
	 */
	public void alta(Estudiante e) {
		String sql = "INSERT INTO biblioteca.estudiantes VALUES (?,?,?,?,?,?)";
		String password = Integer.toString(e.getDNI());
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			UserDAO udao = new UserDAO();

			udao.alta(e.usuario(), password);

			ps.setInt(1, e.getDNI());
			ps.setString(2, e.getNombre());
			ps.setInt(3, e.getCurso());
			ps.setString(4, e.getTelefono());
			ps.setDate(5, Date.valueOf(LocalDate.now()));
			ps.setInt(6, udao.idFrom(e.getUsername()));

			ps.executeUpdate();

			udao.cerrarConexion();
		} catch (SQLException exc) {
			// TODO Bloque catch generado automáticamente
			exc.printStackTrace();
		}
	}
	
	public Estudiante estudianteFromUser(User user) {
		UserDAO udao = new UserDAO();
		int id = udao.idFrom(user.getUsername());
		ResultSet rs = ResultSetFiltro("user", id);
		return ResultSetToArrayList(rs).get(0);
	}

}
