package com.home.gftest.ejb.ping;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class PingControllerBean
 */
@Stateless
@LocalBean
public class PingControllerBean {
	private static final Logger LOG = Logger.getLogger(PingControllerBean.class);

	@EJB(beanName="PingAlias")
	PingWorkerBean pingAlias;

	@EJB
	PingWorkerBean pingWorker;

	@EJB
	PingWorkerBean pingProcessor;

	/**
	 * Default constructor.
	 */
	public PingControllerBean() {
		super();
		LOG.info("--> PingControllerBean");
		LOG.info("<-- PingControllerBean");
	}

	public String runPing() {
		LOG.info("--> runPing");
		LOG.info("<-- runPing");

		return pingAlias.ping("PingAlias") + pingWorker.ping("PingWorker") + pingProcessor.ping("PingProcessor");
	}
}
