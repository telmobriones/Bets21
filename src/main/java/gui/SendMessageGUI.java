package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Message;
import domain.User;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.SwingConstants;

public class SendMessageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User loggedUser;
	private static BLFacade facade = MainGUI.getBusinessLogic();
	JButton jButtonClose = null;
	private JScrollPane scrollPaneMessages;

	private JTable tableMessages = new JTable();
	private DefaultTableModel tableModelMessages;
	private String[] columnNamesMessages = new String[] { ResourceBundle.getBundle("Etiquetas").getString("Date"),
			ResourceBundle.getBundle("Etiquetas").getString("User"),
			ResourceBundle.getBundle("Etiquetas").getString("Message") };
	private JTextField textFieldSendMessage;
	private JTextField textFieldDestinatary;
	private JButton jBtnSearchDestinatary;
	private JButton jBtnSendMessage;
	private JButton jBtnInbox;
	private JLabel lblContact;
	private JLabel lblDestinataryUser;
	private JLabel lblError;

	/**
	 * This is the default constructor
	 * 
	 * @param loggedUser
	 */
	public SendMessageGUI(User loggedUser) {
		super();
		this.loggedUser = loggedUser;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SeeMovements"));
		this.setBounds(100, 100, 690, 400);
		setContentPane(getJContentPane());
		getRecievedChats();
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
			contentPane.add(getButtonInbox());
			contentPane.add(getLblDestinatary());
			contentPane.add(getlblContact());
			contentPane.add(getLblError());

		}
		return contentPane;
	}

	private JButton getjButtonClose() {
		if (jButtonClose == null) {
			jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
			jButtonClose.setBounds(591, 329, 85, 25);
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
			scrollPaneMessages.setBounds(12, 68, 664, 249);

			scrollPaneMessages.setViewportView(tableMessages);
			tableModelMessages = new DefaultTableModel(null, columnNamesMessages);
			tableMessages.setModel(tableModelMessages);
			tableMessages.getColumnModel().getColumn(0).setPreferredWidth(200);
			tableMessages.getColumnModel().getColumn(1).setPreferredWidth(150);
			tableMessages.getColumnModel().getColumn(2).setPreferredWidth(400);
			scrollPaneMessages.setVisible(false);

		}
		return scrollPaneMessages;
	}
	
	private JButton getButtonInbox() {
		if (jBtnInbox == null) {
			jBtnInbox = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Inbox"));
			jBtnInbox.setBounds(12, 9, 180, 25);
			jBtnInbox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ChooseUser"));
					textFieldDestinatary.setText("");
					getRecievedChats();
				}
			});
		}
		return jBtnInbox;
	}

	private JButton getButtonSearchDestinatary() {
		if (jBtnSearchDestinatary == null) {
			jBtnSearchDestinatary = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SearchDestinatary"));
			jBtnSearchDestinatary.setBounds(470, 41, 205, 25);
			jBtnSearchDestinatary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String destUser = textFieldDestinatary.getText();

					if (destUser.isEmpty()) {
						lblDestinataryUser.setVisible(false);
						printError(ResourceBundle.getBundle("Etiquetas").getString("ChooseUser") + "!!!!");
					} else {
						if (!facade.existUser(destUser)) {
							printError(ResourceBundle.getBundle("Etiquetas").getString("UserNotFound"));
						} else {
							lblDestinataryUser.setVisible(true);
							lblDestinataryUser.setText(destUser);
							getUpdatedChat();
						}
					}
				}
			});
		}
		return jBtnSearchDestinatary;
	}

	private JButton getButtonSendMessage() {
		if (jBtnSendMessage == null) {
			jBtnSendMessage = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SendMessage"));
			jBtnSendMessage.setBounds(404, 329, 175, 25);
			jBtnSendMessage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					User remitent = loggedUser;
					String destinataryName = lblDestinataryUser.getText();
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
					String formatDate = formatter.format(date);
					String messageText = textFieldSendMessage.getText();
					if (destinataryName != "") {
						if (!messageText.isEmpty()) {
							facade.createMessage(remitent, destinataryName, formatDate, messageText);
							textFieldSendMessage.setText("");
							getUpdatedChat();
						} else {
							printError(ResourceBundle.getBundle("Etiquetas").getString("MessageEmpty"));
						}
					} else {
						printError(ResourceBundle.getBundle("Etiquetas").getString("ChooseUser"));
					}
				}
			});
			jBtnSendMessage.setVisible(false);
		}
		return jBtnSendMessage;
	}

	private JTextField getTextFieldDestinatary() {
		if (textFieldDestinatary == null) {
			textFieldDestinatary = new JTextField();
			textFieldDestinatary.setBounds(319, 41, 140, 25);
			textFieldDestinatary.setColumns(10);
		}
		return textFieldDestinatary;
	}

	private JTextField getTextFieldSendMessage() {
		if (textFieldSendMessage == null) {
			textFieldSendMessage = new JTextField();
			textFieldSendMessage.setBounds(22, 329, 370, 25);
			textFieldSendMessage.setColumns(10);
			textFieldSendMessage.setVisible(false);
		}
		return textFieldSendMessage;
	}

	private JLabel getLblDestinatary() {
		if (lblDestinataryUser == null) {
			lblDestinataryUser = new JLabel();
			lblDestinataryUser.setBounds(140, 46, 180, 15);
		}
		return lblDestinataryUser;
	}

	private JLabel getlblContact() {
		if (lblContact == null) {
			lblContact = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Destinatary")+":");
			lblContact.setHorizontalAlignment(SwingConstants.TRAILING);
			lblContact.setBounds(20, 46, 100, 15);
		}
		return lblContact;
	}

	private JLabel getLblError() {
		if (lblError == null) {
			lblError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ChooseUser"));
			lblError.setHorizontalAlignment(SwingConstants.TRAILING);
			lblError.setBounds(210, 9, 440, 25);
			lblError.setForeground(Color.RED);
			lblError.setVisible(true);
		}
		return lblError;
	}

	private void closeSendMessage() {
		this.setVisible(false);
	}

	private void getUpdatedChat() {
		try {
			scrollPaneMessages.setVisible(true);
			textFieldSendMessage.setVisible(true);
			jBtnSendMessage.setVisible(true);
			tableModelMessages.setRowCount(0);
			String remUsername = loggedUser.getUsername();
			String destUsername = lblDestinataryUser.getText();
			ArrayList<Message> messages = facade.getMessagesForThisChat(remUsername, destUsername);
			lblError.setText("");
			if (messages != null) {
				for (domain.Message mes : messages) {
					Vector<Object> row = new Vector<Object>();
					row.add(mes.getMesDate());
					row.add(mes.getRemitent().getUsername());
					row.add(mes.getMessage());
					tableModelMessages.addRow(row);
				}
			} else {
				printError(ResourceBundle.getBundle("Etiquetas").getString("NoMessages"));
			}
		} catch (Exception e) {
			System.out.println("You have not chosen receiver or there has been an error ");
		}
	}

	private void getRecievedChats() {
		try {
			String remUsername = loggedUser.getUsername();
			ArrayList<Message> messages = facade.getMessagesForThisUser(remUsername);
			if (!messages.isEmpty()) {
				scrollPaneMessages.setVisible(true);
				textFieldSendMessage.setVisible(true);
				jBtnSendMessage.setVisible(true);
				tableModelMessages.setRowCount(0);
				lblDestinataryUser.setText("");
				for (domain.Message mes : messages) {
					Vector<Object> row = new Vector<Object>();
					row.add(mes.getMesDate());
					row.add(mes.getRemitent().getUsername());
					row.add(mes.getMessage());
					tableModelMessages.addRow(row);
				}
			} else {
				printError(ResourceBundle.getBundle("Etiquetas").getString("NoMessages"));
			}
		} catch (Exception e) {
			System.out.println("There has been an ERROR");
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
