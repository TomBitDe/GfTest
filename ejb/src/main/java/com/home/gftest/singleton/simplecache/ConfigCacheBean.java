package com.home.gftest.singleton.simplecache;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Startup
@Local(ConfigCache.class)
@Singleton
public class ConfigCacheBean implements ConfigCache {
	private static final Logger LOG = LogManager.getLogger(ConfigCacheBean.class);

	private Map<String, String> cache;

	@EJB
	private CacheDataProvider cacheDataProvider;

	public ConfigCacheBean() {
		super();
		LOG.debug("--> ConfigCacheBean");
		LOG.debug("<-- ConfigCacheBean");
	}

	/**
	 * Refresh the cache data REST controlled
	 */
	@Override
	public void refresh() {
		LOG.debug("--> refresh");

		cache = createFreshCache();

		LOG.debug("<-- refresh");
	}

	/**
	 * Populate the cache data for the application timer controlled
	 */
	@Schedule(minute = "*/2", hour = "*", persistent = false)
	@PostConstruct
	private void populateCache() {
		LOG.debug("--> populateCache");

		cache = createFreshCache();

		LOG.debug("<-- populateCache");
	}

	/**
	 * Load the data for the cache
	 *
	 * @return the data map for the cache
	 */
	private Map<String, String> createFreshCache() {
		LOG.debug("--> createFreshCache");

		Map<String, String> map = cacheDataProvider.loadCacheData();

		LOG.info("-------- Cache data --------");
		if (map.isEmpty()) {
			LOG.info("EMPTY!");
		}
		else {
			map.forEach((k,v) -> LOG.info("Key=[" + k + "] Value=[" + v + ']'));
		}

		LOG.debug("<-- createFreshCache");

		return map;
	}

	@Override
	@Lock(LockType.READ)
	public String getData(String key) {
		LOG.debug("Key=[" + key + ']');

		String val = cache.get(key);

		LOG.debug("Value=[" + val + ']');

	    return val;
	}

	@Override
	@Lock(LockType.READ)
	public String getData(String key, String defaultValue) {
		LOG.debug("Key=[" + key + "] DefaultValue=[" + defaultValue + ']');

		String val = cache.get(key);

		if (val == null) {
			val = defaultValue;
		}

		LOG.debug("Value=[" + val + ']');

	    return val;
	}

	@Override
	@Lock(LockType.READ)
	public int getData(String key, int defaultValue) {
		LOG.debug("Key=[" + key + "] DefaultValue=[" + defaultValue + ']');

		int val;

		try {
		    val = Integer.valueOf(cache.get(key));
		}
		catch(NumberFormatException nex) {
			val = defaultValue;
		}

		LOG.debug("Value=[" + val + ']');

	    return val;
	}

	@Override
	@Lock(LockType.READ)
	public long getData(String key, long defaultValue) {
		LOG.debug("Key=[" + key + "] DefaultValue=[" + defaultValue + ']');

		long val;

		try {
		    val = Long.valueOf(cache.get(key));
		}
		catch(NumberFormatException nex) {
			val = defaultValue;
		}

		LOG.debug("Value=[" + val + ']');

	    return val;
	}
}
