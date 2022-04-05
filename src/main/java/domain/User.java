package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.ArrayList;

@Entity
public class User {
	@Id
	private String username;
	private String password;
	private boolean isAdmin;
	private int balance; // Kontuko saldoa
	private ArrayList<Bet> bets = new ArrayList<Bet>();
	private ArrayList<Movement> movements = new ArrayList<Movement>(); // Erabiltzailearen diru mugimenduen lista
	
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

	public void updateBalance(int money) {
		System.out.println("Money before update: " + this.balance);
		this.balance += money;
		System.out.println("Money after update: " + this.balance);
		
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
