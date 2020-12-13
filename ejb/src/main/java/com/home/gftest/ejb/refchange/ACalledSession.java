package com.home.gftest.ejb.refchange;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class ACalledSession<br>
 * <p>
 * Implements two local interfaces. CalledSessionLocal is common to all called session for overriding.
 */
@Stateless
@Local({CalledSessionLocal.class, ACalledSessionLocal.class})
public class ACalledSession implements CalledSessionLocal {
	public static final Logger LOG = Logger.getLogger(ACalledSession.class);

	/**
	 * Default constructor.
	 */
	public ACalledSession() {
		super();

		LOG.info("--> ACalledSession");
		LOG.info("<-- ACalledSession");
	}

	/**
	 * @see CalledSessionLocal#called()
	 */
	@Override
	public void called() {
		LOG.info("--> called");
		LOG.info("<-- called");
	}
}
