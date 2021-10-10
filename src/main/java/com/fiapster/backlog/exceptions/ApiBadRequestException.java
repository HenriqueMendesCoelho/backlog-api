package com.fiapster.backlog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiBadRequestException extends ReflectiveOperationException{
	private static final long serialVersionUID = 1L;

	public ApiBadRequestException(String message) {
		super(message);
	}

	public ApiBadRequestException(Throwable cause) {
		super(cause);
	}
	
	
}
