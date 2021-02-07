package com.home.gftest.async;

import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * A session with async business methods.
 */
@Stateless
@Local(com.home.gftest.async.AsyncSession.class)
public class AsyncSessionBean implements AsyncSession {
	private static final Logger LOG = Logger.getLogger(AsyncSessionBean.class.getName());

	private static final int TIMEOUT = 10000; // 10 seconds

	@Resource
	SessionContext ctx;

	public AsyncSessionBean() {
		super();
		LOG.info("--> AsyncSessionBean");
		LOG.info("<-- AsyncSessionBean");
	}

	@Override
	@Asynchronous
	public void asyncFireAndForgetMethod() {
		LOG.info("--> asyncFireAndForgetMethod");

		try {
			LOG.info("Sleep " + TIMEOUT + " now...");
			Thread.sleep(TIMEOUT);
		}
		catch (InterruptedException iex) {
			LOG.info(iex.getMessage());
		}
		LOG.info("... Wakeup");

		LOG.info("<-- asyncFireAndForgetMethod");
	}

	@Override
	@Asynchronous
	public Future<String> asyncControlledMethod(int duration) {
		LOG.info("--> asyncControlledMethod");

		String status;

		try {
			LOG.info("Sleep " + duration + " now...");
			status = "Sleep";

			Thread.sleep(duration);
		}
		catch (InterruptedException iex) {
			status = "Interrupted";

			LOG.info(iex.getMessage());
		}
		LOG.info("... Wakeup");

		if (ctx.wasCancelCalled()) {
			status = "Canceled";
		}
		else {
			status = "Wakeup";
		}

		LOG.info("<-- asyncControlledMethod");

		return new AsyncResult<>(status);
	}
}
