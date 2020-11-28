package com.home.gftest.ejb.samplesession;

import org.apache.log4j.Logger;


/**
 * A simple POJO class
 */
public class ThirdSession {
	private static final Logger LOG = Logger.getLogger(ThirdSession.class.getName());

	public ThirdSession() {
		super();
		LOG.info("--> ThirdSession");
		LOG.info("<-- ThirdSession");
	}

	public boolean businessMethod() {
		boolean ret = false;

		LOG.info("--> businessMethod");

		ret = true;

		LOG.info("<-- businessMethod");

		return ret;
	}
}
