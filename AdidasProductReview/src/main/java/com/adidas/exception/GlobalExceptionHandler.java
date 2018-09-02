package com.adidas.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
//import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError error = new ApiError();
		error.setMessage(ex.getMessage());
		error.setStatus(status);
		logger.error(ex.getMessage());
		ex.printStackTrace();
		return new ResponseEntity<>(error, status);
	}

	@ExceptionHandler(com.adidas.exception.JsonProcessingException.class)
	public ResponseEntity<ApiError> jsonProcessingException(com.adidas.exception.JsonProcessingException e)
			throws Exception {
		ApiError error = new ApiError();
		error.setMessage(e.message);
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		logger.error(e.getMessage());
		e.printStackTrace();
		return new ResponseEntity<ApiError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(com.adidas.exception.NotFoundException.class)
	public ResponseEntity<ApiError> notFoundException(com.adidas.exception.NotFoundException e) throws Exception {
		ApiError error = new ApiError();
		error.setMessage(e.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND);
		logger.error(e.getMessage());
		e.printStackTrace();
		return new ResponseEntity<ApiError>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(com.adidas.exception.IncorrectDataInRequest.class)
	public ResponseEntity<ApiError> notFoundException(com.adidas.exception.IncorrectDataInRequest e) throws Exception {
		ApiError error = new ApiError();
		error.setMessage(e.getMessage());
		error.setStatus(HttpStatus.BAD_REQUEST);
		logger.error(e.getMessage());
		e.printStackTrace();
		return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> generalException(Exception e) throws Exception {
		ApiError error = new ApiError();
		String errorMessage = e.getMessage();
		error.setMessage(errorMessage);
		logger.error(e.getMessage());
		e.printStackTrace();
		if (errorMessage.contains("404")) {
			error.setStatus(HttpStatus.NOT_FOUND);
			return new ResponseEntity<ApiError>(error, HttpStatus.NOT_FOUND);
		} else {
			error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<ApiError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
