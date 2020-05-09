package vista.abm;

import javax.swing.*;

import biblioteca.Estudiante;
import biblioteca.Reserva;
import biblioteca.User;
import dao.ReservaDAO;
import vista.AppMain;
import vista.HomeEstudiante;
import vista.buscar.BLibro;

import java.awt.Font;
import java.time.LocalDate;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ABMReserva extends JPanel {
	private JTextField txtCantidadDeDias;

	User user = AppMain.user;

	private int w = 560;
	private int h = 255;
	private int x = AppMain.x - (w / 2);
	private int y = AppMain.y - (h / 2);

	public ABMReserva(JFrame marco, Reserva reserva) {
		// SETTINGS
		marco.setTitle("Buscar Libro");
		marco.setBounds(x, y, w + 15, h + 50);
		setBounds(0, 0, w, h);
		setLayout(null);

		reserva.setEstudiante((Estudiante) user);

		JLabel lblNuevaReserva = new JLabel("Nueva Reserva");
		lblNuevaReserva.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		lblNuevaReserva.setBounds(10, 11, 193, 20);
		add(lblNuevaReserva);

		JLabel lblEstudiante = new JLabel("Estudiante:");
		lblEstudiante.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEstudiante.setBounds(20, 54, 106, 14);
		add(lblEstudiante);

		JLabel lblLibro = new JLabel("Libro:");
		lblLibro.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLibro.setBounds(20, 79, 90, 14);
		add(lblLibro);

		JLabel lblPorFavorSeleccione = new JLabel("Por favor seleccione un libro");
		lblPorFavorSeleccione.setBackground(Color.WHITE);
		lblPorFavorSeleccione.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPorFavorSeleccione.setBounds(138, 79, 277, 14);
		add(lblPorFavorSeleccione);

		JLabel lblElEstudiante = new JLabel("elsinombre");
		lblElEstudiante.setText(((Estudiante) AppMain.user).getNombre());
		lblElEstudiante.setBackground(Color.WHITE);
		lblElEstudiante.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblElEstudiante.setBounds(138, 54, 356, 14);
		add(lblElEstudiante);

		JLabel lblCantidadDeDas = new JLabel("Cantidad de d\u00EDas:");
		lblCantidadDeDas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCantidadDeDas.setBounds(20, 120, 145, 14);
		add(lblCantidadDeDas);

		JLabel lblFechaDeHoy = new JLabel("Fecha de hoy:");
		lblFechaDeHoy.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaDeHoy.setBounds(20, 165, 118, 14);
		add(lblFechaDeHoy);

		JLabel lblFechaHoy = new JLabel("fecha");
		lblFechaHoy.setText(reserva.getAlta().toString());
		lblFechaHoy.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaHoy.setBounds(138, 165, 118, 14);
		add(lblFechaHoy);

		JLabel lblRetirarAntesDe = new JLabel("Retirar antes de:");
		lblRetirarAntesDe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRetirarAntesDe.setBounds(244, 165, 145, 14);
		add(lblRetirarAntesDe);

		JLabel lblFechaExpira = new JLabel("fecha");
		lblFechaExpira.setText(reserva.getExpira().toString());
		lblFechaExpira.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaExpira.setBounds(388, 165, 118, 14);
		add(lblFechaExpira);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 41, 537, 2);
		add(separator);
		if (reserva.getLibro() != null)
			lblPorFavorSeleccione.setText(reserva.getLibro().getTitulo());

		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BLibro(marco, new Reserva()));
				marco.validate();
			}
		});
		btnSeleccionar.setBounds(433, 75, 114, 23);
		add(btnSeleccionar);

		txtCantidadDeDias = new JTextField();
		txtCantidadDeDias.setText("0");
		if (reserva.getEstudiante() == null || reserva.getLibro() == null) {
			txtCantidadDeDias.setEnabled(false);
		}
		txtCantidadDeDias.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (txtCantidadDeDias.getText().length() <= 2) {
					if (Character.isDigit(e.getKeyChar())) {
						if (txtCantidadDeDias.getText().length() > 0) {
							reserva.setExpira(Integer.parseInt(txtCantidadDeDias.getText()));
							lblFechaExpira.setText(reserva.getExpira().toString());
						}
					} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						if (txtCantidadDeDias.getText().length() > 0) {
							reserva.setExpira(Integer.parseInt(txtCantidadDeDias.getText()));
							lblFechaExpira.setText(reserva.getExpira().toString());
						} else {
							reserva.setExpira(0);
							lblFechaExpira.setText(reserva.getExpira().toString());
						}
					} else {
						e.consume();
					}
				} else {
					e.consume();
				}
			}
		});
		txtCantidadDeDias.setBounds(172, 118, 42, 20);
		if (reserva.getCantidadDias() != 0)
			txtCantidadDeDias.setText(Integer.toString(reserva.getCantidadDias()));
		add(txtCantidadDeDias);
		txtCantidadDeDias.setColumns(10);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new HomeEstudiante(marco));
				marco.validate();
			}
		});
		btnCancelar.setBounds(309, 210, 106, 23);
		add(btnCancelar);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reserva.setCantidadDias(Integer.parseInt(txtCantidadDeDias.getText()));
				reserva.setEstudiante((Estudiante) AppMain.user);
				ReservaDAO rdao = new ReservaDAO();
				rdao.alta(reserva);
				rdao.cerrarConexion();
				marco.setContentPane(new HomeEstudiante(marco));
				marco.validate();
			}
		});
		btnAceptar.setBounds(425, 210, 124, 23);
		add(btnAceptar);
	}

	public boolean dataValida(JFrame marco, Reserva r) {
		if (r.getLibro() == null) {
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
