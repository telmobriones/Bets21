package businessLogic;

//hola
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.User;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;
	private User logUser;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();
		dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	/**
	 * This method checks if a user can LogIn with the introduced credentials
	 * 
	 * @param username wich is trying to get logged in
	 * @param password associated with the specified username
	 * @return true or false
	 * 
	 */
	public User checkCredentials(String pUsername, char[] pPassword) {
		dbManager.open(false);
		User user = dbManager.checkCredentials(pUsername, String.valueOf(pPassword));
		if (user != null && logUser == null) {
			dbManager.close();
			logUser = user;
			System.out.println(user.getUsername() + " logged in.");
			return user;
		}
		System.out.println("Unsuccessful loggin by " + pUsername + " user.");
		dbManager.close();
		return null;
	};
	
	/**
	 * This method checks if the login is already done
	 * 
	 * @return true if user is logged, false if there's no logged user
	 * 
	 */
	public boolean checkCurrentLoginStatus() {
		return logUser != null;
	}
	
	/**
	 * 
	 * @return user if logged, null if there's no logged user
	 * 
	 */
	public User getLogUser() {
		return this.logUser;
	}
	
	/**
	 * This method tries to register a user with the introduced credentials
	 * 
	 * @param username wich is trying to get logged in
	 * @param password associated with the specified username
	 * @return true or false
	 * 
	 */
	public boolean registerUser(String pUsername, char[] pPassword) {
		dbManager.open(false);
		boolean regSuccess = false;
		if(dbManager.findUser(pUsername)==null) {
			User u = dbManager.createUser(pUsername, String.valueOf(pPassword));
			if(u!=null) {
				regSuccess=true;
			}
		}
		dbManager.close();
		return regSuccess;
	};
	
	

	/**
	 * This method updates user's balance
	 * 
	 * @param user
	 * @param amount of money to be added
	 * @return new balance after update
	 * 
	 */
	public int updateBalance(User pUser, int pMoney) {
		dbManager.open(false);
		int newBalance = dbManager.updateBalance(pUser,pMoney);
		dbManager.close();
		return newBalance;
	}
	
	
	
	/**
	 * This method invokes the data access to create a new event
	 * 
	 * @param username wich is trying to get logged in
	 * @param password associated with the specified username
	 * @return true or false
	 * 
	 */
	public boolean createEvent(String pDescription, Date pDate) {
		dbManager.open(false);
		Event ev = dbManager.createEvent(pDescription, pDate);
		dbManager.close();
		return (ev != null);
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}
	
	/**
	 * This method invokes the data access to retrieve the questions of a given event
	 * 
	 * @param event in which questions are retrieved
	 * @return collection of questions
	 */
	public Vector<Question> getQuestions(Event event) {
		dbManager.open(false);
		Vector<Question> questions = dbManager.getQuestions(event);
		dbManager.close();
		return questions;
	}
	
	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}


}
