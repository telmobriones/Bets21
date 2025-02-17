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
public class Pronostic {
	@Id
	@GeneratedValue
	private Integer pronID;
	private String pronDescription;
	private float pronOdd;
	private Question pronQuestion;
	private boolean pronResult;
	private boolean pronClosed;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Bet> pronBets;
	
	public Pronostic(float odd, String pronDescription, Question pronQuestion) {
		super();

		this.pronDescription = pronDescription;
		this.pronOdd = odd;
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

	public float getPronOdd() {
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
	
	public void addBetToPronostic(Bet bet) {
		this.pronBets.add(bet);
	}
	public boolean getPronResult() {
		return pronResult;
	}

	public void setPronResult(boolean pronResult) {
		this.pronResult = pronResult;
	}

	public boolean isPronClosed() {
		return pronClosed;
	}

	public void setPronClosed() {
		this.pronClosed = true;
	}
	public ArrayList<Bet> getBets4Pronostic(){
		return (ArrayList<Bet>) this.pronBets;
	}

}
