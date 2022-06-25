package com.home.gftest.latestarter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.ejb.ping.PingControllerBean;

/**
 * Timer to later start the PingController.
 */
@Singleton
public class PingControllerTimer {
	private static final Logger LOG = LogManager.getLogger(PingControllerTimer.class);
	private static final String EVERY = "*";

	@Inject
	private Instance<String> refererRuns;

	@Resource
	TimerService timerService;

	@EJB
	PingControllerBean pingController;

	public void initializeTimer() {
		LOG.info("--> initializeTimer");

		ScheduleExpression expression = new ScheduleExpression();

		// Every 5 seconds
		expression.year(EVERY).month(EVERY).dayOfMonth(EVERY).hour(EVERY).minute(EVERY).second("*/5");

		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setPersistent(false);

		timerService.createCalendarTimer(expression, timerConfig);

		LOG.info("<-- initializeTimer");
	}

	@Timeout
	public void ping() {
		LOG.info("--> ping");

		LOG.info("\t Result of runPing() = [" + pingController.runPing() + ']');

		LOG.info("<-- ping");
	}

}
