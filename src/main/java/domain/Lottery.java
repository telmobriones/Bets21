package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Lottery {
	
	@Id
	private int lotteryID;
	
	private int jackpot;
	private boolean isRaffle;
	private int ticketPrice;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Ticket> tickets = new Vector<Ticket>();;
	
	
	public Lottery(int lotteryID, int jackpot, Vector<Ticket> tickets, boolean isRaffle, int ticketPrice) {
		super();
		this.lotteryID = lotteryID;
		this.jackpot = jackpot;
		this.tickets = tickets;
		this.isRaffle = isRaffle;
		this.ticketPrice = ticketPrice;
	}

	public int getLotteryID() {
		return lotteryID;
	}

	public void setLotteryID(int lotteryID) {
		this.lotteryID = lotteryID;
	}

	public int getJackpot() {
		return jackpot;
	}

	public void setJackpot(int jackpot) {
		this.jackpot = jackpot;
	}

	public Vector<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Vector<Ticket> tickets) {
		this.tickets = tickets;
	}

	public boolean isRaffle() {
		return isRaffle;
	}

	public void setRaffle(boolean isRaffle) {
		this.isRaffle = isRaffle;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	/**
	 * This method creates a new ticket for the lottery
	 * 
	 * @param the id of the ticket
	 * @param the user who buys the ticket
	 * @param the price of the ticket
	 * @return Ticket
	 */
	public void addTicket(Ticket t) {
			tickets.add(t);
	}
	
	public int getParticipantsNumber() {
		if(tickets != null) {
			return tickets.size();
		}
		else {
			return 0;
		}

	}
}
