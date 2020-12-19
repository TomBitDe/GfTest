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

import com.home.gftest.jpa.model.Order;
import com.home.gftest.jpa.model.OrderItem;

/**
 * Test the order manager session bean.
 */
@RunWith(Arquillian.class)
public class OrderManagerTest {
	private static final Logger LOG = Logger.getLogger(OrderManagerTest.class);

	@EJB
	OrderManagerLocal orderManager;
	@EJB
	OrderItemManagerLocal orderItemManager;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-*.xml in JARs META-INF folder as *.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-persistence.xml"), "persistence.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"), "glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(
						OrderManagerLocal.class, OrderManagerBean.class,
						OrderItemManagerLocal.class, OrderItemManagerBean.class
						);

		System.out.println(archive.toString(true));

		return archive;
	}

	/**
	 * Simple order with no reference to order items
	 */
	@Test
	@InSequence(0)
	public void create() {
		LOG.info("Test create()");

		Order expOrder = new Order("SYMRISE");

		orderManager.create(expOrder);
		Order order = orderManager.getById(expOrder.getId());
		assertEquals(expOrder, order);
	}

	/**
	 * Order with reference to order items
	 * On create(expOrder) all the order items are persisted too (CascadeType.PERSIST)
	 */
	@Test
	@InSequence(0)
	public void cascade_create() {
		LOG.info("Test cascade_create()");

		Order expOrder = new Order("BASF");
		OrderItem expOrderItem1 = new OrderItem(expOrder, 10);
		expOrder.addItem(expOrderItem1);
		OrderItem expOrderItem2 = new OrderItem(expOrder, 500);
		expOrder.addItem(expOrderItem2);
		OrderItem expOrderItem3 = new OrderItem(expOrder, 70);
		expOrder.addItem(expOrderItem3);
		OrderItem expOrderItem4 = new OrderItem(expOrder, 12);
		expOrder.addItem(expOrderItem4);

		orderManager.create(expOrder);
		Order order = orderManager.getById(expOrder.getId());
		assertEquals(expOrder, order);
	}

	@Test
	public void getAllOrders() {
		LOG.info("Test Order getAll()");

		List<Order> orders = orderManager.getAll();
		assertFalse(orders.isEmpty());

		orders.forEach(elem -> {LOG.info(elem);});
	}

	@Test
	public void getAllOrderItems() {
		LOG.info("Test OrderItem getAll()");

		List<OrderItem> orderItems = orderItemManager.getAll();
		assertFalse(orderItems.isEmpty());

		orderItems.forEach(elem -> {LOG.info(elem);});
	}

	@Test
	public void delete() {
		LOG.info("Test delete()");

		Order expOrder = new Order("BAYER");

		orderManager.create(expOrder);
		Order order = orderManager.delete(expOrder);

		assertEquals(expOrder, order);

		expOrder = new Order("Curenta");
		// No persistence before delete
		order = orderManager.delete(expOrder);

		assertNull(order);
	}

	/**
	 * Order with reference to order items exist
	 * On delete(elem.getId()) all the order items are deleted too (CascadeType.REMOVE)
	 */
	@Test
	@InSequence(99)
	public void cascade_delete() {
		LOG.info("Test cascade_delete()");

		List<Order> orders = orderManager.getAll();
		orders.forEach(elem -> {orderManager.delete( elem ); });

		assertTrue(orderManager.getAll().isEmpty());
		assertTrue(orderItemManager.getAll().isEmpty());
	}
}
