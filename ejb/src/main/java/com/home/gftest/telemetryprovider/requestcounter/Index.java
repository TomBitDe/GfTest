package com.home.gftest.telemetryprovider.requestcounter;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.home.gftest.telemetryprovider.monitoring.entity.Diagnostics;

@Model
public class Index {
	private static AtomicLong REQUEST_COUNTER = new AtomicLong(0);

	@Inject
	Event<Diagnostics> diagnostics;

	@Inject
	GoodMorning gm;

	@PostConstruct
	public void onNewRequest() {
		final Diagnostics requests = Diagnostics.with("request", REQUEST_COUNTER.incrementAndGet());
		diagnostics.fire(requests);

		try {
            Cache<String, Logger> cache = CacheBuilder.newBuilder().build();
            cache.put("", null);
        }
		catch (Exception e) {
            e.printStackTrace();
        }
	}

	public Object ok() {
		gm.say();
		return null;
	}

	public Object tooEarly() {
		gm.tooEarly();
		return null;
	}
}
