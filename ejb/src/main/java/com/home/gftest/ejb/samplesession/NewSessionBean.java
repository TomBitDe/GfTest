package com.home.gftest.ejb.samplesession;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * A new session
 */
@Stateless
@Local(com.home.gftest.ejb.samplesession.NewSession.class)
public class NewSessionBean implements NewSession {
	private static final Logger LOG = Logger.getLogger(NewSessionBean.class.getName());

	public NewSessionBean() {
		super();
		LOG.info("--> NewSessionBean");
		LOG.info("<-- NewSessionBean");
	}

	@Override
	public boolean businessMethod() {
		boolean ret = false;

		LOG.info("--> businessMethod");

		ret = true;

		LOG.info("<-- businessMethod");

		return ret;
	}
}
