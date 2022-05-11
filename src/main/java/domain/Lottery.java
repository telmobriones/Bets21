package domain;

import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lottery {
	
	@Id
	private int lotteryID;
	private int jackpot;
	private Vector<Ticket> tickets;
	private boolean isRaffle;
	private int ticketPrice;
	
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

	
}
