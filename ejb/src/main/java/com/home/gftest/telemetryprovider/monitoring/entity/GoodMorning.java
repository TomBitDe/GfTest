package com.home.gftest.telemetryprovider.monitoring.entity;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors(PerformanceAuditor.class)
public class GoodMorning {
    public void say() {
    	try {
			Thread.sleep(200);
		}
    	catch (Exception ex)
    	{
			Logger.getLogger(GoodMorning.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

    public void tooEarly() {
    	throw new IllegalStateException("Too early for good morning!");
    }
}
