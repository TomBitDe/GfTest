package com.home.gftest.singleton.simplecache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Load data for a cache from a properties file.<br>
 * Since this are application global data the file name is 'globals.properties'.<br>
 */
public class CacheDataFromProperties implements CacheDataProvider {
	private static final Logger LOG = Logger.getLogger(CacheDataFromProperties.class);
	private static final String GLOBAL_PROPS = "globals.properties";

	/**
	 * Default constructor.
	 */
	public CacheDataFromProperties() {
		super();
		LOG.info("--> CacheDataFromProperties");
		LOG.info("<-- CacheDataFromProperties");
	}

	@Override
	public Map<String, String> loadCacheData() {
		LOG.debug("--> loadCacheData");

		Map<String, String> map = new HashMap<>();

		InputStream inputStream = null;

		try {
			LOG.info("Refresh cache from [" + GLOBAL_PROPS + ']');

			inputStream = this.getClass().getClassLoader().getResourceAsStream(GLOBAL_PROPS);

	        if (inputStream == null) {
	        	LOG.warn("InputStream is: " + inputStream + " --> cache is EMPTY!");
	        }
	        else {
		        LOG.info("InputStream is: " + inputStream);

		        Properties properties = new Properties();

				properties.load(inputStream);
				inputStream.close();

				properties.forEach((key, val) -> map.put((String) key, (String) val));
				LOG.info(GLOBAL_PROPS + " = " + map);
	        }
		}
        catch (IOException e) {
			LOG.error(e.getMessage());
			if (inputStream != null) {
				try { inputStream.close(); } catch (IOException ex) {}
			}
		}

		LOG.debug("<-- loadCacheData");

		return map;
	}
}
