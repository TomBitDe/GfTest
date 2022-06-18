package com.home.gftest.ejb.samplesession;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test the other session bean.
 */
@RunWith(Arquillian.class)
public class OtherSessionBeanTest {
	private static final Logger LOG = LogManager.getLogger(OtherSessionBeanTest.class);

	/**
	 * OtherSessionBean has a the standard local interface OtherSession. No problem then with @Inject...
	 */
	@Inject
	OtherSession otherSession;

	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addClasses(
						OtherSession.class, OtherSessionBean.class);
	}

	@Test
	public void businessMethod() {
		LOG.debug("--> businessMethod");

		boolean result = otherSession.businessMethod();
		assertTrue(result);

		LOG.debug("<-- businessMethod");
	}
}
