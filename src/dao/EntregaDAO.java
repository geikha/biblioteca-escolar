package dao;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import biblioteca.*;
import vista.AppMain;

//DEVOLVER ENTREGA?

public class EntregaDAO extends DAO {

	private User user = AppMain.user;

	public EntregaDAO() {
		setTabla("entregas");
	}

	// COLUMNAS DE SQL: |id_entrega | estudiante | libro | retirado | expira |
	// devuelta |

	/**
	 * @param rs ResultSet
	 * @return Formato ArrayList de Entregas del RS recibido
	 * @implNote Usa LibroDAO y EstudianteDAO para recibir los objetos necesarios a
	 *           partir de las FK de cada Reserva.
	 */
	private ArrayList<Entrega> ResultSetToArrayList(ResultSet rs) {
		ArrayList<Entrega> entregas = new ArrayList<Entrega>();
		LibroDAO ldao = new LibroDAO();
		EstudianteDAO edao = new EstudianteDAO();

		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				Estudiante e = edao.estudianteFromEntrega(id);
				Libro l = ldao.libroFromISBN(rs.getString(3));
				LocalDate retirado = rs.getDate(4).toLocalDate();
				LocalDate expira = rs.getDate(5).toLocalDate();
				boolean devuelta = rs.getBoolean(6);

				if (user.getTipoUsuario() == 1) {
					if (e.equals((Estudiante) user)) {
						entregas.add(new Entrega(id, e, l, retirado, expira, devuelta));
					}
				} else {
					entregas.add(new Entrega(id, e, l, retirado, expira, devuelta));
				}
			}
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		ldao.cerrarConexion();
		edao.cerrarConexion();
		return entregas;
	}

	// QUERIES

	/**
	 * @return Lista de entregas no devueltas
	 * @implNote SELECT * FROM entregas WHERE devuelta = FALSE
	 * @category Queries
	 */
	public ArrayList<Entrega> entregasNoDevueltas() {
		String sql = "SELECT * FROM entregas WHERE devuelta = FALSE";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}

	/**
	 * @return Lista de entregas expiradas no devueltas
	 * @implNote SELECT * FROM entregas WHERE expira < current_date() AND devuelta =
	 *           FALSE
	 * @category Queries
	 */
	public ArrayList<Entrega> entregasExpiradasNoDevueltas() {
		String sql = "SELECT * FROM entregas WHERE expira < current_date() AND devuelta = FALSE";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}

	/**
	 * @return Todas las entregas
	 * @category Queries
	 */
	public ArrayList<Entrega> todas() {
		ResultSet rs = ResultSetTodos();
		return ResultSetToArrayList(rs);
	}

	// ABM

	/**
	 * @param en Una entrega
	 * @apiNote Le da de alta a una entrega
	 * @category ABM
	 */
	public void alta(Entrega en) {
		String sql = "INSERT INTO biblioteca.entregas VALUES (null,?,?,?,?,?);";
		// ID = NULL para dar lugar al AUTO_INCREMENT
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setInt(1, en.getEstudiante().getDNI());
			ps.setString(2, en.getLibro().getISBN());
			ps.setDate(3, Date.valueOf(en.getRetirado()));
			ps.setDate(4, Date.valueOf(en.getExpira()));
			ps.setBoolean(5, false); // TODA ENTREGA GENERADA NO FUE DEVUELTA

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	/**
	 * @param en    Entrega original
	 * @param nueva Entrega actualizada
	 * @apiNote Actualiza la información de una entrega
	 * @category ABM
	 */
	public void modificacion(Entrega en, Entrega nueva) {
		String sql = "UPDATE `biblioteca`.`entregas` "
				+ "SET `id_entrega` = ?, `estudiante` = ?, `libro` = ?, `retirado` = ?, `expira` = ?, `devuelta` = ? "
				+ "WHERE `id_entrega` = ?;";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setInt(1, nueva.getId());
			ps.setInt(2, nueva.getEstudiante().getDNI());
			ps.setString(3, nueva.getLibro().getISBN());
			ps.setDate(4, Date.valueOf(nueva.getRetirado()));
			ps.setDate(5, Date.valueOf(nueva.getExpira()));
			ps.setBoolean(6, false);
			ps.setInt(7, en.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	// VOIDS

	/**
	 * @param Entrega
	 * @apiNote Toma una entrega y la devuelve
	 * @implNote Cambia devuelta = TRUE
	 * @implNote {@link biblioteca.Reserva#retirarReserva() Referenciado en clase
	 *           Entrega}
	 * @category Actions
	 */
	public void devolverEntrega(Entrega en) {
		if (!en.isDevuelta()) {
			String sql = "UPDATE `biblioteca`.`entregas` SET `devuelta` = TRUE WHERE `id_entrega` = ?;";
			try {
				PreparedStatement ps = conexion.prepareStatement(sql);
				ps.setInt(1, en.getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			en.setDevuelta(true);
		}
	}

	public ArrayList<Entrega> entregasPorNombreDelAlumno(String nombre) {
		nombre = control.StringFormat.stringForSQL(nombre);
		String sql = "SELECT en.* FROM entregas AS en JOIN estudiantes AS e ON en.estudiante = e.DNI WHERE e.nombre_apellido LIKE '%"
				+ nombre + "%'";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}

	public ArrayList<Entrega> entregasPorTituloDelLibro(String titulo) {
		String sql = "SELECT en.* FROM entregas AS en JOIN libros AS l ON en.libro = l.ISBN WHERE l.titulo LIKE '%"
				+ titulo + "%'";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}
	
}
