package com.home.gftest.jpa;

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

import com.home.gftest.jpa.model.Order;

/**
 * Test the first caller session bean.
 */
@RunWith(Arquillian.class)
public class OrderManagerTest {
	private static final Logger LOG = Logger.getLogger(OrderManagerTest.class);

	@EJB
	OrderManagerLocal orderManager;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-persistence.xml"), "persistence.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"), "glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						OrderManagerLocal.class, OrderManagerBean.class
						);

		System.out.println(archive.toString(true));

		return archive;
	}

	@Test
	public void create() {
		LOG.info("Test create()");

		Order expOrder = new Order("Test customer");

		orderManager.create(expOrder);
		Order order = orderManager.getById(expOrder.getId());
		assertEquals(expOrder, order);
	}
}
