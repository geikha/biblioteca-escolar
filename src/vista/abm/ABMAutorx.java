package vista.abm;

import javax.swing.*;
import dao.AutorxDAO;
import vista.AppMain;
import vista.Login;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ABMAutorx extends JFrame{
	private JTextField textField;
	private JButton btnAceptar;

	public interface AutorListener {
		public void autorxCreado(String nombre);
	}
	
	private int x = AppMain.x-(410/2);
	private int y = AppMain.y-(171/2);
	
	public ABMAutorx(AutorListener callback) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 410, 171);
		getContentPane().setLayout(null);
		
		JLabel lblNuevxAutorx = new JLabel("Nuevx Autorx:");
		lblNuevxAutorx.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNuevxAutorx.setBounds(12, 12, 147, 26);
		getContentPane().add(lblNuevxAutorx);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textField.getText();
				AutorxDAO audao = new AutorxDAO();
				nombre = control.StringFormat.stringForSQL(nombre);
				audao.alta(nombre);
				audao.cerrarConexion();
				callback.autorxCreado(nombre);
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
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAceptar.doClick();
				}
				else if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE || arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					
				}
				else if(!control.CharValido.nombreDePersona(arg0.getKeyChar())) {
					arg0.consume();
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
