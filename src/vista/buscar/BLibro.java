package vista.buscar;

import java.awt.color.CMMException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import biblioteca.*;
import dao.LibroDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import vista.AppMain;
import vista.HomeBibliotecario;
import vista.HomeEstudiante;
import vista.abm.ABMLibro;
import vista.abm.ABMReserva;
import vista.abm.ABMRetirarLibroEnBiblioteca;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BLibro extends JPanel {

	private JTextField txtFiltro;
	private JTable table;
	private ArrayList<Libro> libros = new ArrayList<Libro>();
	private JButton btnModificar;
	private JButton btnEliminar;
	private JComboBox<String> comboBox;
	private JButton btnNuevo;
	private JButton btnBuscar;
	private JButton btnListo;
	DefaultTableModel modelo;
	
	private int w = 650;
	private int h = 500;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);

	private User user = AppMain.user;

	public void mostrarEnTabla() {
		modelo.setRowCount(0);
		for (Libro l : libros)
			modelo.addRow(new Object[] { l.getISBN(), l.getTitulo(), l.autorxsString(), l.getEditorial(),
					l.getAñoOriginal(), l.getStockDisponible()==0 ? "SIN STOCK" : l.getStockDisponible() });
	}

	/**
	 * @param marco
	 * @apiNote ABM
	 * @wbp.parser.constructor
	 */
	public BLibro(JFrame marco) {
		initComponents(marco);
		botonesABM(marco);
		btnListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getTipoUsuario() == 2)
					marco.setContentPane(new HomeBibliotecario(marco));
				else if (user.getTipoUsuario() == 1)
					marco.setContentPane(new HomeEstudiante(marco));
				marco.validate();
			}
		});
	}

	/**
	 * @param marco
	 * @apiNote Seleccionar
	 */
	public BLibro(JFrame marco, Reserva reserva) {
		initComponents(marco);
		botonesABM(marco);
		btnListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > -1) {
					reserva.setLibro(libros.get(table.getSelectedRow()));
					marco.setContentPane(new ABMReserva(marco, reserva));
					marco.validate();
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(libros.get(table.getSelectedRow()).getStockDisponible()==0)
					table.clearSelection();
			}
		});
	}

	/**
	 * @param marco
	 * @apiNote Seleccionar
	 */
	public BLibro(JFrame marco, Entrega entrega) {
		initComponents(marco);
		botonesABM(marco);
		btnListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > -1) {
					entrega.setLibro(libros.get(table.getSelectedRow()));
					marco.setContentPane(new ABMRetirarLibroEnBiblioteca(marco, entrega));
					marco.validate();
				}
			}
		});
	}

	@SuppressWarnings("serial")
	public void initComponents(JFrame marco) {
		// SETTINGS
		marco.setTitle("Buscar Libro");
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		setLayout(null);

		// TABLA

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 77, 521, 362);
		add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		modelo = new DefaultTableModel(
				new Object[] { "ISBN", "Título", "Autorx", "Editorial", "Año", "Stock Disponible" }, 0)
			{	@Override public boolean isCellEditable(int row, int column) { return false; } };

		table.setModel(modelo);

		JLabel lblFiltro = new JLabel("Filtro:");
		lblFiltro.setBounds(10, 11, 46, 14);
		add(lblFiltro);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(396, 33, 143, 20);
		comboBox.addItem("Título");
		comboBox.addItem("Autorx");

		// TXT

		txtFiltro = new JTextField();
		txtFiltro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnBuscar.doClick();
			}
		});

		txtFiltro.setBounds(18, 33, 368, 20);
		add(txtFiltro);
		txtFiltro.setColumns(10);
		add(comboBox);

		// BOTONES

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String campo = (String) comboBox.getSelectedItem();
				LibroDAO ldao = new LibroDAO();

				switch (campo) {
				case "Título":
					libros = ldao.librosPorTitulo(txtFiltro.getText());
					break;
				case "Autorx":
					libros = ldao.librosPorAutor(txtFiltro.getText());
					break;
				}

				mostrarEnTabla();
				ldao.cerrarConexion();
			}
		});
		btnBuscar.setBounds(549, 32, 91, 23);
		add(btnBuscar);

		btnListo = new JButton("Listo");
		btnListo.setBounds(549, 449, 91, 23);
		add(btnListo);

		// OPENING ACTIONS

		LibroDAO ldao = new LibroDAO();
		libros = ldao.todos();
		ldao.cerrarConexion();
		mostrarEnTabla();
	}

	public void botonesABM(JFrame marco) {
		if (user.getTipoUsuario() >= 2) {
			btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() > -1) {
						Libro libro = libros.get(table.getSelectedRow());
						LibroDAO ldao = new LibroDAO();
						ldao.baja(libro);
						ldao.cerrarConexion();
						libros.remove(libro);
						mostrarEnTabla();
					}
				}
			});

			btnNuevo = new JButton("Nuevo");
			btnNuevo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					marco.setContentPane(new ABMLibro(marco));
					marco.validate();
				}
			});
			btnNuevo.setBounds(549, 106, 91, 23);
			add(btnNuevo);

			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() > -1) {
						Libro libro = libros.get(table.getSelectedRow());
						marco.setContentPane(new ABMLibro(marco, libro));
						marco.validate();
					}
				}
			});
			btnModificar.setBounds(549, 140, 91, 23);
			add(btnModificar);
			btnEliminar.setBounds(549, 173, 91, 23);
			add(btnEliminar);
		}
	}
}
