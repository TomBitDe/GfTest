package com.home.gftest.async;

/**
 * Async controller session interface.
 */
public interface AsyncControllerSession {
	/**
	 * Do the fire and forget call of an async local bean.
	 */
	public void fireAndForget();

	/**
	 * Do the controlled async call of an async local bean.
	 *
	 * @param duration the called methods sleep duration
	 */
	public void runAsyncCall(int duration);

	/**
	 * Do the cancel async call of an async local bean.
	 *
	 * @param duration the called methods sleep duration
	 */
	public void cancelAsyncCall(int duration);
}
