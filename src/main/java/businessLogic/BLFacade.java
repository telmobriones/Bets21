package businessLogic;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;

//import domain.Booking;
import domain.Question;
import domain.User;
import domain.Bet;
import domain.Event;
import domain.Message;
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

	public Event getEventByN(int evNumber);

	public Pronostic getPronosticByID(int pronID);


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


	public void setPronosticResult(Pronostic betPronostic, boolean pronResult);

	/**
	 * This method adds a new bet to a certain pronostic
	 *
	 * @param the money betted
	 * @param the user that made the bet
	 * @param the pronostic that the bet is related to
	 * @return the created bet, or null, or an exception
	 */
	public Bet addBetToPronostic(int betMoney, User betUser, boolean isMultipleBet, ArrayList<Pronostic> betPronostics);
	/**
	 * This method creates a movement
	 * 
	 * @param Type of movement
	 * @param Amount of money
	 * @param The user who create the movement
	 * @param The event it's related to
	 * @param The question it's related to

	 * @return new movement
	 */
	public Movement createMovement(String movType, float money, User pUser, Event pEvent, Question pQuestion);



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

	public float updateBalance(User pUser, float pMoney);

	public void updateUserBet(User betUser, Bet bet);

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
	
	/**
	 * This method add a message
	 * 
	 * @param The remitent
	 * @param The desinatary
	 * @param The date the message was sent
	 * @return a new message
	 * 
	 */
	public Message createMessage(User remitent, String destinataryUsername, String formatDate, String Message);
	
	/**
	 * This method retrieves from the database all the messages related to that chat
	 * 
	 * @param the remitent of the messages
	 * @param the destinatary of the messages
	 * @return collection of messages
	 */

	public Vector<Message>getMessagesForThisChat(String pRemitent, String pDestinataryUsername);
	
	/**
	 * This method chech if a user exists
	 * 
	 * @param username
	 * @return if the user exist
	 * 
	 */
	public boolean existUser(String pUsername);


}