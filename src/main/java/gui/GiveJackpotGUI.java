package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Rectangle;

public class GiveJackpotGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	private static LoginGUI frame = new LoginGUI();
	private Lottery lottery;
	private Lottery newLottery;
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
		lottery = facade.getLastActiveLottery();
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
			lblAmoMoney.setText(String.valueOf(lottery.getJackpot()));
			lblAmoMoney.setBounds(267, 47, 70, 15);
		}
		return lblAmoMoney;
	}
	private JLabel getlblLotteryID(){
		if(lblLotID == null) {
			lblLotID = new JLabel();
			lblLotID.setText(String.valueOf(lottery.getLotteryID()));
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

			if(lottery == null) btnNewLottery.setEnabled(true);
			else btnNewLottery.setEnabled(false);

			btnNewLottery.setText(ResourceBundle.getBundle("Etiquetas").getString("GiveJackpotGUI.btnNewLottery.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnNewLottery.setBounds(218, 300, 179, 47);
			btnNewLottery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String num = textField.getText();
					newLottery = facade.createLottery(Integer.valueOf(num));
					redibujar(newLottery);
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
					facade.giveJackpot(lottery);
				}
			});
			
			if(lottery != null) btnNewLottery.setEnabled(true);
			else btnNewLottery.setEnabled(false);
			
			btnGiveJackpot.setText("Give Jackpot");
			btnGiveJackpot.setBounds(96, 248, 159, 40);

		}
		return btnGiveJackpot;
	}


	private JLabel getlblAmoPeople() {
		if(lblAmoPeople == null) {
			lblAmoPeople = new JLabel();
			lblAmoPeople.setText(String.valueOf(lottery.getParticipantsNumber()));
			lblAmoPeople.setBounds(267, 93, 70, 15);
		}
		return lblAmoPeople;
	}

	private JTextField gettxtField() {
		if(textField == null) {
			textField = new JTextField();
			textField.setText(ResourceBundle.getBundle("Etiquetas").getString("GiveJackpotGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
			textField.setFont(new Font("Dialog", Font.PLAIN, 18));
			textField.setBounds(409, 300, 62, 47);
		}
		return textField;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GiveJackpotGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
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
			lblNextLottery = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GiveJackpotGUI.lblNextLottery.text")); //$NON-NLS-1$ //$NON-NLS-2$
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


			ArrayList<User> players = facade.getPlayersLottery(lottery);
			for (User pl:players) {
				Vector<Object> row = new Vector<Object>();
				row.add(pl.getUsername());
				tableModelPlayers.addRow(row);
			}

		}
		return scrollPanePlayers;
	}

	public void redibujar(Lottery lot) {
		lblAmoPeople.setText(String.valueOf(lot.getParticipantsNumber()));
		lblAmoMoney.setText(String.valueOf(lot.getJackpot()));
		lblLotID.setText(String.valueOf(lot.getLotteryID()));
		tableModelPlayers.setRowCount(0);
		btnGiveJackpot.setEnabled(false);
	}
}