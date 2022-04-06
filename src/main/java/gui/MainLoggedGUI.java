package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Event;
import domain.User;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainLoggedGUI extends JFrame {
	
	private User loggedUser;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonTopUpBalance = null;
	private JButton jButtonSeeMovements = null;
	private JButton jButtonBet = null;
	
    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * This is the default constructor
	 */
	public MainLoggedGUI() {
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
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(495, 408);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoggedTitle") + ": " + loggedUser.getUsername());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3());
			jContentPane.add(getTopUpBalanceButton());
			jContentPane.add(getSeeMovementsButton());
			jContentPane.add(getBetButton());
			jContentPane.add(getPanel());
	
		}
		return jContentPane;
	}
	
	private JButton getBetButton() {
		if(jButtonBet == null) {
			jButtonBet = new JButton();
			jButtonBet.setBounds(0, 113, 495, 61);
			jButtonBet.setText(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
			jButtonBet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame bet = new BetGUI();
					bet.setVisible(true);
				}
			});
		}
		return jButtonBet;
	}
	
	/**jButtonQueryQuery
	 * This method initializes boton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(0, 51, 495, 61);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();

					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
	}
	

	/**jButtonTopUpBalance
	 * This method initializes topUpBalance button
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getTopUpBalanceButton() {
		if (jButtonTopUpBalance == null) {
			jButtonTopUpBalance = new JButton();
			jButtonTopUpBalance.setBounds(0, 237, 495, 61);
			jButtonTopUpBalance.setText(ResourceBundle.getBundle("Etiquetas").getString("TopUpBalance"));
			jButtonTopUpBalance.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TopUpBalanceGUI a = new TopUpBalanceGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonTopUpBalance;
	}
	

	/**jButtonSeeMovements
	 * This method initializes SeeMovement button
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSeeMovementsButton() {
		if (jButtonSeeMovements == null) {
			jButtonSeeMovements = new JButton();
			jButtonSeeMovements.setBounds(0, 175, 495, 61);
			jButtonSeeMovements.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeMovements"));
			jButtonSeeMovements.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SeeMovementsGUI a = new SeeMovementsGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonSeeMovements;
	}


	
	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 1, 495, 61);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}

	

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(0, 307, 495, 61);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonTopUpBalance.setText(ResourceBundle.getBundle("Etiquetas").getString("TopUpBalance"));
		jButtonSeeMovements.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeMovements"));
		jButtonBet.setText(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoggedTitle") + ": " + loggedUser.getUsername());
	}
	
	private void closeMain() {
		this.setVisible(false);
	}
} // @jve:decl-index=0:visual-constraint="0,0"

