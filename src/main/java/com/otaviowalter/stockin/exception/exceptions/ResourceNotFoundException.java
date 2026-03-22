package com.otaviowalter.stockin.exception.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}