package domain;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bet {
	@Id
	private int betID;
	private int betMoney;
	private User betUser;
	private Pronostic betPronostic;
	
	public Bet(Integer betMoney, User betUser, Pronostic betPronostic) {
		this.betMoney = betMoney;
		this.betUser = betUser;
		this.betPronostic = betPronostic;
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
