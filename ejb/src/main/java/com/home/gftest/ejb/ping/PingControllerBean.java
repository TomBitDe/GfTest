package com.home.gftest.ejb.ping;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class PingControllerBean<br>
 * <p>
 * Purpose is to call local ping worker session beans.
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

	/**
	 * Run the worker local session beans
	 * 
	 * @return pong for every called worker
	 */
	public String runPing() {
		LOG.info("--> runPing");
		LOG.info("<-- runPing");

		return pingAlias.ping("PingAlias") + pingWorker.ping("PingWorker") + pingProcessor.ping("PingProcessor");
	}
}
