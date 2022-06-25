package com.home.gftest.latestarter;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.home.gftest.ejb.ping.PingControllerBean;
import com.home.gftest.ejb.ping.PingWorkerBean;
import com.home.gftest.ejb.samplesession.ControllerSession;
import com.home.gftest.ejb.samplesession.ControllerSessionBean;
import com.home.gftest.ejb.samplesession.NewSession;
import com.home.gftest.ejb.samplesession.NewSessionBean;

/**
 * Test the LateStarter.
 */
@RunWith(Arquillian.class)
public class LateStarterTest {
	private static final Logger LOG = LogManager.getLogger(LateStarterTest.class);

	@Deployment
	public static Archive<?> createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"), "glassfish-ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						LateStarter.class
						, PingControllerTimer.class
						, PingControllerBean.class, PingWorkerBean.class
						, ControllerSessionTimer.class
						, ControllerSession.class, ControllerSessionBean.class
						, NewSession.class, NewSessionBean.class
						);

		LOG.debug(archive.toString(true));

		return archive;
	}

	/**
	 * Just to start the AppServer, wait a little and the shutdown the AppServer
	 *
	 * @throws InterruptedException in case the wait is interrupted
	 */
	@Test
	public void testLateStarter() throws InterruptedException {
		LOG.info("--> testLateStarter");

		Thread.sleep(60000);

		assertTrue(true);

		LOG.info("<-- testLateStarter");
	}

}
