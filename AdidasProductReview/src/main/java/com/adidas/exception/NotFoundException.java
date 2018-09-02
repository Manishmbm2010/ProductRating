package com.adidas.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

	private static final Logger logger = LoggerFactory.getLogger(NotFoundException.class);
	private String message;
	private int status;

	public NotFoundException(String message) {
		this.message = message;
		logger.warn("In Not Found Exception class : " + message);
		this.status = HttpStatus.NOT_FOUND.value();

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
