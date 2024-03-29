package com.home.gftest.singleton.simplecache;

import java.util.List;

import com.home.gftest.singleton.simplecache.model.ApplConfig;

/**
 * Interface definition for application configuration management.
 */
public interface ApplConfigManager {
	/**
	 * Deliver all entries in ApplConfig
	 *
	 * @return the entries
	 */
	public List<ApplConfig> getAll();
}
