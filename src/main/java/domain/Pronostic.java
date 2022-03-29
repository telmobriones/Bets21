package domain;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pronostic {
	@Id
	private int pronID;
	private String pronDescription;
	private int pronOdd;
	private Question pronQuestion;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArrayList<Bet> pronBets;

}
