package com.home.gftest.telemetryprovider.monitoring.boundary;

import java.util.List;
import java.util.Map;

import com.home.gftest.telemetryprovider.monitoring.entity.Invocation;

public interface MonitoringRessourceMXBean {
	List<Invocation> getSlowestMethods();
	Map<String, String> getDiagnostics();
	Map<String, Integer> getExceptionStatistics();
	String getNumberOfExceptions();
	String getExceptions();
	void clear();
}
