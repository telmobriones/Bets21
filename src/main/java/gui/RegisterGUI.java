package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ResourceBundle;
import javax.swing.SwingConstants;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField passwordField;
	private JTextField usernameField;
	private JPasswordField password2Field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Register")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblNewLabel.setBounds(12, 30, 140, 53);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblNewLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(169, 42, 218, 29);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblNewLabel_1.setBounds(12, 83, 140, 53);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(169, 95, 218, 29);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(12, 136, 140, 53);
		contentPane.add(lblNewLabel_1_1);
		
		password2Field = new JPasswordField();
		password2Field.setColumns(10);
		password2Field.setBounds(169, 148, 218, 29);
		contentPane.add(password2Field);
		
		JButton registerButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		registerButton.setBounds(152, 249, 142, 29);
		contentPane.add(registerButton);
		
		
	}
}