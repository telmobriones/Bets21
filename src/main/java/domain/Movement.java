package domain;

import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movement {
	
	@Id
	private int movID;
	private String movementType;
	private float money;
	private int balance;
	private User user;
	private Event event;
	private Question question;
	
	public Movement(int pMovID, float pMoney, String pMovType, User pUser, Event pEvent, Question pQuestion) {
		super();
		this.movID = pMovID;
		this.movementType = pMovType;
		this.money= pMoney;
		this.user = pUser;
		this.event = pEvent;
		this.question = pQuestion;
	}

	public int getMovID() {
		return movID;
	}

	public void setMovID(int movID) {
		this.movID = movID;
	}

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
