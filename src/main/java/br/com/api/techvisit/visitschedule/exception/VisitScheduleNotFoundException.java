package br.com.api.techvisit.visitschedule.exception;

public class VisitScheduleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7851284676599120306L;

	public VisitScheduleNotFoundException(String message) {
		super(message);
	}

}
