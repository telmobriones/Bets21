package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Movement;
import domain.Pronostic;
import domain.User;
import jdk.internal.misc.FileSystemOption;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Rectangle;

public class SeeMovementsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User loggedUser;
	private static BLFacade facade = MainGUI.getBusinessLogic();

	JLabel lblUsername = null;
	JLabel lblLoggedUsername = null;
	JLabel lblCurrentBalance  = null;
	JLabel lblCurrentBalanceAmount = null;
	JButton jButtonClose = null;
	private JScrollPane scrollPaneMovements;
	
	private JTable tableMovements= new JTable();
	private DefaultTableModel tableModelMovements;
	private String[] columnNamesMovements = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("MovType"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"),
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("Money"), 
	};

	/**
	 * This is the default constructor
	 */
	public SeeMovementsGUI() {
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
	 * Create the frame.
	 */
	private void initialize() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SeeMovements"));
		this.setBounds(100, 100, 629, 376);
		setContentPane(getJContentPane());
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
			contentPane.add(getjButtonClose());
			contentPane.add(getScrollPaneMovements());
		}
		return contentPane;
	}

	private JLabel getlblUsername() {
		if (lblUsername == null) {
			lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User") + ":");
			lblUsername.setBounds(48, 12, 222, 15);
		}
		return lblUsername;
	}

	private JLabel getlblLoggedUsername() {
		if (lblLoggedUsername == null) {
			lblLoggedUsername = new JLabel(facade.getLogUser().getUsername());
			lblLoggedUsername.setBounds(282, 12, 70, 15);
		}
		return lblLoggedUsername;
	}

	private JLabel getlblCurrentBalance() {
		if(lblCurrentBalance == null) {
			lblCurrentBalance = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CurrentBalance") + ":");
			lblCurrentBalance.setBounds(357, 288, 218, 15);
		}
		return lblCurrentBalance;
	}

	private JLabel getlblCurrentBalanceAmount() {
		if(lblCurrentBalanceAmount == null) {
			lblCurrentBalanceAmount = new JLabel(facade.getLogUser().getBalance() + "€");
			lblCurrentBalanceAmount.setBounds(572, 288, 45, 15);
		}
		return lblCurrentBalanceAmount;
	}

	private JButton getjButtonClose() {
		if(jButtonClose == null) {
			jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
			jButtonClose.setBounds(282, 315, 117, 25);
			jButtonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeSeeMovements();
				}
			});
		}
		return jButtonClose;
	}

	private void redibujar() {
		this.loggedUser = facade.getLogUser();
		this.lblUsername.setText(ResourceBundle.getBundle("Etiquetas").getString("User") + ":");
		lblLoggedUsername.setText(loggedUser.getUsername());
		lblCurrentBalance.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrentBalance") + ":");
		lblCurrentBalanceAmount.setText(facade.getLogUser().getBalance() + "€");
		jButtonClose.setText(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SeeMovements"));
	}

	private JScrollPane getScrollPaneMovements() {
		if (scrollPaneMovements == null) {
			scrollPaneMovements = new JScrollPane();
			scrollPaneMovements.setBounds(new Rectangle(40, 274, 406, 160));
			scrollPaneMovements.setBounds(12, 39, 605, 249);
			
			scrollPaneMovements.setViewportView(tableMovements);
			tableModelMovements = new DefaultTableModel(null, columnNamesMovements);
			tableMovements.setModel(tableModelMovements);
			tableMovements.getColumnModel().getColumn(0).setPreferredWidth(20);
			tableMovements.getColumnModel().getColumn(1).setPreferredWidth(100);
			tableMovements.getColumnModel().getColumn(2).setPreferredWidth(150);
			tableMovements.getColumnModel().getColumn(3).setPreferredWidth(5);
			
			Vector<Movement> movements = loggedUser.getMovements();
			for (domain.Movement mov:movements) {
				Vector<Object> row = new Vector<Object>();
				row.add(mov.getMovementType());
				row.add(mov.getEvent());
				row.add(mov.getQuestion());
				row.add(mov.getMoney());
				tableModelMovements.addRow(row);
			}

		}
		return scrollPaneMovements;
	}
	
	private void closeSeeMovements() {
		this.setVisible(false);
	}
}
