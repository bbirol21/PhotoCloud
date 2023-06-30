package exceptions;
/**
 * InvalidPasswrodException is a specialized error that was being thrown if the password is invalid
 * 
 * @author student
 *
 */
public class InvalidPasswordException extends Exception{
	
	private String message;
	/**
	 * 
	 * @param message
	 */
	public InvalidPasswordException(String message) {
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
