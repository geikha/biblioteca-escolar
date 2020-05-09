package vista.buscar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import biblioteca.*;
import dao.EntregaDAO;
import dao.ReservaDAO;
import vista.AppMain;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;

public class BEntrega extends JPanel {

	private JTextField txtFiltro;
	private JTable table;

	private User user = AppMain.user;
	private ArrayList<Entrega> entregas = new ArrayList<Entrega>();
	private JButton btnBuscar;
	private JCheckBox chckbxFaltantes;
	
	private int w = 650;
	private int h = 500;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);

	public void mostrarEnTabla(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		for (Entrega en : entregas)
			modelo.addRow(new Object[] { en.getEstudiante().getNombre(), en.getLibro().getTitulo(), en.getRetirado(),
					en.getExpira(), en.isDevuelta() ? "Sí" : "No" });
	}
	
	public BEntrega(JFrame marco) {
		initComponents(marco);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public BEntrega(JFrame marco, boolean noDevueltasExpiradas) {
		initComponents(marco);
		chckbxFaltantes.setSelected(noDevueltasExpiradas);
		btnBuscar.doClick();
	}

	public void initComponents(JFrame marco) {

		// SETTINGS
		marco.setTitle("Buscar Entregas");
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		setLayout(null);

		// TABLA

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 77, 521, 362);
		add(scrollPane);

		table = new JTable();
		table.removeEditor();
		scrollPane.setViewportView(table);

		@SuppressWarnings("serial")
		DefaultTableModel modelo = new DefaultTableModel(
				new Object[] { "Nombre y Apellido", "Título Libro", "Desde", "Hasta", "Devuelto" }, 0) 
		{	@Override public boolean isCellEditable(int row, int column) { return false; } };

		table.setModel(modelo);

		// TXT

		txtFiltro = new JTextField();
		txtFiltro.setBounds(18, 33, 521, 20);
		add(txtFiltro);
		txtFiltro.setColumns(10);

		// BOTONES

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EntregaDAO endao = new EntregaDAO();
				if (user.getTipoUsuario() == 1) {
					entregas = endao.entregasPorTituloDelLibro(txtFiltro.getText());
				} else {
					entregas = endao.entregasPorNombreDelAlumno(txtFiltro.getText());
				}
				entregas = endao.entregasPorNombreDelAlumno(txtFiltro.getText());
				endao.cerrarConexion();
				if (chckbxFaltantes.isSelected())
					filtrarNoDevueltasExpiradas();
				mostrarEnTabla(modelo);
			}
		});

		chckbxFaltantes = new JCheckBox("Faltantes");
		chckbxFaltantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBuscar.doClick();
			}
		});
		chckbxFaltantes.setBounds(545, 79, 97, 23);
		add(chckbxFaltantes);
		btnBuscar.setBounds(549, 32, 91, 23);
		add(btnBuscar);

		JButton btnListo = new JButton("Listo");
		btnListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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

		JButton btnDevolver = new JButton("Devolver");
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > -1) {
					entregas.get(table.getSelectedRow()).devolverEntrega();
					btnBuscar.doClick();
				}
			}
		});
		btnDevolver.setBounds(549, 109, 91, 23);
		add(btnDevolver);

		// OPENING ACTIONS
		EntregaDAO endao = new EntregaDAO();
		entregas = endao.todas();
		endao.cerrarConexion();
		mostrarEnTabla(modelo);
	}

	public void filtrarNoDevueltasExpiradas() {
		for (Iterator<Entrega> iterador = entregas.iterator(); iterador.hasNext(); ) {
			Entrega en = iterador.next();
			if (en.isDevuelta() && !en.estaExpirada())
				iterador.remove();
		}
			
	}
}
