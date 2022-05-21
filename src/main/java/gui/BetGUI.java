package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Pronostic;
import domain.Question;
import domain.User;
import domain.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class BetGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private User loggedUser;
	private int betMoney;
	private float minBetAmmount;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private final JLabel jLabelPronostics = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Pronostics"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPanePronostics = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tablePronostics = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelPronostics;

	private String[] columnNamesEvents = new String[] { 
			ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { 
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesPronostics = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("PronosticN"),
			ResourceBundle.getBundle("Etiquetas").getString("Pronostic")

	};

	private JTextField textFieldBetMoney;
	private JLabel lblPronOdds;
	private final JLabel lblErrors = new JLabel("");

	private Event pronEvent;
	private Question pronQuestion;
	private Pronostic betPronostic;

	private static BLFacade facade = MainGUI.getBusinessLogic();

	public BetGUI(User loggedUser) {
		try {
			this.loggedUser = facade.getUser(loggedUser.getUsername());
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(750, 710));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreatePronostic"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 247, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);
		jLabelPronostics.setBounds(40, 463, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		this.getContentPane().add(jLabelPronostics);

		jButtonClose.setBounds(new Rectangle(527, 621, 112, 30));

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev : events) {
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
																												// in
																												// JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 274, 406, 160));
		scrollPanePronostics.setBounds(new Rectangle(40, 491, 406, 160));

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);
		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPanePronostics.setViewportView(tablePronostics);
		tableModelPronostics = new DefaultTableModel(null, columnNamesPronostics);
		tablePronostics.setModel(tableModelPronostics);
		tablePronostics.getColumnModel().getColumn(0).setPreferredWidth(25);
		tablePronostics.getColumnModel().getColumn(1).setPreferredWidth(268);

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				pronEvent = ev;
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3); // another column added to allocate q objects

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					tableModelQueries.addRow(row);
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2));
			}
		});

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				domain.Question quest = (domain.Question) tableModelQueries.getValueAt(i, 2);
				System.out.println("Question: " + quest.getQuestion());
				pronQuestion = quest;

				ArrayList<Pronostic> pronostics = pronQuestion.getPronostics();

				tableModelPronostics.setDataVector(null, columnNamesPronostics);
				tableModelPronostics.setColumnCount(3); // another column added to allocate p objects

				if (pronostics.isEmpty())
					jLabelPronostics.setText(ResourceBundle.getBundle("Etiquetas").getString("NoPronostics") + ": "
							+ pronQuestion.getQuestion());
				else
					jLabelPronostics.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedQuestion") + ": "
							+ pronQuestion.getQuestion());

				for (domain.Pronostic p : pronostics) {
					Vector<Object> row = new Vector<Object>();

					row.add(p.getPronID());
					row.add(p.getPronDescription());
					row.add(p);
					tableModelPronostics.addRow(row);
				}
				tablePronostics.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePronostics.getColumnModel().getColumn(1).setPreferredWidth(268);
				tablePronostics.getColumnModel().removeColumn(tablePronostics.getColumnModel().getColumn(2));

			}
		});

		tablePronostics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tablePronostics.getSelectedRow();
				domain.Pronostic pron = (domain.Pronostic) tableModelPronostics.getValueAt(i, 2);
				betPronostic = pron;
				lblPronOdds.setText("" + betPronostic.getPronOdd());
				System.out.println(betPronostic.getPronDescription());
			}
		});

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePronostics, null);

		////////////////////////////////////////////////////
		JLabel lblBetMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Money")); //$NON-NLS-1$ //$NON-NLS-2$
		lblBetMoney.setBounds(480, 403, 90, 15);
		getContentPane().add(lblBetMoney);

		JLabel lblPronosticOdds = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PronosticOdds")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPronosticOdds.setBounds(480, 329, 149, 14);
		getContentPane().add(lblPronosticOdds);

		lblPronOdds = new JLabel(); // $NON-NLS-1$ //$NON-NLS-2$
		lblPronOdds.setBounds(635, 329, 70, 15);
		getContentPane().add(lblPronOdds);

		textFieldBetMoney = new JTextField();
		textFieldBetMoney.setBounds(602, 401, 134, 19);
		textFieldBetMoney.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // if it's not a number, ignore the event
				}
			}
		});
		getContentPane().add(textFieldBetMoney);
		textFieldBetMoney.setColumns(10);

		lblErrors.setBounds(490, 514, 225, 15);
		lblErrors.setForeground(Color.RED);
		getContentPane().add(lblErrors);

		JButton btnMakeBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet")); //$NON-NLS-1$ //$NON-NLS-2$
		btnMakeBet.setBounds(490, 456, 200, 30);
		btnMakeBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean error = false;
				betMoney = Integer.parseInt(textFieldBetMoney.getText());
				minBetAmmount = pronQuestion.getBetMinimum();
				
				if (betPronostic == null) {
					lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorSelectPronostic"));
					error = true;
				} else {
					if (betMoney <= minBetAmmount) {
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorBetMinimum"));
						error = true;
					} else if (betMoney > loggedUser.getBalance()) {
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNoBalance"));
						error = true;
//					} else if (new Date().compareTo(pronEvent.getEventDate()) > 0) {
//						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPastEvent"));
//						error = true;
					} else if(pronQuestion.isAnswered()) {
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPastEvent"));
					}
				}

				if (!error) {
					lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("BetAdded"));
					ArrayList<Pronostic> pronList=new ArrayList<Pronostic>();
					pronList.add(betPronostic);
					facade.makeBet(betMoney, loggedUser, false, pronList, "Bet Made", pronEvent.getDescription(), pronQuestion.getQuestion());
				}
			}
		});
		getContentPane().add(btnMakeBet);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
