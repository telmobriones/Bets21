package dataAccess;

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
import domain.Movement;
import domain.Pronostic;
import domain.Question;
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
		} else if (obj.getPassword().equals(pPassword)) {
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
	 * @throws UserAlreadyExist if the same user already exists for the event
	 */
	public User createUser(String name, String password) {
		System.out.println(">> DataAccess: createUser=>  name= " + name);
		db.getTransaction().begin();
		User user = new User(name, password, false);
		db.persist(user);
		db.getTransaction().commit();

		System.out.println("Gordeta " + name);
		return user;
	}


	/**
	 * This method adds a new pronostic to the DB
	 *
	 * @param the odd you gain
	 * @param the description of the pronostic
	 * @param the question of the pronostic
	 * @return the created pronostic, or null, or an exception
	 */
	public Pronostic createPronostic(int pOdd, String pDescription, Question pQuestion) {

		TypedQuery<Pronostic> query = db.createQuery("SELECT FROM Pronostic ORDER BY pronID DESC", Pronostic.class);
		Pronostic lastPronostic = query.getResultList().get(0);
		System.out.println("LastEvent: " + lastPronostic);
		int newPronID = lastPronostic.getPronID()+1;
		System.out.println("NewEventNumber: " + newPronID);

		db.getTransaction().begin();
		Pronostic pronostic = new Pronostic(newPronID, pOdd, pDescription, pQuestion);
		db.persist(pronostic);
		db.getTransaction().commit();
		System.out.println(pronostic.getPronDescription()+" added to the DB!");
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
	public Bet addBetToPronostic(int betMoney, User betUser, Pronostic betPronostic) {
		int newBetID;
		TypedQuery<Bet> query = db.createQuery("SELECT FROM Bet ORDER BY betID DESC", Bet.class);
		if(query.getResultList().size() != 0) {
			Bet lastBet = query.getResultList().get(0);
			System.out.println("LastBetID: " + lastBet.getBetID());
			newBetID = lastBet.getBetID()+1;
			System.out.println("NewBetNumber: " + newBetID);
		} else {
			newBetID = 0;
		}

		db.getTransaction().begin();
		Pronostic pronostic = findPronosticByID(betPronostic.getPronID());
		Bet bet = pronostic.addBetToPronostic(newBetID,betUser, betMoney);
		db.persist(pronostic);
		db.getTransaction().commit();
		return bet;
	}


	/**
	 * This method finds a pronostic by its description
	 *
	 * @param The description
	 * @return The Pronostic, null or exception
	 */
	public Pronostic findPronosticByDescription(String pDescription) {
		try {
			TypedQuery<Pronostic> query = db.createQuery("SELECT pron FROM Pronostic pron WHERE pron.pronDescription = ?1 ", Pronostic.class);
			query.setParameter(1, pDescription);
			return query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * set the pronostic result in the DB
	 *
	 * @param the pronostic
	 * @param the result (true or false)
	 * @return nothing
	 */
	public void setPronosticResult(Pronostic betPronostic, boolean pronResult) {
		db.getTransaction().begin();
		Pronostic pronostic = db.find(Pronostic.class, betPronostic.getPronID());
		pronostic.setPronResult(pronResult);
		db.persist(pronostic);
		db.getTransaction().commit();
	}

	public void updateUserBet(User betUser, Bet bet) {
		db.getTransaction().begin();
		User user = db.find(User.class, betUser.getUsername());
		user.addBet(bet);
		db.persist(user);
		db.getTransaction().commit();
		System.out.println(betUser.getUsername()+" has been updated with the new bet!");
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
	 * This method updates user's balance
	 * 
	 * @param user
	 * @param amount of money to be added
	 * @return new balance after update
	 * 
	 */
	//	public int updateBalance(User pUser, int pMoney) {
	//		db.getTransaction().begin();
	//		User u = db.find(User.class, pUser.getUsername());
	//		if(u != null) {
	//			u.updateBalance(pMoney);
	//			db.persist(u);
	//		} else {
	//			System.out.println("UNEXPECTED ERROR OCCURRED WHEN UPDATING BALANCE!");
	//		}
	//		db.getTransaction().commit();
	//		return u.getBalance();
	//	}

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
	 * @param pDescription      New event description
	 * @param pDate   			Date of the new event
	 * @return the new event or null if there is a problem
	 */
	public Event createEvent(String pDescription, Date pDate){
		System.out.println(">> DataAccess: createEvent=> eventDesc= " + pDescription + " Date= " + pDate);
		TypedQuery<Event> query = db.createQuery("SELECT FROM Event ORDER BY eventNumber DESC", Event.class);
		Event lastEvent = query.getResultList().get(0);
		System.out.println("LastEvent: " + lastEvent);
		int newEvNumber = lastEvent.getEventNumber()+1;
		System.out.println("NewEventNumber: " + newEvNumber);
		Event ev;
		if(!isThereEventByNumber(newEvNumber)) {
			db.getTransaction().begin();
			ev = new Event(newEvNumber, pDescription, pDate);
			db.persist(ev);
			db.getTransaction().commit();
			System.out.println("New event created successfully!");
		} else {
			ev = null;
		}
		return ev;

	}

	/**
	 * This method retrieves from the database if there is the event with a given ID number
	 * 
	 * @param ID number
	 * @return If there is the event
	 */
	public boolean isThereEventByNumber(int pN) {
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE eventNumber=?1", Event.class);
		query.setParameter(1,pN);
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


	/**
	 * This method updates the question with a new pronostic
	 * 
	 * @param the question to be updated
	 * @param the new pronostic to be added
	 * @return nothing
	 */
	public void updateQuestion(Question question, Pronostic pronostic) {
		db.getTransaction().begin();
		Question q = db.find(Question.class, question.getQuestionNumber());
		q.newPronostic(pronostic);
		db.getTransaction().commit();
		System.out.println(pronostic.getPronDescription() + " pronostic added to the question" + question.getQuestion());
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


	public Movement createMovement(String movType, int money, User pUser, Event pEvent, Question pQuestion) {

		TypedQuery<Movement> query = db.createQuery("SELECT FROM Movement ORDER BY movID DESC", Movement.class);
		Movement lastMovement = query.getResultList().get(0);
		System.out.println("LastMovement: " + lastMovement);
		int newMovID = lastMovement.getMovID()+1;
		System.out.println("NewMovNum: " + newMovID);



		db.getTransaction().begin();
		Movement movement = new Movement(newMovID,money, movType, pUser,pEvent,pQuestion);
		db.persist(movement);
		db.getTransaction().commit();
		System.out.println(movement+" added to the DB!");
		return movement;
	}

	public void updateMovement(User user, Movement movement) {
		db.getTransaction().begin();
		User us = db.find(User.class, user.getUsername());
		us.newMovement(movement);
		db.getTransaction().commit();
		System.out.println(movement + " movement added to the user" + user);
	}

	//	public int updateBalance(User pUser) {
	////		TypedQuery<Movement> query = db.createQuery("SELECT FROM Movement ORDER BY movID DESC", Movement.class);
	////		Movement lastMovement = query.getResultList().get(0);
	////		System.out.println("LastMovement: " + lastMovement);
	////		int newMovID = lastMovement.getMovID()+1;
	////		System.out.println("NewMovNum: " + newMovID);
	////		
	////		db.getTransaction().begin();
	////		Movement movement = new Movement(newMovID,money, movType);
	////		db.persist(movement);
	////		db.getTransaction().commit();
	////		System.out.println(movement+" added to the DB!");
	////		return movement;
	//	}

	/**
	 * This method updates user's balance
	 * 
	 * @param user
	 * @param amount of money to be added
	 * @return new balance after update
	 * 
	 */
	public int updateBalance(User pUser, int pMoney) {
		db.getTransaction().begin();
		User u = db.find(User.class, pUser.getUsername());
		if(u != null) {
			u.updateBalance(pMoney);
			db.persist(u);
		} else {
			System.out.println("UNEXPECTED ERROR OCCURRED WHEN UPDATING BALANCE!");
		}
		db.getTransaction().commit();
		return u.getBalance();
	}
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

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}


}