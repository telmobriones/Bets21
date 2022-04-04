package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import domain.User;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ResourceBundle;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JPasswordField jPassword;
	private JTextField jUsername;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	private static LoginGUI frame = new LoginGUI();

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
	public LoginGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblUsername.setBounds(5, 12, 440, 53);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblUsername);

		jUsername = new JTextField();
		jUsername.setBounds(91, 58, 274, 29);
		contentPane.add(jUsername);
		jUsername.setColumns(10);

		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword.setBounds(5, 99, 440, 53);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblPassword);

		jPassword = new JPasswordField();
		jPassword.setBounds(96, 142, 269, 29);
		contentPane.add(jPassword);
		jPassword.setColumns(10);

		JLabel lblIfYouDont = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterText")); //$NON-NLS-1$ //$NON-NLS-2$
		lblIfYouDont.setBounds(55, 289, 347, 15);
		contentPane.add(lblIfYouDont);
		
		JLabel lblErrors = new JLabel("");
		lblErrors.setForeground(Color.RED);
		lblErrors.setBounds(106, 183, 238, 15);
		contentPane.add(lblErrors);

		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register")); //$NON-NLS-1$ //$NON-NLS-2$
		btnRegister.setBounds(266, 284, 136, 25);
		contentPane.add(btnRegister);
		
		JButton btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		btnLogin.setBounds(139, 210, 184, 37);
		boolean alreadyLogged = facade.checkCurrentLoginStatus();
		contentPane.add(btnLogin);
		if(alreadyLogged) {
			btnLogin.setEnabled(false);
			lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("AlreadyLogged"));
		} else {
			btnLogin.setEnabled(true);
		}

		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(168, 349, 117, 25);
		contentPane.add(btnClose);

		// Akzioak

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = jUsername.getText();
				char[] password = jPassword.getPassword();
				if (jUsername.getText().length() != 0 && password.length != 0) {

					User loggedUser = facade.checkCredentials(username, password);

					if (loggedUser != null) {
						btnLogin.setEnabled(false);
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("AlreadyLogged"));
						if(loggedUser.isUserAdmin()) {
							MainAdminGUI adminGUI = new MainAdminGUI();
							adminGUI.setVisible(true);
							closeLogin();
						}
						else {
							MainLoggedGUI loggedGUI = new MainLoggedGUI(loggedUser);
							loggedGUI.setVisible(true);
							closeLogin();
						}
						
					} else {
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("WrongCredentials"));
					}
				} else {
					lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyField"));
				}
			}
		});

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI ru = new RegisterGUI();
				ru.setVisible(true);
				closeLogin();
			}
		});

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI mainGUI = new MainGUI();
				mainGUI.setVisible(true);
				closeLogin();
			}
		});
	}
	
	private void closeLogin() {
		this.setVisible(false);
	}
}