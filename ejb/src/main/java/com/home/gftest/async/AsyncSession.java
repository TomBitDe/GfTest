package com.home.gftest.async;

import java.util.concurrent.Future;

/**
 * Async session interface.
 */
public interface AsyncSession {
	/**
	 * Fire and forget because it is a void return
	 */
	public void asyncFireAndForgetMethod();

	/**
	 * Controlled because it is a future return
	 *
	 * @param duration the Sleep time inside the method to simulate long work
	 *
	 * @return the Future result as a String
	 */
	public Future<String> asyncControlledMethod(int duration);

	/**
	 * Controlled with exception because it is a future return
	 *
	 * @param duration the Sleep time inside the method to simulate long work
	 *
	 * @return the Future result as a String
	 *
	 * @throws AsyncControlledMethodException in case the duration is less or equal zero
	 */
	public Future<String> asyncControlledMethodWithCustomEx(int duration) throws AsyncControlledMethodException;
}
