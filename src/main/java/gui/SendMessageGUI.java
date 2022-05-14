package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Message;
import domain.Movement;
import domain.Pronostic;
import domain.User;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Rectangle;

public class SendMessageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User loggedUser;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	JButton jButtonClose = null;
	private JScrollPane scrollPaneMessages;

	private JTable tableMessages= new JTable();
	private DefaultTableModel tableModelMessages;
	private String[] columnNamesMessages = new String[] {
			"Date", "User", "Message"
	};
	private JTextField textFieldSendMessage;
	private JTextField textFieldDestinatary;
	private JButton jBtnSearchDestinatary;
	private JButton jBtnSendMessage;
	private JLabel lblUser;
	private JLabel lblDestinataryUser;
	private JLabel lblError;
	/**
	 * This is the default constructor
	 * @param loggedUser 
	 */
	public SendMessageGUI(User loggedUser) {
		super();
		this.loggedUser = loggedUser;
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
			contentPane.add(getjButtonClose());
			contentPane.add(getscrollPaneMessages());
			contentPane.add(getButtonSearchDestinatary());
			contentPane.add(getTextFieldDestinatary());
			contentPane.add(getTextFieldSendMessage());		
			contentPane.add(getButtonSendMessage());
			contentPane.add(getLblDestinatary());
			contentPane.add(getLblUser());
			contentPane.add(getLblError());

		}
		return contentPane;
	}

	private JButton getjButtonClose() {
		if(jButtonClose == null) {
			jButtonClose = new JButton("Close");
			jButtonClose.setBounds(500, 300, 117, 25);
			jButtonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeSendMessage();
				}
			});
		}
		return jButtonClose;
	}


	private JScrollPane getscrollPaneMessages() {
		if (scrollPaneMessages == null) {
			scrollPaneMessages = new JScrollPane();
			scrollPaneMessages.setBounds(new Rectangle(40, 274, 406, 160));
			scrollPaneMessages.setBounds(12, 39, 605, 249);

			scrollPaneMessages.setViewportView(tableMessages);
			tableModelMessages= new DefaultTableModel(null, columnNamesMessages);
			tableMessages.setModel(tableModelMessages);
			tableMessages.getColumnModel().getColumn(0).setPreferredWidth(200);
			tableMessages.getColumnModel().getColumn(1).setPreferredWidth(150);
			tableMessages.getColumnModel().getColumn(2).setPreferredWidth(400);
			scrollPaneMessages.setVisible(false);



		}
		return scrollPaneMessages;
	}
	private JButton getButtonSearchDestinatary() {
		if(jBtnSearchDestinatary == null) {
			jBtnSearchDestinatary = new JButton("Search Destinatary");
			jBtnSearchDestinatary.setBounds(508, 12, 98, 25);
			jBtnSearchDestinatary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String destUser = textFieldDestinatary.getText();

					if(destUser.isEmpty()){
						lblDestinataryUser.setVisible(false);
						printError("Choose a user to send a message to");
					}
					else {
						if(!facade.existUser(destUser)) {
							printError("The user does not exist");
						}
						else{
							lblDestinataryUser.setVisible(true);
							lblDestinataryUser.setText(destUser);
							updateChat();
						}
					}
				}
			});
		}
		return jBtnSearchDestinatary;
	}

	private JButton getButtonSendMessage() {
		if(jBtnSendMessage == null) {
			jBtnSendMessage = new JButton("Send Message");
			jBtnSendMessage.setBounds(353, 300, 135, 25);
			jBtnSendMessage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					User remitent = loggedUser;
					String destinataryName = lblDestinataryUser.getText();
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
					String formatDate = formatter.format(date);
					String message = textFieldSendMessage.getText();
					if (!message.isEmpty()) {
						facade.createMessage(remitent, destinataryName, formatDate, message);
						updateChat();
					}

				}
			});
			jBtnSendMessage.setVisible(false);
		}
		return jBtnSendMessage;
	}

	private JTextField getTextFieldDestinatary() {
		if(textFieldDestinatary == null) {
			textFieldDestinatary = new JTextField();
			textFieldDestinatary.setBounds(319, 10, 177, 29);
			textFieldDestinatary.setColumns(10);
		}
		return textFieldDestinatary;
	}

	private JTextField getTextFieldSendMessage() {
		if(textFieldSendMessage == null) {
			textFieldSendMessage = new JTextField();
			textFieldSendMessage.setBounds(38, 300, 314, 25);
			textFieldSendMessage.setColumns(10);
			textFieldSendMessage.setVisible(false);
		}
		return textFieldSendMessage;
	}
	private JLabel getLblDestinatary() {
		if(lblDestinataryUser == null) {
			lblDestinataryUser = new JLabel();
			lblDestinataryUser.setBounds(83, 17, 70, 15);
		}
		return lblDestinataryUser;
	}

	private JLabel getLblUser() {
		if(lblUser == null) {
			lblUser = new JLabel("User:");
			lblUser.setBounds(23, 17, 48, 15);
		}
		return lblUser;
	}

	private JLabel getLblError() {
		if(lblError == null) {
			lblError = new JLabel("Choose a user to send a message to");
			lblError.setBounds(38,100,550,106);
			lblError.setForeground(Color.RED);
			lblError.setVisible(true);
		}
		return lblError;
	}


	private void closeSendMessage() {
		this.setVisible(false);
	}

	private void updateChat() {
		try {
			scrollPaneMessages.setVisible(true);
			textFieldSendMessage.setVisible(true);
			jBtnSendMessage.setVisible(true);
			tableModelMessages.setRowCount(0);
			String remUsername = loggedUser.getUsername();
			String destUsername = lblDestinataryUser.getText();
			Vector<Message> messages = facade.getMessagesForThisChat(remUsername, destUsername);
			for (domain.Message mes:messages) {
				Vector<Object> row = new Vector<Object>();
				row.add(mes.getMesDate());
				row.add(mes.getRemitent().getUsername());
				row.add(mes.getMessage());
				tableModelMessages.addRow(row);
			}
		} catch (Exception e) {
			System.out.println("You have not chosen receiver or there has been an error ");
		}
	}
	private void printError(String pError) {
		scrollPaneMessages.setVisible(false);
		textFieldSendMessage.setVisible(false);
		jBtnSendMessage.setVisible(false);
		lblError.setText(pError);
		lblError.setVisible(true);
	}
}
