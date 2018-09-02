package com.adidas.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class IncorrectDataInRequest extends RuntimeException {
	private static final Logger logger = LoggerFactory.getLogger(IncorrectDataInRequest.class);
	private String message;
	private int status;

	public IncorrectDataInRequest(String message) {
		this.message = message;
		logger.warn("In IncorrectDataInRequest Exception class : " + message);
		this.status = HttpStatus.BAD_REQUEST.value();

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
