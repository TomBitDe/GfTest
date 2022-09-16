package com.home.gftest.ejb.samplesession;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A new session
 */
@Stateless
@Local(com.home.gftest.ejb.samplesession.NewSession.class)
public class NewSessionBean implements NewSession {
	private static final Logger LOG = LogManager.getLogger(NewSessionBean.class.getName());

	public NewSessionBean() {
		super();
		LOG.trace("--> NewSessionBean");
		LOG.trace("<-- NewSessionBean");
	}

	@Override
	public boolean businessMethod() {
		boolean ret = false;

		LOG.trace("--> businessMethod");

		ret = true;

		LOG.trace("<-- businessMethod");

		return ret;
	}
}
