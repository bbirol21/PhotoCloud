package exceptions;

/**
 * 
 * @author student
 *
 */

public class FilterOutOfBondsException extends Exception{
	
	private String message;
	
	/**
	 * 
	 * @param message
	 */
	
	public FilterOutOfBondsException(String message) {
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
