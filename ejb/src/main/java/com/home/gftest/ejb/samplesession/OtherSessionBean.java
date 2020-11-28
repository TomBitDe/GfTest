package com.home.gftest.ejb.samplesession;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;


/**
 *
 */
@Stateless
@Local(com.home.gftest.ejb.samplesession.OtherSession.class)
public class OtherSessionBean implements OtherSession {
	private static final Logger LOG = Logger.getLogger(OtherSessionBean.class.getName());

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
