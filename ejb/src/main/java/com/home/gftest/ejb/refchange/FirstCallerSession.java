package com.home.gftest.ejb.refchange;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Session Bean implementation class FirstCallerSession
 */
@Stateless
@Local(com.home.gftest.ejb.refchange.FirstCallerSessionLocal.class)
public class FirstCallerSession implements FirstCallerSessionLocal {
	public static final Logger LOG = LogManager.getLogger(FirstCallerSession.class);

	/**
	 * Two beans implement the CalledSessionLocal interface.
	 * Specify with beanName="..." the bean to use.
	 */
	@EJB(beanName="XCalledSession")
	private CalledSessionLocal calledSession;

	/**
	 * Default constructor.
	 */
	public FirstCallerSession() {
		super();
		LOG.info("--> FirstCallerSession");
		LOG.info("<-- FirstCallerSession");
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
