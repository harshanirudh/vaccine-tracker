package com.harsha.tracker.vaccinetracker.exceptions;

public class IncorrectArgumentsForSubscriptionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IncorrectArgumentsForSubscriptionException(String errorMessage) {
		super(errorMessage);
	}
}
