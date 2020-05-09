package vista.abm;

import javax.swing.*;
import biblioteca.*;
import dao.UserDAO;
import vista.AppMain;
import vista.HomeBibliotecario;
import vista.HomeEstudiante;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ABMCambiarContraseña extends JPanel {
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JPasswordField txtNewPassword;
	private JPasswordField txtNewPassword2;
	JButton btnAceptar;

	User user = AppMain.user;

	private int w = 535;
	private int h = 220;
	private int x = AppMain.x - (w / 2);
	private int y = AppMain.y - (h / 2);

	public ABMCambiarContraseña(JFrame marco) {
		// SETTINGS
		marco.setTitle("Cambiar username y contraseña");
		marco.setBounds(x, y, w + 15, h + 50);
		setBounds(0, 0, w, h);
		setLayout(null);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 41, 506, 2);
		add(separator);

		JLabel lblUsername = new JLabel("Nuevo Username:");
		lblUsername.setBounds(10, 57, 123, 14);
		add(lblUsername);

		JLabel lblContrasea = new JLabel("Contraseña Actual:");
		lblContrasea.setBounds(10, 85, 133, 14);
		add(lblContrasea);

		JLabel lblNuevaContrasea = new JLabel("Nueva contrase\u00F1a:");
		lblNuevaContrasea.setBounds(10, 116, 131, 14);
		add(lblNuevaContrasea);

		JLabel label = new JLabel("Nueva contrase\u00F1a:");
		label.setBounds(10, 147, 133, 14);
		add(label);

		JLabel lblCambiarUsuarioY = new JLabel("Cambiar Usuario y Contrase\u00F1a");
		lblCambiarUsuarioY.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		lblCambiarUsuarioY.setBounds(10, 11, 309, 20);
		add(lblCambiarUsuarioY);

		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!control.CharValido.username(e.getKeyChar()) & e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					e.consume();
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToMenu(marco);
			}
		});
		btnCancelar.setBounds(310, 172, 107, 23);
		add(btnCancelar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (dataValida(marco)) {
					UserDAO udao = new UserDAO();
					if (user.equals(udao.logIn(txtUsername.getText(), txtPassword.getText()))) {
						udao.modificacion(user, new User(txtUsername.getText(), user.getTipoUsuario()));
						udao.cambiarContraseña(user, txtNewPassword.getText());
						goToMenu(marco);
					} else {
						JOptionPane.showMessageDialog(marco,
								"No se pudo confirmar su información. Revise su username y password.", "Log In Error",
								JOptionPane.ERROR_MESSAGE);
					}
					udao.cerrarConexion();
				}
			}
		});
		btnAceptar.setBounds(427, 172, 89, 23);
		add(btnAceptar);
		txtUsername.setBounds(161, 55, 329, 20);
		txtUsername.setText(user.getUsername());
		add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(161, 83, 329, 20);
		add(txtPassword);

		txtNewPassword = new JPasswordField();
		txtNewPassword.setColumns(10);
		txtNewPassword.setBounds(161, 114, 329, 20);
		add(txtNewPassword);

		txtNewPassword2 = new JPasswordField();
		txtNewPassword2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnAceptar.doClick();
			}
		});
		txtNewPassword2.setColumns(10);
		txtNewPassword2.setBounds(161, 145, 329, 20);
		add(txtNewPassword2);

		JLabel lblUsuarioActual = new JLabel("Usuario actual: ");
		lblUsuarioActual.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblUsuarioActual.setBounds(10, 181, 75, 14);
		add(lblUsuarioActual);

		JLabel lblUsernameActual = new JLabel("<dynamic>");
		lblUsernameActual.setText(user.getUsername());
		lblUsernameActual.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblUsernameActual.setBounds(87, 181, 213, 14);
		add(lblUsernameActual);
	}

	public void goToMenu(JFrame marco) {
		switch (user.getTipoUsuario()) {
		case 1:
			marco.setContentPane(new HomeEstudiante(marco));
			break;
		case 2:
			marco.setContentPane(new HomeBibliotecario(marco));
			break;
		}
		marco.validate();
	}

	@SuppressWarnings("deprecation")
	public boolean dataValida(JFrame marco) {
		if (!control.StringValido.username(txtUsername.getText())) {
			JOptionPane.showMessageDialog(marco, "Nombre de usuario no válido.", "Invalid Username",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!txtNewPassword.getText().contentEquals(txtNewPassword2.getText())) {
			JOptionPane.showMessageDialog(marco, "La nueva contraseña debe ser igual en ambos campos.",
					"Non-Matching Passwords", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!control.StringValido.password(txtNewPassword.getText())) {
			JOptionPane.showMessageDialog(marco,
					"Nueva contraseña invalida. Las contraseñas deben tener por lo menos 6 caracteres",
					"Invalid Password", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
}
