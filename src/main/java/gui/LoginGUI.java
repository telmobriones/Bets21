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

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField password;
	private JTextField username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblNewLabel.setBounds(5, 12, 440, 53);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblNewLabel);
		
		username = new JTextField();
		username.setBounds(91, 58, 274, 29);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblNewLabel_1.setBounds(5, 99, 440, 53);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblNewLabel_1);
		
		password = new JPasswordField();
		password.setBounds(96, 142, 269, 29);
		contentPane.add(password);
		password.setColumns(10);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		btnNewButton.setBounds(138, 201, 184, 37);
		contentPane.add(btnNewButton);
		
		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register")); //$NON-NLS-1$ //$NON-NLS-2$
		btnRegister.setBounds(266, 284, 117, 25);
		contentPane.add(btnRegister);
		
		JLabel lblIfYouDont = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterText")); //$NON-NLS-1$ //$NON-NLS-2$
		lblIfYouDont.setBounds(55, 289, 220, 15);
		contentPane.add(lblIfYouDont);
	}
}