package com.home.gftest.jpa;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.jpa.model.OrderItem;

/**
 * Session Bean implementation class OrderItemManagerBean
 */
@Stateless
@Local(OrderItemManagerLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderItemManagerBean implements OrderItemManagerLocal {
	private static final Logger LOG = LogManager.getLogger(OrderItemManagerBean.class);

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public OrderItemManagerBean() {
		super();
		LOG.debug("--> OrderManager");
		LOG.debug("<-- OrderManager");
	}

	@Override
	public OrderItem getById(Long id) {
		LOG.debug("--> getById(" + id + ')');

		OrderItem orderItem = em.find(OrderItem.class, id);

		LOG.debug("<-- getById");

		return orderItem;
	}

	@Override
	public OrderItem delete(Long id) {
		LOG.debug("--> delete(" + id + ')');

		OrderItem orderItem = getById(id);

		if (orderItem != null) {
			em.remove(orderItem);

			LOG.debug("deleted: " + orderItem);
		}
		else {
			LOG.debug("Id <" + id + "> not found");
		}
		return orderItem;
	}

	@Override
	public OrderItem delete(OrderItem orderItem) {
		LOG.debug("--> delete(" + orderItem + ')');

		if (orderItem != null && orderItem.getId() != null) {
			OrderItem oldOrderItem = delete(orderItem.getId());

			LOG.debug("<-- delete()");

			return oldOrderItem;
		}

		LOG.debug("<-- delete()");

		return null;
	}

	@Override
	public List<OrderItem> getAll() {
		LOG.debug("--> getAll()");

		TypedQuery<OrderItem> query = em.createQuery("SELECT oi FROM OrderItem oi", OrderItem.class);
		List<OrderItem> orderItems = query.getResultList();

		LOG.debug("<-- getAll()");

		return orderItems;
	}
}
