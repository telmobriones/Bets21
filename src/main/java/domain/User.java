package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String username;
	private String password;
	private boolean isAdmin;
	
	public User (String pUsername, String pPassword, boolean pIsAdmin) {
		super();
		this.username = pUsername;
		this.password = pPassword;
		this.isAdmin = pIsAdmin;
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
}
