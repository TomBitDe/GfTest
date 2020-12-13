/**
 * Stateless session bean classes for testing reference changes.<br>
 * <p>
 * All the reference changes are defined in ejb-jar.xml. Called local session beans all
 * implement the same local interface CalledSessionLocal. This makes it possible to change
 * the referenced bean by override in ejb-jar.xml by declaration.<br>
 * <p>
 * This is a sample for definitions like @Local({CalledSessionLocal.class, ACalledSessionLocal.class})<br>
 * Also a sample for @EJB(beanName="XCalledSession")
 */
package com.home.gftest.ejb.refchange;
