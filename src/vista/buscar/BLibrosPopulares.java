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
import java.awt.Font;

public class BLibrosPopulares extends JPanel {
	private JTable table;
	private ArrayList<Libro> libros = new ArrayList<Libro>();
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnNuevo;
	private JButton btnVolver;
	DefaultTableModel modelo;

	private int w = 562;
	private int h = 484;
	private int x = AppMain.x - (w / 2);
	private int y = AppMain.y - (h / 2);

	private User user = AppMain.user;
	private JLabel lblLibrosPopulares;
	private JButton btnReservar;

	public void mostrarEnTabla() {
		modelo.setRowCount(0);
		for (Libro l : libros)
			modelo.addRow(new Object[] { l.getTitulo(), l.autorxsString(), l.getAñoOriginal(),
					l.getStockDisponible() == 0 ? "SIN STOCK" : l.getStockDisponible(), l.getPopularidad() });
	}

	/**
	 * @param marco
	 * @apiNote ABM
	 * @wbp.parser.constructor
	 */
	public BLibrosPopulares(JFrame marco) {
		initComponents(marco);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getTipoUsuario() == 2)
					marco.setContentPane(new HomeBibliotecario(marco));
				else if (user.getTipoUsuario() == 1)
					marco.setContentPane(new HomeEstudiante(marco));
				marco.validate();
			}
		});
	}

	@SuppressWarnings("serial")
	public void initComponents(JFrame marco) {
		// SETTINGS
		marco.setTitle("Buscar Libro");
		marco.setBounds(x, y, w + 5, h + 50);
		setBounds(0, 0, 562, 484);
		setLayout(null);

		// TABLA

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 542, 388);
		add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		modelo = new DefaultTableModel(new Object[] { "Título", "Autorx", "Año", "Stock" ,"Veces reservado/llevado" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table.setModel(modelo);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(461, 450, 91, 23);
		add(btnVolver);

		lblLibrosPopulares = new JLabel("Libros Populares");
		lblLibrosPopulares.setHorizontalAlignment(SwingConstants.CENTER);
		lblLibrosPopulares.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblLibrosPopulares.setBounds(10, 11, 542, 29);
		add(lblLibrosPopulares);

		if (user.getTipoUsuario() == 1) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() > -1) {
						Reserva r = new Reserva();
						r.setLibro(libros.get(table.getSelectedRow()));
						marco.setContentPane(new ABMReserva(marco, r));
						marco.validate();
					}
				}
			});
			btnReservar.setBounds(237, 450, 89, 23);
			add(btnReservar);
		}

		// OPENING ACTIONS

		LibroDAO ldao = new LibroDAO();
		libros = ldao.librosPopulares();
		ldao.cerrarConexion();
		mostrarEnTabla();
		
	}
}
