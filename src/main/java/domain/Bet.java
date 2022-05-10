package domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Bet {
	@Id
	@GeneratedValue
	private int betID;
	private int betMoney;
	private User betUser;
	private boolean isMultipleBet;
	@ManyToMany(mappedBy = "pronBets")
	private List<Pronostic> betPronostics;
	
	public Bet(int betID, int betMoney, User betUser, boolean isMultipleBet, ArrayList<Pronostic> betPronostics) {
		this.betID = betID;
		this.betMoney = betMoney;
		this.betUser = betUser;
		this.isMultipleBet = isMultipleBet;
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
	public Pronostic getBetPronostic() {
		if(!isMultipleBet) {
			return betPronostics.get(0);
		} else {
			return null;
		}
	}
	public boolean isBetMultiple() {
		return isMultipleBet;
	}
	public ArrayList<Pronostic> getMultipleBetPronostic(){
		return (ArrayList<Pronostic>) betPronostics;
	}
	public void addPronosticToMultipleBet(Pronostic p) {
		this.betPronostics.add(p);
	}
	public float getMultipleBetOdd() {
		float odd = 1;
		for(Pronostic p : betPronostics) {
			odd = odd * p.getPronOdd();
		}
		return odd;
	}

}
