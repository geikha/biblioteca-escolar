package vista.buscar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import biblioteca.Entrega;
import biblioteca.Reserva;
import biblioteca.User;
import dao.ReservaDAO;
import vista.AppMain;
import vista.abm.ABMReserva;
import vista.abm.ABMRetirarLibroEnBiblioteca;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class BReserva extends JPanel {

	private JTextField txtFiltro;
	private JTable table;

	User user = AppMain.user;
	private ArrayList<Reserva> reservas = new ArrayList<Reserva>();
	
	private int w = 650;
	private int h = 500;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);

	public void mostrarEnTabla(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		for (Reserva r : reservas) {
			modelo.addRow(new Object[] { r.getEstudiante().getNombre(), r.getLibro().getTitulo(), r.getAlta(),
					r.getExpira(), r.isEfectiva() ? "Sí" : "No" });
		}
	}

	public BReserva(JFrame marco) {

		// SETTINGS
		marco.setTitle("Buscar Reservas");
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		setLayout(null);

		// TABLA

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 77, 513, 362);
		add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		@SuppressWarnings("serial")
		DefaultTableModel modelo = new DefaultTableModel(
				new Object[] { "Nombre y Apellido", "Título Libro", "Desde", "Hasta", "Efectiva" }, 0)
		{	@Override public boolean isCellEditable(int row, int column) { return false; } };

		table.setModel(modelo);

		// TXT

		txtFiltro = new JTextField();
		txtFiltro.setBounds(18, 33, 513, 20);
		add(txtFiltro);
		txtFiltro.setColumns(10);

		// BOTONES

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReservaDAO rdao = new ReservaDAO();
				if (user.getTipoUsuario() == 1) {
					reservas = rdao.reservasPorTituloDelLibro(txtFiltro.getText());
				} else {
					reservas = rdao.reservasPorNombreDelAlumno(txtFiltro.getText());
				}
				reservas = rdao.reservasPorNombreDelAlumno(txtFiltro.getText());
				rdao.cerrarConexion();
				mostrarEnTabla(modelo);
			}
		});
		btnBuscar.setBounds(540, 32, 100, 23);
		add(btnBuscar);

		JButton btnListo = new JButton("Listo");
		btnListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getTipoUsuario() == 1) {
					marco.setContentPane(new vista.HomeEstudiante(marco));
					marco.validate();
				} else {
					marco.setContentPane(new vista.HomeBibliotecario(marco));
					marco.validate();
				}
			}
		});
		btnListo.setBounds(549, 449, 91, 23);
		add(btnListo);

		JLabel lblFiltro = new JLabel("Filtro:");
		lblFiltro.setBounds(10, 11, 46, 14);
		add(lblFiltro);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > -1) {
					if(!reservas.get(table.getSelectedRow()).estaExpirada()) {
						reservas.get(table.getSelectedRow()).cancelarReserva();
						ReservaDAO rdao = new ReservaDAO();
						reservas = rdao.reservasPorNombreDelAlumno(txtFiltro.getText());
						rdao.cerrarConexion();
						mostrarEnTabla(modelo);
					}
					else {
						JOptionPane.showMessageDialog(marco, "Una reserva expirada no puede cancelarse", 
								"No puede cancelarse", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnCancelar.setBounds(540, 140, 100, 23);
		add(btnCancelar);

		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getTipoUsuario() == 1) {
					marco.setContentPane(new ABMReserva(marco, new Reserva()));
				} else {
					marco.setContentPane(new ABMRetirarLibroEnBiblioteca(marco, new Entrega()));
				}
				marco.validate();
			}
		});
		btnNuevo.setBounds(540, 105, 100, 23);
		add(btnNuevo);

		if (user.getTipoUsuario() != 1) {
			JButton btnRetirar = new JButton("Retirar");
			btnRetirar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(table.getSelectedRow() > -1) {
						reservas.get(table.getSelectedRow()).retirarReserva();
						btnBuscar.doClick();
					}
				}
			});
			btnRetirar.setBounds(540, 174, 100, 23);
			add(btnRetirar);
		}

		// OPENING ACTIONS
		ReservaDAO rdao = new ReservaDAO();
		reservas = rdao.todas();
		rdao.cerrarConexion();
		mostrarEnTabla(modelo);
	}
}
