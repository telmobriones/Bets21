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

	public void setBalance(int balance) {
		this.balance = balance;
	}
}
