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
}