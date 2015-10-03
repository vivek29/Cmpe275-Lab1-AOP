package com.vivek.model;
import java.util.UUID;
import org.springframework.stereotype.Repository;


@Repository
public class Secret {

	private UUID id;
	private String message;
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @param message
	 */
	public Secret(String message){
		this.message = message;
		this.id = UUID.randomUUID();
	}
	
}