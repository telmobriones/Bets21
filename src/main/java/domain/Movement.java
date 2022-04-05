package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movement {
	
	@Id
	private int movID;
	private String movementType;
	private int money;
	private int balance;
	private User user;
	private Event event;
	private Question question;
	
	public Movement(int pMovID, int pMoney, String pMovType, User pUser, Event pEvent, Question pQuestion) {
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

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
