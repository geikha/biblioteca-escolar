package vista.buscar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import biblioteca.Entrega;
import biblioteca.Estudiante;
import biblioteca.User;
import dao.EstudianteDAO;
import vista.AppMain;
import vista.HomeBibliotecario;
import vista.abm.ABMEstudiante;
import vista.abm.ABMRetirarLibroEnBiblioteca;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.color.CMMException;
import java.awt.event.ActionEvent;

public class BEstudiante extends JPanel {

	private JTextField txtFiltro;
	private JTable table;
	JButton btnListo;
	
	private int w = 650;
	private int h = 500;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);

	private ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
	private JComboBox<String> cmbxCampo;

	public void mostrarEnTabla(DefaultTableModel modelo) {
		modelo.setRowCount(0);
		for (Estudiante e : estudiantes)
			modelo.addRow(new Object[] { e.getDNI(), e.getNombre(), e.cursoString(), e.getUsername() });
	}

	public BEstudiante(JFrame marco) {
		// SETTINGS
		marco.setTitle("Buscar Estudiante");
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		setLayout(null);
		initComponents(marco);
		btnListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new HomeBibliotecario(marco));
				marco.validate();
			}
		});
	}
	
	public BEstudiante(JFrame marco, Entrega entrega) {
		// SETTINGS
		marco.setTitle("Buscar Estudiante");
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		setLayout(null);
		initComponents(marco);
		btnListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() > -1) {
					entrega.setEstudiante(estudiantes.get(table.getSelectedRow()));;
					marco.setContentPane(new ABMRetirarLibroEnBiblioteca(marco, entrega));
					marco.validate();
				}
			}
		});
	}

	public void initComponents(JFrame marco) {

		// TABLA

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 77, 512, 362);
		add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		@SuppressWarnings("serial")
		DefaultTableModel modelo = new DefaultTableModel(new Object[] { "DNI", "Nombre y Apellido", "Curso", "User" },0)
			{	@Override public boolean isCellEditable(int row, int column) { return false; } };

		table.setModel(modelo);

		// TXT

		txtFiltro = new JTextField();
		txtFiltro.setBounds(18, 33, 371, 20);
		add(txtFiltro);
		txtFiltro.setColumns(10);

		cmbxCampo = new JComboBox<String>();
		cmbxCampo.setBounds(399, 33, 129, 20);
		cmbxCampo.addItem("Nombre y/o Apellido");
		cmbxCampo.addItem("DNI");
		add(cmbxCampo);

		// BOTONES

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String campo = (String) cmbxCampo.getSelectedItem();
				EstudianteDAO edao = new EstudianteDAO();

				switch (campo) {
				case "Nombre y/o Apellido":
					estudiantes = edao.estudiantesPorNombre(txtFiltro.getText());
					break;
				case "DNI":
					estudiantes = edao.estudiantesPorDNI(txtFiltro.getText());
					break;
				}

				edao.cerrarConexion();
				mostrarEnTabla(modelo);
			}
		});

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > -1) {
					Estudiante estudiante = estudiantes.get(table.getSelectedRow());
					marco.setContentPane(new ABMEstudiante(marco, estudiante));
					marco.validate();
				}
			}
		});
		btnModificar.setBounds(542, 140, 98, 23);
		add(btnModificar);

		btnListo = new JButton("Listo");
		btnListo.setBounds(549, 449, 91, 23);
		add(btnListo);

		JLabel lblFiltro = new JLabel("Filtro:");
		lblFiltro.setBounds(10, 11, 46, 14);
		add(lblFiltro);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(542, 173, 98, 23);
		add(btnEliminar);

		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new ABMEstudiante(marco));
				marco.validate();
			}
		});
		btnNuevo.setBounds(542, 106, 98, 23);
		add(btnNuevo);

		btnBuscar.setBounds(540, 32, 100, 23);
		add(btnBuscar);

		// OPENINIG ACTIONS
		EstudianteDAO edao = new EstudianteDAO();
		estudiantes = edao.todxs();
		edao.cerrarConexion();
		mostrarEnTabla(modelo);
	}
}
