package com.home.gftest.ejb.ping;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.telemetryprovider.monitoring.PerformanceAuditor;

/**
 * Session Bean implementation class PingWorkerBean<br>
 * <p>
 * Purpose is to implement just one local worker session bean that can be used in ejb-jar.xml for redefinition.
 */
@Stateless(name = "PingAlias")
@LocalBean
@Interceptors(PerformanceAuditor.class)
public class PingWorkerBean {
	private static final Logger LOG = LogManager.getLogger(PingWorkerBean.class);

	/**
	 * Default constructor
	 */
	public PingWorkerBean() {
		super();
		LOG.trace("--> PingWorkerBean");
		LOG.trace("<-- PingWorkerBean");
	}

	/**
	 * Reply with pong
	 *
	 * @param who the name of the called worker session bean
	 *
	 * @return pong
	 */
	public String ping(String who) {
		LOG.info("--> ping (" + who + ")");
		LOG.info("<-- ping (" + who + ")");

		return "pong";
	}
}
