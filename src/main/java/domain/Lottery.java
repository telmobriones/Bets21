package domain;

import java.util.ArrayList;
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

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();;


	public Lottery(int lotteryID, int jackpot, boolean isRaffle, int ticketPrice) {
		super();
		this.lotteryID = lotteryID;
		this.jackpot = jackpot;
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

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
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

	public ArrayList<User> getParticipants() {
		if(tickets != null) {
			ArrayList<User> players = new ArrayList<User>();
			for (Ticket t:tickets) {
				players.add(t.getUser());
				System.out.println(t.getUser().getUsername() + "\n");
			}
			return players;
		}
		else {
			return null;
		}
	}
}
