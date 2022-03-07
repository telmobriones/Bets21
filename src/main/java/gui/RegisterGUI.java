package gui;

import java.util.Arrays;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.SwingConstants;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JPasswordField jPassword1;
	private JPasswordField jPassword2;
	private JTextField jUsername;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	private static RegisterGUI frame = new RegisterGUI();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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

		JLabel lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblUsername.setBounds(12, 30, 140, 53);
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblUsername);

		jUsername = new JTextField();
		jUsername.setBounds(169, 42, 218, 29);
		contentPane.add(jUsername);
		jUsername.setColumns(10);

		JLabel lblPassword1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword1.setBounds(12, 83, 140, 53);
		lblPassword1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblPassword1);

		jPassword1 = new JPasswordField();
		jPassword1.setBounds(169, 95, 218, 29);
		contentPane.add(jPassword1);
		jPassword1.setColumns(10);

		JLabel lblPassword2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword2.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblPassword2.setBounds(12, 136, 140, 53);
		contentPane.add(lblPassword2);

		jPassword2 = new JPasswordField();
		jPassword2.setColumns(10);
		jPassword2.setBounds(169, 148, 218, 29);
		contentPane.add(jPassword2);

		JLabel lblErrors = new JLabel("");
		lblErrors.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrors.setForeground(Color.RED);
		lblErrors.setBounds(92, 222, 269, 15);
		contentPane.add(lblErrors);

		JButton registerButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		registerButton.setBounds(152, 249, 142, 29);
		contentPane.add(registerButton);

		// Akzioak

		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = jUsername.getText();
				char[] password = jPassword1.getPassword();
				if (Arrays.equals(password, jPassword2.getPassword())) {
					boolean regSuccess = facade.registerUser(username, password);
					if(!regSuccess) {
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("TakenUsername"));
					} else {
						registerButton.setEnabled(false);
						Arrays.fill(password, '0');
						frame.setVisible(false);
					}
				} else {
					lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("PasswordMissmatch"));
				}
			}
		});

	}
}