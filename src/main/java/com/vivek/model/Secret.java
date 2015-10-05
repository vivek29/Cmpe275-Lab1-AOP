package com.vivek.Model;
import java.util.UUID;
import org.springframework.stereotype.Repository;


@Repository
public class Secret {

	private UUID id;
	private String message;
	
	/**
	 Default Constructor
	 */
	public Secret(){
		this.message = "This is default secret..";
	}
	
	/**
	 Constructor
	 * @param message
	 */
	public Secret(String message){
		this.message = message;
	}
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}
	
	/**
	 Generates the unique UUID
	 */
	public void generateUUID(){
		this.id = UUID.randomUUID();
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

	
}