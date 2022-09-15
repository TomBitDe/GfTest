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

import com.home.gftest.jpa.model.Address;
import com.home.gftest.jpa.model.User;
import com.home.gftest.telemetryprovider.monitoring.entity.PerformanceAuditor;

@Stateless
@Local(UserAddressManagerLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(PerformanceAuditor.class)
public class UserAddressManagerBean implements UserAddressManagerLocal {
	private static final Logger LOG = LogManager.getLogger(UserAddressManagerBean.class);

	@PersistenceContext
	private EntityManager em;

	public UserAddressManagerBean() {
		super();
		LOG.debug("--> UserAddressManager");
		LOG.debug("<-- UserAddressManager");
	}

	@Override
	public void create(User user) {
		LOG.debug("--> create");

		em.persist(user);

		LOG.debug("<-- create");
	}

	@Override
	public User delete(Long id) {
		LOG.debug("--> delete(" + id + ')');

		User user = getById(id);

		if (user != null) {
			em.remove(user);

			LOG.debug("deleted: " + user);
		}
		else {
			LOG.debug("Id <" + id + "> not found");
		}
		return user;
	}

	@Override
	public User delete(User user) {
		LOG.debug("--> delete(" + user + ')');

		if (user != null && user.getId() != null) {

			User oldUser = delete(user.getId());

			LOG.debug("<-- delete()");

			return oldUser;
		}

		LOG.debug("<-- delete()");

		return null;
	}

	@Override
	public User getById(Long id) {
		LOG.debug("--> getById(" + id + ')');

		User user = em.find(User.class, id);

		LOG.debug("<-- getById");

		return user;
	}

	@Override
	public List<User> getAll() {
		LOG.debug("--> getAll()");

		TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
		List<User> users = query.getResultList();

		LOG.debug("<-- getAll()");

		return users;
	}

	@Override
	public Address getByUserId(Long id) {
		LOG.debug("--> getByUserId(" + id + ')');

		User user = em.find(User.class, id);

		LOG.debug("<-- getByUserId");

		return user.getAddress();
	}

	@Override
	public Address getByUser(User user) {
		LOG.debug("--> getByUser(" + user + ')');

		Address address = getByUserId(user.getId());

		LOG.debug("<-- getByUser");

		return address;
	}

	@Override
	public void setUserInAddress(User user) {
		LOG.debug("--> setUserInAddress(" + user + ')');

		Address address = getByUserId(user.getId());

		address.setUser(user);

		LOG.debug("<-- setUserInAddress");
	}
}
