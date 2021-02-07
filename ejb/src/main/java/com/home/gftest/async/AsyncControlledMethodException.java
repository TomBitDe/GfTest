package com.home.gftest.async;

/**
 * Custom exception for tests.
 */
public class AsyncControlledMethodException extends Exception {
	private static final long serialVersionUID = 1L;

	public AsyncControlledMethodException(String message) {
		super(message);
	}
}
