package com.home.gftest.singleton.simplecache;

import java.util.List;

import com.home.gftest.singleton.simplecache.model.ApplConfig;

/**
 * Interface definition for application configuration management.
 */
public interface ApplConfigService {
	/**
	 * Deliver all entries in ApplConfig
	 *
	 * @return the entries
	 */
	public List<ApplConfig> getContent();

	/**
	 * Get an ApplConfig item by its Primary Key id
	 *
	 * @param id the Key value
	 *
	 * @return the matching ApplConfig item
	 */
	public ApplConfig getById(String keyVal);

	/**
	 * Persist the given ApplConfig item
	 *
	 * @param config the item data
	 *
	 * @return the created item
	 */
	public ApplConfig create(ApplConfig config);

	/**
	 * Remove an ApplConfig item by Primary Key access
	 *
	 * @param id the Key value
	 *
	 * @return the deleted ApplConfig item or null
	 */
	public ApplConfig delete(String keyVal);

	/**
	 * Count the ApplConfig items
	 *
	 * @return the number of ApplConfig items
	 */
	public int count();
}
