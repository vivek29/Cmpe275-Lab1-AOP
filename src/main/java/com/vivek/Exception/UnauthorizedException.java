package com.vivek.Exception;

/**
 * @author Vivek
 UnauthorizedException is the custom exception thrown
 */
public class UnauthorizedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String message = null;
	
    public UnauthorizedException() {
        super();
    }
    
    /**
     * @param cause
     */
    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
    
    /**
     * @param message
     */
    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}