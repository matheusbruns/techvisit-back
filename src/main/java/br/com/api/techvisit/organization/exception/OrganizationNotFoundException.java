package br.com.api.techvisit.organization.exception;

public class OrganizationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2136579644071968238L;

	public OrganizationNotFoundException(String message) {
		super(message);
	}

}
