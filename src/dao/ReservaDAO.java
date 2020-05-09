package dao;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import biblioteca.*;
import vista.AppMain;

public class ReservaDAO extends DAO {
	
	User user = AppMain.user;

	public ReservaDAO() {
		setTabla("reservas");
	}
	
	// COLUMNAS EN SQL: | id_reserva | estudiante | libro | alta | expira | cant_dias | efectiva |
	
	/**
	 * @param rs ResultSet
	 * @return Formato ArrayList de Reservas del RS recibido
	 * @implNote Usa LibroDAO y EstudianteDAO para recibir los objetos necesarios a partir de las FK de cada Reserva.
	 */
	private ArrayList<Reserva> ResultSetToArrayList(ResultSet rs) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		LibroDAO ldao = new LibroDAO();
		EstudianteDAO edao = new EstudianteDAO();

		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				Estudiante e = edao.estudianteFromReserva(id);
				Libro l = ldao.libroFromReserva(id);
				LocalDate desde = rs.getDate(4).toLocalDate();
				LocalDate hasta = rs.getDate(5).toLocalDate();
				int cantidadDias = rs.getInt(6);
				boolean efectiva = rs.getBoolean(7);

				if(user.getTipoUsuario() == 1) {
					if(e.equals((Estudiante) user)) {
						reservas.add(new Reserva(id, e, l, desde, hasta, cantidadDias, efectiva));
					}
				}else {
					reservas.add(new Reserva(id, e, l, desde, hasta, cantidadDias, efectiva));
				}
			}
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		ldao.cerrarConexion();
		edao.cerrarConexion();
		return reservas;
	}

	// QUERIES
	
	/**
	 * @return Todas las reservas
	 * @category Queries
	 */
	public ArrayList<Reserva> todas() {
		ResultSet rs = ResultSetTodos();
		return ResultSetToArrayList(rs);
	}

	/**
	 * @return Reservas canceladas (`reservas`.`efectiva` = FALSE)
	 * @category Queries
	 */
	public ArrayList<Reserva> reservasCanceladas() {
		String sql = "SELECT * FROM reservas WHERE efectiva = FALSE";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}

	/**
	 * @return Reservas expiradas (Cuya expiración es previa al día de la fecha)
	 * @category Queries
	 */
	public ArrayList<Reserva> reservasExpiradas() {
		String sql = "SELECT * FROM reservas WHERE expira < current_date()";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}

	// ABM
	
	/**
	 * @param r Una Reserva
	 * @apiNote Le da de alta a una Reserva en la BDDR
	 * @category ABM
	 */
	public void alta(Reserva r) {
		String sql = "INSERT INTO biblioteca.reservas VALUES (null,?,?,?,?,?,?)"; 
								//ID = NULL para dar lugar al AUTO_INCREMENT
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setInt(1, r.getEstudiante().getDNI());
			ps.setString(2, r.getLibro().getISBN());
			ps.setDate(3, Date.valueOf(r.getAlta()));
			ps.setDate(4, Date.valueOf(r.getExpira()));
			ps.setInt(5, r.getCantidadDias());
			ps.setBoolean(6, true);	//TODA NUEVA RESERVA ES EFECTIVA

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	/**
	 * @param r Una Reserva
	 * @apiNote Le da de baja a una Reserva en la BDDR
	 * @category ABM
	 */
	private void baja(Reserva r) {
		String condicion = "id_reserva = '" + r.getId() + "'";
		String sql = sqlBaja(condicion);
		ejecutarUpdate(sql);
	}

	
	/**
	 * @param r Reserva original
	 * @param nueva Reserva actualizada
	 * @apiNote Actualiza la información de una reserva
	 * @category ABM
	 */
	public void modificacion(Reserva r, Reserva nueva) {
		String sql = "UPDATE `biblioteca`.`reservas` "
				+ "SET `id_reserva` = ?, `estudiante` = ? `libro` = ?, `desde` = ?, `expira` = ?, `cant_dias` = ?, `efectiva` = ? "
				+ "WHERE `id_reserva` = ?;";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setInt(1, nueva.getId());
			ps.setInt(2, nueva.getEstudiante().getDNI());
			ps.setString(3, nueva.getLibro().getISBN());
			ps.setDate(4, Date.valueOf(nueva.getAlta()));
			ps.setDate(5, Date.valueOf(nueva.getExpira()));
			ps.setInt(6, nueva.getCantidadDias());
			ps.setBoolean(7, r.isEfectiva());
			ps.setInt(8, r.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	// VOIDS
	
	/**
	 * @param Reserva
	 * @apiNote Toma una reserva y la cancela (efectiva = false).
	 * @implNote {@link biblioteca.Reserva#cancelarReserva() Referenciado en clase Reserva}
	 * @category Actions
	 */
	public void cancelarReserva(Reserva r) { //TOMA UNA RESERVA Y LA CANCELA (efectiva = false). REFERENCIADO EN Reserva : class
		String sql = "UPDATE `biblioteca`.`reservas` SET `efectiva` = FALSE WHERE `id_reserva` = ?;";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, r.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	/**
	 * @param Reserva
	 * @apiNote Toma una reserva y da alta a una entrega basada en ella. Elimina la reserva.
	 * @implNote {@link biblioteca.Reserva#retirarReserva() Referenciado en clase Reserva}
	 * @category Actions
	 */
	public void retirarReserva(Reserva r) {
		EntregaDAO endao = new EntregaDAO();
		endao.alta(new Entrega(r));
		baja(r);
	}
	
	public ArrayList<Reserva> reservasPorNombreDelAlumno(String nombre){
		nombre = control.StringFormat.stringForSQL(nombre);
		String sql = "SELECT r.* FROM reservas AS r JOIN estudiantes AS e ON r.estudiante = e.DNI WHERE e.nombre_apellido LIKE '%" + nombre + "%'";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}
	
	public ArrayList<Reserva> reservasPorTituloDelLibro(String titulo){
		String sql = "SELECT r.* FROM reservas AS r JOIN libros AS l ON r.libro = l.ISBN WHERE l.titulo LIKE '%" + titulo + "%'";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}
}
