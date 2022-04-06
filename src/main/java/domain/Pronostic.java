package domain;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pronostic {
	@Id
	@GeneratedValue
	private int pronID;
	private String pronDescription;
	private int pronOdd;
	private Question pronQuestion;
	private boolean pronResult;
	private boolean pronClosed;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArrayList<Bet> pronBets;
	
	public Pronostic(int pronID, int odd, String pronDescription, Question pronQuestion) {
		super();
		this.pronID = pronID;
		this.pronOdd = odd;
		this.pronDescription = pronDescription;
		this.pronQuestion = pronQuestion;
		this.pronResult = false;
		this.pronClosed = false;
		this.pronBets = new ArrayList<Bet>();
	}
	
	public int getPronID() {
		return pronID;
	}

	public String getPronDescription() {
		return pronDescription;
	}

	public void setPronDescription(String pronDescription) {
		this.pronDescription = pronDescription;
	}

	public int getPronOdd() {
		return pronOdd;
	}

	public void setPronOdd(int pronOdd) {
		this.pronOdd = pronOdd;
	}

	public Question getPronQuestion() {
		return pronQuestion;
	}

	public void setPronQuestion(Question pronQuestion) {
		this.pronQuestion = pronQuestion;
	}
	
	public Bet addBetToPronostic(int betID, User betUser, int betMoney) {
		Bet bet = new Bet(betID, betMoney, betUser, this);
		this.pronBets.add(bet);
		return bet;
	}
	public boolean isPronResult() {
		return pronResult;
	}

	public void setPronResult(boolean pronResult) {
		this.pronResult = pronResult;
	}

	public boolean isPronClosed() {
		return pronClosed;
	}

	public void setPronClosed(boolean pronClosed) {
		this.pronClosed = pronClosed;
	}

}
