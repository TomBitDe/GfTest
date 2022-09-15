package com.home.gftest.telemetryprovider;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.ejb.EJB;

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

import com.home.gftest.telemetryprovider.monitoring.entity.GoodMorning;
import com.home.gftest.telemetryprovider.monitoring.entity.MonitoringRessource;
import com.home.gftest.telemetryprovider.monitoring.entity.PerformanceAuditor;

/**
 * Test the LateStarter.
 */
@RunWith(Arquillian.class)
public class TelemetryProviderTest {
	private static final Logger LOG = LogManager.getLogger(TelemetryProviderTest.class);

	@EJB
	GoodMorning goodMorning;

	@Deployment
	public static Archive<?> createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"), "glassfish-ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						GoodMorning.class,
						PerformanceAuditor.class,
						MonitoringRessource.class
						);

		LOG.debug(archive.toString(true));

		return archive;
	}

	/**
	 * Just call method say of GoodMorning
	 */
	@Test
	public void testGoodMorning() {
		LOG.info("--> testGoodMorning");

		goodMorning.say();

		assertTrue(true);

		LOG.info("<-- testGoodMorning");
	}

}
