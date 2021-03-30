package booking.broker.gateway.applications;

import booking.broker.gateway.messages.MessageReceiverGateway;
import booking.broker.gateway.messages.MessageSenderGateway;
import booking.broker.gateway.serializers.AgencySerializer;
import booking.broker.model.agency.AgencyReply;
import booking.broker.model.agency.AgencyRequest;
import booking.broker.model.client.ClientBookingReply;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to send and receive message to the agency application.
 * @author Jocelyn Routin
 */

public class AgencyAppGateway {
    // Objects used to serialize and send the messages
    private MessageSenderGateway sender;
    private MessageReceiverGateway receiver;
    private AgencySerializer serializer = new AgencySerializer();
    // Hashmap to store the agency requests with the correlation ID as key
    private Map<String, AgencyRequest> requests = new HashMap<>();

    /**
     * Send a request to the agencies.
     * @param agencyName The name of the agency.
     * @param corrID The correlation ID of the request.
     * @param aggrID The aggregation ID of the request.
     * @param request The agency request object.
     */
    public void sendAgencyRequest(String agencyName, String corrID, Integer aggrID, AgencyRequest request) {
        // We define the channel name according to the agency name
        String channelName = null;
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
        // We create a new MessageSenderGateway object with the previously defined channel name
        sender = new MessageSenderGateway(channelName);
        // We serialize the agency request object
        String body = serializer.requestToString(request);
        // We put the serialized string into a JMS message object
        Message requestMessage = sender.createTextMessage(body);
        try {
            // We set the correlation ID of the message
            requestMessage.setJMSCorrelationID(corrID);
            // We set the aggregation ID of the message
            requestMessage.setIntProperty("aggregationID", aggrID);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        // We send the message
        sender.send(requestMessage);
        // We store the request object in the hashmap
        requests.put(corrID, request);
    }

    /**
     * Listener used to get the replies of the agencies.
     */
    public void setAgencyListener(){
        // We define the listening queue
        receiver = new MessageReceiverGateway("agencyReplyQueue");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try{
                    // We get the agency reply and deserialize it into an AgencyReply object
                    AgencyReply reply = serializer.replyFromString(((TextMessage) message).getText());
                    // We get the initial agency request object thanks to the correlation ID in the hashmap
                    AgencyRequest request = requests.get(message.getJMSCorrelationID());
                    // We send the answer to the client
                    onAgencyReplyArrived(message.getJMSCorrelationID(), message.getIntProperty("aggregationID"), reply, request);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Get a booking reply object thanks to an agency reply object.
     * @param reply The agency reply object.
     * @return The booking reply object.
     */
    protected ClientBookingReply getBookingReply(AgencyReply reply){
        ClientBookingReply bookingReply = new ClientBookingReply();
        bookingReply.setAgencyName(reply.getNameAgency());
        bookingReply.setTotalPrice(reply.getTotalPrice());
        return bookingReply;
    }

    /**
     * Called when an agency reply arrives, must be overrided in the BrokerFrame class.
     * @param corrID The correlation ID of the received message.
     * @param aggrID The aggregation ID of the received message.
     * @param reply The agency reply object.
     * @param request The agency request object.
     * @see booking.broker.gui.BrokerFrame
     * @see AgencyAppGateway#setAgencyListener()
     */
    public void onAgencyReplyArrived(String corrID, Integer aggrID, AgencyReply reply, AgencyRequest request) {
        // Must be keep empty
    }
}