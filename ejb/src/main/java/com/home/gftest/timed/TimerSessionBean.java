package com.home.gftest.timed;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import org.apache.log4j.Logger;

import com.home.gftest.ejb.ping.PingControllerBean;
import com.home.gftest.ejb.refchange.FirstCallerSessionLocal;
import com.home.gftest.ejb.refchange.SecondCallerSessionLocal;
import com.home.gftest.ejb.samplesession.ControllerSession;
import com.home.gftest.ejb.samplesession.ThirdSession;

/**
 * Implementation of a timer controlled bean.
 */
@Singleton
public class TimerSessionBean {
	private static final Logger LOG = Logger.getLogger(TimerSessionBean.class);

	private Date lastProgrammaticTimeout;
	private Date lastAutomaticTimeout;

	@Resource
	TimerService timerService;

	@EJB
	PingControllerBean pingControllerBean;

	@EJB
	FirstCallerSessionLocal firstCallerSession;

	@EJB
	SecondCallerSessionLocal secondCallerSession;

	@EJB
	ControllerSession controllerSession;

	@EJB
	ThirdSession thirdSession;

	@Timeout
	public void programmaticTimeout(Timer timer) {
		this.setLastProgrammaticTimeout(new Date());
		LOG.info("Programmatic timeout occurred.");
	}

	@Schedule(minute="*/1", hour="*")
	public void automaticTimeout() {
		LOG.info("--> automaticTimeout()");

		this.setLastAutomaticTimeout(new Date());
		pingControllerBean.runPing();
		firstCallerSession.call();
		secondCallerSession.call();
		controllerSession.control();
		thirdSession.businessMethod();

		LOG.info("<-- automaticTimeout()");
	}

	public String getLastProgrammaticTimeout() {
		if (lastProgrammaticTimeout != null) {
			return lastProgrammaticTimeout.toString();
		} else {
			return "never";
		}

	}

	public void setLastProgrammaticTimeout(Date lastTimeout) {
		this.lastProgrammaticTimeout = lastTimeout;
	}

	public String getLastAutomaticTimeout() {
		if (lastAutomaticTimeout != null) {
			return lastAutomaticTimeout.toString();
		} else {
			return "never";
		}
	}

	public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
		this.lastAutomaticTimeout = lastAutomaticTimeout;
	}
}
