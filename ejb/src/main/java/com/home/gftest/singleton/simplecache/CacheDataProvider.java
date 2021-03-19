package com.home.gftest.singleton.simplecache;

import java.util.Map;

public interface CacheDataProvider {
	public Map<String, String> loadCacheData();
}
