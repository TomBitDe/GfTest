package com.home.gftest.async;

import java.io.File;

import javax.ejb.EJB;

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
 * Test the async controller session bean.
 */
@RunWith(Arquillian.class)
public class AsyncControllerSessionTest {
	private static final Logger LOG = LogManager.getLogger(AsyncControllerSessionTest.class);

	@EJB
	AsyncControllerSession asyncControllerSession;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"), "glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						AsyncSession.class, AsyncSessionBean.class,
						AsyncControllerSession.class, AsyncControllerSessionBean.class);

		LOG.debug(archive.toString(true));

		return archive;
	}

	@Test
	public void fireAndForgetTest() {
		LOG.debug("--> fireAndForgetTest");

		asyncControllerSession.fireAndForget();

		LOG.debug("<-- fireAndForgetTest");
	}

	@Test
	public void runAsyncCallTest() {
		LOG.debug("--> runAsyncCallTest");

		asyncControllerSession.runAsyncCall(5000);

		LOG.debug("<-- runAsyncCallTest");
	}

	@Test
	public void cancelAsyncCallTest() {
		LOG.debug("--> cancelAsyncCallTest");

		asyncControllerSession.cancelAsyncCall(5000);

		LOG.debug("<-- cancelAsyncCallTest");
	}

	@Test
	public void runAsyncCallWithCustomExTest() {
		LOG.debug("--> runAsyncCallWithCustomExTest");

		asyncControllerSession.runAsyncCallWithCustomEx(-1);

		LOG.debug("<-- runAsyncCallWithCustomExTest");
	}
}
