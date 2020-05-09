package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import biblioteca.Reserva;
import biblioteca.User;
import vista.abm.ABMCambiarContraseña;
import vista.abm.ABMEstudiante;
import vista.abm.ABMLibro;
import vista.abm.ABMReserva;
import vista.abm.ABMRetirarLibroEnBiblioteca;
import vista.buscar.BEntrega;
import vista.buscar.BEstudiante;
import vista.buscar.BLibro;
import vista.buscar.BLibrosPopulares;
import vista.buscar.BReserva;

public class MarcoEstudiante extends JPanel {

	JFrame marco = AppMain.marco;
	User user = AppMain.user;

	public MarcoEstudiante() {
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 665, 550);
		
		JMenuBar menuBar = new JMenuBar();
		marco.setJMenuBar(menuBar);
		
		JMenu mnNuevo = new JMenu("Nuevo");
		menuBar.add(mnNuevo);
		
		JMenuItem mntmReserva = new JMenuItem("Reserva");
		mntmReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMReserva(marco, new Reserva()));
				marco.validate();
			}
		});
		mnNuevo.add(mntmReserva);

		JMenu mnBuscar = new JMenu("Buscar");
		menuBar.add(mnBuscar);
		
		JMenuItem mntmLibros = new JMenuItem("Libros");
		mntmLibros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BLibro(marco));
				marco.validate();
			}
		});
		mnBuscar.add(mntmLibros);
		
		JMenuItem mntmReservas = new JMenuItem("Reservas");
		mntmReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BReserva(marco));
				marco.validate();
			}
		});
		mnBuscar.add(mntmReservas);
		
		JMenuItem mntmEntregas = new JMenuItem("Entregas");
		mntmEntregas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BEntrega(marco));
				marco.validate();
			}
		});
		mnBuscar.add(mntmEntregas);
		
		JMenu mnVer = new JMenu("Ver");
		menuBar.add(mnVer);
		
		JMenuItem mntmLibrosMsPopulares = new JMenuItem("Libros más populares");
		mntmLibrosMsPopulares.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BLibrosPopulares(marco));
				marco.validate();
			}
		});
		mnVer.add(mntmLibrosMsPopulares);
		JMenuItem mntmEntregasFaltantes = new JMenuItem("Entregas No Devueltas Expiradas");
		mntmEntregasFaltantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BEntrega(marco, true));
				marco.validate();
			}
		});
		mnVer.add(mntmEntregasFaltantes);
		
		
		JMenuItem mntmMen = new JMenuItem("Menú");
		mntmMen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new HomeEstudiante(marco));
				marco.validate();
			}
		});
		mnVer.add(mntmMen);
		
		JMenu mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		JMenuItem mntmCambiarContrasea = new JMenuItem("Cambiar contrase\u00F1a");
		mntmCambiarContrasea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMCambiarContraseña(marco));
				marco.validate();
			}
		});
		mnOpciones.add(mntmCambiarContrasea);
		setVisible(true);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user = null;
				marco.setJMenuBar(null);
				marco.setContentPane(new Login(marco));
				marco.validate();
			}
		});
		menuBar.add(mntmSalir);
		
		marco.validate();
	}
}
