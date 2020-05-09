package biblioteca;

import java.time.LocalDate;

public class Estudiante extends User{

	private int DNI;
	private String nombre = "";
	private int curso;
	private String telefono;
	private LocalDate fechaAlta;
	
	public Estudiante(User user, int DNI, String nombre, int curso, String telefono, LocalDate fechaAlta) {
		super(user);
		this.DNI = DNI;
		this.nombre = nombre;
		this.curso = curso;
		this.telefono = telefono;
		this.fechaAlta = fechaAlta;
	}
	
	// USER
	
	public Estudiante() {
		fechaAlta = LocalDate.now();
		setUsername("");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return El usuario que corresponde a lx estudiante
	 */
	public User usuario() {
		return new User(getUsername(),getTipoUsuario());
	}

	// String
	
	/**
	 * @return Un formato String del curso
	 * @apiNote Ejemplo: 51 -> "5° 1°"
	 */
	public String cursoString() {
		char[] cursoChar = String.valueOf(this.curso).toCharArray();
		String cursoString = "";
		for(char c : cursoChar)
			cursoString += c + "° ";
		return cursoString.trim();
	}
	
	public String añoCurso() {
		return Integer.toString(curso).substring(0, 1);
	}
	
	public String divisionCurso() {
		return Integer.toString(curso).substring(1, 2);
	}
	
	// GET SET
	
	public int getDNI() {
		return DNI;
	}
	public void setDNI(int DNI) {
		this.DNI = DNI;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCurso() {
		return curso;
	}
	public void setCurso(int curso) {
		this.curso = curso;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	public String getPrimerNombre() {
		char[] ch = nombre.toCharArray();
		for(int i=0;i<ch.length;i++)
			if(ch[i]==' ')
				return nombre.substring(0, i);
		return nombre;
	}
}
