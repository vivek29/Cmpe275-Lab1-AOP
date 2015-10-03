package com.vivek.CustomException;

public class SecretException extends Exception {

	private String message=null;
	
	public SecretException() {
        super();
    }

	 public SecretException(Throwable cause) {
	        super(cause);
	 }
	
    public SecretException(String message) {
        super(message);
        this.message = message;
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
	
}