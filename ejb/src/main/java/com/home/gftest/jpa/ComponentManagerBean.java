package com.home.gftest.jpa;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.home.gftest.jpa.model.Component;

/**
 * Session Bean implementation class ComponentManagerBean
 */
@Stateless
@Local(ComponentManagerLocal.class)
public class ComponentManagerBean implements ComponentManagerLocal {
	private static final Logger LOG = Logger.getLogger(ComponentManagerBean.class);

	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor.
     */
    public ComponentManagerBean() {
		super();
		LOG.debug("--> ComponentManager");
		LOG.debug("<-- ComponentManager");
    }

	/**
     * @see ComponentManagerLocal#delete(Component)
     */
    @Override
	public Component delete(Component component) {
		LOG.debug("--> delete(" + component + ')');

		if (component != null && component.getComponentId() != null) {

			Component oldComponent = delete(component.getComponentId());

			LOG.debug("<-- delete()");

			return oldComponent;
		}

		LOG.debug("<-- delete()");

		return null;
    }

	/**
     * @see ComponentManagerLocal#getById(Long)
     */
    @Override
	public Component getById(Long componentId) {
		LOG.debug("--> getById(" + componentId + ')');

		Component component = em.find(Component.class, componentId);

		LOG.debug("<-- getById");

		return component;
    }

	/**
     * @see ComponentManagerLocal#create(Component)
     */
    @Override
	public void create(Component component) {
		LOG.debug("--> create");

		em.persist(component);

		LOG.debug("<-- create");
    }

	/**
     * @see ComponentManagerLocal#delete(Long)
     */
    @Override
	public Component delete(Long componentId) {
		LOG.debug("--> delete(" + componentId + ')');

		Component component = getById(componentId);

		if (component != null) {
			em.remove(component);

			LOG.debug("deleted: " + component);
		}
		else {
			LOG.debug("Id <" + componentId + "> not found");
		}
		return component;
    }

	/**
     * @see ComponentManagerLocal#getAll()
     */
    @Override
	public List<Component> getAll() {
		LOG.debug("--> getAll()");

		TypedQuery<Component> query = em.createQuery("SELECT c FROM Component c", Component.class);
		List<Component> component = query.getResultList();

		LOG.debug("<-- getAll()");

		return component;
    }

}
