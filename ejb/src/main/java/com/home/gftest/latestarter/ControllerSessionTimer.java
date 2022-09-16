package com.home.gftest.latestarter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.configurator.base.NotConfiguredException;
import com.home.gftest.ejb.samplesession.ControllerSession;

/**
 * Timer to later start the ControllerSession.
 */
@Singleton
public class ControllerSessionTimer {
	private static final Logger LOG = LogManager.getLogger(ControllerSessionTimer.class);
	private static final String EVERY = "*";
	private static final String DEFAULT_RUNS_SECS = "*/8";

	@Inject
	private Instance<String> controllerSessionRuns;

	@Resource
	TimerService timerService;

	@EJB
	ControllerSession controllerSession;

	public void initializeTimer() {
		LOG.trace("--> initializeTimer");

		ScheduleExpression expression = new ScheduleExpression();
		String secs;

		try {
		    secs = controllerSessionRuns.get();
			LOG.info("Got injected configuration [" + secs + "] ...");
		}
		catch (NotConfiguredException ncex) {
			LOG.error(ncex.getMessage());
			secs = DEFAULT_RUNS_SECS;
			LOG.warn("Use DEFAULT value [" + secs + "] now...");
		}
		catch (UnsatisfiedResolutionException ex) {
			LOG.error(ex.getMessage());
			secs = DEFAULT_RUNS_SECS;
			LOG.warn("Use DEFAULT value [" + secs + "] now...");
		}
		expression.year(EVERY).month(EVERY).dayOfMonth(EVERY).hour(EVERY).minute(EVERY).second(secs);

		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setPersistent(false);

		timerService.createCalendarTimer(expression, timerConfig);

		LOG.trace("<-- initializeTimer");
	}

	@Timeout
	public void control() {
		LOG.trace("--> control");

		LOG.info("\t Result of control() = [" + controllerSession.control() + ']');

		LOG.trace("<-- control");
	}

}
