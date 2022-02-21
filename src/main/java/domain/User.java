package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String username;
	private String password;
	
	public User (String pUsername, String pPassword) {
		super();
		this.username = pUsername;
		this.password = pPassword;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
