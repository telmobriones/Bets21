package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Lottery;
import domain.User;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.util.ResourceBundle;
import businessLogic.BLFacade;

public class BuyTicketsGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	private static LoginGUI frame = new LoginGUI();
	private User loggedUser;
	private int lotteryID;
	private boolean canPlay;
	JLabel lblErrors;
	JButton btnClose;
	JLabel lblJackpot;
	JLabel lblAmoMoney;
	JLabel lblPlayers;
	JButton btnBuyTickets;
	JLabel lblAmoPeople;
	JLabel lblLotID;


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
	public BuyTicketsGUI() {
		this.loggedUser = facade.getLogUser();
		lotteryID = facade.getLastActiveLotteryID();
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
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login")); //$NON-NLS-1$ //$NON-NLS-2$
		this.setBounds(100, 100, 450, 282);
		setContentPane(getJContentPane());
		redibujar(lotteryID);
	}

	private JPanel getJContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(null);
			contentPane.add(getlblAmoMoney());
			contentPane.add(getlblAmoPeople());
			contentPane.add(getlblError());
			contentPane.add(getlblJackpot());
			contentPane.add(getlblPlayers());
			contentPane.add(getbtnBuyTickets());
			contentPane.add(getbtnClose());
			contentPane.add(getlblLotteryID());


		}
		return contentPane;
	}



	private JLabel getlblError() {
		if(lblErrors == null) {
			lblErrors = new JLabel();
			lblErrors.setForeground(Color.RED);
			lblErrors.setBounds(36, 110, 370, 25);
		}
		return lblErrors;
	}

	private JButton getbtnClose() {
		if(btnClose == null) {
			btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
			btnClose.setBounds(153, 210, 142, 25);
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeGUI();
				}
			});
		}
		return btnClose;
	}

	private JLabel getlblJackpot() {
		if(lblJackpot == null) {

			lblJackpot = new JLabel("Jackpot");
			lblJackpot.setBounds(107, 31, 148, 25);
		}
		return lblJackpot;
	}

	private JLabel getlblAmoMoney(){
		if(lblAmoMoney == null) {
			lblAmoMoney = new JLabel();
			lblAmoMoney.setBounds(267, 36, 70, 15);
		}
		return lblAmoMoney;
	}

	private JLabel getlblPlayers() {
		if(lblPlayers == null) {
			lblPlayers = new JLabel("Players: ");
			lblPlayers.setBounds(106, 83, 70, 15);
		}
		return lblPlayers;
	}

	private JButton getbtnBuyTickets() {
		if(btnBuyTickets == null) {
			btnBuyTickets = new JButton();
			btnBuyTickets.setBounds(134, 136, 179, 47);
			btnBuyTickets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					canPlay = facade.buyTicket(loggedUser.getUsername(), lotteryID, "Ticket purchased");
					redibujar(lotteryID);
					if (canPlay == false) {
						lblErrors.setText("You have alredy played this lottery");
					}
				}
			});
		}
		return btnBuyTickets;
	}

	private JLabel getlblAmoPeople() {
		if(lblAmoPeople == null) {
			lblAmoPeople = new JLabel();
			lblAmoPeople.setBounds(267, 83, 70, 15);
		}
		return lblAmoPeople;
	}
	private JLabel getlblLotteryID(){
		if(lblLotID == null) {
			lblLotID = new JLabel();
			lblLotID.setBounds(25, 13, 96, 15);
		}
		return lblLotID;
	}

	public void redibujar(int lotID) {
		System.out.println("The lottery ID is: " + lotID);
		Lottery lot = facade.getLotteryByID(lotID);
		if (lot != null) {
			lblAmoPeople.setText(String.valueOf(lot.getParticipantsNumber()));
			lblAmoMoney.setText(String.valueOf(lot.getJackpot()));
			lblLotID.setText(String.valueOf(lot.getLotteryID()));

		}
		else {
			btnBuyTickets.setVisible(false);
			lblAmoPeople.setVisible(false);
			lblAmoMoney.setVisible(false);
			lblLotID.setVisible(false);
			lblJackpot.setVisible(false);
			lblPlayers.setVisible(false);
			lblErrors.setVisible(true);
			lblErrors.setText("No lotteries available");
		}
	}

	private void closeGUI() {
		this.setVisible(false);
	}
}