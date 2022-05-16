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
	private Lottery lottery;
	private String winner;
	private JLabel lblErrors;
	private JButton btnClose;
	private JButton btnGiveJackpot;
	private JLabel lblJackpot;
	private JLabel lblAmoMoney;
	private JLabel lblPlayers;
	private JButton btnNewLottery;
	private JLabel lblAmoPeople;
	private JTextField textField;
	private JLabel lblEuro;
	private JLabel lblNextLottery;
	private JScrollPane scrollPanePlayers;
	private JLabel lblLotID;
	private JLabel lblID;

	private JTable tablePlayers = new JTable();
	private String[] columNamesPlayers = new String[] { ResourceBundle.getBundle("Etiquetas").getString("User") };
	private DefaultTableModel tableModelPlayers;
	private JLabel lblWinner;
	private JLabel lblCountDown;

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		lottery = facade.getLastActiveLottery();
		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("GiveJackpot"));
		this.setBounds(100, 100, 695, 406);
		setContentPane(getJContentPane());
		if (lottery != null) {
			lblLotID.setText(""+lottery.getLotteryID());
			System.out.println("The lotteryID is: " + lottery.getLotteryID());
		}
		redibujar();
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
			contentPane.add(getLblEuro());
			contentPane.add(getLblNextLottery());
			contentPane.add(getScrollPaneMovements());
			contentPane.add(getlblLotteryID());
			contentPane.add(getbtnGiveJackpot());
			contentPane.add(getLblWinner());
			contentPane.add(getLblCountDown());
			contentPane.add(getlblID());

		}
		return contentPane;
	}

	private JLabel getlblError() {
		if (lblErrors == null) {
			lblErrors = new JLabel("");
			lblErrors.setForeground(Color.RED);
			lblErrors.setBounds(87, 183, 238, 15);
		}
		return lblErrors;
	}

	private JButton getbtnClose() {
		if (btnClose == null) {
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
		if (lblJackpot == null) {

			lblJackpot = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Jackpot")); //$NON-NLS-1$ //$NON-NLS-2$
			lblJackpot.setBounds(100, 60, 100, 15);
		}
		return lblJackpot;
	}

	private JLabel getlblAmoMoney() {
		if (lblAmoMoney == null) {
			lblAmoMoney = new JLabel();
			lblAmoMoney.setBounds(260, 60, 70, 15);
		}
		return lblAmoMoney;
	}

	private JLabel getlblLotteryID() {
		if (lblLotID == null) {
			lblLotID = new JLabel("");
			lblLotID.setBounds(260, 33, 70, 15);
		}
		return lblLotID;
	}

	private JLabel getlblPlayers() {
		if (lblPlayers == null) {
			lblPlayers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Players"));
			lblPlayers.setBounds(100, 90, 100, 15);
		}
		return lblPlayers;
	}

	private JButton getbtnNewLottery() {
		if (btnNewLottery == null) {
			btnNewLottery = new JButton();
			btnNewLottery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateLottery"));
			btnNewLottery.setBounds(218, 300, 179, 47);
			btnNewLottery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String price = textField.getText();
					facade.createLottery(Integer.valueOf(price)); // We create a new lottery
					tableModelPlayers.setRowCount(0);
					lblLotID.setText("");
					lblAmoMoney.setText("");
					lblAmoPeople.setText("");
					btnNewLottery.setEnabled(false);
					btnGiveJackpot.setEnabled(false);
					contentPane.remove(lblWinner);
					redibujar();
				}
			});
		}
		return btnNewLottery;
	}

	private JButton getbtnGiveJackpot() {
		if (btnGiveJackpot == null) {
			btnGiveJackpot = new JButton();
			btnGiveJackpot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					winner = facade.giveJackpot(lottery.getLotteryID());
					redibujar();
					countDown();
					btnNewLottery.setEnabled(true);
					btnGiveJackpot.setEnabled(false);

				}
			});

			btnGiveJackpot.setText(ResourceBundle.getBundle("Etiquetas").getString("GiveJackpot"));
			btnGiveJackpot.setBounds(96, 248, 159, 40);

		}
		return btnGiveJackpot;
	}

	private JLabel getlblID() {
		if (lblID == null) {
			lblID = new JLabel();
			lblID.setText(ResourceBundle.getBundle("Etiquetas").getString("Lottery")); 
			lblID.setBounds(100, 33, 118, 15);
		}
		return lblID;
	}
	private JLabel getlblAmoPeople() {
		if (lblAmoPeople == null) {
			lblAmoPeople = new JLabel();
			lblAmoPeople.setBounds(260, 90, 70, 15);
		}
		return lblAmoPeople;
	}

	private JTextField gettxtField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setText("20");
			textField.setFont(new Font("Dialog", Font.PLAIN, 18));
			textField.setBounds(409, 300, 62, 47);
		}
		return textField;
	}

	private JLabel getLblEuro() {
		if (lblEuro == null) {
			lblEuro = new JLabel("€");
			lblEuro.setFont(new Font("Dialog", Font.BOLD, 22));
			lblEuro.setBounds(470, 309, 32, 25);
		}
		return lblEuro;
	}

	private void closeGUI() {
		this.setVisible(false);
	}

	private JLabel getLblNextLottery() {
		if (lblNextLottery == null) {
			lblNextLottery = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NextLotteryPrice"));
			lblNextLottery.setBounds(376, 273, 283, 15);
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

			if (lottery != null) {
				ArrayList<User> players = facade.getPlayersLottery(lottery.getLotteryID());
				if(players != null) {
					for (User pl : players) {
						Vector<Object> row = new Vector<Object>();
						row.add(pl.getUsername());
						tableModelPlayers.addRow(row);
					}
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

	private JLabel getLblCountDown() {
		if (lblCountDown == null) {
			lblCountDown = new JLabel();
			lblCountDown.setForeground(Color.RED);
			lblCountDown.setFont(new Font("Dialog", Font.BOLD, 120));
			lblCountDown.setBounds(131, 122, 124, 103);
			lblCountDown.setVisible(false);
		}
		return lblCountDown;
	}

	public void redibujar() {
		if (lottery != null) {
			if (lottery.getParticipantsNumber() == 0) {
				btnGiveJackpot.setEnabled(false);
				btnGiveJackpot.setText(ResourceBundle.getBundle("Etiquetas").getString("NoPlayers"));
				btnNewLottery.setEnabled(false);
				btnNewLottery.setText(ResourceBundle.getBundle("Etiquetas").getString("Raffling"));
			} else {
				lblAmoPeople.setText(String.valueOf(lottery.getParticipantsNumber()));
				lblAmoMoney.setText(String.valueOf(lottery.getJackpot()));
				lblLotID.setText(String.valueOf(lottery.getLotteryID()));
				btnNewLottery.setEnabled(false);
				btnGiveJackpot.setEnabled(true);
			}
		} else {
			btnGiveJackpot.setEnabled(false);
			btnGiveJackpot.setText(ResourceBundle.getBundle("Etiquetas").getString("NoLotteries"));
		}
	}

	/**
	 * This method creates a countDown
	 */
	public void countDown() {
		new Thread() {
			int counter = 3;
			public void run() {
				lblCountDown.setVisible(true);
				while (counter >= 0) {
					if (counter == 0) {
						lblCountDown.setText("");
						lblWinner.setText(winner);
					} else
						lblCountDown.setText("" + (counter--));
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}

			}
		}.start();

	}
}
