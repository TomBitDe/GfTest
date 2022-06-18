package com.home.gftest.timed;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.jpa.DeliveryManagerLocal;
import com.home.gftest.jpa.OrderManagerLocal;
import com.home.gftest.jpa.UserAddressManagerLocal;
import com.home.gftest.jpa.model.Address;
import com.home.gftest.jpa.model.Component;
import com.home.gftest.jpa.model.Delivery;
import com.home.gftest.jpa.model.Order;
import com.home.gftest.jpa.model.User;

/**
 * Implementation of a timer controlled bean<br>
 * <p>
 * Call JPA session beans to test when running in the application server.
 */
@Singleton
public class TimerJpaSessionsBean {
	private static final Logger LOG = LogManager.getLogger(TimerJpaSessionsBean.class);

	private Date lastProgrammaticTimeout;
	private Date lastAutomaticTimeout;

	@Resource
	TimerService timerService;

	@EJB
	OrderManagerLocal orderManager;

	@EJB
	DeliveryManagerLocal deliveryManager;

	@EJB
	UserAddressManagerLocal userAddressManager;

	@Timeout
	public void programmaticTimeout(Timer timer) {
		this.setLastProgrammaticTimeout(new Date());
		LOG.info("Programmatic timeout occurred.");
	}

	/**
	 * Call all the session beans to run them in the application server periodically
	 */
	@Schedule(minute="*/2", hour="*")
	public void automaticTimeout() {
		LOG.info("--> automaticTimeout()");

		this.setLastAutomaticTimeout(new Date());

		Order order = new Order("Test customer");
		orderManager.create(order);
		orderManager.delete(order.getId());

		Delivery delivery = new Delivery(1L, "BASF");
		Component comp1 = new Component(1L, "Component1");
		comp1.addDelivery(delivery);
		delivery.addComponent(comp1);
		Component comp2 = new Component(2L, "Component2");
		comp2.addDelivery(delivery);
		delivery.addComponent(comp2);
		Component comp3 = new Component(3L, "Component3");
		comp3.addDelivery(delivery);
		delivery.addComponent(comp3);
		Component comp4 = new Component(4L, "Component4");
		comp4.addDelivery(delivery);
		delivery.addComponent(comp4);
		Component comp5 = new Component(5L, "Component5");
		comp5.addDelivery(delivery);
		delivery.addComponent(comp5);
		Component comp6 = new Component(6L, "Component6");
		comp6.addDelivery(delivery);
		delivery.addComponent(comp6);
		Component comp7 = new Component(7L, "Component7");
		comp7.addDelivery(delivery);
		delivery.addComponent(comp7);
		Component comp8 = new Component(8L, "Component8");
		comp8.addDelivery(delivery);
		delivery.addComponent(comp8);

		deliveryManager.create(delivery);

		deliveryManager.getAll().forEach(elem -> { LOG.info(elem); });

		deliveryManager.delete(delivery);

		Address address = new Address("4711");
		User user = new User("Dummy", address);
		address.setUser(user);
		userAddressManager.create(user);

		userAddressManager.getAll().forEach(elem -> { LOG.info(elem); });

		userAddressManager.delete(user);

		LOG.info("<-- automaticTimeout()");
	}

	public String getLastProgrammaticTimeout() {
		if (lastProgrammaticTimeout != null) {
			return lastProgrammaticTimeout.toString();
		} else {
			return "never";
		}

	}

	public void setLastProgrammaticTimeout(Date lastTimeout) {
		this.lastProgrammaticTimeout = lastTimeout;
	}

	public String getLastAutomaticTimeout() {
		if (lastAutomaticTimeout != null) {
			return lastAutomaticTimeout.toString();
		} else {
			return "never";
		}
	}

	public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
		this.lastAutomaticTimeout = lastAutomaticTimeout;
	}
}
