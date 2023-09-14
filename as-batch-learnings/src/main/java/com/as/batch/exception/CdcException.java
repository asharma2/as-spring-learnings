package com.as.batch.exception;

public class CdcException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CdcException() {
	}

	public CdcException(String error) {
		super(error);
	}

	public CdcException(Throwable cause) {
		super(cause);
	}

	public CdcException(String error, Throwable cause) {
		super(error, cause);
	}

}
