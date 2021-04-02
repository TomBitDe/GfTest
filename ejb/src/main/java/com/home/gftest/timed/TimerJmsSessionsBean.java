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

import com.home.gftest.jms.MsgQueueProducer1;
import com.home.gftest.jms.MsgTopicProducer1;
import com.home.gftest.singleton.simplecache.ConfigCache;

/**
 * Implementation of a timer controlled bean<br>
 * <p>
 * Call JMS session beans to test when running in the application server.
 */
@Singleton
public class TimerJmsSessionsBean {
	private static final Logger LOG = LogManager.getLogger(TimerJmsSessionsBean.class);

	private Date lastProgrammaticTimeout;
	private Date lastAutomaticTimeout;

	@Resource
	TimerService timerService;

	@EJB
	ConfigCache configCache;

	@EJB
	MsgQueueProducer1 msgQueueProducer1;

	@EJB
	MsgTopicProducer1 msgTopicProducer1;

	@Timeout
	public void programmaticTimeout(Timer timer) {
		this.setLastProgrammaticTimeout(new Date());
		LOG.info("Programmatic timeout occurred.");
	}

	/**
	 * Call all the session beans to run them in the application server periodically
	 */
	@Schedule(minute="*/2", hour="*")
	public void automaticTimeout() {
		LOG.info("--> automaticTimeout()");

		this.setLastAutomaticTimeout(new Date());

		msgQueueProducer1.shouldBeAbleToSendMessage();
		msgQueueProducer1.sendManyMessages(Integer.valueOf(configCache.getData("QueueMsgNum", "66")));

		msgTopicProducer1.sendOneMessage();
		msgTopicProducer1.sendManyMessages(Integer.valueOf(configCache.getData("TopicMsgNum", "77")));

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
