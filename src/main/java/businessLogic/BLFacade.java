package businessLogic;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;

//import domain.Booking;
import domain.Question;
import domain.User;
import domain.Bet;
import domain.Event;
import domain.Lottery;
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
	
	public User getUser(String uName);


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


//	/**
//	 * 
//	 * @return user if logged, null if there's no logged user
//	 * 
//	 */
//	public User getLogUser();


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
	public Pronostic createPronostic(float pronOdd, String pronDescription, Question pronQuestion);


	public void questionSolution(Question pronosticQuestion, Pronostic correctPronostic);

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
	 * @param The description of the event it's related to
	 * @param The question it's related to

	 * @return new movement
	 */
	public float createMovement(String movType, float betMoney, User pUser, String pEventDesc, String pQuestionDesc);


	public Bet makeBet(int betMoney, User betUser, boolean isMultipleBet, ArrayList<Pronostic> betPronostics, String movType, String pEventDesc, String pQuestionDesc);

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


//	/**
//	 * This method updates the question with a new pronostic
//	 * 
//	 * @param the question to be updated
//	 * @param the new pronostic
//	 * @return nothing
//	 */
//	public void updateQuestion(Question question, Pronostic pronostic);

//	/**
//	 * This method updates user's balance
//	 * 
//	 * @param user
//	 * @param amount of money to be added
//	 * @return new balance after update
//	 * 
//	 */
//	public float updateBalance(User pUser, float pMoney);

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

	public ArrayList<Message>getMessagesForThisChat(String pRemitent, String pDestinataryUsername);
	
	public ArrayList<Message> getMessagesForThisUser(String username);


	public ArrayList<Movement> getUserMovements(String username);


	/**
	 * This method checks if a user exists
	 * 
	 * @param username
	 * @return if the user exist
	 * 
	 */
	public boolean existUser(String pUsername);


	/**
	 * This method distributes the prize of 
	 * the last lottery that is not closed among all 
	 * the participants who have bought a ticket
	 * 
	 * @param the id of the lottery
	 * 
	 * @param the new price for the next lottery
	 * @return the winner username
	 */
	public String giveJackpot(int lotID);

	/**
	 * This method creates and return a new Lottery
	 * 
	 * @param the price of the tickets
	 * 
	 * @return nothing
	 */
	public void createLottery(int ticketPrice);


	/**
	 * This method buys a ticket for the user and a lottery
	 * 
	 * @param the username who buys the ticket
	 * @param the id of the lottery
	 * @param the description of the movement
	 * @return an error code, 0 if there are no errors
	 */
	public int buyTicket(String username, int lotID, String movDesc);

	/**
	 * This method gets the last active lottery
	 * 
	 * @return lottery
	 */
	public Lottery getLastActiveLottery();

	/**
	 * This method returns the players of a lottery
	 * 
	 * @param the lottery
	 * @return a list of players
	 */
	public ArrayList<User> getPlayersLottery(int lotteryID);

	/**
	 * This method a lottery with a given a id
	 *  
	 * @param the lottery id
	 * @return the lottery
	 */

	public Lottery getLotteryByID(int lotteryID);


}