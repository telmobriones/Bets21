package domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bet {
	@Id
	@GeneratedValue
	private int betID;
	private int betMoney;
	private User betUser;
	private Pronostic betPronostic;
	
	public Bet(int betID, int betMoney, User betUser, Pronostic betPronostic) {
		this.betID = betID;
		this.betMoney = betMoney;
		this.betUser = betUser;
		this.betPronostic = betPronostic;
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

}
