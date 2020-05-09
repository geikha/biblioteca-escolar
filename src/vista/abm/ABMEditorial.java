package vista.abm;

import javax.swing.*;

import dao.AutorxDAO;
import dao.EditorialDAO;
import vista.AppMain;
import vista.Login;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ABMEditorial extends JFrame {
	private JTextField textField;

	public interface EditorialListener {
		public void editorialCreada(String nombre);
	}

	private int x = AppMain.x - (410 / 2);
	private int y = AppMain.y - (171 / 2);
	private JButton btnAceptar;

	public ABMEditorial(EditorialListener callback) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 410, 172);
		getContentPane().setLayout(null);

		JLabel lblNuevaEditorial = new JLabel("Nueva Editorial:");
		lblNuevaEditorial.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNuevaEditorial.setBounds(12, 12, 147, 26);
		getContentPane().add(lblNuevaEditorial);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textField.getText();
				nombre = control.StringFormat.stringForSQL(nombre);
				EditorialDAO edao = new EditorialDAO();
				edao.alta(nombre);
				edao.cerrarConexion();
				callback.editorialCreada(nombre);
				dispose();
			}
		});
		btnAceptar.setBounds(264, 94, 114, 25);
		getContentPane().add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(138, 94, 114, 25);
		getContentPane().add(btnCancelar);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnAceptar.doClick();
				else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_LEFT
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
				}
			}
		});
		textField.setBounds(22, 50, 356, 32);
		getContentPane().add(textField);
		textField.setColumns(10);
		setVisible(true);
		validate();
	}
}
