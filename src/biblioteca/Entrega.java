package biblioteca;

import java.time.LocalDate;
import dao.EntregaDAO;

public class Entrega {

	private int id;
	private Estudiante estudiante;
	private Libro libro;
	private LocalDate retirado;
	private LocalDate expira;
	private boolean devuelta;
	
	//CONSTRUCTORES
	
	/**
	 * @param r Una reserva
	 * @apiNote Crea una entrega a partir de una Reserva
	 */
	public Entrega(Reserva r) {
		super();
		this.id = 0;
		this.estudiante = r.getEstudiante();
		this.libro = r.getLibro();
		this.retirado = LocalDate.now();
						//Si se crea una entrega desde una reserva es porque es una nueva -> retirado : HOY
		this.expira = LocalDate.now().plusDays(r.getCantidadDias());
						//Dia de retiro + cantidad de dias = expiración de la entrega
		this.devuelta = false;
	}
	
	public Entrega(int id, Estudiante estudiante, Libro libro, LocalDate retira, LocalDate expiracion,
			boolean devuelto) {
		super();
		this.id = id;
		this.estudiante = estudiante;
		this.libro = libro;
		this.retirado = retira;
		this.expira = expiracion;
		this.devuelta = devuelto;
	}
	
	// METODOS
	
	public Entrega() {
		retirado = LocalDate.now();
		expira = retirado;
		devuelta = false;
	}

	/**
	 * @apiNote Llama a EntregaDAO y devuelve la entrega
	 */
	public void devolverEntrega() {
		EntregaDAO endao = new EntregaDAO();
		endao.devolverEntrega(this);
		endao.cerrarConexion();
	}
	
	public boolean estaExpirada() {
		int i = expira.compareTo(LocalDate.now());
		return i<=0;
	}
	
	// GET SET
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Estudiante getEstudiante() {
		return estudiante;
	}
	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	public LocalDate getRetirado() {
		return retirado;
	}
	public void setRetirado(LocalDate retirado) {
		this.retirado = retirado;
	}
	public LocalDate getExpira() {
		return expira;
	}
	public void setExpira(LocalDate expira) {
		this.expira = expira;
	}
	public void setExpira(int cantidadDeDias) {
		this.expira = retirado.plusDays(cantidadDeDias);
	}
	public boolean isDevuelta() {
		return devuelta;
	}
	public void setDevuelta(boolean devuelta) {
		this.devuelta = devuelta;
	}

}
