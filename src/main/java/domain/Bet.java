package domain;
import java.util.ArrayList;

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
	private boolean multipleBet;
	private Pronostic betPronostic;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArrayList<Pronostic> multipleBetPronostic;
	
	public Bet(int betID, int betMoney, User betUser, Pronostic betPronostic) {
		this.betID = betID;
		this.betMoney = betMoney;
		this.betUser = betUser;
		this.multipleBet = false;
		this.betPronostic = betPronostic;
		this.multipleBetPronostic = null;
	}
	
	public Bet(int betID, int betMoney, User betUser, ArrayList<Pronostic> multipleBetPronostic) {
		this.betID = betID;
		this.betMoney = betMoney;
		this.betUser = betUser;
		this.multipleBet = true;
		this.betPronostic = null;
		this.multipleBetPronostic = multipleBetPronostic;
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
		return betPronostic;
	}
	public boolean isBetMultiple() {
		return multipleBet;
	}
	public ArrayList<Pronostic> getMultipleBetPronostic(){
		return multipleBetPronostic;
	}
	public void addPronosticToMultipleBet(Pronostic p) {
		this.multipleBetPronostic.add(p);
	}
	public float getMultipleBetOdd() {
		float odd = 1;
		for(Pronostic p : multipleBetPronostic) {
			odd = odd * p.getPronOdd();
		}
		return odd;
	}

}
