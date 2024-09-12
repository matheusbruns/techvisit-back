package br.com.api.techvisit.user.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5336154830335176806L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
