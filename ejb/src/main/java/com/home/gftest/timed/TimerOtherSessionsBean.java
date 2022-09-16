package com.home.gftest.timed;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.async.AsyncControllerSession;
import com.home.gftest.ejb.ping.PingControllerBean;
import com.home.gftest.ejb.refchange.FirstCallerSessionLocal;
import com.home.gftest.ejb.refchange.SecondCallerSessionLocal;
import com.home.gftest.ejb.samplesession.ControllerSession;
import com.home.gftest.ejb.samplesession.ThirdSession;
import com.home.gftest.telemetryprovider.monitoring.entity.GoodMorning;

/**
 * Implementation of a timer controlled bean<br>
 * <p>
 * Call OTHER session beans to test when running in the application server.
 */
@Singleton
public class TimerOtherSessionsBean {
	private static final Logger LOG = LogManager.getLogger(TimerOtherSessionsBean.class);

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

	@EJB
	AsyncControllerSession asyncControllerSession;

	@EJB
	GoodMorning goodMorning;

	@Timeout
	public void programmaticTimeout(Timer timer) {
		this.setLastProgrammaticTimeout(new Date());
		LOG.info("Programmatic timeout occurred.");
	}

	/**
	 * Call all the session beans to run them in the application server periodically
	 */
	@Schedule(minute="*/1", hour="*")
	public void automaticTimeout() {
		LOG.trace("--> automaticTimeout()");

		this.setLastAutomaticTimeout(new Date());
		pingControllerBean.runPing();
		firstCallerSession.call();
		secondCallerSession.call();
		controllerSession.control();
		thirdSession.businessMethod();
		asyncControllerSession.fireAndForget();
		asyncControllerSession.runAsyncCall(10000);
		asyncControllerSession.cancelAsyncCall(5000);
		goodMorning.say();
		goodMorning.tooEarly();

		LOG.trace("<-- automaticTimeout()");
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
