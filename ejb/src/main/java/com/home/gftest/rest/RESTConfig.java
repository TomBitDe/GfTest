package com.home.gftest.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * REST configuration for services located in ejb.
 * <p>
 * Use RESTConfig to set the ApplicationPath for the REST services that are located in ejb.
 * Check annotation and adapt if needed.
 */
@ApplicationPath("resources")
public class RESTConfig extends Application {
}
