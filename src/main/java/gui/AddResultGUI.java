package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Pronostic;
import domain.Question;
import domain.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class AddResultGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private int pronosticResult;

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

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesPronostics = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("PronosticN"),
			ResourceBundle.getBundle("Etiquetas").getString("Pronostic")

	};
	private final JLabel lblErrors = new JLabel("");

	private Event pronEvent;
	private Question pronQuestion;
	private Pronostic betPronostic;

	private static BLFacade facade = MainGUI.getBusinessLogic();
	private final JButton btnPronosticResult = new JButton();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private final JLabel jLabelEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Event"));
	private final JLabel jLabelEventDescr = new JLabel("");
	private final JLabel jLabelQuestion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Query"));
	private final JLabel jLabelQuestionDescr = new JLabel("");
	private final JLabel jLabelPronostic = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Pronostic"));
	private final JLabel jLabelPronosticDescr = new JLabel("");

	private final JRadioButton rdbtnCorrectPronostic = new JRadioButton();

	public AddResultGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(775, 710));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreatePronostic"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 247, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);
		jLabelPronostics.setBounds(40, 463, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		this.getContentPane().add(jLabelPronostics);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

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
					// jCalendar1.setCalendar(calendarAct);
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
				jLabelEventDescr.setText(pronEvent.getDescription());
				jLabelQuestionDescr.setText("");
				jLabelPronosticDescr.setText("");
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
				jLabelQuestionDescr.setText(pronQuestion.getQuestion());
				jLabelPronosticDescr.setText("");

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
				jLabelPronosticDescr.setText(betPronostic.getPronDescription());
				System.out.println(betPronostic.getPronDescription());
			}
		});

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePronostics, null);

		lblErrors.setBounds(490, 514, 225, 15);
		lblErrors.setForeground(Color.RED);
		getContentPane().add(lblErrors);

		jLabelEvent.setHorizontalAlignment(SwingConstants.TRAILING);
		jLabelEvent.setBounds(450, 300, 98, 15);
		jLabelQuestion.setHorizontalAlignment(SwingConstants.TRAILING);
		jLabelQuestion.setBounds(450, 350, 98, 15);
		jLabelPronostic.setHorizontalAlignment(SwingConstants.TRAILING);
		jLabelPronostic.setBounds(450, 400, 98, 15);
		jLabelEventDescr.setFont(new Font("Dialog", Font.PLAIN, 12));
		jLabelEventDescr.setBounds(570, 300, 180, 15);
		jLabelQuestionDescr.setFont(new Font("Dialog", Font.PLAIN, 12));
		jLabelQuestionDescr.setBounds(570, 350, 180, 15);
		jLabelPronosticDescr.setFont(new Font("Dialog", Font.PLAIN, 12));
		jLabelPronosticDescr.setBounds(570, 400, 180, 15);
		getContentPane().add(jLabelEvent);
		getContentPane().add(jLabelEventDescr);
		getContentPane().add(jLabelQuestion);
		getContentPane().add(jLabelQuestionDescr);
		getContentPane().add(jLabelPronostic);
		getContentPane().add(jLabelPronosticDescr);

		btnPronosticResult
				.setText(ResourceBundle.getBundle("Etiquetas").getString("AddResultGUI.btnPronosticResult.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnPronosticResult.setBounds(505, 521, 210, 52);
		btnPronosticResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				boolean error = false;

				if (new Date().compareTo(pronEvent.getEventDate()) <= 0) {
					lblErrors.setText("Event hasn't happened yet!");
					error = false;
				} else if (betPronostic == null) {
					lblErrors.setText("No pronostic selected!");
					error = true;
				} else if (pronosticResult != 1) {
					lblErrors.setText("Check the button first");
					error = true;
				} else if (pronQuestion.isAnswered()) {
					lblErrors.setText("Question is already answered!");
				}
				if (!error) {
					facade.questionSolution(pronQuestion, betPronostic);
				}
				rdbtnCorrectPronostic.setSelected(false);
			}
		});
		getContentPane().add(btnPronosticResult);

		rdbtnCorrectPronostic.setText("CorrectPronostic");
		buttonGroup.add(rdbtnCorrectPronostic);
		rdbtnCorrectPronostic.setBounds(529, 460, 149, 23);
		rdbtnCorrectPronostic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pronosticResult = 1; // Correct pronostic!
			}
		});
		getContentPane().add(rdbtnCorrectPronostic);

		jButtonClose.setBounds(new Rectangle(556, 602, 112, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeAddResultGUI();
			}
		});
		getContentPane().add(jButtonClose, null);

	}

	private void closeAddResultGUI() {
		this.setVisible(false);
	}
}