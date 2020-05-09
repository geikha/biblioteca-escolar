package vista;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import vista.buscar.*;
import java.awt.Color;
import java.awt.Font;

public class HomeAdmin extends JPanel {
	
	private int w = 650;
	private int h = 500;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);

	public HomeAdmin(JFrame marco) {
		setBackground(Color.BLACK);
		marco.setBackground(Color.BLACK);
		// SETTINGS
		marco.setTitle("Login");
		marco.setBounds(x, y, w+15, h+30);
		setBounds(0, 0, w, h);
		setLayout(null);
		
		JLabel lblSuperusuario = new JLabel("SUPERUSUARIO ");
		lblSuperusuario.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSuperusuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuperusuario.setForeground(Color.GREEN);
		lblSuperusuario.setBounds(10, 135, 630, 15);
		add(lblSuperusuario);
		
		JButton btnUsuarios = new JButton("USUARIOS");
		btnUsuarios.setBackground(Color.GREEN);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BUser(marco));
				marco.validate();
			}
		});
		btnUsuarios.setBounds(328, 248, 114, 57);
		add(btnUsuarios);
		
		JButton btnIngresarComoBibliotecarix = new JButton("<html><center>Ingresar<br>como<br>Bibliotecarix");
		btnIngresarComoBibliotecarix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				marco.setContentPane(new MarcoBibliotecario());
				marco.setContentPane(new HomeBibliotecario(marco));
				marco.validate();
			}
		});
		btnIngresarComoBibliotecarix.setBackground(Color.GREEN);
		btnIngresarComoBibliotecarix.setBounds(204, 249, 114, 56);
		add(btnIngresarComoBibliotecarix);
		
		JLabel lblUsuarioActual = new JLabel("Usuario actual: " + AppMain.user.getUsername());
		lblUsuarioActual.setForeground(Color.GREEN);
		lblUsuarioActual.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsuarioActual.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblUsuarioActual.setBounds(350, 475, 290, 14);
		add(lblUsuarioActual);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppMain.user = null;
				marco.setJMenuBar(null);
				marco.setContentPane(new Login(marco));
				marco.validate();
			}
		});
		btnSalir.setBackground(Color.GREEN);
		btnSalir.setBounds(10, 466, 89, 23);
		add(btnSalir);
	}
}
