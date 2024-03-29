package com.home.gftest.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.home.gftest.telemetryprovider.monitoring.PerformanceAuditor;
import com.home.gftest.telemetryprovider.monitoring.boundary.MonitoringResource;

/**
 * Test the delivery manager session bean.
 */
@RunWith(Arquillian.class)
public class DeliveryManagerTest {
	private static final Logger LOG = LogManager.getLogger(DeliveryManagerTest.class);

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
						ComponentManagerLocal.class, ComponentManagerBean.class,
						PerformanceAuditor.class,
						MonitoringResource.class
						);

		LOG.debug(archive.toString(true));

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
		expComponent1.addDelivery(expDelivery);
		expDelivery.addComponent(expComponent1);
		Component expComponent2 = new Component(2L, "Component 2");
		expComponent2.addDelivery(expDelivery);
		expDelivery.addComponent(expComponent2);
		Component expComponent3 = new Component(3L, "Component 3");
		expComponent3.addDelivery(expDelivery);
		expDelivery.addComponent(expComponent3);
		Component expComponent4 = new Component(4L, "Component 4");
		expComponent4.addDelivery(expDelivery);
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
				assertEquals(4, delivery.getComponents().size());
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
			assertNotNull(elem.getComponentId());
			assertNotNull(elem.getComment());

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
		Component comp1 = new Component(11L, "Component1");
		comp1.addDelivery(expDelivery);
		expDelivery.addComponent(comp1);
		Component comp2 = new Component(12L, "Component2");
		comp2.addDelivery(expDelivery);
		expDelivery.addComponent(comp2);
		Component comp3 = new Component(13L, "Component3");
		comp3.addDelivery(expDelivery);
		expDelivery.addComponent(comp3);
		Component comp4 = new Component(14L, "Component4");
		comp4.addDelivery(expDelivery);
		expDelivery.addComponent(comp4);
		Component comp5 = new Component(15L, "Component5");
		comp5.addDelivery(expDelivery);
		expDelivery.addComponent(comp5);
		Component comp6 = new Component(16L, "Component6");
		comp6.addDelivery(expDelivery);
		expDelivery.addComponent(comp6);
		Component comp7 = new Component(17L, "Component7");
		comp7.addDelivery(expDelivery);
		expDelivery.addComponent(comp7);
		Component comp8 = new Component(18L, "Component8");
		comp7.addDelivery(expDelivery);
		expDelivery.addComponent(comp8);

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
		assertTrue(componentManager.getAll().isEmpty());
	}
}
