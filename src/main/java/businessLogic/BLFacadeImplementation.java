package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
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

		// if (new Date().compareTo(event.getEventDate()) > 0)
		// throw new
		// EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	public Question getQuestionByN(int qNumber) {
		dbManager.open(false);
		Question q = dbManager.findQuestionByN(qNumber);
		return q;
	}

	public Event getEventByN(int evNumber) {
		dbManager.open(false);
		Event ev = dbManager.findEventByN(evNumber);
		return ev;
	}

	public Pronostic getPronosticByID(int pronID) {
		dbManager.open(false);
		Pronostic pron = dbManager.findPronosticByID(pronID);
		return pron;
	}

	public User getUser(String uName) {
		dbManager.open(false);
		User user = dbManager.findUser(uName);
		return user;
	}

	/**
	 * This method checks if a user can LogIn with the introduced credentials
	 * 
	 * @param username which is trying to get logged in
	 * @param password associated with the specified username
	 * @return user
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
	 * This method tries to register a user with the introduced credentials
	 * 
	 * @param username which is trying to get logged in
	 * @param password associated with the specified username
	 * @return true or false
	 * 
	 */
	public boolean registerUser(String pUsername, char[] pPassword) {
		dbManager.open(false);
		boolean regSuccess = dbManager.createUser(pUsername, String.valueOf(pPassword));
		dbManager.close();
		return regSuccess;
	};

	/**
	 * This method invokes the data access to create a new event
	 * 
	 * @param username which is trying to get logged in
	 * @param password associated with the specified username
	 * @return true or false
	 * 
	 */
	public boolean createEvent(String pDescription, Date pDate) {
		dbManager.open(false);
		boolean ev = dbManager.createEvent(pDescription, pDate);
		dbManager.close();
		return ev;
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
	 * This method invokes the data access to retrieve the questions of a given
	 * event
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

	@Override
	public Pronostic createPronostic(float pronOdd, String pronDescription, Question pronQuestion) {
		dbManager.open(false);
		Pronostic pron = dbManager.createPronostic(pronOdd, pronDescription, pronQuestion);
		dbManager.close();
		return pron;
	}

	@Override
	public void questionSolution(Question pronosticQuestion, Pronostic correctPronostic) {
		dbManager.open(false);
		dbManager.questionSolution(pronosticQuestion, correctPronostic);
		dbManager.close();

	}

	@Override
	public float createMovement(String movType, float betMoney, User pUser, String pEventDesc, String pQuestionDesc) {
		dbManager.open(false);
		float newBalance = dbManager.createMovement(movType, betMoney, pUser, pEventDesc, pQuestionDesc);
		dbManager.close();
		return newBalance;
	}

	public Bet makeBet(int betMoney, User betUser, boolean isMultipleBet, ArrayList<Pronostic> betPronostics,
			String movType, String pEventDesc, String pQuestionDesc) {
		dbManager.open(false);
		Bet bet = dbManager.makeBet(betMoney, betUser, isMultipleBet, betPronostics, movType, pEventDesc,
				pQuestionDesc);
		dbManager.close();
		return bet;
	}

	public void updateUserBet(User betUser, Bet bet) {
		dbManager.open(false);
		dbManager.updateUserBet(betUser, bet);
		dbManager.close();
	}

	@Override
	public Message createMessage(User remitent, String destinataryUsername, String formateDate, String message) {
		dbManager.open(false);
		Message mes = dbManager.createMessage(remitent, destinataryUsername, formateDate, message);
		dbManager.close();
		return mes;
	}

	/**
	 * This method retrieves from the database all the messages related to that chat
	 * 
	 * @param the remitent of the messages
	 * @param the destinatary of the messages
	 * @return collection of messages
	 */
	@Override
	public ArrayList<Message> getMessagesForThisChat(String pRemitentUsername, String pDestinataryUsername) {
		dbManager.open(false);
		ArrayList<Message> messages = dbManager.getMessagesForThisChat(pRemitentUsername, pDestinataryUsername);
		dbManager.close();
		return messages;
	}

	public ArrayList<Message> getMessagesForThisUser(String username) {
		dbManager.open(false);
		ArrayList<Message> messages = dbManager.getMessagesForThisUser(username);
		dbManager.close();
		return messages;
	}

	public ArrayList<Movement> getUserMovements(String username) {
		dbManager.open(false);
		ArrayList<Movement> movements = dbManager.getUserMovements(username);
		dbManager.close();
		return movements;
	}

	/**
	 * This method chech if a user exists
	 * 
	 * @param username
	 * @return if the user exist
	 * 
	 */
	public boolean existUser(String pUsername) {
		dbManager.open(false);
		boolean existUser = dbManager.existUser(pUsername);
		dbManager.close();
		return existUser;
	}

	/**
	 * This method distributes the prize of the last lottery that is not closed
	 * among all the participants who have bought a ticket
	 * 
	 * @param the id of the lottery
	 * @return the winner username
	 */

	public String giveJackpot(int lotID) {
		dbManager.open(false);
		String winner = dbManager.giveJackpot(lotID);
		dbManager.close();
		return winner;
	}

	/**
	 * This method buys a ticket for the user and a lottery
	 * 
	 * @param the use who buys the ticket
	 * @param the id of the lottery
	 * @param the description of the movement
	 * @return an error code, 0 if there are no errors
	 */
	public int buyTicket(String username, int lotID, String movDesc) {
		dbManager.open(false);
		int codeError = dbManager.buyTicket(username, lotID, movDesc);
		dbManager.close();
		return codeError;
	}

	/**
	 * This method gets the last active lottery
	 * 
	 * @return lottery
	 */
	public Lottery getLastActiveLottery() {
		dbManager.open(false);
		Lottery lot = dbManager.getLastActiveLottery();
		dbManager.close();
		return lot;
	}

	/**
	 * This method returns the players of a lottery
	 * 
	 * @return a list of players
	 */
	public ArrayList<User> getPlayersLottery(int lotteryID) {
		dbManager.open(false);
		ArrayList<User> players = dbManager.getPlayersLottery(lotteryID);
		dbManager.close();
		return players;
	}

	/**
	 * This method creates a new Lottery
	 */
	public void createLottery(int ticketPrice) {
		dbManager.open(false);
		dbManager.createLottery(ticketPrice);
		dbManager.close();
	}

	/**
	 * This method a lottery with a given a id
	 * 
	 * @param the lottery id
	 * @return the lottery
	 */

	public Lottery getLotteryByID(int lotteryID) {
		dbManager.open(false);
		Lottery lot = dbManager.getLotteryByID(lotteryID);
		return lot;

	}
}
