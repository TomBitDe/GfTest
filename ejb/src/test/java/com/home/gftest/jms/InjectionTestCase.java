package com.home.gftest.jms;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class InjectionTestCase {
	private static final Logger LOG = Logger.getLogger(InjectionTestCase.class);

	private static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	static {
		ProcessBuilder builder = new ProcessBuilder();

		builder.directory(new File(System.getProperty("user.home")));

		if (isWindows) {
		    builder.command("cmd.exe", "/c", "C:/Users/Tom/Programme/Payara_Server/bin/asadmin.bat create-jms-resource --enabled=true --property=Name=Queue1 --restype=javax.jms.Queue queue/Queue1");
		}
		else {
		    builder.command("sh", "-c", "/Users/Tom/Programme/Payara_Server/bin/asadmin.bat create-jms-resource --enabled=true --property=Name=Queue1 --restype=javax.jms.Queue queue/Queue1");
		}

		try {
			Process process = builder.start();
			StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			int exitCode = process.waitFor();
			LOG.info("Exit code [" + exitCode + "]");
		}
		catch (IOException | InterruptedException ex) {
			LOG.error(ex.getMessage());
		}
	}

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				/* Put the test-ejb-jar.xml in JARs META-INF folder as ejb-jar.xml */
				.addAsManifestResource(new File("src/test/resources/META-INF/test-ejb-jar.xml"), "ejb-jar.xml")
				.addAsManifestResource(new File("src/test/resources/META-INF/test-glassfish-ejb-jar.xml"),
						"glassfish-ejb-jar.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml").addClasses(MsgQueueConsumer1Bean.class);

		System.out.println(archive.toString(true));

		return archive;
	}

	@Resource(lookup = "jms/__defaultConnectionFactory")
	private ConnectionFactory factory;

	@Resource(lookup = "queue/Queue1")
	private Queue queue1;

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

		MessageProducer producer = session.createProducer(queue1);
		assertNotNull(producer);
		LOG.info("Message Producer created...");

		Message message = session.createTextMessage("Ping");
		assertNotNull(message);
		LOG.info("Text Message created...");

		producer.send(message);

		LOG.info("Message [" + message.getBody(String.class) + "] send");
	}

	private static class StreamGobbler implements Runnable {
	    private InputStream inputStream;
	    private Consumer<String> consumer;

	    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
	        this.inputStream = inputStream;
	        this.consumer = consumer;
	    }

	    @Override
	    public void run() {
	        new BufferedReader(new InputStreamReader(inputStream)).lines()
	          .forEach(consumer);
	    }
	}
}