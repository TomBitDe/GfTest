package com.home.gftest.jms;

/**
 * MsgTopicProducer1 Session local interface definition
 */
public interface MsgTopicProducer1 {
	/**
	 * Send a simple text message
	 */
	public void sendOneMessage();
	/**
	 * Send an amount of simple text messages
	 */
	public void sendManyMessages(int noMsgs);
}
