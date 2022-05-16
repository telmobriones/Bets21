package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private Integer ticketID;
	private User user;
	private Lottery lot;
	private int price;
	
	public Ticket(User user, Lottery lot, int price) {
		super();
		this.user = user;
		this.lot = lot;
		this.price = price;
	}

	public int getTicketID() {
		return ticketID;
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
