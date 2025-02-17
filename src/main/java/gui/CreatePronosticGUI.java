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


public class CreatePronosticGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private JTextField textFieldPronostic;
	private JTextField textFieldOdds;
	private final JLabel lblErrors= new JLabel("");
	
	private Event pronEvent;
	private Question pronQuestion;
	
	private static BLFacade facade = MainGUI.getBusinessLogic();

	public CreatePronosticGUI()
	{
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit() throws Exception
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(750, 535));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreatePronostic"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 247, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(526, 459, 112, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 274, 406, 160));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				pronEvent=ev;
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});

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
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				int qNumber = (int) tableModelQueries.getValueAt(i, 0); // obtain Question object
				pronQuestion = facade.getQuestionByN(qNumber);
				System.out.println(pronQuestion.getQuestion());
			}
		});
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		
		
		////////////////////////////////////////////////////
		JLabel lblPronostic = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Pronostic")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPronostic.setBounds(480, 262, 90, 15);
		getContentPane().add(lblPronostic);
		
		JLabel lblPronosticOdds = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PronosticOdds")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPronosticOdds.setBounds(480, 329, 225, 14);
		getContentPane().add(lblPronosticOdds);
		
		textFieldPronostic = new JTextField();
		textFieldPronostic.setBounds(480, 283, 134, 19);
		getContentPane().add(textFieldPronostic);
		textFieldPronostic.setColumns(10);
		
		textFieldOdds = new JTextField();
		textFieldOdds.setBounds(480, 353, 134, 19);
		textFieldOdds.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_PERIOD)) { 
		            e.consume();// if it's not a number, a period or a backspace, ignore the event
		        }
		     }
		});
		getContentPane().add(textFieldOdds);
		textFieldOdds.setColumns(10);
		
		lblErrors.setBounds(480, 419, 225, 15);
		lblErrors.setForeground(Color.RED);
		getContentPane().add(lblErrors);
		
		JButton btnAddPronostic = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreatePronostic")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAddPronostic.setBounds(480, 384, 200, 30);
		btnAddPronostic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String pronDescription = textFieldPronostic.getText();
				float pronOdd = Float.parseFloat(textFieldOdds.getText());
				boolean error = false;

//				if (new Date().compareTo(pronEvent.getEventDate()) > 0) {
//					lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPastEvent"));
//					error = true;
//				}
				if (pronQuestion == null) {
					lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorSelectQuestion"));
					error = true;
				}
				if (pronEvent == null) {
					lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorSelectEvent"));
					error = true;
				}
				if ((pronEvent != null) && (pronQuestion != null)) {
					if (pronDescription.equals("")) {
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEnterData"));
						error = true;
					}
					if (pronOdd <= 0 || textFieldOdds.getText().length()==0) {
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
						error = true;
					}
				}

				if (!error) {
					Pronostic pronostico = facade.createPronostic(pronOdd, pronDescription, pronQuestion);
					if(pronostico != null) {
						lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("PronosticAdded"));
						textFieldPronostic.setText("");
						textFieldOdds.setText("");
					}
				}

			}
		});
		getContentPane().add(btnAddPronostic);
		

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
