package com.home.gftest.ejb.samplesession;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *  An other session
 */
@Stateless
@Local(com.home.gftest.ejb.samplesession.OtherSession.class)
public class OtherSessionBean implements OtherSession {
	private static final Logger LOG = LogManager.getLogger(OtherSessionBean.class.getName());

	public OtherSessionBean() {
		super();
		LOG.info("--> OtherSessionBean");
		LOG.info("<-- OtherSessionBean");
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
