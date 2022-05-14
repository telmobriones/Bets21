package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Vector;

@Entity
public class User {
	@Id
	private String username;
	private String password;
	private boolean isAdmin;
	private float balance; // Kontuko saldoa
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private ArrayList<Bet> bets = new ArrayList<Bet>();
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArrayList<Movement> movements = new ArrayList<Movement>(); // Erabiltzailearen diru mugimenduen lista
	
	private Vector<Ticket> tickets = new Vector<Ticket>();
	
	public User (String pUsername, String pPassword, boolean pIsAdmin) {
		super();
		this.username = pUsername;
		this.password = pPassword;
		this.isAdmin = pIsAdmin;
		this.balance = 0;
		
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean checkPassword(String pass) {
		return this.password.equals(pass);
	}
	
	public boolean isUserAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean pIsAdmin) {
		this.isAdmin = pIsAdmin;
	}

	public float getBalance() {
		return balance;
	}
	
	public void addBet(Bet bet) {
		this.bets.add(bet);
	}

	public float updateBalance(float money) {
		System.out.println("Money before update: " + this.balance);
		this.balance += money;
		System.out.println("Money after update: " + this.balance);
		return this.balance;
	}
	
	/**
	 * Add a new movement to this user
	 * 
	 */
	public Movement newMovement(int movID, String movementType, float money, String eventDesc, String questionDesc) {
		System.out.println("Creating movement...");
		Movement mov = new Movement(movID, movementType, money, this, eventDesc, questionDesc);
		movements.add(mov);
		System.out.println("Movement added!");
		return mov;
	}
		
	public ArrayList<Movement> getMovements() {
		return this.movements;
	}


	/**
	 * Add a new ticket to this user
	 * 
	 * @param the ticket to be added
	 */
	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
	}
}
