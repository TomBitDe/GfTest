package com.home.gftest.jpa;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.gftest.jpa.model.Component;

/**
 * Session Bean implementation class ComponentManagerBean
 */
@Stateless
@Local(ComponentManagerLocal.class)
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@Interceptors(PerformanceAuditor.class)
public class ComponentManagerBean implements ComponentManagerLocal {
	private static final Logger LOG = LogManager.getLogger(ComponentManagerBean.class);

	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor.
     */
    public ComponentManagerBean() {
		super();
		LOG.trace("--> ComponentManager");
		LOG.trace("<-- ComponentManager");
    }

	/**
     * @see ComponentManagerLocal#delete(Component)
     */
    @Override
	public Component delete(Component component) {
		LOG.trace("--> delete(" + component + ')');

		if (component != null && component.getComponentId() != null) {

			Component oldComponent = delete(component.getComponentId());

			LOG.debug("<-- delete()");

			return oldComponent;
		}

		LOG.trace("<-- delete()");

		return null;
    }

	/**
     * @see ComponentManagerLocal#getById(Long)
     */
    @Override
	public Component getById(Long componentId) {
		LOG.trace("--> getById(" + componentId + ')');

		Component component = em.find(Component.class, componentId);

		LOG.trace("<-- getById");

		return component;
    }

	/**
     * @see ComponentManagerLocal#create(Component)
     */
    @Override
	public void create(Component component) {
		LOG.trace("--> create");

		em.persist(component);

		LOG.trace("<-- create");
    }

	/**
     * @see ComponentManagerLocal#delete(Long)
     */
    @Override
	public Component delete(Long componentId) {
		LOG.trace("--> delete(" + componentId + ')');

		Component component = getById(componentId);

		if (component != null) {
			em.remove(component);

			LOG.debug("deleted: " + component);
		}
		else {
			LOG.debug("Id <" + componentId + "> not found");
		}
		LOG.trace("<-- delete(" + componentId + ')');

		return component;
    }

	/**
     * @see ComponentManagerLocal#getAll()
     */
    @Override
	public List<Component> getAll() {
		LOG.trace("--> getAll()");

		TypedQuery<Component> query = em.createQuery("SELECT c FROM Component c", Component.class);
		List<Component> component = query.getResultList();

		LOG.trace("<-- getAll()");

		return component;
    }

}
