package vista;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import dao.*;
import biblioteca.*;
import java.awt.Font;

public class Login extends JPanel {

	private JTextField txtUser;
	private JPasswordField passwordField;
	private int w = 440;
	private int h = 255;
	private int x = AppMain.x-(w/2);
	private int y = AppMain.y-(h/2);

	public Login(JFrame marco) {
		// SETTINGS
		marco.setTitle("Login");
		marco.setBounds(x, y, w+15, h+30);
		setBounds(0, 0, w, h);
		setLayout(null);

		JLabel lblUser = new JLabel("User:");
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setBounds(112, 97, 46, 14);
		add(lblUser);

		txtUser = new JTextField();
		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!control.CharValido.username(ch))
					e.consume();
			}
		});

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(82, 125, 76, 14);
		add(lblPassword);

		JLabel lnlAviso = new JLabel("\u00BFNo est\u00E1s registrado/a? Acercate a la biblioteca");
		lnlAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lnlAviso.setBounds(10, 202, 418, 14);
		add(lnlAviso);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				UserDAO udao = new UserDAO();

				AppMain.user = udao.logIn(txtUser.getText(), passwordField.getText());
				if (AppMain.user != null) {
					switch (AppMain.user.getTipoUsuario()) {
					case 1:
						EstudianteDAO edao = new EstudianteDAO();
						AppMain.user = edao.estudianteFromUser(AppMain.user);
						AppMain.marco.setContentPane(new MarcoEstudiante());
						AppMain.marco.setContentPane(new HomeEstudiante(marco));
						edao.cerrarConexion();
						break;
					case 2:
						AppMain.marco.setContentPane(new MarcoBibliotecario());
						AppMain.marco.setContentPane(new HomeBibliotecario(marco));
						break;
					case 3:
						AppMain.marco.setContentPane(new HomeAdmin(marco));
						break;
					}
					marco.validate();
				} else {
					JOptionPane.showMessageDialog(marco,
							"No se pudo confirmar su información. Revise su username y password.", "Log In Error",
							JOptionPane.ERROR_MESSAGE);
				}
				udao.cerrarConexion();

			}
		});
		btnLogin.setBounds(239, 153, 89, 23);

		add(btnLogin);
		txtUser.setBounds(174, 94, 154, 20);
		add(txtUser);
		txtUser.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!control.CharValido.password(ch))
					e.consume();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnLogin.doClick();
			}
		});
		passwordField.setBounds(174, 122, 154, 20);
		add(passwordField);
		
		JLabel lblSistemaDeBiblioteca = new JLabel("SISTEMA DE BIBLIOTECA ET37");
		lblSistemaDeBiblioteca.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		lblSistemaDeBiblioteca.setHorizontalAlignment(SwingConstants.CENTER);
		lblSistemaDeBiblioteca.setBounds(10, 33, 418, 14);
		add(lblSistemaDeBiblioteca);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(55, 58, 333, 2);
		add(separator);
	}
}
