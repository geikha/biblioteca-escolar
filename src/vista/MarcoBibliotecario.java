package vista;

import javax.swing.*;

import biblioteca.Entrega;
import biblioteca.User;
import vista.abm.*;
import vista.buscar.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MarcoBibliotecario extends JPanel {

	JFrame marco = AppMain.marco;
	User user = AppMain.user;

	public MarcoBibliotecario() {
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 665, 550);

		JMenuBar menuBar = new JMenuBar();
		AppMain.marco.setJMenuBar(menuBar);

		JMenu mnNuevo = new JMenu("Nuevo");
		menuBar.add(mnNuevo);

		JMenuItem mntmLibro = new JMenuItem("Libro");
		mntmLibro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMLibro(marco));
				marco.validate();
			}
		});
		mnNuevo.add(mntmLibro);

		JMenuItem mntmEstudiante = new JMenuItem("Estudiante");
		mntmEstudiante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMEstudiante(marco));
				marco.validate();
			}
		});
		mnNuevo.add(mntmEstudiante);

		JMenuItem mntmReserva = new JMenuItem("Retirar libro sin reserva");
		mntmReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMRetirarLibroEnBiblioteca(marco, new Entrega()));
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

		JMenuItem mntmEstudiantes = new JMenuItem("Estudiantes");
		mntmEstudiantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BEstudiante(marco));
				marco.validate();
			}
		});
		mnBuscar.add(mntmEstudiantes);

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
				marco.setContentPane(new HomeBibliotecario(marco));
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

		if (user.getTipoUsuario() == 3) {
			JMenuItem mntmVolverAVista = new JMenuItem("Volver a vista de SUPERUSUARIO");
			mntmVolverAVista.setForeground(Color.RED);
			mntmVolverAVista.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					marco.setJMenuBar(null);
					marco.setContentPane(new HomeAdmin(marco));
					marco.validate();
				}
			});
			menuBar.add(mntmVolverAVista);
			setVisible(true);
		}
	}

}
