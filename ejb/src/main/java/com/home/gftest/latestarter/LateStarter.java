package com.home.gftest.latestarter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The LateStarter Singleton.
 * Instantiated at Startup. Startup is delayed (see DELAY_IN_SECCONDS).
 * Basic concept is using Singleton / Startup / TimerService.
 */
@Startup
@Singleton
public class LateStarter {
	private static final Logger LOG = LogManager.getLogger(LateStarter.class);

	/**
	 * Startup delay in seconds
	 */
	private static final int DELAY_IN_SECCONDS = 10;

	@Resource
	TimerService timerService;

	@Inject
	PingControllerTimer pingControllerTimer;

	@Inject
	ControllerSessionTimer controllerSessionTimer;

	private long startDelay = DELAY_IN_SECCONDS * 1000;
	private Timer timer;

	@PostConstruct
	public void initializeTimer() {
		LOG.info("--> initializeTimer");

		TimerConfig config = new TimerConfig();
		config.setPersistent(false);
		LOG.info("StartDelay = " + startDelay + " [msec.]");
		this.timer = timerService.createSingleActionTimer(startDelay, config);

		LOG.info("<-- initializeTimer");
	}

	@Timeout
	public void startLater() {
		LOG.info("--> startLater");

		pingControllerTimer.initializeTimer();

		controllerSessionTimer.initializeTimer();

		LOG.info("<-- startLater");
	}

	@PreDestroy
	public void cleanupTimer() {
		LOG.info("--> cleanupTimer");

		this.timer.cancel();

		LOG.info("<-- cleanupTimer");
	}
}
