package domain;

import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Movement {
	
	@Id
	@GeneratedValue
	private Integer movID;
	private String movementType;
	private float money;
	private User user;
	private String eventDesc;
	private String questionDesc;
	


	public Movement(String movementType, float money, User user, String eventDesc, String questionDesc) {
		super();
		this.movementType = movementType;
		this.money = money;
		this.user = user;
		this.eventDesc = eventDesc;
		this.questionDesc = questionDesc;
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



	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
