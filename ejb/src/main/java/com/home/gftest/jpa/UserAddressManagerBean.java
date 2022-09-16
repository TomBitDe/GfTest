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

import com.home.gftest.jpa.model.Address;
import com.home.gftest.jpa.model.User;

@Stateless
@Local(UserAddressManagerLocal.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
//@Interceptors(PerformanceAuditor.class)
public class UserAddressManagerBean implements UserAddressManagerLocal {
	private static final Logger LOG = LogManager.getLogger(UserAddressManagerBean.class);

	@PersistenceContext
	private EntityManager em;

	public UserAddressManagerBean() {
		super();
		LOG.trace("--> UserAddressManager");
		LOG.trace("<-- UserAddressManager");
	}

	@Override
	public void create(User user) {
		LOG.trace("--> create");

		em.persist(user);

		LOG.trace("<-- create");
	}

	@Override
	public User delete(Long id) {
		LOG.trace("--> delete(" + id + ')');

		User user = getById(id);

		if (user != null) {
			em.remove(user);

			LOG.debug("deleted: " + user);
		}
		else {
			LOG.debug("Id <" + id + "> not found");
		}
		LOG.trace("<-- delete(" + id + ')');

		return user;
	}

	@Override
	public User delete(User user) {
		LOG.trace("--> delete(" + user + ')');

		if (user != null && user.getId() != null) {

			User oldUser = delete(user.getId());

			LOG.trace("<-- delete()");

			return oldUser;
		}

		LOG.trace("<-- delete()");

		return null;
	}

	@Override
	public User getById(Long id) {
		LOG.trace("--> getById(" + id + ')');

		User user = em.find(User.class, id);

		LOG.trace("<-- getById");

		return user;
	}

	@Override
	public List<User> getAll() {
		LOG.trace("--> getAll()");

		TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
		List<User> users = query.getResultList();

		LOG.trace("<-- getAll()");

		return users;
	}

	@Override
	public Address getByUserId(Long id) {
		LOG.trace("--> getByUserId(" + id + ')');

		User user = em.find(User.class, id);

		LOG.trace("<-- getByUserId");

		return user.getAddress();
	}

	@Override
	public Address getByUser(User user) {
		LOG.trace("--> getByUser(" + user + ')');

		Address address = getByUserId(user.getId());

		LOG.trace("<-- getByUser");

		return address;
	}

	@Override
	public void setUserInAddress(User user) {
		LOG.trace("--> setUserInAddress(" + user + ')');

		Address address = getByUserId(user.getId());

		address.setUser(user);

		LOG.trace("<-- setUserInAddress");
	}
}
