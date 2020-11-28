package com.home.gftest.ejb.samplesession;

import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test the controller session bean.
 */
@RunWith(Arquillian.class)
public class ControllerSessionBeanTest {
	private static final Logger LOG = Logger.getLogger(ControllerSessionBeanTest.class);

	/**
	 * Here @EJB works because ControllerSessionBean has a standard local interface ControllerSession.
	 */
	@EJB
	ControllerSession controllerSession;

	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addClasses(
						ControllerSession.class, ControllerSessionBean.class
						, NewSession.class, NewSessionBean.class);
	}

	@Test
	public void testControl() {
		LOG.debug("--> testControl");

		boolean result = controllerSession.control();
		assertTrue(result);

		LOG.debug("<-- testControl");
	}
}
