package com.home.gftest.ejb.samplesession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A simple POJO class session
 */
public class ThirdSession {
	private static final Logger LOG = LogManager.getLogger(ThirdSession.class.getName());

	public ThirdSession() {
		super();
		LOG.trace("--> ThirdSession");
		LOG.trace("<-- ThirdSession");
	}

	public boolean businessMethod() {
		boolean ret = false;

		LOG.trace("--> businessMethod");

		ret = true;

		LOG.trace("<-- businessMethod");

		return ret;
	}
}
