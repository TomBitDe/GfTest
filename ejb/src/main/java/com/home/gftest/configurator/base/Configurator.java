package com.home.gftest.configurator.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configurator {
	private static final Logger LOG = LogManager.getLogger(Configurator.class);
	private Map<String, String> configuration;
	private Set<String> unconfiguredFields = new HashSet<>();

	@PostConstruct
	public void fetchConfiguration() {
		this.configuration = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

		    {
		    	// Default values are here
			    put("com.home.gftest.latestarter.ControllerSessionTimer.controllerSessionRuns", "*/8");
			    put("pingControllerRuns", "*/5");
		    }
		};

		// Override defaults with explicitly configured entries
		this.configuration.put("com.home.gftest.latestarter.ControllerSessionTimer.controllerSessionRuns", "*/3");
	}

	private String obtainConfigurableName(InjectionPoint ip) {
		AnnotatedField<?> field = (AnnotatedField<?>) ip.getAnnotated();
		Configurable configurable = field.getAnnotation(Configurable.class);

		if (configurable != null) {
			return configurable.value();
		}
		else {
			String clazzName = ip.getMember().getDeclaringClass().getName();
			String memberName = ip.getMember().getName();
			String fqn = clazzName + "." + memberName;

			return fqn;
		}
	}

	@Produces
	public String getString(InjectionPoint ip) throws NotConfiguredException {
		String fieldName = obtainConfigurableName(ip);

		return getValueForKey(fieldName);
	}

	@Produces
	public int getInteger(InjectionPoint ip) throws NotConfiguredException {
		String stringValue = getString(ip);

		if (stringValue == null) {
			throw new NotConfiguredException();
		}
		return Integer.parseInt(stringValue);
	}

	private String getValueForKey(String fieldName) throws NotConfiguredException {
		String valueForFieldName = configuration.get(fieldName);

		if (valueForFieldName == null) {
			this.unconfiguredFields.add(fieldName);
			LOG.fatal("No configuration in Configurator for fieldName [" + fieldName + ']');
			throw new NotConfiguredException("No configuration in Configurator for fieldName [" + fieldName + ']');
		}

		return valueForFieldName;
	}
}
