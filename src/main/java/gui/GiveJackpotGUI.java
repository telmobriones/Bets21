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
import javax.swing.table.DefaultTableModel;

import domain.Lottery;
import domain.User;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import businessLogic.BLFacade;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Rectangle;

public class GiveJackpotGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	private static LoginGUI frame = new LoginGUI();
	private int lotteryID;
	private int newLotteryID;
	private String winner;
	JLabel lblErrors;
	JButton btnClose;
	JButton btnGiveJackpot;
	JLabel lblJackpot;
	JLabel lblAmoMoney;
	JLabel lblPlayers;
	JButton btnNewLottery;
	JLabel lblAmoPeople;
	JTextField textField;
	private JLabel lblNewLabel;
	private JLabel lblNextLottery;
	private JScrollPane scrollPanePlayers;
	private JLabel lblLotID;

	private JTable tablePlayers = new JTable();
	private String[] columNamesPlayers = new String[] {
			"Username"
	};
	private DefaultTableModel tableModelPlayers;
	private JLabel lblWinner;
	private JLabel lblNewLabel_1;


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
		lotteryID = facade.getLastActiveLotteryID();
		System.out.println("The lotteryID is: "+lotteryID);
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
		this.setBounds(100, 100, 695, 406);
		setContentPane(getJContentPane());
		redibujar(lotteryID);
		lblNewLabel.setVisible(false);
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
			contentPane.add(getbtnNewLottery());
			contentPane.add(getbtnClose());
			contentPane.add(gettxtField());
			contentPane.add(getLblNewLabel());
			contentPane.add(getLblNextLottery());
			contentPane.add(getScrollPaneMovements());
			contentPane.add(getlblLotteryID());
			contentPane.add(getbtnGiveJackpot());
			contentPane.add(getLblWinner());
			contentPane.add(getLblNewLabel_1());


		}
		return contentPane;
	}


	private JLabel getlblError() {
		if(lblErrors == null) {
			lblErrors = new JLabel("");
			lblErrors.setForeground(Color.RED);
			lblErrors.setBounds(87, 183, 238, 15);
		}
		return lblErrors;
	}

	private JButton getbtnClose() {
		if(btnClose == null) {
			btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
			btnClose.setBounds(529, 333, 142, 25);
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
			lblJackpot.setBounds(107, 42, 148, 25);
		}
		return lblJackpot;
	}

	private JLabel getlblAmoMoney(){
		if(lblAmoMoney == null) {
			lblAmoMoney = new JLabel();
			lblAmoMoney.setBounds(267, 47, 70, 15);
		}
		return lblAmoMoney;
	}
	private JLabel getlblLotteryID(){
		if(lblLotID == null) {
			lblLotID = new JLabel();
			lblLotID.setBounds(25, 13, 96, 15);
		}
		return lblLotID;
	}

	private JLabel getlblPlayers() {
		if(lblPlayers == null) {
			lblPlayers = new JLabel("Players: ");
			lblPlayers.setBounds(107, 93, 70, 15);
		}
		return lblPlayers;
	}

	private JButton getbtnNewLottery() {
		if(btnNewLottery == null) {
			btnNewLottery = new JButton();
			btnNewLottery.setText("Create new lottery");
			btnNewLottery.setBounds(218, 300, 179, 47);
			btnNewLottery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String price = textField.getText();
					newLotteryID = facade.createLottery(Integer.valueOf(price));
					tableModelPlayers.setRowCount(0);
					lblLotID.setText("");
					lblAmoMoney.setText("");
					lblAmoPeople.setText("");
					btnNewLottery.setEnabled(false);
					btnGiveJackpot.setEnabled(false);
					contentPane.remove(lblWinner);
				}
			});
		}
		return btnNewLottery;
	}

	private JButton getbtnGiveJackpot() {
		if(btnGiveJackpot == null) {
			btnGiveJackpot = new JButton();
			btnGiveJackpot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					winner = facade.giveJackpot(lotteryID);
					countDown();
					redibujar(lotteryID);
					btnNewLottery.setEnabled(true);
					btnGiveJackpot.setEnabled(false);

				}
			});

			btnGiveJackpot.setText("Give Jackpot");
			btnGiveJackpot.setBounds(96, 248, 159, 40);

		}
		return btnGiveJackpot;
	}


	private JLabel getlblAmoPeople() {
		if(lblAmoPeople == null) {
			lblAmoPeople = new JLabel();
			lblAmoPeople.setBounds(267, 93, 70, 15);
		}
		return lblAmoPeople;
	}

	private JTextField gettxtField() {
		if(textField == null) {
			textField = new JTextField();
			textField.setText("20");
			textField.setFont(new Font("Dialog", Font.PLAIN, 18));
			textField.setBounds(409, 300, 62, 47);
		}
		return textField;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("€");
			lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 22));
			lblNewLabel.setBounds(470, 309, 32, 25);
		}
		return lblNewLabel;
	}

	private void closeGUI() {
		this.setVisible(false);
	}
	private JLabel getLblNextLottery() {
		if (lblNextLottery == null) {
			lblNextLottery = new JLabel("Next lottery price");
			lblNextLottery.setBounds(406, 273, 96, 15);
		}
		return lblNextLottery;
	}

	private JScrollPane getScrollPaneMovements() {
		if (scrollPanePlayers == null) {
			scrollPanePlayers = new JScrollPane();
			scrollPanePlayers.setBounds(new Rectangle(40, 274, 406, 160));
			scrollPanePlayers.setBounds(355, 12, 304, 249);

			scrollPanePlayers.setViewportView(tablePlayers);
			tableModelPlayers = new DefaultTableModel(null, columNamesPlayers);
			tablePlayers.setModel(tableModelPlayers);
			tablePlayers.getColumnModel().getColumn(0).setPreferredWidth(20);

			if (lotteryID != -1) {

				ArrayList<User> players = facade.getPlayersLottery(lotteryID);
				for (User pl:players) {
					Vector<Object> row = new Vector<Object>();
					row.add(pl.getUsername());
					tableModelPlayers.addRow(row);
				}
			}

		}
		return scrollPanePlayers;
	}


	private JLabel getLblWinner() {
		if (lblWinner == null) {
			lblWinner = new JLabel();
			lblWinner.setForeground(Color.MAGENTA);
			lblWinner.setFont(new Font("Dialog", Font.BOLD, 37));
			lblWinner.setBounds(107, 142, 259, 63);
		}
		return lblWinner;

	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel();
			lblNewLabel_1.setForeground(Color.RED);
			lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 120));
			lblNewLabel_1.setBounds(131, 122, 124, 103);
			lblNewLabel.setVisible(false);
		}
		return lblNewLabel_1;
	}

	public void redibujar(int lotID) {
		Lottery lot = facade.getLotteryByID(lotID);
		if (lot != null) {
			if(lot.getParticipantsNumber()==0) {
				btnGiveJackpot.setEnabled(false);
				btnGiveJackpot.setText("No players");
				btnNewLottery.setEnabled(false);
			}
			else{
				lblAmoPeople.setText(String.valueOf(lot.getParticipantsNumber()));
				lblAmoMoney.setText(String.valueOf(lot.getJackpot()));
				lblLotID.setText(String.valueOf(lot.getLotteryID()));
				btnNewLottery.setEnabled(false);
				btnGiveJackpot.setEnabled(true);
			}

		}
	}
	/**
	 * This method creates a countDown
	 */
	public void countDown() {
		new Thread() {
			int counter = 3;
			public void run() {
				while(counter >= 0) {
					if(counter == 0) {
						lblNewLabel_1.setText(""); 
						lblWinner.setText(winner);
					}
					else lblNewLabel_1.setText("" + (counter--));
					try{
						Thread.sleep(1000);
					} catch(Exception e) {}
				}

			}
		}.start();

	}
}
