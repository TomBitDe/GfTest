package com.home.gftest.ejb.samplesession;


import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Session Bean implementation class ControllerSessionBean
 */
@Stateless
@Local(com.home.gftest.ejb.samplesession.ControllerSession.class)
public class ControllerSessionBean implements ControllerSession {
	private static final Logger LOG = LogManager.getLogger(ControllerSessionBean.class.getName());

	/**
	 * Simple call of an other local bean can be overwritten in ejb-jar.xml
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
