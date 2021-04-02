package com.home.gftest.ejb.samplesession;

import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test the new session bean.
 */
@RunWith(Arquillian.class)
public class NewSessionBeanTest {
	private static final Logger LOG = LogManager.getLogger(NewSessionBeanTest.class);

	/**
	 * NewSessionBean has a the standard local interface NewSession. No problem then with @EJB...
	 */
	@EJB
	NewSession newSession;

	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addClasses(
						NewSession.class, NewSessionBean.class);
	}

	@Test
	public void businessMethod() {
		LOG.debug("--> businessMethod");

		boolean result = newSession.businessMethod();
		assertTrue(result);

		LOG.debug("<-- businessMethod");
	}
}
