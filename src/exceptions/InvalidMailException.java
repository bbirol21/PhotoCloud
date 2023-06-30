package exceptions;

/**
 * InvalidMailException is a specialized error that was being thrown if the mail is invalid
 * 
 * @author student
 *
 */
public class InvalidMailException extends Exception{
	
	private String message;
	
	/**
	 * 
	 * @param message
	 */
	public InvalidMailException(String message) {
		this.message = message;
	}
	/**
	 * toString method is for returning the message
	 */
	@Override
	public String toString() {
		return message;
	}
	
}
