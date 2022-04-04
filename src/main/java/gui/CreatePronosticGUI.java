package gui;

import java.text.DateFormat;
import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class CreatePronosticGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelQuery = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Query"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreatePronostic"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private Date firstDay;
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private BLFacade facade = MainGUI.getBusinessLogic();
	private JTextField textField;
	private final JLabel jLabelMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreatePronosticGUI.lblQuote_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JTextField jTextQuote = new JTextField();
	private final JLabel jLabelError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreatePronosticGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jLabelMsg = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreatePronosticGUI.jLabelMsg.text")); //$NON-NLS-1$ //$NON-NLS-2$

	public CreatePronosticGUI(Vector<domain.Event> v) {
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 396));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelQuery.setBounds(new Rectangle(35, 212, 230, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(75, 304, 190, 30));
		jButtonCreate.setEnabled(true);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(322, 304, 190, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelQuery, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);



		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);



		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JComboBox jComboBoxQuestions = new JComboBox();
		jComboBoxQuestions.setBounds(171, 210, 354, 24);
		getContentPane().add(jComboBoxQuestions);
		
		JLabel lblPronostic = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreatePronosticGUI.lblPronostic.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPronostic.setBounds(new Rectangle(35, 240, 230, 20));
		lblPronostic.setBounds(35, 244, 166, 20);
		getContentPane().add(lblPronostic);
		
		textField = new JTextField();
		textField.setText("nada"); 
		textField.setBounds(171, 244, 354, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblQuote = new JLabel("Quote"); 
		lblQuote.setBounds(new Rectangle(35, 240, 230, 20));
		lblQuote.setBounds(35, 272, 63, 20);
		getContentPane().add(lblQuote);
		jLabelMoney.setBounds(new Rectangle(35, 240, 230, 20));
		jLabelMoney.setBounds(171, 272, 94, 20);
		
		getContentPane().add(jLabelMoney);
		jTextQuote.setText((String) null);
		jTextQuote.setColumns(10);
		jTextQuote.setBounds(97, 276, 63, 16);
		
		getContentPane().add(jTextQuote);
		jLabelError.setBounds(275, 275, 70, 15);
		
		getContentPane().add(jLabelError);
		jLabelMsg.setBounds(new Rectangle(35, 240, 230, 20));
		jLabelMsg.setBounds(286, 177, 166, 20);
		
		getContentPane().add(jLabelMsg);


		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
				//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
					}



					paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);
						//Vector<domain.Question> questions = facade.getQuestion(firstDay);
					// See the events on the selected day
						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						//						if (events.size() == 0)
						//							jButtonCreate.setEnabled(false);
						//						else
						//							jButtonCreate.setEnabled(true);

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
			}
		});
	}


	public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);
			System.out.println(d);



			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}

	
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());

		try {
			jLabelError.setText("");
			jLabelMsg.setText("");

			// Displays an exception if the query field is empty
			String inputQuery = jTextFieldQuery.getText();

			if (inputQuery.length() > 0) {

				// It could be to trigger an exception if the introduced string is not a number
				float inputPrice = Float.parseFloat(jTextFieldPrice.getText());

				if (inputPrice <= 0)
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
				else {

					// Obtain the business logic from a StartWindow class (local or remote)
					BLFacade facade = MainGUI.getBusinessLogic();

					facade.createQuestion(event, inputQuery, inputPrice);

					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryCreated"));
				}
			} else
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQuery"));
		} catch (EventFinished e1) {
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished") + ": "
					+ event.getDescription());
		} catch (QuestionAlreadyExist e1) {
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
		} catch (java.lang.NumberFormatException e1) {
			jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
		} catch (Exception e1) {

			e1.printStackTrace();

		}
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}