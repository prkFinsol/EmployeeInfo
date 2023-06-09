package com.imaginnovate.empinfo.exception;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9079454849611061074L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(final String message) {
		super(message);
	}
}
