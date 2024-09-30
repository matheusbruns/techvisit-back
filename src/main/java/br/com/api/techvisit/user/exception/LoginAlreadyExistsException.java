package br.com.api.techvisit.user.exception;

public class LoginAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2078839328042678570L;

	public LoginAlreadyExistsException(String message) {
		super(message);
	}
}
