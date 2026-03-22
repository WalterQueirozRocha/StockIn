package com.otaviowalter.stockin.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.otaviowalter.stockin.exception.exceptions.BusinessException;
import com.otaviowalter.stockin.exception.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusiness(
	        BusinessException ex,
	        HttpServletRequest request
	) {
	    return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(
	        Exception ex,
	        HttpServletRequest request
	) {
	    return buildResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status, HttpServletRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(status.getReasonPhrase());
		error.setMessage(message);
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

}
