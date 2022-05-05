package domain;

import java.util.Date;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message {

	@Id
	private int messageID;
	private User remitent;
	private User destinatary;
	private String mesDate;
	private String message;

	public Message(int mesID, User remitent, User destinatary, String mesDate, String message) {
		super();
		this.messageID = mesID;
		this.remitent = remitent;
		this.destinatary = destinatary;
		this.mesDate = mesDate;
		this.message = message;
	}

	public int getMesID() {
		return messageID;
	}

	public void setMesID(int mesID) {
		this.messageID = mesID;
	}

	public User getRemitent() {
		return remitent;
	}

	public void setRemitent(User remitent) {
		this.remitent = remitent;
	}


	public String getMesDate() {
		return mesDate;
	}

	public void setMesDate(String mesDate) {
		this.mesDate = mesDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getDestinatary() {
		return destinatary;
	}

	public void setDestinatary(User destinatary) {
		this.destinatary = destinatary;
	}




}
