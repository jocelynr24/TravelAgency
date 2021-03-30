package booking.agency.gateway.messages;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Class used to send JMS messages through ActiveMQ.
 * @author Jocelyn Routin
 */

public class MessageSenderGateway {
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;

    /**
     * This method is used to setup the sender.
     * @param channelName Name of the channel we want to send a message.
     */
    public MessageSenderGateway(String channelName) {
        try {
            // Setup the connection
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            props.put(("queue." + channelName), channelName);
            Context jndiContext = new InitialContext(props);
            ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = (Destination) jndiContext.lookup(channelName);
            producer = session.createProducer(destination);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to setup the sender (without channel).
     */
    public MessageSenderGateway(){
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");

            Context jndiContext = new InitialContext(props);
            ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            producer = session.createProducer(null);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to produce a Message object from a string.
     * @param body String content of the message.
     * @return JMS message object used by ActiveMQ.
     */
    public Message createTextMessage(String body) {
        Message message = null;
        try {
            message = session.createTextMessage(body);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * This method is used to send a previously defined message.
     * @param message JMS message to send.
     * @return The JMS message ID which is the correlation ID needed for the answer.
     */
    public String send(Message message) {
        try {
            producer.send(message);
            return message.getJMSMessageID();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to send a previously defined message (with reply to parameter).
     * @param message JMS message to send.
     * @param replyTo The JMS reply destination.
     * @return The JMS message ID which is the correlation ID needed for the answer.
     */
    public String send(Message message, Destination replyTo) {
        try {
            producer.send(replyTo, message);
            return message.getJMSMessageID();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
}