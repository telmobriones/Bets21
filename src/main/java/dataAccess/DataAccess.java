package dataAccess;

import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Lottery;
import domain.Message;
import domain.Movement;
import domain.Pronostic;
import domain.Question;
import domain.Ticket;
import domain.User;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method checks if a user can LogIn with the introduced credentials
	 * 
	 * @param username wich is trying to get logged in
	 * @param password associated with the specified username
	 * @return user or null
	 * 
	 */
	public User checkCredentials(String pUsername, String pPassword) {
		User obj = db.find(User.class, pUsername);
		if (obj == null) {
			System.out.println("Username not found");
			return null;
		} else if (obj.checkPassword(pPassword)) {
			return obj;
		}
		System.out.println("Password is not correct");
		return null;
	}

	/**
	 * This method creates a new user, with a username and a password
	 *
	 * @param username name of the user
	 * @param password the password of the user
	 * @return the created user, or null, or an exception
	 * @throws true or false
	 */
	public boolean createUser(String name, String password) {
		System.out.println(">> DataAccess: createUser=>  name= " + name);
		User user = db.find(User.class, name);
		if (user == null) {
			db.getTransaction().begin();
			user = new User(name, password, false);
			db.persist(user);
			db.getTransaction().commit();
			System.out.println("Gordeta " + name);
			return true;
		} else {
			System.out.println(name + "jadanik existitzen da!");
			return false;
		}
	}

	/**
	 * This method adds a new pronostic to the DB
	 *
	 * @param the odd you gain
	 * @param the description of the pronostic
	 * @param the question of the pronostic
	 * @return the created pronostic, or null, or an exception
	 */
	public Pronostic createPronostic(float pOdd, String pDescription, Question pQuestion) {

		db.getTransaction().begin();
		Question q = db.find(Question.class, pQuestion.getQuestionNumber());
		Pronostic pronostic = q.newPronostic(pOdd, pDescription);
		db.persist(pronostic);
		db.getTransaction().commit();

		System.out.println(pronostic.getPronDescription() + " added to the DB!");
		return pronostic;
	}

	/**
	 * This method adds a new bet to a certain pronostic
	 *
	 * @param the money betted
	 * @param the user that made the bet
	 * @param the pronostic that the bet is related to
	 * @return the created bet, or null, or an exception
	 */
	public Bet addBetToPronostic(int betMoney, User betUser, boolean isMultipleBet,
			ArrayList<Pronostic> betPronostics) {

		db.getTransaction().begin();
		Bet bet;
		if (isMultipleBet) {
			bet = new Bet(betMoney, betUser, isMultipleBet, betPronostics);
			for (Pronostic pron : betPronostics) {
				Pronostic p = db.find(Pronostic.class, pron.getPronID());
				p.addBetToPronostic(bet);
			}
		} else {
			Pronostic p = db.find(Pronostic.class, betPronostics.get(0).getPronID());
			bet = new Bet(betMoney, betUser, isMultipleBet, betPronostics);
			p.addBetToPronostic(bet);
		}
		User user = db.find(User.class, betUser.getUsername());
		user.addBet(bet);
		db.persist(user);
		db.persist(bet);
		db.getTransaction().commit();
		return bet;
	}

	public float createMovement(String movType, float money, User pUser, String pEventDesc, String pQuestionDesc) {

		User user = db.find(User.class, pUser.getUsername());

		if (user != null) {
			db.getTransaction().begin();
			Movement mov = user.newMovement(movType, money, pEventDesc, pQuestionDesc);
			float newBalance = user.updateBalance(money);
			db.persist(user);
			db.getTransaction().commit();
			System.out.println(mov + " added to the DB!");
			return newBalance;

		} else {
			System.out.println("UNEXPECTED ERROR OCCURRED WHEN UPDATING BALANCE!");
			return -10000000;
		}

	}

	public Bet makeBet(int betMoney, User betUser, boolean isMultipleBet, ArrayList<Pronostic> betPronostics,
			String movType, String pEventDesc, String pQuestionDesc) {
		db.getTransaction().begin();
		Bet bet;
		User user = db.find(User.class, betUser.getUsername());
		if (user != null) {
			if (isMultipleBet) {
				bet = user.newBet(betMoney, isMultipleBet, betPronostics);
				for (Pronostic pron : betPronostics) {
					Pronostic p = db.find(Pronostic.class, pron.getPronID());
					p.addBetToPronostic(bet);
				}
			} else {
				bet = user.newBet(betMoney, isMultipleBet, betPronostics);
				Pronostic p = db.find(Pronostic.class, betPronostics.get(0).getPronID());
				p.addBetToPronostic(bet);
			}

			// CreateMovement
			Movement mov = user.newMovement(movType, -betMoney, pEventDesc, pQuestionDesc);
			System.out.println(mov + " added to the DB!");
			user.updateBalance(-betMoney);
			db.persist(user);
			db.getTransaction().commit();
			return bet;
		} else {
			System.out.println("UNEXPECTED ERROR OCCURRED!");
			return null;
		}
	}

	/**
	 * Solves all the bets related to a question
	 *
	 * @param the Question
	 * @param the correct Pronostic
	 * @return nothing
	 */
	public void questionSolution(Question pronosticQuestion, Pronostic correctPronostic) {
		Pronostic correctPron = db.find(Pronostic.class, correctPronostic.getPronID());
		Question q = db.find(Question.class, pronosticQuestion);
		ArrayList<Pronostic> pronostics = q.getPronostics();
		String evDescr = q.getEventDescription();
		String qDescr = q.getQuestion();

		db.getTransaction().begin();

		for (Pronostic p : pronostics) {
			if (p != correctPron) { // Pronostic is wrong

				p.setPronResult(false);
				p.setPronClosed();
				ArrayList<Bet> bets = p.getBets4Pronostic();

				for (Bet b : bets) { // All these bets are lost, no matter if they are simple or multiple

					// Sets the bet as lost
					Bet lostBet = db.find(Bet.class, b.getBetID());
					lostBet.betLost();

					// Tells the user the bet is lost
					User u = db.find(User.class, lostBet.getBetUsername());

					u.newMovement("Bet Lost", 0, evDescr, qDescr);
					db.persist(u);

				}

			} else { // Pronostic is right

				p.setPronResult(true);
				p.setPronClosed();

				ArrayList<Bet> bets = p.getBets4Pronostic();

				// All the bets with the right pronostic
				for (Bet b : bets) {
					if (!b.isBetMultiple()) { // The bet is simple and correct, so we inmediatelly return the money

						// We set the bet as won
						Bet wonBet = db.find(Bet.class, b.getBetID());
						wonBet.betWon();

						User u = db.find(User.class, wonBet.getBetUsername());
						float earnings = wonBet.getSimpleBetEarnings();

						// We give the earnings to the user
						u.newMovement("Simple Bet Won", earnings, evDescr, qDescr);
						u.updateBalance(earnings);

						db.persist(u);

					} else { // The bet is multiple, so we have to check if it is completelly solved

						Bet wonBet = db.find(Bet.class, b.getBetID());
						User u = db.find(User.class, wonBet.getBetUsername());
						ArrayList<Pronostic> prons = wonBet.getMultipleBetPronostic();
						Boolean allSolved = true;

						for (Pronostic pron : prons) {
							if (!pron.isPronClosed()) {
								allSolved = false;
								break;
							}
						}
						if (allSolved) { // Means all are solved and correct

							wonBet.betWon(); // We set de bet as won

							float earnings = wonBet.getMultipleBetEarnings();
							u.newMovement("Multiple Bet Won", earnings, evDescr, qDescr);
							u.updateBalance(earnings);

							db.persist(u);

						} else {
							// Nothing
						}
					}
				}
			}
		}

		q.setIsAnswered();
		db.persist(q);
		db.getTransaction().commit();

	}

	public void updateUserBet(User betUser, Bet bet) {
		db.getTransaction().begin();
		User user = db.find(User.class, betUser.getUsername());
		user.addBet(bet);
		db.persist(user);
		db.getTransaction().commit();
		System.out.println(betUser.getUsername() + " has been updated with the new bet!");
	}

	/**
	 * This method tries to find a username
	 *
	 * @param username name of the user
	 * @return the found user, or null
	 */
	public User findUser(String name) {
		User u = db.find(User.class, name);
		return u;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
		// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method creates a new event
	 * 
	 * @param pDescription New event description
	 * @param pDate        Date of the new event
	 * @return the new event or null if there is a problem
	 */
	public boolean createEvent(String pDescription, Date pDate) {

		db.getTransaction().begin();
		Event ev = new Event(pDescription, pDate);
		db.persist(ev);
		db.getTransaction().commit();
		System.out.println("New event created successfully!");
		return ev != null;

	}

	/**
	 * This method retrieves from the database if there is the event with a given ID
	 * number
	 * 
	 * @param ID number
	 * @return If there is the event
	 */
	public boolean isThereEventByNumber(int pN) {
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE eventNumber=?1", Event.class);
		query.setParameter(1, pN);
		List<Event> ev = query.getResultList();
		return (ev == null);

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the questions of a given event
	 * 
	 * @param event in which questions are retrieved
	 * @return collection of questions
	 */
	public Vector<Question> getQuestions(Event event) {
		System.out.println(">> DataAccess: getQuestions");
		Vector<Question> res = new Vector<Question>();
		TypedQuery<Question> query = db.createQuery("SELECT qu FROM Question qu WHERE qu.event=?1", Question.class);
		query.setParameter(1, event);
		List<Question> questions = query.getResultList();
		for (Question qu : questions) {
			System.out.println(qu.toString());
			res.add(qu);
		}
		return res;
	}

	public Question findQuestionByN(int qNumber) {
		Question q = db.find(Question.class, qNumber);
		return q;
	}

	public Event findEventByN(int evNumber) {
		Event ev = db.find(Event.class, evNumber);
		return ev;
	}

	public Pronostic findPronosticByID(int pronID) {
		Pronostic pron = db.find(Pronostic.class, pronID);
		return pron;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public ArrayList<Movement> getUserMovements(String username) {
		System.out.println(">> DataAccess: getUserMovements");
		User user = db.find(User.class, username);
		ArrayList<Movement> movements = user.getMovements();
		return movements;
	}

	/**
	 * This method updates user's balance
	 * 
	 * @param user
	 * @param amount of money to be added
	 * @return new balance after update
	 * 
	 */

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	/**
	 * This method add a message
	 * 
	 * @param The remitent
	 * @param The desinatary
	 * @param The date the message was sent
	 * @return a new message
	 * 
	 */
	public Message createMessage(User pRemitent, String pDestinataryUsername, String pDate, String pMessage) {

		db.getTransaction().begin();
		User destinatary = findUser(pDestinataryUsername);
		System.out.println(">> DataAccess: createMessage=> remitent= " + pRemitent + "desinatary= " + destinatary
				+ "message= " + pMessage + " Date= " + pDate);
		User remitent = db.find(User.class, pRemitent.getUsername());
		Message mes = remitent.sendMessage(destinatary, pDate, pMessage);
		destinatary.addRecivedMessage(mes);
		db.persist(remitent);
		db.persist(destinatary);
		db.getTransaction().commit();
		System.out.println("The new message has been saved successfully!");
		return mes;

	}

	/**
	 * This method retrieves from the database all the messages related to that chat
	 * 
	 * @param the remitent of the messages
	 * @param the destinatary of the messages
	 * @return collection of messages
	 */
	public ArrayList<Message> getMessagesForThisChat(String pRemitentUsername, String pDestinataryUsername) {
		System.out.println(">> DataAccess: getMessagesForThisChat");
		User remitent = findUser(pRemitentUsername);
		User destinatary = findUser(pDestinataryUsername);
		ArrayList<Message> res = new ArrayList<Message>();
		TypedQuery<Message> query = db.createQuery(
				"SELECT FROM Message WHERE (remitent=?1 AND destinatary=?2) OR (remitent=?2 AND destinatary=?1) ORDER BY messageID ASC",
				Message.class);
		query.setParameter(1, remitent);
		query.setParameter(2, destinatary);
		List<Message> messages = query.getResultList();
		for (Message mes : messages) {
			System.out.println(mes.toString());
			res.add(mes);
		}
		return res;
	}

	public ArrayList<Message> getMessagesForThisUser(String username) {
		User u = db.find(User.class, username);
		return u.getRecievedMessages();
	}

	/**
	 * This method chech if a user exists
	 * 
	 * @param username
	 * @return if the user exist
	 * 
	 */
	public boolean existUser(String pUsername) {
		User user = findUser(pUsername);
		System.out.println("Check if exists: " + user);
		if (user == null)
			return false;
		else
			return true;
	}

	/**
	 * This method distributes the prize of the last lottery that is not closed
	 * among all the participants who have bought a ticket
	 * 
	 * @return
	 */
	public String giveJackpot(int lotID) {
		System.out.println("The lotteryID is: " + lotID);

		Lottery l = getLotteryByID(lotID);
		User winner = l.selectWinner();
		System.out.println("The user " + winner.getUsername() + " has won " + l.getJackpot() + "€");
		l.setRaffle(true);

		createMovement("Lottery won", l.getJackpot(), winner, null, null);
		System.out.println("Lottery is raffled: " + l.isRaffle());
		return winner.getUsername();

	}

	/**
	 * This method creates a new lottery
	 * 
	 * @return nothing
	 */
	public void createLottery(int ticketPrice) {
		System.out.println(">> DataAccess: createLottery");
		db.getTransaction().begin();
		Lottery lot = new Lottery(0, ticketPrice);
		db.persist(lot);
		db.getTransaction().commit();
		System.out.println("The new lottery has been created successfully!");
	}

	/**
	 * This method gets the id of last active lottery
	 * 
	 * @return lottery
	 */
	public Lottery getLastActiveLottery() {
		System.out.println(">> DataAccess: getLastActiveLottery");
		TypedQuery<Lottery> query = db
				.createQuery("SELECT lot FROM Lottery lot WHERE isRaffle=false ORDER BY lotteryID DESC", Lottery.class);
		try {
			Lottery lastLottery = query.getResultList().get(0);
			System.out.println("The last lottery whitout raffle is " + lastLottery.getLotteryID());
			return lastLottery;
		} catch (Exception e) {
			System.out.println("There are not active lotteries");
			return null;
		}

	}

	/**
	 * This method a lottery with a given a id
	 * 
	 * @param the lottery id
	 * @return the lottery
	 */

	public Lottery getLotteryByID(int lotteryID) {
		Lottery lot = db.find(Lottery.class, lotteryID);
		return lot;
	}

	/**
	 * This method buys a ticket for the user and a lottery
	 * 
	 * @param the username who buys the ticket
	 * @param the id of the lottery
	 * @param the description of the movement
	 * 
	 * @return an error code, 0 if there are no errors
	 */
	public int buyTicket(String username, int lastLotteryID, String movDesc) {
		System.out.println(">> DataAccess: buyTicket");

		Lottery lot = db.find(Lottery.class, lastLotteryID);
		User user = db.find(User.class, username);
		ArrayList<User> players = lot.getParticipants();

		boolean contains = players.contains(user);
		boolean enoughMoney = user.getBalance() >= lot.getTicketPrice();

		if (contains == true)
			return 1;
		else if (enoughMoney == false)
			return 2;

		System.out.println("The player " + user.getUsername() + " can play this lottery");

		// create new Ticket
		db.getTransaction().begin();
		Ticket t = lot.createTicket(user, lot.getTicketPrice());
		db.persist(lot);
		db.getTransaction().commit();
		user.addTicket(t);
		createMovement(movDesc, -t.getPrice(), user, null, null);
		System.out.println("Ticket added " + t);

		System.out.println("Tickets: " + lot.getTickets());
		return 0;
	}

	/**
	 * This method returns the players of a lottery
	 * 
	 * @return a list of players
	 */
	public ArrayList<User> getPlayersLottery(int lotteryID) {
		System.out.println(">> DataAccess: getPlayersLottery");
		Lottery lottery = getLotteryByID(lotteryID);
		ArrayList<User> players = lottery.getParticipants();
		System.out.println("The players are: " + players);

		return players;

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

}