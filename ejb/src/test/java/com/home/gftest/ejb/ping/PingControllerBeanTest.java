package com.home.gftest.ejb.ping;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test the ping controller bean.
 */
@RunWith(Arquillian.class)
public class PingControllerBeanTest {
	private static final Logger LOG = Logger.getLogger(PingControllerBeanTest.class);

	@EJB
	PingControllerBean pingControllerBean;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						PingControllerBean.class
						, PingWorkerBean.class
						);

		System.out.println(archive.toString(true));

		return archive;
	}

	@Test
	public void testRunPing() {
		LOG.debug("--> testRunPing");

		String result = pingControllerBean.runPing();
		assertEquals("pongpongpong", result);

		LOG.debug("<-- testRunPing");
	}
}
