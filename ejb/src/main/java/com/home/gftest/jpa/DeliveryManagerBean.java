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

import com.home.gftest.jpa.model.Delivery;

/**
 * Session Bean implementation class DeliveryManagerBean
 */
@Stateless
@Local(DeliveryManagerLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@Interceptors(PerformanceAuditor.class)
public class DeliveryManagerBean implements DeliveryManagerLocal {
	private static final Logger LOG = LogManager.getLogger(DeliveryManagerBean.class);

	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor.
     */
    public DeliveryManagerBean() {
		super();
		LOG.trace("--> DeliveryManager");
		LOG.trace("<-- DeliveryManager");
    }

	@Override
	public void create(Delivery delivery) {
		LOG.trace("--> create");

		em.persist(delivery);

		LOG.trace("<-- create");
	}

	@Override
	public Delivery delete(Long deliveryId) {
		LOG.trace("--> delete(" + deliveryId + ')');

		Delivery delivery = getById(deliveryId);

		if (delivery != null) {
			em.remove(delivery);

			LOG.debug("deleted: " + delivery);
		}
		else {
			LOG.debug("Id <" + deliveryId + "> not found");
		}
		LOG.trace("<-- delete(" + deliveryId + ')');

		return delivery;
	}

	@Override
	public Delivery delete(Delivery delivery) {
		LOG.trace("--> delete(" + delivery + ')');

		if (delivery != null && delivery.getDeliveryId() != null) {

			Delivery oldDelivery = delete(delivery.getDeliveryId());

			LOG.trace("<-- delete()");

			return oldDelivery;
		}

		LOG.trace("<-- delete()");

		return null;
	}

	@Override
	public Delivery getById(Long id) {
		LOG.trace("--> getById(" + id + ')');

		Delivery delivery = em.find(Delivery.class, id);

		LOG.trace("<-- getById");

		return delivery;
	}

	@Override
	public List<Delivery> getAll() {
		LOG.trace("--> getAll()");

		TypedQuery<Delivery> query = em.createQuery("SELECT d FROM Delivery d", Delivery.class);
		List<Delivery> deliveries = query.getResultList();

		LOG.trace("<-- getAll()");

		return deliveries;
	}
}