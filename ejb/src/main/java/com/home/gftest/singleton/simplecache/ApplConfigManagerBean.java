package com.home.gftest.singleton.simplecache;

import java.util.List;

import javax.ejb.Local;
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
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplConfigManagerBean implements ApplConfigManager {
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

}
