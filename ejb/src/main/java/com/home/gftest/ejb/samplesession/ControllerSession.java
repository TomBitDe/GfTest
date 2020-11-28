package com.home.gftest.ejb.samplesession;

/**
 * Controller session interface
 */
public interface ControllerSession {
	/**
	 * Control the call of an other local bean.
	 * 
	 * @return the return value of the called bean
	 */
	public boolean control();
}
