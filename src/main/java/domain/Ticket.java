package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ticket {

	@Id
	private int ticketID;
	private Lottery lottery;
	private User user;
	private int price;
	
	public Ticket(int ticketID, Lottery lottery, User user, int price) {
		super();
		this.ticketID = ticketID;
		this.lottery = lottery;
		this.user = user;
		this.price = price;
	}

	public int getTicketID() {
		return ticketID;
	}

	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
