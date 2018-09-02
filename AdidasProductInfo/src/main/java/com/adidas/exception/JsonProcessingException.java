package com.adidas.exception;

public class JsonProcessingException extends RuntimeException {

	String message;

	public JsonProcessingException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
