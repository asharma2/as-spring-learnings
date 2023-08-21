package com.as.batch.exception;

public class SchedulerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SchedulerException() {
	}

	public SchedulerException(String error) {
		super(error);
	}

	public SchedulerException(Throwable cause) {
		super(cause);
	}

	public SchedulerException(String error, Throwable cause) {
		super(error, cause);
	}

}
