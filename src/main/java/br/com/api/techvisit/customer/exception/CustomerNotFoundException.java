package br.com.api.techvisit.customer.exception;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2136579644071968238L;

	public CustomerNotFoundException(String message) {
		super(message);
	}

}
