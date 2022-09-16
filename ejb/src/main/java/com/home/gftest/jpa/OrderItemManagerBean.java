package com.home.gftest.jpa;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.jpa.model.OrderItem;
import com.home.gftest.telemetryprovider.monitoring.entity.PerformanceAuditor;

/**
 * Session Bean implementation class OrderItemManagerBean
 */
@Stateless
@Local(OrderItemManagerLocal.class)
@Interceptors(PerformanceAuditor.class)
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
		LOG.trace("--> OrderManager");
		LOG.trace("<-- OrderManager");
	}

	@Override
	public OrderItem getById(Long id) {
		LOG.trace("--> getById(" + id + ')');

		OrderItem orderItem = em.find(OrderItem.class, id);

		LOG.trace("<-- getById");

		return orderItem;
	}

	@Override
	public OrderItem delete(Long id) {
		LOG.trace("--> delete(" + id + ')');

		OrderItem orderItem = getById(id);

		if (orderItem != null) {
			em.remove(orderItem);

			LOG.debug("deleted: " + orderItem);
		}
		else {
			LOG.debug("Id <" + id + "> not found");
		}
		LOG.trace("<-- delete(" + id + ')');

		return orderItem;
	}

	@Override
	public OrderItem delete(OrderItem orderItem) {
		LOG.trace("--> delete(" + orderItem + ')');

		if (orderItem != null && orderItem.getId() != null) {
			OrderItem oldOrderItem = delete(orderItem.getId());

			LOG.trace("<-- delete()");

			return oldOrderItem;
		}

		LOG.trace("<-- delete()");

		return null;
	}

	@Override
	public List<OrderItem> getAll() {
		LOG.trace("--> getAll()");

		TypedQuery<OrderItem> query = em.createQuery("SELECT oi FROM OrderItem oi", OrderItem.class);
		List<OrderItem> orderItems = query.getResultList();

		LOG.trace("<-- getAll()");

		return orderItems;
	}
}
