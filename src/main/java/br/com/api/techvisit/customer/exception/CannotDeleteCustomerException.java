package br.com.api.techvisit.customer.exception;

public class CannotDeleteCustomerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CannotDeleteCustomerException(String message) {
		super(message);
	}
}
