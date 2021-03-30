package booking.client.gateway.messages;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Class used to receive JMS messages through ActiveMQ.
 * @author Jocelyn Routin
 */

public class MessageReceiverGateway {
    // Objects used in the class
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;

    /**
     * This method is used to setup the receiver.
     * @param channelName Name of the channel we want to receive a message.
     */
    public MessageReceiverGateway(String channelName) {
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
            consumer = session.createConsumer(destination);
            // Start the connection
            connection.start();
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set the listener of the previously defined receiver.
     * @param ml MessageListener object.
     */
    public void setListener(MessageListener ml) {
        try {
            consumer.setMessageListener(ml);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the JMS destination of the message.
     * @return Destination of the JMS message.
     */
    public Destination getDestination(){
        return destination;
    }
}