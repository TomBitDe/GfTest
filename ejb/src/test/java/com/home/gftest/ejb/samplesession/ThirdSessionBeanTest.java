package com.home.gftest.ejb.samplesession;

import static org.junit.Assert.assertTrue;

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
 * Test the third session bean.
 */
@RunWith(Arquillian.class)
public class ThirdSessionBeanTest {
	private static final Logger LOG = LogManager.getLogger(ThirdSessionBeanTest.class);

	/**
	 * Here @Inject works, but @EJB fails. ThirdSession has a local-bean definition in ejb-jar.xml
	 */
	@Inject
	ThirdSession thirdSession;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* @Inject always works, no need to put ejb-jar.xml in META-INF folder */
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						ThirdSession.class);

		LOG.debug(archive.toString(true));

		return archive;
	}

	@Test
	public void businessMethod() {
		LOG.debug("--> businessMethod");

		boolean result = thirdSession.businessMethod();
		assertTrue(result);

		LOG.debug("<-- businessMethod");
	}
}
