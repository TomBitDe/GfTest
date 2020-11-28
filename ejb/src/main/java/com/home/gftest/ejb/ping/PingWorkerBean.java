package com.home.gftest.ejb.ping;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class PingWorkerBean
 */
@Stateless(name = "PingAlias")
@LocalBean
public class PingWorkerBean {
	private static final Logger LOG = Logger.getLogger(PingWorkerBean.class);

	/**
	 * Default constructor.
	 */
	public PingWorkerBean() {
		super();
		LOG.info("--> PingWorkerBean");
		LOG.info("<-- PingWorkerBean");
	}

	public String ping(String who) {
		LOG.info("--> ping (" + who + ")");
		LOG.info("<-- ping (" + who + ")");

		return "pong";
	}
}
