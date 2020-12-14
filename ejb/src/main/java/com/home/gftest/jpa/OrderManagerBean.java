package com.home.gftest.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.home.gftest.jpa.model.Order;

/**
 * Session Bean implementation class OrderManagerBean
 */
@Stateless
@Local(OrderManagerLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderManagerBean implements OrderManagerLocal {
	private static final Logger LOG = Logger.getLogger(OrderManagerBean.class);

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public OrderManagerBean() {
		super();
		LOG.debug("--> OrderManager");
		LOG.debug("<-- OrderManager");
	}

	@Override
	public void create(Order order) {
		LOG.debug("--> create");

		em.persist(order);

		LOG.debug("<-- create");
	}

	@Override
	public Order getById(long id) {
		LOG.debug("--> getById(" + id + ')');

		Order order = em.find(Order.class, id);

		LOG.debug("<-- getById");

		return order;
	}

	@Override
	public Order delete(long id) {
		LOG.debug("--> delete(" + id + ')');

		Order order = getById(id);

		if (order != null) {
			em.remove(order);

			LOG.debug("deleted: " + order);
		}
		else {
			LOG.debug(id + " not found");
		}
		return order;
	}
}
