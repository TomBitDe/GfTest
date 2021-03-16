package com.home.gftest.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * Local session bean to control an other local session bean containing async business methods.
 */
@Local(com.home.gftest.async.AsyncControllerSession.class)
@Stateless
public class AsyncControllerSessionBean implements AsyncControllerSession {
	private static final Logger LOG = Logger.getLogger(AsyncControllerSessionBean.class.getName());

	private static final int DEFAULT_DURATION = 1000;

	@EJB
	AsyncSession asyncSession;

	public AsyncControllerSessionBean() {
		super();
		LOG.info("--> AsyncControllerSessionBean");
		LOG.info("<-- AsyncControllerSessionBean");
	}

	@Override
	public void fireAndForget() {
		LOG.info("--> fireAndForget");

		asyncSession.asyncFireAndForgetMethod();

		LOG.info("--> fireAndForget");
	}

	@Override
	public void runAsyncCall(int duration) {
		LOG.info("--> runAsyncCall");

		Future<String> future = asyncSession.asyncControlledMethod(duration);

		try {
		    String result = future.get();

		    LOG.info("Result <" + result + '>');
		}
		catch (ExecutionException eex) {
			LOG.error(eex.getMessage());
		}
		catch(InterruptedException iex) {
			LOG.error(iex.getMessage());
		}

		LOG.info("--> runAsyncCall");
	}


	@Override
	public void cancelAsyncCall(int duration) {
		LOG.info("--> cancelAsyncCall");

		Future<String> future = asyncSession.asyncControlledMethod(duration);

		boolean ret = future.cancel(true);

		LOG.info("Cancel returned [" + ret + ']');

		LOG.info("--> cancelAsyncCall");
	}

	@Override
	public void runAsyncCallWithCustomEx(int duration) {
		LOG.info("--> runAsyncCallWithCustomEx");

		try {
			Future<String> future = asyncSession.asyncControlledMethodWithCustomEx(duration);

			LOG.info("Future result = [" + future.get() + ']');
		}
		catch (AsyncControlledMethodException | InterruptedException | ExecutionException aex) {
			LOG.error(aex.getMessage());

			try {
				// Run with a DEFAULT duration
				LOG.info("Run with DEFAULT duration [" + DEFAULT_DURATION + ']');
				Future<String> future = asyncSession.asyncControlledMethod(DEFAULT_DURATION);

				LOG.info("Future result = [" + future.get() + ']');
			}
			catch (InterruptedException | ExecutionException e) {
				LOG.error(e.getMessage());
			}
		}

		LOG.info("--> runAsyncCallWithCustomEx");
	}
}