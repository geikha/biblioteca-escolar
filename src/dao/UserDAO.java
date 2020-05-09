package dao;

import java.sql.*;
import java.util.ArrayList;

import biblioteca.User;

public class UserDAO extends DAO { // id_user,username,password,tipo_usuario

	public UserDAO() {
		setTabla("users");
	}

	public ArrayList<User> ResultSetToArrayList(ResultSet rs) {
		ArrayList<User> users = new ArrayList<User>();
		EstudianteDAO edao = new EstudianteDAO();
		try {
			while (rs.next()) {
				String username = rs.getString(2);
				int tipoUsuario = rs.getInt(4);
				User user = new User(username,tipoUsuario);
				if (tipoUsuario == 1) {
					users.add(edao.estudianteFromUser(user));
				}
				else {
					users.add(user);
				}
			}
		} catch (SQLException e) {

		}
		return users;
	}

	public void baja(User u) {
		String condicion = "id_user = '" + idFrom(u.getUsername()) + "'";
		String sql = sqlBaja(condicion);
		ejecutarUpdate(sql);
	}

	public void modificacion(User u, User nuevx) {
		String sql = "UPDATE `biblioteca`.`users` SET `username` = ?, `tipo_usuario` = ? "
				+ "WHERE `id_user` = ?;";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setString(1, nuevx.getUsername());
			ps.setInt(2, nuevx.getTipoUsuario());
			ps.setInt(3, idFrom(u.getUsername()));

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	public void cambiarContraseña(User u, String password) {
		String sql = "UPDATE `biblioteca`.`users` SET `pass` = SHA2(?,256) "
				+ "WHERE `id_user` = ?;";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setString(1, password);
			ps.setInt(2, idFrom(u.getUsername()));

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	public void alta(User u, String password) {
		String sql = "INSERT INTO biblioteca.users VALUES (null, ?, SHA2(?,256), ?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setString(1, u.getUsername());
			ps.setString(2, password);
			ps.setInt(3, u.getTipoUsuario());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	public User logIn(String username, String password) {

		String sql = "SELECT username, tipo_usuario FROM users WHERE username = ? AND pass = SHA2(?,256)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				return new User(rs.getString("username"), rs.getInt("tipo_usuario"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int idFrom(String username) {
		ResultSet rs = ResultSetFiltro("username", username);
		int id = 0;

		try {
			rs.next();
			id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		return id;
	}

	public User userFrom(int id) {
		ResultSet rs = ResultSetFiltro("id_user", id);
		User user = new User();

		try {
			rs.next();
			String username = rs.getString(2);
			int tipoUsuario = rs.getInt(4);
			user = new User(username, tipoUsuario);
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		return user;
	}
	
	public User userFrom(String username) {
		String sql = "SELECT * FROM users WHERE username = " + username;
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs).get(0);
	}

	public ArrayList<User> todxs() {
		ResultSet rs = ResultSetTodos();
		return ResultSetToArrayList(rs);
	}

	public ArrayList<User> usersPorUsername(String username){
		ResultSet rs = ResultSetFiltro("username", username);
		return ResultSetToArrayList(rs);
	}
	
	public boolean usernameDisponible(String username) {
		ResultSet rs = ResultSetFiltro("username", username);
		try {
			if(rs.next())
				return false;
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		return true;
	}
	
}
