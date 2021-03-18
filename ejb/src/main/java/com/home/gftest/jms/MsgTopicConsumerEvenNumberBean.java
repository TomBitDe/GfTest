package com.home.gftest.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

/**
 * Simple MDB topic text message consumer.
 */
@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "NonDurable"),
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "topic/Topic1"),
				@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "type = 0"),
				@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		})
public class MsgTopicConsumerEvenNumberBean implements MessageListener {
	private static final Logger LOG = Logger.getLogger(MsgTopicConsumerEvenNumberBean.class);

    /**
     * Default constructor.
     */
    public MsgTopicConsumerEvenNumberBean() {
		super();
		LOG.info("--> MsgTopicConsumerEvenNumberBean");
		LOG.info("<-- MsgTopicConsumerEvenNumberBean");
    }

	@Override
	public void onMessage(Message message) {
    	try
        {
            LOG.info("onMessage: Message of Type [" + message.getClass().toString() + "] received");
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                LOG.info("TextMessage contains this text: [" + textMessage.getText() + ']');
            }
            else {
        	    LOG.info("Other message type. Try toString() = [" + message.toString() + ']');
		    }
        }
        catch (JMSException jex)
        {
        	LOG.fatal("Error on message processing: " + jex.getMessage(), jex );
            throw new EJBException ("Error on message processing: " + jex.getMessage(), jex );
        }
	}
}
