package exceptions;

public class NoCommentException extends Exception{
	
	private String message;
	
	public NoCommentException(String message) {
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
