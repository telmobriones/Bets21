package domain;

import java.util.ArrayList;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Lottery {

	@Id
	@GeneratedValue
	private Integer lotteryID;
	private int jackpot;
	private boolean isRaffle;
	private int ticketPrice;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();;

	public Lottery(int jackpot, int ticketPrice) {
		super();
		this.jackpot = jackpot;
		this.isRaffle = false;
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

	public void updateJackpot() {
		this.jackpot += this.ticketPrice;
	}
	/**
	 * This method creates a new ticket for the lottery
	 * 
	 * @param the user who buys the ticket
	 * @param the price of the ticket
	 * @return Ticket
	 */
	public Ticket createTicket(User user, int price) {
		Ticket t = new Ticket(user, this, price);
		tickets.add(t);
		updateJackpot();
		return t;
	}

	public void addTicket(Ticket t) {
		tickets.add(t);
	}

	public int getParticipantsNumber() {
		if (tickets != null) {
			return tickets.size();
		} else {
			return 0;
		}

	}

	public ArrayList<User> getParticipants() {
		ArrayList<User> players = new ArrayList<User>();
		if (!tickets.isEmpty()) {
			for (Ticket t : tickets) {
				players.add(t.getUser());
				System.out.println(t.getUser().getUsername() + "\n");
			}
		}
		return players;
		
	}

	/**
	 * This method chooses a winner for the lottery
	 */
	public User selectWinner() {
		int winnerPosition = (int) (Math.random() * getParticipantsNumber());
		User winner = getParticipants().get(winnerPosition);
		return winner;

	}
}
