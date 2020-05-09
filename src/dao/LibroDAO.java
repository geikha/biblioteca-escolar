package dao;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import biblioteca.Libro;

public class LibroDAO extends DAO { 

	public LibroDAO() {
		setTabla("libros");
	}
	
	// COLUMNAS EN SQL : |ISBN|titulo|editorial|stock|stock_disponible|año_original|fecha_alta|ultimo_restock|popularidad
	
	/**
	 * @param rs ResultSet
	 * @return Formato ArrayList de Reservas del RS recibido
	 * @implNote Usa AutorxDAO y EditorialDAO para obtener los Strings necesarios.
	 */
	private ArrayList<Libro> ResultSetToArrayList(ResultSet rs) {
		ArrayList<Libro> libros = new ArrayList<Libro>();
		AutorxDAO audao = new AutorxDAO();
		EditorialDAO editdao = new EditorialDAO();

		try {
			while (rs.next()) {
				String ISBN = rs.getString(1);
				String titulo = rs.getString(2);
				String editorial = editdao.editorialFrom(rs.getInt(3));
				int stock = rs.getInt(4);
				int stockDisponible = rs.getInt(5);
				String añoOriginal = rs.getString(6);
				LocalDate fechaAlta = rs.getDate(7).toLocalDate();
				LocalDate ultimoRestock = rs.getDate(8).toLocalDate();
				ArrayList<String> autorxs = audao.autorxsFromLibro(ISBN);
				int popularidad = rs.getInt(9);

				libros.add(new Libro(ISBN, titulo, editorial, stock, stockDisponible, añoOriginal, fechaAlta,
						ultimoRestock, autorxs, popularidad));
			}
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		audao.cerrarConexion();
		editdao.cerrarConexion();
		return libros;
	}

	// QUERIES
	
	/**
	 * @param autorx El nombre de un autorx (usualmente ingresado por el usuario)
	 * @return Una lista de libros con esa autoría.
	 * @category Queries
	 */
	public ArrayList<Libro> librosPorAutor(String autorx){
		ArrayList<Libro> todos = todos();
		ArrayList<Libro> librosPorAutor = new ArrayList<Libro>();
		
		for(Libro l : todos) {
			boolean contieneAutorx = false;
			for(String au : l.getAutorxs())
				contieneAutorx = au.toLowerCase().contains(autorx.toLowerCase());
			if(contieneAutorx)
				librosPorAutor.add(l);
		}
		
		return librosPorAutor;
	}
	
	/**
	 * @param titulo Título del libro (usualmente ingresado por el usuario).
	 * @return Una lista de libros con ese título.
	 * @category Queries
	 */
	public ArrayList<Libro> librosPorTitulo(String titulo){
		ResultSet rs = ResultSetFiltro("titulo", titulo);
		return ResultSetToArrayList(rs);
	}

	/**
	 * @return Todos los libros.
	 * @category Queries
	 */
	public ArrayList<Libro> todos() {
		ResultSet rs = ResultSetTodos();
		return ResultSetToArrayList(rs);
	}
		
	// FROM
	
	/**
	 * @param idReserva El ID de una Reserva
	 * @return El Libro que le corresponde a esa reserva
	 * @category Queries
	 */
	public Libro libroFromReserva(int idReserva) {
		String sql = "SELECT l.* FROM reservas AS r JOIN libros AS l ON r.libro = l.ISBN WHERE r.id_reserva = " + idReserva;
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs).get(0);
	}
	
	public Libro libroFromISBN(String ISBN) {
		String sql = "SELECT * FROM libros WHERE ISBN = '" +ISBN+ "'";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs).get(0);
	}
	
	// ABM
	
	/**
	 * @param l Un Libro
	 * @apiNote Le da de alta al libro en la BDDR
	 * @category ABM
	 */
	public void alta(Libro l) {
		String sql = "INSERT INTO biblioteca.libros VALUES (?,?,?,?,?,?,?,?,0)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			AutorxDAO adao = new AutorxDAO();
			EditorialDAO editdao = new EditorialDAO();

			ps.setString(1, l.getISBN());
			ps.setString(2, l.getTitulo());
			ps.setInt(3, editdao.idFrom(l.getEditorial()));
			ps.setInt(4, l.getStock());
			ps.setInt(5, l.getStockDisponible());
			ps.setString(6, l.getAñoOriginal());
			ps.setDate(7, Date.valueOf(l.getFechaAlta()));
			ps.setDate(8, Date.valueOf(l.getUltimoRestock()));

			ps.executeUpdate();
			
			for(String autor : l.getAutorxs())
				adao.altaLibroAutor(l, autor);

			adao.cerrarConexion();
			editdao.cerrarConexion();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	/**
	 * @param l Un Libro
	 * @apiNote Elimina el libro de la BDDR
	 * @category ABM
	 */
	public void baja(Libro l) {
		String condicion = "ISBN = '" + l.getISBN() + "'";
		String sql = sqlBaja(condicion);
		ejecutarUpdate(sql);
	}
	
	/**
	 * @param l Libro original
	 * @param nuevo Libro actualizado
	 * @category ABM
	 */
	public void modificacion(Libro l, Libro nuevo) {
		String sql = "UPDATE `biblioteca`.`libros` "
				+ "SET `ISBN` = ?, `titulo` = ?, `editorial` = ?, `stock` = ?, `stock_disponible` = ?, "
				+ "`ano_original` = ?, `fecha_alta` = ?, `ultimo_restock` = ? WHERE `ISBN` = ?;";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			AutorxDAO adao = new AutorxDAO();
			EditorialDAO editdao = new EditorialDAO();

			ps.setString(1, nuevo.getISBN());
			ps.setString(2, nuevo.getTitulo());
			ps.setInt(3, editdao.idFrom(nuevo.getEditorial()));
			ps.setInt(4, nuevo.getStock());
			ps.setInt(5, nuevo.getStockDisponible());
			ps.setString(6, nuevo.getAñoOriginal());
			ps.setDate(7, Date.valueOf(nuevo.getFechaAlta()));
			ps.setDate(8, Date.valueOf(nuevo.getUltimoRestock()));
			ps.setString(9, l.getISBN());

			ps.executeUpdate();
			
			for(String autor : nuevo.getAutorxs())
				adao.altaLibroAutor(l, autor);
			
			adao.bajaLibroAutor(l, nuevo);
			
			adao.cerrarConexion();
			editdao.cerrarConexion();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	public ArrayList<Libro> librosPopulares(){
		String sql = "SELECT * FROM `libros` ORDER BY popularidad DESC LIMIT 200";
		ResultSet rs = ejecutarQuery(sql);
		return ResultSetToArrayList(rs);
	}

}
