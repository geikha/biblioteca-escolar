package vista.abm;

import javax.swing.*;

import biblioteca.Estudiante;
import biblioteca.User;
import dao.EstudianteDAO;
import dao.UserDAO;
import vista.AppMain;
import vista.buscar.BEstudiante;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ABMEstudiante extends JPanel {
	private JTextField txtDNI;
	private JTextField txtTelefono;
	private JTextField txtNombre;
	private JTextField txtUsername;
	private JComboBox<String> cmbxDivision;
	private JComboBox<String> cmbxCurso;
	private JLabel lblTituloPanel = new JLabel();
	private JButton btnAceptar;
	
	private int w = 450;
	private int h = 270;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);

	/**
	 * @param marco
	 * @apiNote ALTAS
	 * @wbp.parser.constructor
	 */
	public ABMEstudiante(JFrame marco) {
		// SETTINGS
		marco.setTitle("Registrar Estudiante");
		initComponets(marco, new Estudiante());
		lblTituloPanel.setText("Registrar Estudiante:");
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataValida(marco)) {
					EstudianteDAO edao = new EstudianteDAO();
					Estudiante estudiante = makeEstudiante();
					edao.alta(estudiante);
					edao.cerrarConexion();
					JOptionPane.showMessageDialog(marco, "Contraseña auto-generada: " + estudiante.getDNI(), "Contraseña auto-generada",
							JOptionPane.INFORMATION_MESSAGE);
					marco.setContentPane(new BEstudiante(marco));
					marco.validate();
				}
			}
		});

	}

	/**
	 * @param marco
	 * @apiNote MODIFS
	 */
	public ABMEstudiante(JFrame marco, Estudiante estudiante) {
		// SETTINGS
		marco.setTitle("Modificar Estudiante");
		initComponets(marco, estudiante);
		lblTituloPanel.setText("Modificar Estudiante:");
		txtUsername.setEnabled(false);

		cmbxCurso.setSelectedItem(estudiante.añoCurso());
		cmbxDivision.setSelectedItem(estudiante.divisionCurso());

		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataValida(marco)) {
					EstudianteDAO edao = new EstudianteDAO();
					edao.modificacion(estudiante, makeEstudiante());
					edao.cerrarConexion();
					marco.setContentPane(new BEstudiante(marco));
					marco.validate();
				}
			}
		});

	}

	public void initComponets(JFrame marco, Estudiante estudiante) {
		setLayout(null);
		marco.setBounds(x, y, w+15, h+50);
		setBounds(0, 0, w, h);
		lblTituloPanel = new JLabel();
		lblTituloPanel.setBounds(10, 11, 225, 20);
		lblTituloPanel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		add(lblTituloPanel);

		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(10, 65, 46, 14);
		add(lblDni);

		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(10, 121, 81, 14);
		add(lblTelefono);

		JLabel lblNombreCompleto = new JLabel("Nombre Completo:");
		lblNombreCompleto.setBounds(10, 93, 89, 14);
		add(lblNombreCompleto);

		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(245, 121, 46, 14);
		add(lblCurso);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 150, 89, 14);
		add(lblUsername);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 41, 419, 2);
		add(separator);

		txtDNI = new JTextField();
		txtDNI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!control.CharValido.DNI(e.getKeyChar()) & e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					e.consume();
			}
		});
		txtDNI.setBounds(109, 65, 320, 20);
		if (estudiante.getDNI() != 0)
			txtDNI.setText(Integer.toString(estudiante.getDNI()));
		add(txtDNI);
		txtDNI.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!control.CharValido.nombreDePersona(e.getKeyChar()) & e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					e.consume();
			}
		});
		txtNombre.setColumns(10);
		txtNombre.setBounds(109, 90, 320, 20);
		txtNombre.setText(estudiante.getNombre());
		add(txtNombre);

		txtTelefono = new JTextField();
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!control.CharValido.telefono(e.getKeyChar()) & e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					e.consume();
			}
		});
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(109, 118, 126, 20);
		txtTelefono.setText(estudiante.getTelefono());
		add(txtTelefono);

		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					btnAceptar.doClick();
				else if (!control.CharValido.username(e.getKeyChar()) & e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					e.consume();
			}
		});
		txtUsername.setColumns(10);
		txtUsername.setBounds(109, 147, 320, 20);
		if (estudiante.getUsername() != "") {
			txtUsername.setText(estudiante.getUsername());
			txtUsername.setEnabled(false);
		}
		add(txtUsername);

		cmbxCurso = new JComboBox<String>();
		cmbxCurso.setBounds(301, 118, 59, 20);
		add(cmbxCurso);

		// TODO Filtrar divisiones segun año (ej, solo hay 3 quintos pero hay 5
		// primeros)
		cmbxDivision = new JComboBox<String>();
		cmbxDivision.setBounds(370, 118, 59, 20);
		add(cmbxDivision);

		for (int i = 1; i <= 6; i++)
			cmbxCurso.addItem(Integer.toString(i));
		for (int i = 1; i <= 5; i++)
			cmbxDivision.addItem(Integer.toString(i));

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new BEstudiante(marco));
				marco.validate();
			}
		});
		btnCancelar.setBounds(230, 219, 107, 23);
		add(btnCancelar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(347, 219, 89, 23);
		add(btnAceptar);
	}

	public Estudiante makeEstudiante() {
		int DNI = control.StringFormat.DNI(txtDNI.getText());
		String nombre = control.StringFormat.stringForSQL(txtNombre.getText());
		int curso = getCurso();
		String telefono = control.StringFormat.telefono(txtTelefono.getText());
		LocalDate fechaAlta = LocalDate.now();
		String username = txtUsername.getText();

		User user = new User(username, 1);

		return new Estudiante(user, DNI, nombre, curso, telefono, fechaAlta);
	}
	
	public Estudiante makeEstudiante(Estudiante e) {
		int DNI = control.StringFormat.DNI(txtDNI.getText());
		String nombre = control.StringFormat.stringForSQL(txtNombre.getText());
		int curso = getCurso();
		String telefono = control.StringFormat.telefono(txtTelefono.getText());
		LocalDate fechaAlta = e.getFechaAlta();

		User user = e.usuario();

		return new Estudiante(user, DNI, nombre, curso, telefono, fechaAlta);
	}

	public int getCurso() {
		String año = (String) cmbxCurso.getSelectedItem();
		String division = (String) cmbxDivision.getSelectedItem();

		int curso = Integer.parseInt(año + division);

		return curso;
	}

	public boolean dataValida(JFrame marco) {
		if (!control.StringValido.DNI(txtDNI.getText())) {
			JOptionPane.showMessageDialog(marco, "Número de DNI no válido.", "DNI Inválido",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!control.StringValido.nombreApellido(txtNombre.getText())) {
			JOptionPane.showMessageDialog(marco, "Nombre y apellido no válidos", "Invalid Name",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!control.StringValido.telefono(txtTelefono.getText())) {
			JOptionPane.showMessageDialog(marco, "Número de teléfono inválido.", "Invalid phone number",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!control.StringValido.curso((String) cmbxCurso.getSelectedItem(),
				(String) cmbxDivision.getSelectedItem())) {
			JOptionPane.showMessageDialog(marco, "Ingrese un curso válido.", "Invalid", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!control.StringValido.username(txtUsername.getText())) {
			JOptionPane.showMessageDialog(marco, "Nombre de usuario no válido.", "Invalid Username",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else {
			UserDAO udao = new UserDAO();
			if(!udao.usernameDisponible(txtUsername.getText())) {
				JOptionPane.showMessageDialog(marco, "Nombre de usuario no disponible", "Occupied Username",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			udao.cerrarConexion();
		}
		return true;
	}
}
