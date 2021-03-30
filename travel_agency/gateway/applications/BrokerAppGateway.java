package booking.agency.gateway.applications;

import booking.agency.gateway.messages.MessageReceiverGateway;
import booking.agency.gateway.messages.MessageSenderGateway;
import booking.agency.gateway.serializers.AgencySerializer;
import booking.agency.model.agency.AgencyReply;
import booking.agency.model.agency.AgencyRequest;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to send and receive message to the broker application.
 * @author Jocelyn Routin
 */

public class BrokerAppGateway {
    // Objects used to serialize and send the messages
    private MessageSenderGateway sender;
    private MessageReceiverGateway receiver;
    private AgencySerializer agencySerializer = new AgencySerializer();
    // Hashmaps used in the class
    private Map<AgencyRequest, String> agencyRequests = new HashMap<>();
    private Map<AgencyRequest, Integer> agencyAggregation = new HashMap<>();

    /**
     * Define the listener of the agency.
     * @param agencyName The name of the agency.
     */
    public void setAgencyListener(String agencyName) {
        // Variable used to store the channel name
        String channelName = null;
        // Get the channel name thanks to the name of the agency
        switch (agencyName) {
            case "Book Fast":
                channelName = "fastRequestQueue";
                break;
            case "Book Cheap":
                channelName = "cheapRequestQueue";
                break;
            case "Book Good Service":
                channelName = "goodRequestQueue";
                break;
        }
        // Define the message receiver with the previously defined channel name
        receiver = new MessageReceiverGateway(channelName);
        // Enable the listener of agency requests
        receiver.setListener(new MessageListener() {
            // When a message arrives
            @Override
            public void onMessage(Message message) {
                try{
                    // Transform the received message from string into an AgencyRequest object
                    AgencyRequest agencyRequest = agencySerializer.requestFromString(((TextMessage) message).getText());
                    // Put the correlation ID of the message with the previously defined AgencyRequest object as key
                    agencyRequests.put(agencyRequest, message.getJMSCorrelationID());
                    // Put the aggregation ID of the message with the previously defined AgencyRequest object as key
                    agencyAggregation.put(agencyRequest, message.getIntProperty("aggregationID"));
                    // When a reply arrives, we call this method which is overrided in the BookingAgencyFrame method
                    onAgencyRequestArrived(agencyRequest);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Called when a booking request arrives, must be overrided in the BookingAgencyFrame class.
     * @param request The booking request passed in the setAgencyListener() method.
     * @see booking.agency.gui.BookingAgencyFrame
     * @see BrokerAppGateway#setAgencyListener(String)
     */
    public void onAgencyRequestArrived(AgencyRequest request) {
        // Must be keep empty
    }

    /**
     * Called when an agency replies to a booking request.
     * @param request The request received by the agencies.
     * @param reply The reply provided by an agency.
     */
    public void sendAgencyReply(AgencyRequest request, AgencyReply reply) {
        // Define the reply queue
        sender = new MessageSenderGateway("agencyReplyQueue");
        // Serialize the AgencyReply object into a string
        String body = agencySerializer.replyToString(reply);
        // Convert the previous string into a JMS message
        Message replyMessage = sender.createTextMessage(body);
        try {
            // Set the correlation ID to the previously defined JMS message
            replyMessage.setJMSCorrelationID(agencyRequests.get(request));
            // Set the aggregation ID to the previously defined JMS message
            replyMessage.setIntProperty("aggregationID", agencyAggregation.get(request));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        // Send the previously defined message
        sender.send(replyMessage);
    }
}