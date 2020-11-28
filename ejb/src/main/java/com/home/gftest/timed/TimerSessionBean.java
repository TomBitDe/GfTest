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
import com.home.gftest.ejb.refchange.SecondCallerSessionLocal;

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
	SecondCallerSessionLocal secondCallerSession;

	@Timeout
	public void programmaticTimeout(Timer timer) {
		this.setLastProgrammaticTimeout(new Date());
		LOG.info("Programmatic timeout occurred.");
	}

	@Schedule(minute="*/1", hour="*")
	public void automaticTimeout() {
		this.setLastAutomaticTimeout(new Date());
		LOG.info("Automatic timeout occured");
		pingControllerBean.runPing();
		secondCallerSession.call();
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
