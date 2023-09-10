package com.as.batch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class BookDoestNotExist extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
