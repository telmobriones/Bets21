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

public class GiveJackpotGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
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
	public GiveJackpotGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblErrors = new JLabel("");
		lblErrors.setForeground(Color.RED);
		lblErrors.setBounds(106, 183, 238, 15);
		contentPane.add(lblErrors);


		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(153, 210, 142, 25);
		contentPane.add(btnClose);
		
		JLabel lblJackpot = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BuyTicketsGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblJackpot.setBounds(107, 31, 148, 25);
		contentPane.add(lblJackpot);
		
		JLabel lblAmoMouney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BuyTicketsGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblAmoMouney.setBounds(267, 36, 70, 15);
		contentPane.add(lblAmoMouney);
		
		JLabel lblPlayers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BuyTicketsGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPlayers.setBounds(106, 83, 70, 15);
		contentPane.add(lblPlayers);
		
		JButton btnRaffle = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BuyTicketsGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnRaffle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.giveJackpot(20);
			}
		});
		btnRaffle.setBounds(134, 136, 179, 47);
		contentPane.add(btnRaffle);
		
		JLabel lblAmoPeople = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BuyTicketsGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblAmoPeople.setBounds(267, 83, 70, 15);
		contentPane.add(lblAmoPeople);

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeGUI();
			}
		});
	}
	
	private void closeGUI() {
		this.setVisible(false);
	}
}