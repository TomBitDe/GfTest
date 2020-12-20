package com.home.gftest.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.home.gftest.jpa.model.Component;
import com.home.gftest.jpa.model.Delivery;

/**
 * Test the delivery manager session bean.
 */
@RunWith(Arquillian.class)
public class DeliveryManagerTest {
	private static final Logger LOG = Logger.getLogger(DeliveryManagerTest.class);

	@EJB
	DeliveryManagerLocal deliverManager;
	@EJB
	ComponentManagerLocal componentManager;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-*.xml in JARs META-INF folder as *.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-persistence.xml"), "persistence.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"), "glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						DeliveryManagerLocal.class, DeliveryManagerBean.class,
						ComponentManagerLocal.class, ComponentManagerBean.class
						);

		System.out.println(archive.toString(true));

		return archive;
	}

	/**
	 * Simple delivery with no reference to component items
	 */
	@Test
	@InSequence(0)
	public void create() {
		LOG.info("Test create()");

		Delivery expDelivery = new Delivery(1L, "BAYER");

		deliverManager.create(expDelivery);
		Delivery delivery = deliverManager.getById(expDelivery.getDeliveryId());
		assertEquals(expDelivery, delivery);
	}

	/**
	 * Delivery with reference to component items
	 * On create(expDelivery) all the components are persisted too (CascadeType.PERSIST)
	 */
	@Test
	@InSequence(10)
	public void cascade_create() {
		LOG.info("Test cascade_create()");

		Delivery expDelivery = new Delivery(2L, "BASF");
		Component expComponent1 = new Component(1L, "Component 1");
		expDelivery.addComponent(expComponent1);
		Component expComponent2 = new Component(2L, "Component 2");
		expDelivery.addComponent(expComponent2);
		Component expComponent3 = new Component(3L, "Component 3");
		expDelivery.addComponent(expComponent3);
		Component expComponent4 = new Component(4L, "Component 4");
		expDelivery.addComponent(expComponent4);

		deliverManager.create(expDelivery);
		Delivery delivery = deliverManager.getById(expDelivery.getDeliveryId());
		assertEquals(expDelivery, delivery);
	}

	@Test
	@InSequence(20)
	public void getAllOrders() {
		LOG.info("Test Delivery getAll()");

		List<Delivery> orders = deliverManager.getAll();
		assertFalse(orders.isEmpty());

		orders.forEach(elem -> {LOG.info(elem);});
	}

	/**
	 * Check if all referenced order items are also available when reading the order
	 */
	@Test
	@InSequence(30)
	public void getDeliveryById() {
		LOG.info("Test Delivery getById()");

		List<Delivery> deliveries = deliverManager.getAll();

		deliveries.forEach(elem -> {
			Delivery delivery = deliverManager.getById(elem.getDeliveryId());
			if (delivery.getCustomer().equals("BASF")) {
				assertTrue(delivery.getComponents().size() == 4);
			}
		});
	}

	/**
	 * Check if delivery in components is always valid
	 */
	@Test
	@InSequence(40)
	public void getComponentById() {
		LOG.info("Test Component getById()");

		List<Component> orderItems = componentManager.getAll();

		assertFalse(orderItems.isEmpty());

		orderItems.forEach(elem -> {
			assertFalse(elem.getComponentId() == null);
			assertFalse(elem.getComment() == null);

			LOG.info(elem);
		});
	}

	@Test
	@InSequence(50)
	public void getAllComponents() {
		LOG.info("Test Component getAll()");

		List<Component> components = componentManager.getAll();
		assertFalse(components.isEmpty());

		components.forEach(elem -> {LOG.info(elem);});
	}

	@Test
	@InSequence(90)
	public void delete() {
		LOG.info("Test delete()");

		Delivery expDelivery = new Delivery(3L, "SYMRISE");

		deliverManager.create(expDelivery);
		Delivery delivery = deliverManager.delete(expDelivery);

		assertEquals(expDelivery, delivery);

		expDelivery = new Delivery(4L, "Currenta");
		// No persistence before delete
		delivery = deliverManager.delete(expDelivery);

		assertNull(delivery);
	}

	/**
	 * Delivery with reference to components exist
	 * On delete(elem.getDeliveryId()) all the components are deleted too (CascadeType.REMOVE)
	 */
	@Test
	@InSequence(99)
	public void cascade_delete() {
		LOG.info("Test cascade_delete()");

		List<Delivery> deliveries = deliverManager.getAll();
		deliveries.forEach(elem -> {deliverManager.delete( elem ); });

		assertTrue(deliverManager.getAll().isEmpty());
		assertFalse(componentManager.getAll().isEmpty());
	}
}
