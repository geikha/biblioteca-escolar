package vista.abm;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import biblioteca.Entrega;
import biblioteca.User;
import dao.EntregaDAO;
import vista.AppMain;
import vista.HomeBibliotecario;
import vista.buscar.BEstudiante;
import vista.buscar.BLibro;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class ABMRetirarLibroEnBiblioteca extends JPanel {
	private JTextField txtCantidadDeDias;

	private int w = 535;
	private int h = 255;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);
	
	public ABMRetirarLibroEnBiblioteca(JFrame marco, Entrega entrega) {
		// SETTINGS
		marco.setTitle("Retirar libro sin reserva");
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		setLayout(null);

		JLabel lblNuevaReserva = new JLabel("Retirar Libro");
		lblNuevaReserva.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		lblNuevaReserva.setBounds(10, 11, 193, 20);
		add(lblNuevaReserva);

		JLabel lblEstudiante = new JLabel("Estudiante:");
		lblEstudiante.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEstudiante.setBounds(20, 54, 90, 14);
		add(lblEstudiante);

		JLabel lblLibro = new JLabel("Libro:");
		lblLibro.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLibro.setBounds(20, 86, 80, 14);
		add(lblLibro);

		JLabel lblPorFavorSeleccione = new JLabel("Por favor seleccione un libro");
		if (entrega.getLibro() != null)
			lblPorFavorSeleccione.setText(entrega.getLibro().getTitulo());
		lblPorFavorSeleccione.setBackground(Color.WHITE);
		lblPorFavorSeleccione.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPorFavorSeleccione.setBounds(108, 86, 313, 14);
		add(lblPorFavorSeleccione);

		JLabel lblElEstudiante = new JLabel("Por favor seleccione unx estudiante");
		if (entrega.getEstudiante() != null)
			lblElEstudiante.setText(entrega.getEstudiante().getNombre());
		lblElEstudiante.setBackground(Color.WHITE);
		lblElEstudiante.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblElEstudiante.setBounds(108, 54, 313, 14);
		add(lblElEstudiante);

		JLabel lblCantidadDeDas = new JLabel("Cantidad de d\u00EDas:");
		lblCantidadDeDas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCantidadDeDas.setBounds(20, 120, 145, 14);
		add(lblCantidadDeDas);

		JLabel lblFechaDeHoy = new JLabel("Retira:");
		lblFechaDeHoy.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaDeHoy.setBounds(21, 165, 68, 14);
		add(lblFechaDeHoy);

		JLabel lblFechaHoy = new JLabel("fecha");
		lblFechaHoy.setText(entrega.getRetirado().toString());
		lblFechaHoy.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaHoy.setBounds(96, 165, 118, 14);
		add(lblFechaHoy);

		JLabel lblDevolverAntesDe = new JLabel("Devolver antes de:");
		lblDevolverAntesDe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDevolverAntesDe.setBounds(224, 165, 134, 14);
		add(lblDevolverAntesDe);

		JLabel lblFechaExpira = new JLabel("fecha");
		lblFechaExpira.setText(entrega.getExpira().toString());
		lblFechaExpira.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaExpira.setBounds(379, 165, 118, 14);
		add(lblFechaExpira);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 42, 506, 1);
		add(separator);

		txtCantidadDeDias = new JTextField();
		txtCantidadDeDias.setText("0");
		if (entrega.getEstudiante() == null || entrega.getLibro() == null) {
			txtCantidadDeDias.setEnabled(false);
		}
		txtCantidadDeDias.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (txtCantidadDeDias.getText().length() <= 2) {
					if (Character.isDigit(e.getKeyChar())) {
						if (txtCantidadDeDias.getText().length() > 0) {
							entrega.setExpira(Integer.parseInt(txtCantidadDeDias.getText()));
							lblFechaExpira.setText(entrega.getExpira().toString());
						}
					} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						if (txtCantidadDeDias.getText().length() > 0) {
							entrega.setExpira(Integer.parseInt(txtCantidadDeDias.getText()));
							lblFechaExpira.setText(entrega.getExpira().toString());
						} else {
							entrega.setExpira(0);
							lblFechaExpira.setText(entrega.getExpira().toString());
						}
					} else {
						e.consume();
					}
				} else {
					e.consume();
				}
			}
		});
		txtCantidadDeDias.setBounds(161, 120, 42, 20);
		add(txtCantidadDeDias);
		txtCantidadDeDias.setColumns(10);

		JButton btnSeleccionarLibro = new JButton("Seleccionar");
		btnSeleccionarLibro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				marco.setContentPane(new BLibro(marco, entrega));
				marco.validate();
			}
		});
		btnSeleccionarLibro.setBounds(425, 84, 89, 23);
		add(btnSeleccionarLibro);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new HomeBibliotecario(marco));
				marco.validate();
			}
		});
		btnCancelar.setBounds(326, 210, 89, 23);
		add(btnCancelar);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EntregaDAO endao = new EntregaDAO();
				endao.alta(entrega);
				endao.cerrarConexion();
				marco.setContentPane(new HomeBibliotecario(marco));
				marco.validate();
			}
		});
		btnAceptar.setBounds(425, 210, 89, 23);
		add(btnAceptar);

		JButton btnSeleccionarEstudiante = new JButton("Seleccionar");
		btnSeleccionarEstudiante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BEstudiante(marco, entrega));
				marco.validate();
			}
		});
		btnSeleccionarEstudiante.setBounds(425, 52, 89, 23);
		add(btnSeleccionarEstudiante);
	}
	
	public boolean dataValida(JFrame marco, Entrega e) {
		if(e.getEstudiante() == null) {
			JOptionPane.showMessageDialog(marco, "Por favor seleccione unx estudiante", "Null student",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (e.getLibro() == null) {
			JOptionPane.showMessageDialog(marco, "Por favor seleccione un libro", "Null book",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (Integer.parseInt(txtCantidadDeDias.getText()) <= 0) {
			JOptionPane.showMessageDialog(marco, "Ingrese una cantidad de días válida", "Invalid days",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (Integer.parseInt(txtCantidadDeDias.getText()) > control.Constantes.maximaDeDiasParaDevolverUnaEnrega) {
			JOptionPane.showMessageDialog(marco,
					"La cantidad máxima de días es " + control.Constantes.maximaDeDiasParaDevolverUnaEnrega,
					"Invalid days", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

}
