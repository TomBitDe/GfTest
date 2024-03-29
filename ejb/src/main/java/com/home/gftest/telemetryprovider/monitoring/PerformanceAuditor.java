package com.home.gftest.telemetryprovider.monitoring;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;

import com.home.gftest.telemetryprovider.monitoring.boundary.MonitoringResource;

public class PerformanceAuditor {

    @Inject
    MonitoringResource monitoring;

    @AroundTimeout
    @AroundInvoke
    public Object measurePerformance(InvocationContext context) throws Exception {
        String methodName = context.getMethod().toString();
        long start = System.currentTimeMillis();

        try {
            return context.proceed();
        }
        catch (Exception e) {
            monitoring.exceptionOccurred(methodName, e);
            throw e;
        }
        finally {
            long duration = System.currentTimeMillis() - start;
            monitoring.add(methodName, duration);
        }
    }
}