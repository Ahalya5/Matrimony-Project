package com.app.matrimony.controllerAdvice;

public class ObjectInvalidException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectInvalidException(String message) {
        super(message);
    }

    public ObjectInvalidException() {
    }
}