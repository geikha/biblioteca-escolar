package vista.abm;

import javax.swing.*;

import biblioteca.User;
import dao.UserDAO;
import vista.AppMain;
import vista.HomeAdmin;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class ABMUser extends JPanel {
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JComboBox<String> cmbxTipoUsuario;
	private int w = 450;
	private int h = 190;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);

	public ABMUser(JFrame marco) {
		// SETTINGS
		marco.setTitle("Nuevo usuario");
		marco.setBounds(x, y, w+15, h+30);
		setBounds(0, 0, w, h);
		setLayout(null);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 41, 419, 2);
		add(separator);

		JLabel lblNuevoUsuario = new JLabel("Nuevo Usuario");
		lblNuevoUsuario.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		lblNuevoUsuario.setBounds(10, 11, 193, 20);
		add(lblNuevoUsuario);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 52, 89, 14);
		add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!control.CharValido.username(e.getKeyChar()) & e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					e.consume();
			}
		});
		txtUsername.setColumns(10);
		txtUsername.setBounds(109, 52, 320, 20);
		add(txtUsername);

		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(109, 77, 320, 20);
		add(txtPassword);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 80, 89, 14);
		add(lblPassword);

		JLabel lblTipoDeUsuario = new JLabel("Tipo de Usuario:");
		lblTipoDeUsuario.setBounds(10, 106, 89, 14);
		add(lblTipoDeUsuario);

		cmbxTipoUsuario = new JComboBox<String>();
		cmbxTipoUsuario.setBounds(109, 103, 320, 20);
		cmbxTipoUsuario.addItem("Bibliotecarix");
		cmbxTipoUsuario.addItem("Admin");
		add(cmbxTipoUsuario);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new HomeAdmin(marco));
				marco.validate();
			}
		});
		btnCancelar.setBounds(228, 156, 106, 23);
		add(btnCancelar);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataValida(marco)) {
					UserDAO udao = new UserDAO();
					String password = txtPassword.getText();
					udao.alta(makeUser(), password);
					udao.cerrarConexion();
					marco.setContentPane(new HomeAdmin(marco));
					marco.validate();
				}
			}
		});
		btnAceptar.setBounds(344, 156, 89, 23);
		add(btnAceptar);
	}

	public boolean dataValida(JFrame marco) {
		if (!control.StringValido.username(txtUsername.getText())) {
			JOptionPane.showMessageDialog(marco, "Nombre de usuario no válido.", "Invalid Username",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!control.StringValido.password(txtPassword.getText())) {
			JOptionPane.showMessageDialog(marco,
					"Contraseña invalida. Las contraseñas deben tener por lo menos 6 caracteres", "Invalid Password",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public User makeUser() {
		String username = txtUsername.getText();
		int tipoUsuario = cmbxTipoUsuario.getSelectedIndex() + 2;
		return new User(username, tipoUsuario);
	}
}
