package biblioteca;

import java.time.LocalDate;
import dao.ReservaDAO;

public class Reserva {

	private int id;
	private Estudiante estudiante;
	private Libro libro;
	private LocalDate alta, expira;
	private int cantidadDias;
	private boolean efectiva;

	public Reserva(int id, Estudiante estudiante, Libro libro, LocalDate desde, LocalDate expira, int cantidadDias,
			boolean efectiva) {
		super();
		this.id = id;
		this.estudiante = estudiante;
		this.libro = libro;
		this.alta = desde;
		this.expira = expira;
		this.cantidadDias = cantidadDias;
		this.efectiva = efectiva;
	}

	public Reserva() {
		alta = LocalDate.now();
		expira = alta.plusDays(control.Constantes.cantidadDeDiasParaRetirarUnaReserva);
		efectiva = true;
		cantidadDias = 0;
	}

	// VOID

	/**
	 * @apiNote Llama a ReservaDAO y cancela la reserva actual
	 * @see {@link dao.ReservaDAO#cancelarReserva}
	 */
	public void cancelarReserva() {
		ReservaDAO rdao = new ReservaDAO();
		rdao.cancelarReserva(this);
		rdao.cerrarConexion();
	}

	/**
	 * @apiNote Llama a ReservaDAO y retira la reserva actual
	 * @see {@link dao.ReservaDAO#retirarReserva}
	 */
	public void retirarReserva() {
		ReservaDAO rdao = new ReservaDAO();
		rdao.retirarReserva(this);
		rdao.cerrarConexion();
	}

	public boolean estaExpirada() {
		int i = expira.compareTo(LocalDate.now());
		return i <= 0;
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

	public LocalDate getAlta() {
		return alta;
	}

	public void setAlta(LocalDate alta) {
		this.alta = alta;
	}

	public LocalDate getExpira() {
		return expira;
	}

	public void setExpira(LocalDate expira) {
		this.expira = expira;
	}
	
	public void setExpira(int cantidadDeDias) {
		this.expira = expira.plusDays(cantidadDeDias);
	}

	public int getCantidadDias() {
		return cantidadDias;
	}

	public void setCantidadDias(int cantidadDias) {
		this.cantidadDias = cantidadDias;
	}

	public boolean isEfectiva() {
		return efectiva;
	}

	public void setEfectiva(boolean efectiva) {
		this.efectiva = efectiva;
	}

}
