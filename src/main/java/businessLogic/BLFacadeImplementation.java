package businessLogic;

import java.util.ArrayList;
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

		//		if (new Date().compareTo(event.getEventDate()) > 0)
		//			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

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
	/**
	 * This method checks if a user can LogIn with the introduced credentials
	 * 
	 * @param username wich is trying to get logged in
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
	 * 
	 * @return user if logged, null if there's no logged user
	 * 
	 */
	public User getLogUser() {
		dbManager.open(false);
		this.logUser = dbManager.findUser(logUser.getUsername());
		dbManager.close();
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
		boolean regSuccess = dbManager.createUser(pUsername, String.valueOf(pPassword));
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
	//	public int updateBalance(User pUser, int pMoney) {
	//		dbManager.open(false);
	//		int newBalance = dbManager.updateBalance(pUser,pMoney);
	//		dbManager.close();
	//		return newBalance;
	//	}
	//	


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

	@Override
	public Pronostic findPronosticByDescription(String pronDescription) {
		dbManager.open(false);
		Pronostic pron = dbManager.findPronosticByDescription(pronDescription);
		dbManager.close();
		return pron;
	}

	@Override
	public Pronostic createPronostic(int pronOdd, String pronDescription, Question pronQuestion) {
		dbManager.open(false);
		Pronostic pron = dbManager.createPronostic(pronOdd, pronDescription, pronQuestion);
		dbManager.close();
		return pron;
	}




	@Override
	public void setPronosticResult(Pronostic betPronostic, boolean pronResult) {
		dbManager.open(false);
		dbManager.setPronosticResult(betPronostic, pronResult);
		dbManager.close();

	}

	/**
	 * This method adds a new bet to the pronostic
	 *
	 * @param the money betted
	 * @param the user that made the bet
	 * @param the pronostic that the bet is related to
	 * @return the created bet, or null, or an exception
	 */
	@Override
	public Bet addBetToPronostic(int betMoney, User betUser, boolean isMultipleBet, ArrayList<Pronostic> betPronostics) {
		dbManager.open(false);
		Bet bet = dbManager.addBetToPronostic(betMoney, betUser, isMultipleBet, betPronostics);
		dbManager.close();
		return bet;
	}

	@Override
	public float createMovement(String movType, float betMoney, User pUser, String pEventDesc, String pQuestionDesc) {
		dbManager.open(false);
		float newBalance = dbManager.createMovement(movType, betMoney, pUser, pEventDesc, pQuestionDesc);
		dbManager.close();
		return newBalance;
	}


	public Bet makeBet(int betMoney, User betUser, boolean isMultipleBet, ArrayList<Pronostic> betPronostics, String movType, String pEventDesc, String pQuestionDesc) {
		dbManager.open(false);
		Bet bet = dbManager.makeBet(betMoney,betUser,isMultipleBet, betPronostics, movType, pEventDesc, pQuestionDesc);
		dbManager.close();
		return bet;
	}


	/**
	 * This method updates the question with a new pronostic
	 * 
	 * @param the question to be updated
	 * @param the new pronostic
	 * @return nothing
	 */
	@Override
	public void updateQuestion(Question question, Pronostic pronostic) {
		dbManager.open(false);
		dbManager.updateQuestion(question, pronostic);
		dbManager.close();
	}

	/**
	 * This method updates user's balance
	 * 
	 * @param user
	 * @param amount of money to be added
	 * @return new balance after update
	 * 
	 */
	public float updateBalance(User pUser, float pMoney) {
		dbManager.open(false);
		float newBalance = dbManager.updateBalance(pUser,pMoney);
		dbManager.close();
		return newBalance;
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
	public Vector<Message> getMessagesForThisChat(String pRemitentUsername, String pDestinataryUsername) {
		dbManager.open(false);
		Vector<Message> messages = dbManager.getMessagesForThisChat(pRemitentUsername, pDestinataryUsername);
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
	 * This method distributes the prize of 
	 * the last lottery that is not closed among all 
	 * the participants who have bought a ticket
	 */
	
	public void giveJackpot(Lottery lot) {
		dbManager.open(false);
		dbManager.giveJackpot(lot);
		dbManager.close();
	}
	/**
	 * This method buys a ticket for the active lottery
	 */
	@Override
	public void buyTicket(User user, Lottery lottery, String movDesc) {
		dbManager.open(false);
		dbManager.buyTicket(user, lottery, movDesc);
		dbManager.close();	
	}


	/**
	 * This method gets the last active lottery
	 */
	public Lottery getLastActiveLottery() {
		dbManager.open(false);
		Lottery lot = dbManager.getLastActiveLottery();
		dbManager.close();	
		return lot;
	}

	/**
	 * This method returns the players of a lottery
	 * @return a list of players
	 */
	public ArrayList<User> getPlayersLottery(Lottery lottery) {
		dbManager.open(false);
		ArrayList<User> players = dbManager.getPlayersLottery(lottery);
		dbManager.close();
		return players;
	}


	/**
	 * This method creates a new Lottery
	 */
	public Lottery createLottery(int ticketPrice) {
		dbManager.open(false);
		Lottery lot = dbManager.createLottery(ticketPrice);
		dbManager.close();
		return lot;
	}
}




