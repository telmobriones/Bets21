package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Movement;
import domain.Pronostic;
import domain.User;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TopUpBalanceGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFieldAmount;
	private User loggedUser;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	
	JLabel lblUsername = null;
	JLabel lblLoggedUsername = null;
	JLabel lblCurrentBalance  = null;
	JLabel lblCurrentBalanceAmount = null;
	JLabel lblMoney = null;
	JButton btnTopUp = null;
	JButton jButtonClose = null;
	

	/**
	 * This is the default constructor
	 */
	public TopUpBalanceGUI() {
		super();
		this.loggedUser = facade.getLogUser();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		initialize();
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TopUpBalance"));
		this.setBounds(100, 100, 450, 291);
		setContentPane(getJContentPane());
		
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
		
//		lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User") + ":");
//		lblUsername.setBounds(48, 12, 86, 15);
//		contentPane.add(lblUsername);
//		
//		lblLoggedUsername = new JLabel(loggedUser.getUsername());
//		lblLoggedUsername.setBounds(156, 12, 70, 15);
//		contentPane.add(lblLoggedUsername);
		
//		lblCurrentBalance = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CurrentBalance") + ":");
//		lblCurrentBalance.setBounds(48, 62, 197, 15);
//		contentPane.add(lblCurrentBalance);
		
//		lblCurrentBalanceAmount = new JLabel(loggedUser.getBalance() + "€");
//		lblCurrentBalanceAmount.setBounds(257, 62, 70, 15);
//		contentPane.add(lblCurrentBalanceAmount);
		
//		lblMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AmountToBeAdded")+" (in €) :");
//		lblMoney.setBounds(48, 114, 197, 15);
//		contentPane.add(lblMoney);
		
//		txtFieldAmount = new JTextField();
//		txtFieldAmount.setBounds(290, 109, 114, 25);
//		txtFieldAmount.setColumns(10);
//		txtFieldAmount.addKeyListener(new KeyAdapter() {
//		    public void keyTyped(KeyEvent e) {
//		        char c = e.getKeyChar();
//		        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_MINUS)) {
//		            e.consume();  // if it's not a number, ignore the event
//		        }
//		     }
//		});
//		contentPane.add(txtFieldAmount);
		
		
//		btnTopUp = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TopUpBalance"));
//		btnTopUp.setBounds(48, 156, 356, 44);
//		btnTopUp.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvegettxtFieldAmount()nt e) {
//				int money2add = Integer.parseInt(txtFieldAmount.getText());
//				int newBalance = facade.updateBalance(loggedUser, money2add);
//				btnTopUp.setEnabled(false);
//				btnTopUp.setText("NewBalance: " + newBalance);
//				redibujar();
//			}
//		});
//		contentPane.add(btnTopUp);
		
//		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
//		jButtonClose.setBounds(166, 220, 117, 25);
//		jButtonClose.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				closeTopUpBalance();
//			}
//		});
//		contentPane.add(jButtonClose);
	}
	
	private JPanel getJContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setLayout(null);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.add(getlblUsername());
			contentPane.add(getlblLoggedUsername());
			contentPane.add(getlblCurrentBalance());
			contentPane.add(getlblCurrentBalanceAmount());
			contentPane.add(getlblMoney());
			contentPane.add(gettxtFieldAmount());
			contentPane.add(getbtnTopUp());
			contentPane.add(getjButtonClose());
		}
		return contentPane;
	}
	
	private JLabel getlblUsername() {
		if (lblUsername == null) {
			lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User") + ":");
			lblUsername.setBounds(48, 12, 86, 15);
		}
		return lblUsername;
	}
	
	private JLabel getlblLoggedUsername() {
		if (lblLoggedUsername == null) {
			lblLoggedUsername = new JLabel(facade.getLogUser().getUsername());
			lblLoggedUsername.setBounds(156, 12, 70, 15);
		}
		return lblLoggedUsername;
	}
	
	private JLabel getlblCurrentBalance() {
		if(lblCurrentBalance == null) {
			lblCurrentBalance = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CurrentBalance") + ":");
			lblCurrentBalance.setBounds(48, 62, 197, 15);
		}
		return lblCurrentBalance;
	}
	
	private JLabel getlblCurrentBalanceAmount() {
		if(lblCurrentBalanceAmount == null) {
			lblCurrentBalanceAmount = new JLabel(facade.getLogUser().getBalance() + "€");
			lblCurrentBalanceAmount.setBounds(257, 62, 70, 15);
		}
		return lblCurrentBalanceAmount;
	}
	
	private JLabel getlblMoney() {
		if(lblMoney == null) {
			lblMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AmountToBeAdded")+" (in €) :");
			lblMoney.setBounds(48, 114, 197, 15);
		}
		return lblMoney;
	}
	
	private JTextField gettxtFieldAmount() {
		if(txtFieldAmount == null) {
			txtFieldAmount = new JTextField();
			txtFieldAmount.setBounds(290, 109, 114, 25);
			txtFieldAmount.setColumns(10);
			txtFieldAmount.addKeyListener(new KeyAdapter() {
			    public void keyTyped(KeyEvent e) {
			        char c = e.getKeyChar();
			        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_MINUS)) {
			            e.consume();  // if it's not a number, ignore the event
			        }
			     }
			});
		}
		return txtFieldAmount;
	}
	
	private JButton getbtnTopUp() {
		if(btnTopUp == null) {
			btnTopUp = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TopUpBalance"));
			btnTopUp.setBounds(48, 156, 356, 44);
			btnTopUp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int money2add = Integer.parseInt(txtFieldAmount.getText());
					Movement movement = facade.createMovement("TopUp", money2add, loggedUser, null, null);
					facade.updateMovement(loggedUser, movement);
					float newBalance = facade.updateBalance(loggedUser, money2add);
					btnTopUp.setEnabled(false);
					btnTopUp.setText("NewBalance: " + newBalance);
					lblCurrentBalanceAmount.setText(newBalance + "€");
					redibujar();
				}
			});
		}
		return btnTopUp;
	}

	private JButton getjButtonClose() {
		if(jButtonClose == null) {
			jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
			jButtonClose.setBounds(166, 220, 117, 25);
			jButtonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeTopUpBalance();
				}
			});
		}
		return jButtonClose;
	}
	
	private void redibujar() {
		this.lblUsername.setText(ResourceBundle.getBundle("Etiquetas").getString("User") + ":");
		lblLoggedUsername.setText(loggedUser.getUsername());
		lblCurrentBalance.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrentBalance") + ":");
		lblCurrentBalanceAmount.setText(facade.getLogUser().getBalance() + "€");
		lblMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountToBeAdded")+" (in €) :");
		jButtonClose.setText(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("TopUpBalance"));
	}
	private void closeTopUpBalance() {
		this.setVisible(false);
	}
}
