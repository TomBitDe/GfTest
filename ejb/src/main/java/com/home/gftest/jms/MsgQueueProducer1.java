package com.home.gftest.jms;

/**
 * MsgQueueProducer1 Session local interface definition
 */
public interface MsgQueueProducer1 {
	/**
	 * Send a simple text message
	 */
	public void shouldBeAbleToSendMessage();
	/**
	 * Send an amount of simple text messages
	 */
	public void sendManyMessages(int noMsgs);
}
