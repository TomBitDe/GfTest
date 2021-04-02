package com.home.gftest.ejb.refchange;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Session Bean implementation class XCalledSession<br>
 * <p>
 * Implements two local interfaces. CalledSessionLocal is common to all called session for overriding.
 */
@Stateless
@Local({CalledSessionLocal.class, XCalledSessionLocal.class})
public class XCalledSession implements CalledSessionLocal {
	public static final Logger LOG = LogManager.getLogger(XCalledSession.class);

	/**
	 * Default constructor.
	 */
	public XCalledSession() {
		super();

		LOG.info("--> XCalledSession");
		LOG.info("<-- XCalledSession");
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
