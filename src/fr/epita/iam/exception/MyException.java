package fr.epita.iam.exception;

public class MyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	
	/**
	 * @param message
	 */
	public MyException(String message) {
		this.message = message;
	}

	/**
	 * @return
	 *
	 */
	@Override
	public String getMessage() {
		return message;
	}

}