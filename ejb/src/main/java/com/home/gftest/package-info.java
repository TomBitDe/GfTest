/**
 * Many bean classes for testing.<br>
 * <p>
 * The TimerOtherSessionsBean can be used to run session beans on the application server.<br>
 * <p>
 * The TimerJmsSessionsBean can be used to run JMS producers/consumers.<br>
 * It needs little configuration in the application server:<br>
 * - create a Queue named Queue1 with JNDI queue/Queue1<br>
 * - create a Topic named Topic1 with JNDI topic/Topic1<br>
 * <p>
 * See the test package for JUnit test with Arquillian.<br>
 * <p>
 * Always refer to ejb-jar.xml to find out which session bean is really called in the end.
 */
package com.home.gftest;