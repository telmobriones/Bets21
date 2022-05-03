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
	private int balance; // Kontuko saldoa
	//@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArrayList<Bet> bets = new ArrayList<Bet>();
	//@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Movement> movements = new Vector<Movement>(); // Erabiltzailearen diru mugimenduen lista
	
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
	
	public boolean isUserAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean pIsAdmin) {
		this.isAdmin = pIsAdmin;
	}

	public int getBalance() {
		return balance;
	}
	
	public void addBet(Bet bet) {
		this.bets.add(bet);
	}

	public void updateBalance(int money) {
		System.out.println("Money before update: " + this.balance);
		this.balance += money;
		System.out.println("Money after update: " + this.balance);
		
	}
		
	public Vector<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Vector<Movement> movements) {
		this.movements = movements;
	}

	/**
	 * Add a new movemento to this user
	 * 
	 * @param the movement to be added
	 */
	public void newMovement(Movement movement) {
		movements.add(movement);
	}
}
