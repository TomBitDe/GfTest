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

import com.home.gftest.jpa.model.Order;

/**
 * Session Bean implementation class OrderManagerBean
 */
@Stateless
@Local(OrderManagerLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@Interceptors(PerformanceAuditor.class)
public class OrderManagerBean implements OrderManagerLocal {
	private static final Logger LOG = LogManager.getLogger(OrderManagerBean.class);

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public OrderManagerBean() {
		super();
		LOG.trace("--> OrderManager");
		LOG.trace("<-- OrderManager");
	}

	@Override
	public void create(Order order) {
		LOG.trace("--> create");

		em.persist(order);

		LOG.trace("<-- create");
	}

	@Override
	public Order getById(Long id) {
		LOG.trace("--> getById(" + id + ')');

		Order order = em.find(Order.class, id);

		LOG.trace("<-- getById");

		return order;
	}

	@Override
	public Order delete(Long id) {
		LOG.trace("--> delete(" + id + ')');

		Order order = getById(id);

		if (order != null) {
			em.remove(order);

			LOG.debug("deleted: " + order);
		}
		else {
			LOG.debug("Id <" + id + "> not found");
		}
		LOG.trace("<-- delete(" + id + ')');

		return order;
	}

	@Override
	public Order delete(Order order) {
		LOG.trace("--> delete(" + order + ')');

		if (order != null && order.getId() != null) {

			Order oldOrder = delete(order.getId());

			LOG.trace("<-- delete()");

			return oldOrder;
		}

		LOG.trace("<-- delete()");

		return null;
	}

	@Override
	public List<Order> getAll() {
		LOG.trace("--> getAll()");

		TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
		List<Order> orders = query.getResultList();

		LOG.trace("<-- getAll()");

		return orders;
	}
}
