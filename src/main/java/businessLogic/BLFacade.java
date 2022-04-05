package businessLogic;

import java.util.Vector;
import java.util.Date;

//import domain.Booking;
import domain.Question;
import domain.User;
import domain.Event;
import domain.Movement;
import domain.Pronostic;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

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
	Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;

	
	
	public Question getQuestionByN(int qNumber);
	
	public Event getEventByN(int qNumber);
	
	
	/**
	 * This method checks if a user can LogIn with the introduced credentials
	 * 
	 * @param username wich is trying to get logged in
	 * @param password associated with the specified username
	 * @return User
	 * 
	 */
	public User checkCredentials(String pUsername, char[] password);
	
	/**
	 * This method checks if the login is already done
	 * 
	 * @return true if user is logged, false if there's no logged user
	 * 
	 */
	public boolean checkCurrentLoginStatus();
	
	
	/**
	 * 
	 * @return user if logged, null if there's no logged user
	 * 
	 */
	public User getLogUser();
	
	
	/**
	 * This method tries to register a user with the introduced credentials
	 * 
	 * @param username wich is trying to get logged in
	 * @param password associated with the specified username
	 * @return true or false
	 * 
	 */
	public boolean registerUser(String pUsername, char[] password);
	
	
	
	/**
	 * This method finds a pronostic by its description
	 * 
	 * @param Description
	 * @return The Pronostic
	 * 
	 */
	public Pronostic findPronosticByDescription(String pronDescription);
	
	
	/**
	 * This method creates a pronostic
	 * 
	 * @param Odd to be gained
	 * @param The description
	 * @param The question it's related to
	 * @return new balance after update
	 * 
	 */
	public Pronostic createPronostic(int pronOdd, String pronDescription, Question pronQuestion);
	
	public Movement createMovement(String movType, int money, User pUser);

	
	
	/**
	 * This method invokes the data access to create a new event
	 * 
	 * @param username wich is trying to get logged in
	 * @param password associated with the specified username
	 * @return true or false
	 * 
	 */
	public boolean createEvent(String pDescription, Date pDate);
	
	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date);
	
	
	/**
	 * This method retrieves the questions of a given event
	 * 
	 * @param event in which questions are retrieved
	 * @return collection of questions
	 */
	@WebMethod
	public Vector<Question> getQuestions(Event event);
	
	
	/**
	 * This method updates the question with a new pronostic
	 * 
	 * @param the question to be updated
	 * @param the new pronostic
	 * @return nothing
	 */
	public void updateQuestion(Question question, Pronostic pronostic);
	public void updateMovement(User user, Movement movement);

	public int updateBalance(User pUser, int pMoney);

	
	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	
	@WebMethod
	public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();


}
