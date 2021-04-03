package com.home.gftest.ejb.samplesession;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test the the call of two session beans (special cases).
 */
@RunWith(Arquillian.class)
public class TwoSessionBeansCallTest {
	private static final Logger LOG = LogManager.getLogger(TwoSessionBeansCallTest.class);

	/**
	 * Here @EJB works because ControllerSessionBean has a standard local interface ControllerSession.
	 */
	@EJB
	ControllerSession controllerSession;

	/**
	 * Only @Inject works because of local-bean in ejb-jar.xml defined.
	 */
	@Inject
	ThirdSession thirdSession;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						ControllerSession.class, ControllerSessionBean.class
						, NewSession.class, NewSessionBean.class
						, ThirdSession.class);

		LOG.debug(archive.toString(true));

		return archive;
	}

	@Test
	public void testControl() {
		LOG.debug("--> testControl");

		boolean result = controllerSession.control();

		result = result & thirdSession.businessMethod();

		assertTrue(result);

		LOG.debug("<-- testControl");
	}
}
