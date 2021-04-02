package com.home.gftest.ejb.refchange;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Session Bean implementation class SecondCallerSession
 */
@Stateless
@Local(com.home.gftest.ejb.refchange.SecondCallerSessionLocal.class)
public class SecondCallerSession implements SecondCallerSessionLocal {
	public static final Logger LOG = LogManager.getLogger(SecondCallerSession.class);

	/**
	 * Two beans implement the CalledSessionLocal interface.
	 * Specify in ejb-jar.xml the bean to use.
	 */
	@EJB
	private CalledSessionLocal calledSession;

	/**
	 * Default constructor.
	 */
	public SecondCallerSession() {
		super();
		LOG.info("--> SecondCallerSession");
		LOG.info("<-- SecondCallerSession");
	}

	/**
	 * Call a method from other bean
	 */
	@Override
	public void call() {
		LOG.info("--> call");

		calledSession.called();

		LOG.info("<-- call");
	}
}
