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

import com.home.gftest.ejb.samplesession.ControllerSession;

/**
 * Timer to later start the ControllerSession.
 */
@Singleton
public class ControllerSessionTimer {
	private static final Logger LOG = LogManager.getLogger(ControllerSessionTimer.class);
	private static final String EVERY = "*";

	@Inject
	private Instance<String> refererRuns;

	@Resource
	TimerService timerService;

	@EJB
	ControllerSession controllerSession;

	public void initializeTimer() {
		LOG.info("--> initializeTimer");

		ScheduleExpression expression = new ScheduleExpression();

		// Every 8 seconds
		expression.year(EVERY).month(EVERY).dayOfMonth(EVERY).hour(EVERY).minute(EVERY).second("*/8");

		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setPersistent(false);

		timerService.createCalendarTimer(expression, timerConfig);

		LOG.info("<-- initializeTimer");
	}

	@Timeout
	public void control() {
		LOG.info("--> control");

		LOG.info("\t Result of control() = [" + controllerSession.control() + ']');

		LOG.info("<-- control");
	}

}
