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
 * Simple MDB queue text message consumer.
 */
@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
		},
		mappedName = "queue/Queue1")
public class MsgQueueConsumer1Bean implements MessageListener {
	private static final Logger LOG = Logger.getLogger(MsgQueueConsumer1Bean.class);

    /**
     * Default constructor.
     */
    public MsgQueueConsumer1Bean() {
		super();
		LOG.info("--> MsgQueueConsumer1Bean");
		LOG.info("<-- MsgQueueConsumer1Bean");
    }

	/**
     * @see MessageListener#onMessage(Message)
     */
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
