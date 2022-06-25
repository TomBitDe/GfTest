package com.home.gftest.jms;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple topic text message producer session bean.<br>
 * <p>
 * Special is setObjectProperty("type", 0 or 1). See details in the code.
 */
@Stateless
@Local(com.home.gftest.jms.MsgTopicProducer1.class)
public class MsgTopicProducer1Bean implements MsgTopicProducer1 {
	private static final Logger LOG = LogManager.getLogger(MsgTopicProducer1Bean.class);

	@Resource(lookup = "jms/__defaultConnectionFactory")
	private ConnectionFactory factory;

	@Resource(lookup = "topic/Topic1")
	private Topic topic1;

	public MsgTopicProducer1Bean() {
		super();
		LOG.info("--> MsgTopicProducer1Bean");
		LOG.info("<-- MsgTopicProducer1Bean");
	}

	@Override
	public void sendOneMessage() {
		LOG.info("--> sendOneMessage");

		try {
			Connection connection = factory.createConnection();
			LOG.info("Connection created...");

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			LOG.info("Session created...");

			connection.start();
			LOG.info("Connection started...");

			MessageProducer producer = session.createProducer(topic1);
			LOG.info("Message Producer created...");

			Message message = session.createTextMessage("Ping");
			LOG.info("Text Message created...");

			// Prepare the message for the EvenNumberBean to consume
			// The OddNumberBean should not consume this message
			message.setObjectProperty("type", 0);

			producer.send(message);

			LOG.info("Message [" +  message.getBody(String.class) + "] send");
		}
		catch (JMSException jmsEx) {
			LOG.error(jmsEx.getMessage());
		}

		LOG.info("<-- sendOneMessage");
	}

	@Override
	public void sendManyMessages(int noMsgs) {
		LOG.info("--> sendManyMessages");

		try {
			Connection connection = factory.createConnection();
			LOG.info("Connection created...");

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			LOG.info("Session created...");

			connection.start();
			LOG.info("Connection started...");

			MessageProducer producer = session.createProducer(topic1);
			LOG.info("Message Producer created...");

			Message message;

			for (int idx=0; idx < noMsgs; ++idx) {
				message = session.createTextMessage("Message [" + idx + "]");
				LOG.info("Text Message created...");

				message.setObjectProperty("type", idx % 2);

				producer.send(message);

				LOG.info("Message [" +  message.getBody(String.class) + "] send");
			}
		}
		catch (JMSException jmsEx) {
			LOG.error(jmsEx.getMessage());
		}

		LOG.info("<-- sendManyMessages");
	}

}
