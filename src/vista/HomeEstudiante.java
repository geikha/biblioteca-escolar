package vista;

import javax.swing.*;
import java.awt.Font;
import biblioteca.Estudiante;
import biblioteca.Reserva;
import biblioteca.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import vista.buscar.*;
import vista.abm.*;

public class HomeEstudiante extends JPanel {
	
	User user = AppMain.user;
	
	private int w = 650;
	private int h = 500;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);
	
	public HomeEstudiante(JFrame marco) {
		// SETTINGS
		marco.setTitle("Login");
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		setLayout(null);
		
		JLabel lblbienvenidx = new JLabel("\u00A1Bienvenidx, " + ((Estudiante) user).getPrimerNombre() + "!");
		lblbienvenidx.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblbienvenidx.setHorizontalAlignment(SwingConstants.CENTER);
		lblbienvenidx.setBounds(151, 90, 360, 52);
		add(lblbienvenidx);
		
		JLabel lblquDeseaHacer = new JLabel("\u00BFQu\u00E9 desea hacer?");
		lblquDeseaHacer.setHorizontalAlignment(SwingConstants.CENTER);
		lblquDeseaHacer.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblquDeseaHacer.setBounds(151, 144, 360, 52);
		add(lblquDeseaHacer);
		
		JButton btnRegistrarEstudiante = new JButton("<html><center>Reservar<br>un libro");
		btnRegistrarEstudiante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BLibro(marco, new Reserva()));
				marco.validate();
			}
		});
		btnRegistrarEstudiante.setBounds(161, 217, 106, 52);
		add(btnRegistrarEstudiante);
		
		JButton btnRetirarReserva = new JButton("<html><center>Ver mis<br>reservas");
		btnRetirarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				marco.setContentPane(new BReserva(marco));
				marco.validate();
			}
		});
		btnRetirarReserva.setBounds(277, 217, 106, 52);
		add(btnRetirarReserva);
		
		JButton btnRetirarLibroSin = new JButton("<html><center>Cambiar datos de usuario</center></html>");
		btnRetirarLibroSin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMCambiarContraseña(marco));
				marco.validate();
			}
		});
		btnRetirarLibroSin.setBounds(393, 217, 106, 52);
		add(btnRetirarLibroSin);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user = null;
				marco.setJMenuBar(null);
				marco.setContentPane(new Login(marco));
				marco.validate();
			}
		});
		btnSalir.setBounds(10, 466, 89, 23);
		add(btnSalir);
		
		JLabel lblUsuarioActual = new JLabel("Usuario actual: " + user.getUsername());
		lblUsuarioActual.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsuarioActual.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblUsuarioActual.setBounds(350, 475, 290, 14);
		add(lblUsuarioActual);
		
	}
}
