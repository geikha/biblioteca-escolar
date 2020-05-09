package vista;

import javax.swing.*;
import java.awt.Font;

import biblioteca.Entrega;
import biblioteca.User;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import vista.buscar.*;
import vista.abm.*;

public class HomeBibliotecario extends JPanel {
	
	User user = AppMain.user;
	private int w = 650;
	private int h = 500;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);
	
	public HomeBibliotecario(JFrame marco) {
		// SETTINGS
		marco.setTitle("Login");
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		setLayout(null);
		
		JLabel lblbienvenidx = new JLabel("\u00A1Bienvenidx, " + user.getUsername() + "!");
		lblbienvenidx.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblbienvenidx.setHorizontalAlignment(SwingConstants.CENTER);
		lblbienvenidx.setBounds(151, 90, 360, 52);
		add(lblbienvenidx);
		
		JLabel lblquDeseaHacer = new JLabel("\u00BFQu\u00E9 desea hacer?");
		lblquDeseaHacer.setHorizontalAlignment(SwingConstants.CENTER);
		lblquDeseaHacer.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblquDeseaHacer.setBounds(151, 144, 360, 52);
		add(lblquDeseaHacer);
		
		JButton btnNewButton = new JButton("<html><center>A\u00F1dir/Editar<br>Libro</center></html>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BLibro(marco));
				marco.validate();
			}
		});
		btnNewButton.setBounds(97, 217, 106, 52);
		add(btnNewButton);
		
		JButton btnRegistrarEstudiante = new JButton("<html><center>Registrar<br>Estudiante");
		btnRegistrarEstudiante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMEstudiante(marco));
				marco.validate();
			}
		});
		btnRegistrarEstudiante.setBounds(213, 217, 106, 52);
		add(btnRegistrarEstudiante);
		
		JButton btnRetirarReserva = new JButton("<html><center>Retirar<br>reserva");
		btnRetirarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BReserva(marco));
				marco.validate();
			}
		});
		btnRetirarReserva.setBounds(329, 217, 106, 52);
		add(btnRetirarReserva);
		
		JButton btnRetirarLibroSin = new JButton("<html><center>Retirar libro<br>sin reserva");
		btnRetirarLibroSin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMRetirarLibroEnBiblioteca(marco, new Entrega()));
				marco.validate();
			}
		});
		btnRetirarLibroSin.setBounds(445, 217, 106, 52);
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
