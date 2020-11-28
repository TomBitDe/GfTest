package com.home.gftest.ejb.samplesession;


import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;


/**
 * Session Bean implementation class ControllerSessionBean
 */
@Stateless
@Local(com.home.gftest.ejb.samplesession.ControllerSession.class)
public class ControllerSessionBean implements ControllerSession {
	private static final Logger LOG = Logger.getLogger(ControllerSessionBean.class.getName());

	/**
	 * Simple call of an other local bean
	 */
	@EJB
	private NewSession newSession;

	public ControllerSessionBean() {
		super();
		LOG.info("--> ControllerSessionBean");
		LOG.info("<-- ControllerSessionBean");
	}

	@Override
	public boolean control() {
		LOG.info("--> control");

		boolean ret = newSession.businessMethod();

		LOG.info("--> control");

		return ret;
	}
}
