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

import com.home.gftest.configurator.base.Configurable;
import com.home.gftest.ejb.ping.PingControllerBean;

/**
 * Timer to later start the PingController.
 */
@Singleton
public class PingControllerTimer {
	private static final Logger LOG = LogManager.getLogger(PingControllerTimer.class);
	private static final String EVERY = "*";
	private static final String DEFAULT_RUNS_SECS = "*/5";

	@Inject @Configurable("pingControllerRuns")
	private Instance<String> pingControllerRuns;

	@Resource
	TimerService timerService;

	@EJB
	PingControllerBean pingController;

	public void initializeTimer() {
		LOG.info("--> initializeTimer");

		ScheduleExpression expression = new ScheduleExpression();
		String secs;

		try {
			secs = pingControllerRuns.get();
			LOG.info("Got injected configuration [" + secs + "] ...");
		}
		catch (UnsatisfiedResolutionException ex) {
			LOG.error(ex.getMessage());
			secs = DEFAULT_RUNS_SECS;
			LOG.info("Use DEFAULT value [" + secs + "] now...");
		}
		expression.year(EVERY).month(EVERY).dayOfMonth(EVERY).hour(EVERY).minute(EVERY).second(secs);

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
