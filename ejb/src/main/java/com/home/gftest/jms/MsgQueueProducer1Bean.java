package com.home.gftest.jms;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.log4j.Logger;

/**
 * Simple queue text message producer session bean.
 */
@Stateless
@Local(com.home.gftest.jms.MsgQueueProducer1.class)
public class MsgQueueProducer1Bean implements MsgQueueProducer1 {
	private static final Logger LOG = Logger.getLogger(MsgQueueProducer1Bean.class.getName());

	@Resource(lookup = "jms/__defaultConnectionFactory")
	private ConnectionFactory factory;

	@Resource(lookup = "queue/Queue1")
	private Queue queue1;

	public MsgQueueProducer1Bean() {
		super();
		LOG.info("--> MsgQueueProducer1Bean");
		LOG.info("<-- MsgQueueProducer1Bean");
	}

	@Override
	public void shouldBeAbleToSendMessage() {
		LOG.info("--> shouldBeAbleToSendMessage");

		try {
			Connection connection = factory.createConnection();
			LOG.info("Connection created...");

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			LOG.info("Session created...");

			connection.start();
			LOG.info("Connection started...");

			MessageProducer producer = session.createProducer(queue1);
			LOG.info("Message Producer created...");

			Message message = session.createTextMessage("Ping");
			LOG.info("Text Message created...");

			producer.send(message);

			LOG.info("Message [" +  message.getBody(String.class) + "] send");
		}
		catch (JMSException jmsEx) {
			LOG.error(jmsEx.getMessage());
		}

		LOG.info("<-- shouldBeAbleToSendMessage");
	}

}