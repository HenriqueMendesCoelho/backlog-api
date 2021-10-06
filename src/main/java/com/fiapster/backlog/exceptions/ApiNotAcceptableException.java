package com.fiapster.backlog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ApiNotAcceptableException extends ReflectiveOperationException{
	private static final long serialVersionUID = 1L;

	public ApiNotAcceptableException(String message) {
		super(message);
	}
}
