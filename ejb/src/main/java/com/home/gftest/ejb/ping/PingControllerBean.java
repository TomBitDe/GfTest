package com.home.gftest.ejb.ping;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.telemetryprovider.monitoring.PerformanceAuditor;

/**
 * Session Bean implementation class PingControllerBean<br>
 * <p>
 * Purpose is to call local ping worker session beans.
 */
@Stateless
@LocalBean
@Interceptors(PerformanceAuditor.class)
public class PingControllerBean {
	private static final Logger LOG = LogManager.getLogger(PingControllerBean.class);

	@EJB(beanName="PingAlias")
	PingWorkerBean pingAlias;

	@EJB
	PingWorkerBean pingWorker;

	@EJB
	PingWorkerBean pingProcessor;

	/**
	 * Default constructor
	 */
	public PingControllerBean() {
		super();
		LOG.trace("--> PingControllerBean");
		LOG.trace("<-- PingControllerBean");
	}

	/**
	 * Run the worker local session beans
	 *
	 * @return pong for every called worker
	 */
	public String runPing() {
		LOG.trace("--> runPing");
		LOG.trace("<-- runPing");

		return pingAlias.ping("PingAlias") + pingWorker.ping("PingWorker") + pingProcessor.ping("PingProcessor");
	}
}
