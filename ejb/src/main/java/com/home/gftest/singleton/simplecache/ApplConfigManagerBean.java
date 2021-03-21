package com.home.gftest.singleton.simplecache;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.home.gftest.singleton.simplecache.model.ApplConfig;

/**
 * Session Bean implementation class ApplConfigManagerBean
 */
@Stateless
@Local(ApplConfigManager.class)
@Remote(ApplConfigService.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplConfigManagerBean implements ApplConfigManager, ApplConfigService {
	private static final Logger LOG = Logger.getLogger(ApplConfigManagerBean.class);

	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor.
     */
    public ApplConfigManagerBean() {
		super();
		LOG.debug("--> ApplConfigManagerBean");
		LOG.debug("<-- ApplConfigManagerBean");
    }

	@Override
	public List<ApplConfig> getAll() {
		LOG.debug("--> getAll()");

		TypedQuery<ApplConfig> query = em.createQuery("SELECT a FROM ApplConfig a", ApplConfig.class);
		List<ApplConfig> configList = query.getResultList();

		LOG.debug("<-- getAll()");

		return configList;
	}

	@Override
	public List<ApplConfig> getContent() {
		LOG.debug("--> getContent()");

		List<ApplConfig> configList = getAll();

		LOG.debug("<-- getContent()");

		return configList;
	}

	@Override
	public ApplConfig getById(String keyVal) {
		LOG.debug("--> getById(" + keyVal + ')');

		ApplConfig applConfigItem = em.find(ApplConfig.class, keyVal);

		LOG.debug("<-- getById");

		return applConfigItem;
	}

	@Override
	public ApplConfig create(ApplConfig config) {
		LOG.debug("--> create");

		em.persist(config);

		LOG.debug("<-- create");

		return getById(config.getKeyVal());
	}

	@Override
	public ApplConfig delete(String keyVal) {
		LOG.debug("--> delete(" + keyVal + ')');

		ApplConfig config = em.find(ApplConfig.class, keyVal);

		if (config != null) {
			em.remove(config);

			LOG.debug("deleted: " + config);
		}
		else {
			LOG.debug("Key value <" + keyVal + "> not found");
		}
		return config;
    }

	@Override
	public int count() {
		return getAll().size();
	}
}
