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
	private ArrayList<Integer> movements = new ArrayList<Integer>(); // Erabiltzailearen diru mugimenduen lista
	
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
		this.balance += money;
		this.movements.add(money);
	}
}
