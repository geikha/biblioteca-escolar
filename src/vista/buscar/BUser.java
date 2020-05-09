package vista.buscar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import biblioteca.Estudiante;
import biblioteca.User;
import dao.LibroDAO;
import dao.UserDAO;
import vista.AppMain;
import vista.HomeAdmin;
import vista.abm.ABMUser;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class BUser extends JPanel {

	private JTextField txtFiltro;
	private JTable table;

	private ArrayList<User> users = new ArrayList<User>();

	private int w = 650;
	private int h = 500;
	private int x = AppMain.x - (w / 2);
	private int y = AppMain.y - (h / 2);

	private void mostrarEnTabla(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		for (User user : users) {
			String tipoUsuario = "";
			switch (user.getTipoUsuario()) {
			case 1:
				tipoUsuario = "Estudiante";
				break;
			case 2:
				tipoUsuario = "Bibliotecarix";
				break;
			case 3:
				tipoUsuario = "Admin";
				break;
			}
			if (user instanceof Estudiante)
				modelo.addRow(new Object[] { user.getUsername(), tipoUsuario, ((Estudiante) user).getNombre() });
			else {
				modelo.addRow(new Object[] { user.getUsername(), tipoUsuario, "" });
			}
		}
	}

	public BUser(JFrame marco) {

		// SETTINGS
		marco.setTitle("Buscar Estudiante");
		marco.setBounds(x, y, w + 15, h + 30);
		setBounds(0, 0, w, h);
		setLayout(null);

		// TABLA

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 77, 521, 362);
		add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		@SuppressWarnings("serial")
		DefaultTableModel modelo = new DefaultTableModel(new Object[] { "Username", "Tipo de usuario", "Nombre" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table.setModel(modelo);

		// TXT

		txtFiltro = new JTextField();
		txtFiltro.setBounds(18, 33, 521, 20);
		add(txtFiltro);
		txtFiltro.setColumns(10);

		// BOTONES

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDAO udao = new UserDAO();
				users = udao.usersPorUsername(txtFiltro.getText());
				udao.cerrarConexion();
				mostrarEnTabla(modelo);
			}
		});
		btnBuscar.setBounds(549, 32, 91, 23);
		add(btnBuscar);

		JButton btnListo = new JButton("Listo");
		btnListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new HomeAdmin(marco));
				marco.validate();
			}
		});
		btnListo.setBounds(549, 449, 91, 23);
		add(btnListo);

		JLabel lblFiltro = new JLabel("Filtro:");
		lblFiltro.setBounds(10, 11, 46, 14);
		add(lblFiltro);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > -1) {
					UserDAO udao = new UserDAO();
					User user = users.get(table.getSelectedRow());
					udao.baja(user);
					udao.cerrarConexion();
					users.remove(user);
					mostrarEnTabla(modelo);
				}
			}
		});
		btnEliminar.setBounds(549, 140, 91, 23);
		add(btnEliminar);

		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMUser(marco));
				marco.validate();
			}
		});
		btnNuevo.setBounds(549, 106, 91, 23);
		add(btnNuevo);

		// OPENING ACTIONS
		UserDAO udao = new UserDAO();
		users = udao.todxs();
		udao.cerrarConexion();
		mostrarEnTabla(modelo);

	}

}
