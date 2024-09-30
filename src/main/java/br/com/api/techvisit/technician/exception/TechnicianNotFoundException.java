package br.com.api.techvisit.technician.exception;

public class TechnicianNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 63003946238938429L;

	public TechnicianNotFoundException(String message) {
		super(message);
	}

}
