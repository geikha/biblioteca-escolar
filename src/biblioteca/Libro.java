package biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;

public class Libro {
	
	private String ISBN, titulo, editorial;
	private int stock, stockDisponible;
	private String añoOriginal;
	private LocalDate fechaAlta;
	private LocalDate ultimoRestock;
	private int popularidad;
	
	private ArrayList<String> autorxs = new ArrayList<String>();
	
	public Libro(String ISBN, String titulo, String editorial, int stock, int stockDisponible, String añoOriginal,
			LocalDate fechaAlta, LocalDate ultimoRestock, ArrayList<String> autorxs, int popularidad) {
		super();
		this.ISBN = ISBN;
		this.titulo = titulo;
		this.editorial = editorial;
		this.stock = stock;
		this.stockDisponible = stockDisponible;
		this.añoOriginal = añoOriginal;
		this.fechaAlta = fechaAlta;
		this.ultimoRestock = ultimoRestock;
		this.autorxs = autorxs;
		this.popularidad = popularidad;
	}
	
	public int getPopularidad() {
		return popularidad;
	}

	public void setPopularidad(int popularidad) {
		this.popularidad = popularidad;
	}

	public Libro() {
		this.fechaAlta = LocalDate.now();
		this.ultimoRestock = LocalDate.now();
		this.popularidad = 0;
	}

	// STRING
	
	/**
	 * @return Lxs autorxs en formato String
	 * @apiNote Ejemplo: "Pepe Fulano, Fulanito Tal"
	 */
	public String autorxsString() {
		String autorxsString = "";
		for(int i = 0; i<autorxs.size(); i++)
			autorxsString += autorxs.get(i) + (i != autorxs.size()-1 ? ", " : "");
		return autorxsString;
	}
	
	// GET SET

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStockDisponible() {
		return stockDisponible;
	}

	public void setStockDisponible(int stockDisponible) {
		this.stockDisponible = stockDisponible;
	}

	public String getAñoOriginal() {
		return añoOriginal;
	}

	public void setAñoOriginal(String añoOriginal) {
		this.añoOriginal = añoOriginal;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDate getUltimoRestock() {
		return ultimoRestock;
	}

	public void setUltimoRestock(LocalDate ultimoRestock) {
		this.ultimoRestock = ultimoRestock;
	}

	public ArrayList<String> getAutorxs() {
		return autorxs;
	}

	public void setAutorxs(ArrayList<String> autorxs) {
		this.autorxs = autorxs;
	}

}
