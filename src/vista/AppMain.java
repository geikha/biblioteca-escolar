
package vista;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import biblioteca.User;
import control.StringFormat;

public class AppMain {
	
	public static JFrame marco;
	public static User user;
	
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int x = (int) screenSize.getWidth()/2;
	public static int y = (int) screenSize.getHeight()/2;

	public static void main(String[] args) {
		marco = new JFrame();
		
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setBounds(0, 0, 665, 550);	
		marco.setVisible(true);
		marco.setContentPane(new Login(marco));
		marco.setResizable(false);
        marco.validate();
	}
	
}
