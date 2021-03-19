package com.home.gftest.jms;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * On this topic two MDBs are listening. See the setObjectProperty("type", 0 or 1) for details.
 */
@RunWith(Arquillian.class)
public class InjectionTopicTest extends CommonJmsUtility {
	private static final Logger LOG = Logger.getLogger(InjectionTopicTest.class);

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"),
						"glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClasses(MsgTopicConsumerEvenNumberBean.class,
						MsgTopicConsumerOddNumberBean.class);

		System.out.println(archive.toString(true));

		return archive;
	}

	@Resource(lookup = "jms/__defaultConnectionFactory")
	private ConnectionFactory factory;

	@Resource(lookup = "topic/Topic1")
	private Topic topic1;

	@Test
	public void shouldBeAbleToSendMessage() throws Exception {
		Connection connection = factory.createConnection();
		assertNotNull(connection);
		LOG.info("Connection created...");

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		assertNotNull(session);
		LOG.info("Session created...");

		connection.start();
		LOG.info("Connection started...");

		MessageProducer producer = session.createProducer(topic1);
		assertNotNull(producer);
		LOG.info("Message Producer created...");

		Message message = session.createTextMessage("Ping");
		assertNotNull(message);
		LOG.info("Text Message created...");

		// Prepare the message for the EvenNumberBean
		message.setObjectProperty("type", 0);
		producer.send(message);

		// Prepare the message for the OddNumberBean
		message.setObjectProperty("type", 1);
		producer.send(message);

		LOG.info("Message [" + message.getBody(String.class) + "] send");
	}

	@Test
	public void sendManyMessages() throws Exception {
		Connection connection = factory.createConnection();
		assertNotNull(connection);
		LOG.info("Connection created...");

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		assertNotNull(session);
		LOG.info("Session created...");

		connection.start();
		LOG.info("Connection started...");

		MessageProducer producer = session.createProducer(topic1);
		assertNotNull(producer);
		LOG.info("Message Producer created...");

		Message message;

		for (int idx=0; idx < 100; ++idx) {
			message = session.createTextMessage("Message [" + idx + "]");
			LOG.info("Text Message created...");

			// Set the message property "type" to 0 or 1 for the consumer to evaluate
			// 0 for the EvenNumberBean, 1 for the OddNumberBean
			message.setObjectProperty("type", idx % 2);

			producer.send(message);

			LOG.info("Message [" +  message.getBody(String.class) + "] send");
		}

	}
}