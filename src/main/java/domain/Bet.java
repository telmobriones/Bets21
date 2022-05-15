package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Bet {
	@Id
	@GeneratedValue
	private Integer betID;
	private int betMoney;
	private User betUser;
	private boolean isMultipleBet;
	private int betStatus; // 0 if undecided, 1 if won, 2 if lost
	@ManyToMany(mappedBy = "pronBets")
	private List<Pronostic> betPronostics;

	public Bet(int betMoney, User betUser, boolean isMultipleBet, ArrayList<Pronostic> betPronostics) {
		super();
		this.betMoney = betMoney;
		this.betUser = betUser;
		this.isMultipleBet = isMultipleBet;
		this.betStatus = 0;
		this.betPronostics = betPronostics;
	}

	public int getBetID() {
		return betID;
	}

	public int getBetMoney() {
		return betMoney;
	}

	public void setBetMoney(int betMoney) {
		this.betMoney = betMoney;
	}

	public User getBetUser() {
		return betUser;
	}

	public String getBetUsername() {
		return betUser.getUsername();
	}

	public Pronostic getBetPronostic() {
		if (!isMultipleBet) {
			return betPronostics.get(0);
		} else {
			return null;
		}
	}

	public boolean isBetMultiple() {
		return isMultipleBet;
	}

	public ArrayList<Pronostic> getMultipleBetPronostic() {
		ArrayList<Pronostic> betProns = new ArrayList<Pronostic>();
		betProns.addAll(this.betPronostics);
		return betProns;
	}

	public void addPronosticToMultipleBet(Pronostic p) {
		this.betPronostics.add(p);
	}

	public void betWon() {
		this.betStatus = 1;
	}

	public void betLost() {
		this.betStatus = 2;
	}
	
	public String betStatus() {
		switch(this.betStatus) {
		case 1:
			return "won";
		case 2:
			return "lost";
		}
		return "";
	}

	public float getMultipleBetEarnings() {
		float odd = 1;
		for (Pronostic p : betPronostics) {
			odd = odd * p.getPronOdd();
		}
		return odd * betMoney;
	}

	public float getSimpleBetEarnings() {
		Pronostic p = getBetPronostic();
		if (p != null) {
			return p.getPronOdd() * betMoney;
		} else {
			return 0;
		}
	}

}
