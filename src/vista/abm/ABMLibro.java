package vista.abm;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import java.util.ArrayList;

import dao.AutorxDAO;
import dao.EditorialDAO;
import dao.LibroDAO;
import vista.AppMain;
import vista.HomeBibliotecario;
import vista.buscar.BLibro;
import biblioteca.Libro;
import biblioteca.User;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ABMLibro extends JPanel {

	private JTextField txtTitulo;
	private JComboBox<String> cmbxAutorx;
	private JComboBox<String> cmbxEditorial;
	private JFormattedTextField frmtdTxtISBN;
	private JComboBox<String> cmbxAutor2;
	private JSpinner spinner;
	private JButton btnCancelar;
	private JCheckBox chckbxAutorx3;
	private JComboBox<String> cmbxAutor3;
	private JCheckBox chckbxAutorx2;
	private JButton btnAceptar;
	private JLabel lblTituloPanel;
	private JLabel lblAnoOriginal;
	private JTextField txtAno;

	private int w = 450;
	private int h = 400;
	private int x = AppMain.x - (w / 2);
	private int y = AppMain.y - (h / 2);

	/**
	 * @param marco
	 * @apiNote ALTA
	 * @wbp.parser.constructor
	 */
	public ABMLibro(JFrame marco) {
		// SETTINGS
		marco.setTitle("Nuevo Libro");
		marco.setBounds(x, y, w + 15, h + 50);
		setBounds(0, 0, w, h);
		setLayout(null);

		initComponents(marco, new Libro());

		lblTituloPanel.setText("Nuevo Libro");

		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataValida(marco)) {
					LibroDAO ldao = new LibroDAO();
					ldao.alta(makeLibro());
					ldao.cerrarConexion();
					marco.setContentPane(new BLibro(marco));
				}
			}
		});

	}

	/**
	 * @param marco
	 * @apiNote MOD
	 */
	public ABMLibro(JFrame marco, Libro libro) {
		// SETTINGS
		marco.setTitle("Modificar Libro");
		marco.setBounds(x, y, w + 15, h + 50);
		setBounds(0, 0, w, h);
		setLayout(null);

		initComponents(marco, libro);

		lblTituloPanel.setText("Nuevo Libro");

		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataValida(marco, libro)) {
					LibroDAO ldao = new LibroDAO();
					ldao.modificacion(libro, makeLibro(libro));
					ldao.cerrarConexion();
					marco.setContentPane(new BLibro(marco));
				}
			}
		});

	}

	public void initComponents(JFrame marco, Libro libro) {

		ArrayList<String> autorxs = libro.getAutorxs();

		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setBounds(12, 101, 46, 14);
		add(lblISBN);

		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(152, 132, 62, 14);
		add(lblEditorial);

		JLabel lblTitulo = new JLabel("T\u00EDtulo:");
		lblTitulo.setBounds(12, 64, 46, 14);
		add(lblTitulo);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(12, 132, 46, 14);
		add(lblStock);

		JLabel lblAutorx = new JLabel("Autorx/s:");
		lblAutorx.setBounds(12, 206, 46, 14);
		add(lblAutorx);

		lblTituloPanel = new JLabel("Nuevo Libro");
		lblTituloPanel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		lblTituloPanel.setBounds(12, 12, 193, 20);
		add(lblTituloPanel);

		JLabel label1 = new JLabel("1.");
		label1.setBounds(22, 236, 23, 14);
		add(label1);

		JLabel label2 = new JLabel("2.");
		label2.setBounds(22, 267, 23, 14);
		add(label2);

		JLabel label3 = new JLabel("3.");
		label3.setBounds(22, 298, 23, 14);
		add(label3);

		lblAnoOriginal = new JLabel("Año original:");
		lblAnoOriginal.setBounds(12, 170, 106, 14);
		add(lblAnoOriginal);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 42, 419, 2);
		add(separator);

		frmtdTxtISBN = new JFormattedTextField();
		frmtdTxtISBN.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!control.CharValido.ISBN(e.getKeyChar()) & e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					e.consume();
			}
		});
		frmtdTxtISBN.setBounds(61, 98, 370, 20);
		frmtdTxtISBN.setText(libro.getISBN());
		add(frmtdTxtISBN);

		txtAno = new JTextField();
		txtAno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar()) & e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					e.consume();
			}
		});
		txtAno.setBounds(126, 168, 124, 19);
		txtAno.setText(libro.getAñoOriginal());
		add(txtAno);
		txtAno.setColumns(10);

		txtTitulo = new JTextField();
		txtTitulo.setColumns(10);
		txtTitulo.setBounds(61, 61, 370, 20);
		txtTitulo.setText(libro.getTitulo());
		add(txtTitulo);

		spinner = new JSpinner();
		spinner.setBounds(61, 129, 81, 20);
		spinner.setValue(libro.getStock());
		add(spinner);

		cmbxEditorial = new JComboBox<String>();
		cmbxEditorial.setBounds(224, 129, 207, 20);
		cmbxEditorial.addItem("Nueva...");
		EditorialDAO edao = new EditorialDAO();
		for (String s : edao.todas())
			cmbxEditorial.addItem(s);
		edao.cerrarConexion();
		cmbxEditorial.setSelectedItem(libro.getEditorial());
		add(cmbxEditorial);
		if (libro.getEditorial() != null)
			cmbxEditorial.setSelectedItem(libro.getEditorial());
		else
			cmbxEditorial.setSelectedItem("Nueva...");

		cmbxEditorial.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if (cmbxEditorial.getSelectedItem() == "Nueva...") {
					new ABMEditorial(new ABMEditorial.EditorialListener() {
						@Override
						public void editorialCreada(String nombre) {
							cmbxEditorial.addItem(nombre);
							cmbxEditorial.setSelectedItem(nombre);
						}
					});
				}
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});

		cmbxAutorx = new JComboBox<String>();
		cmbxAutorx.setBounds(44, 233, 304, 20);
		cmbxAutorx.addItem("Nuevx...");
		add(cmbxAutorx);

		cmbxAutor2 = new JComboBox<String>();
		cmbxAutor2.setBounds(44, 264, 304, 20);
		cmbxAutor2.addItem("Nuevx...");
		add(cmbxAutor2);

		cmbxAutor3 = new JComboBox<String>();
		cmbxAutor3.setBounds(44, 295, 304, 20);
		cmbxAutor3.addItem("Nuevx...");
		add(cmbxAutor3);

		AutorxDAO adao = new AutorxDAO();
		ArrayList<String> todxsAutorxs = adao.todxs();
		adao.cerrarConexion();

		for (String s : todxsAutorxs) {
			cmbxAutorx.addItem(s);
			cmbxAutor2.addItem(s);
			cmbxAutor3.addItem(s);
		}

		if (autorxs.size() > 0) {
			cmbxAutorx.setSelectedItem(autorxs.get(0));
		}
		if (autorxs.size() > 1) {
			cmbxAutor2.setSelectedItem(autorxs.get(1));
		} else {
			cmbxAutor2.setEnabled(false);
		}
		if (autorxs.size() > 2) {
			cmbxAutor3.setSelectedItem(autorxs.get(2));
		} else {
			cmbxAutor3.setEnabled(false);
		}

		cmbxAutorx.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if (cmbxAutorx.getSelectedItem() == "Nuevx...")
					new ABMAutorx(new ABMAutorx.AutorListener() {
						@Override
						public void autorxCreado(String nombre) {
							cmbxAutorx.addItem(nombre);
							cmbxAutor2.addItem(nombre);
							cmbxAutor3.addItem(nombre);
							cmbxAutorx.setSelectedItem(nombre);
						}
					});
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});

		cmbxAutor2.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if (cmbxAutor2.getSelectedItem() == "Nuevx...")
					new ABMAutorx(new ABMAutorx.AutorListener() {
						@Override
						public void autorxCreado(String nombre) {
							cmbxAutorx.addItem(nombre);
							cmbxAutor2.addItem(nombre);
							cmbxAutor3.addItem(nombre);
							cmbxAutor2.setSelectedItem(nombre);
						}
					});
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		cmbxAutor3.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if (cmbxAutor3.getSelectedItem() == "Nuevx...")
					new ABMAutorx(new ABMAutorx.AutorListener() {
						@Override
						public void autorxCreado(String nombre) {
							cmbxAutorx.addItem(nombre);
							cmbxAutor2.addItem(nombre);
							cmbxAutor3.addItem(nombre);
							cmbxAutor3.setSelectedItem(nombre);
						}
					});
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});

		chckbxAutorx2 = new JCheckBox("Autorx 2");
		chckbxAutorx2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbxAutor2.setEnabled(chckbxAutorx2.isSelected());
			}
		});
		chckbxAutorx2.setBounds(354, 261, 77, 23);
		if (autorxs.size() > 1) {
			chckbxAutorx2.setSelected(true);
			cmbxAutor2.setEnabled(true);
		}
		add(chckbxAutorx2);

		chckbxAutorx3 = new JCheckBox("Autorx 3");
		chckbxAutorx3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxAutorx2.isSelected()) {
					cmbxAutor3.setEnabled(chckbxAutorx3.isSelected());
					chckbxAutorx2.setEnabled(!chckbxAutorx3.isSelected());
				} else {
					chckbxAutorx3.setSelected(false);
					cmbxAutor3.setEnabled(false);
				}
			}
		});
		chckbxAutorx3.setBounds(354, 292, 77, 23);
		if (autorxs.size() > 2) {
			chckbxAutorx3.setSelected(true);
			cmbxAutor3.setEnabled(true);
		}
		add(chckbxAutorx3);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(349, 353, 89, 23);
		add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new vista.HomeBibliotecario(marco));
				marco.validate();
			}
		});
		btnCancelar.setBounds(246, 353, 93, 23);
		add(btnCancelar);

		lblAnoOriginal = new JLabel("Año original:");
		lblAnoOriginal.setBounds(12, 170, 106, 14);
		add(lblAnoOriginal);
	}

	public boolean dataValida(JFrame marco) {
		// TODO ISBN
		if (((int) spinner.getValue()) <= 0) {
			JOptionPane.showMessageDialog(marco, "Ingrese un stock mayor a 0", "Invalid stock",
					JOptionPane.WARNING_MESSAGE);
		}

		return true;
	}

	public boolean dataValida(JFrame marco, Libro l) {
		if (!control.ISBN.ISBNValido(frmtdTxtISBN.getText())) {
			JOptionPane.showMessageDialog(marco, "Ingrese un código ISBN válido" + l.getStockDisponible(),
					"Invalid ISBN", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (((int) spinner.getValue()) < l.getStockDisponible()) {
			JOptionPane.showMessageDialog(marco,
					"Ingrese un stock mayor al stock disponible: " + l.getStockDisponible(), "Invalid stock",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtAno.getText().length() > 5) {
			JOptionPane.showMessageDialog(marco, "Ingrese un año válido", "Invalid year", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (((String) cmbxEditorial.getSelectedItem()).contentEquals("Nueva...")) {
			JOptionPane.showMessageDialog(marco, "Editorial no-válida.", "Invalid publisher", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!autorxsDistintos()) {
			JOptionPane.showMessageDialog(marco, "Autoría no-válida.", "Invalid author", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private boolean autorxsDistintos() {
		String a1 = (String) cmbxAutorx.getSelectedItem();
		String a2 = (String) cmbxAutor2.getSelectedItem();
		String a3 = (String) cmbxAutor3.getSelectedItem();
		if (cmbxAutor2.isEnabled()) {
			if (a1.contentEquals(a2))
				return false;
			else if (a2.contentEquals("Nuevx..."))
				return false;
		}
		if (cmbxAutor3.isEnabled()) {
			if (a1.contentEquals(a3))
				return false;
			else if (a2.contentEquals(a3))
				return false;
			else if (a3.contentEquals("Nuevx..."))
				return false;
		}
		return true;
	}

	public Libro makeLibro() {
		String ISBN = control.ISBN.formatISBN(frmtdTxtISBN.getText());
		String titulo = control.StringFormat.stringForSQL(txtTitulo.getText());
		String editorial = (String) cmbxEditorial.getSelectedItem();
		int stock = (int) spinner.getValue();
		int stockDisponible = stock;
		String año = txtAno.getText();
		LocalDate fechaAlta = LocalDate.now();
		LocalDate ultimoRestock = LocalDate.now();
		ArrayList<String> autorxs = autorxs();

		return new Libro(ISBN, titulo, editorial, stock, stockDisponible, año, fechaAlta, ultimoRestock, autorxs, 0);
	}

	public Libro makeLibro(Libro l) {
		String ISBN = control.ISBN.formatISBN(frmtdTxtISBN.getText());
		String titulo = control.StringFormat.stringForSQL(txtTitulo.getText());
		String editorial = (String) cmbxEditorial.getSelectedItem();
		int stock = (int) spinner.getValue();
		int stockDisponible = l.getStockDisponible() + (stock - l.getStock());
		String año = txtAno.getText();
		LocalDate fechaAlta = l.getFechaAlta();
		LocalDate ultimoRestock = l.getStock() != stock ? LocalDate.now() : l.getUltimoRestock();
		ArrayList<String> autorxs = autorxs();

		return new Libro(ISBN, titulo, editorial, stock, stockDisponible, año, fechaAlta, ultimoRestock, autorxs,
				l.getPopularidad());
	}

	public ArrayList<String> autorxs() {
		ArrayList<String> autorxs = new ArrayList<String>();

		autorxs.add((String) cmbxAutorx.getSelectedItem());
		if (cmbxAutor2.isEnabled())
			autorxs.add((String) cmbxAutor2.getSelectedItem());
		if (cmbxAutor3.isEnabled())
			autorxs.add((String) cmbxAutor3.getSelectedItem());

		return autorxs;
	}
}
