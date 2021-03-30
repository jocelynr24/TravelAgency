package booking.client.gateway.applications;

import booking.client.gateway.messages.MessageReceiverGateway;
import booking.client.gateway.messages.MessageSenderGateway;
import booking.client.gateway.serializers.ClientSerializer;
import booking.client.model.client.ClientBookingReply;
import booking.client.model.client.ClientBookingRequest;

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
    private ClientSerializer clientSerializer = new ClientSerializer();
    // Hashmaps used in the class
    private Map<String, ClientBookingRequest> bookingRequests = new HashMap<>();
    private Map<Integer, Destination> replyJMS = new HashMap<>();
    // Variables used to manage multiple clients
    private Integer clientNumber = 0;
    private static Integer clientNumberTotal = 0;

    /**
     * Define the listener of the booking client.
     */
    public void setBookingListener(){
        // The client number is the last value of client created
        clientNumber = clientNumberTotal;
        // Create a new receiving queue with the client number
        receiver = new MessageReceiverGateway("bookingReplyQueue" + String.valueOf(clientNumber));
        // Put the destination of the previously defined receiver in a hashmap with the client number as key
        replyJMS.put(clientNumber, receiver.getDestination());
        // Add one to the total number of clients
        clientNumberTotal++;
        // Enable the listener of the previously defined receiver
        receiver.setListener(new MessageListener() {
            // When a message arrives
            @Override
            public void onMessage(Message message) {
                try{
                    // Get the ClientBookingRequest object thanks to the correlation ID
                    ClientBookingRequest request = bookingRequests.get(message.getJMSCorrelationID());
                    // We convert the string reply into a ClientBookingReply object
                    ClientBookingReply reply = clientSerializer.replyFromString(((TextMessage) message).getText());
                    // When a reply arrives, we call this method which is overrided in the BookingClientFrame method
                    onBookingReplyArrived(request, reply);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Called when a booking reply arrives, must be overrided in the BookingClientFrame class.
     * @param request The booking request retrieved from a hashmap in the setBookingListener() method.
     * @param reply The booking reply created in the setBookingListener() method.
     * @see booking.client.gui.BookingClientFrame
     * @see BrokerAppGateway#setBookingListener()
     */
    public void onBookingReplyArrived(ClientBookingRequest request, ClientBookingReply reply) {
        // Must be keep empty
    }

    /**
     * Called when the user clicks on the "send booking request" button.
     * @param request The booking request created by the input of the user.
     */
    public void applyForBooking(ClientBookingRequest request) {
        // Define the sender request queue
        sender = new MessageSenderGateway("bookingRequestQueue");
        // Serialize the message into a string
        String body = clientSerializer.requestToString(request);
        // Transform the string into a JMS message
        Message requestMessage = sender.createTextMessage(body);
        try{
            // Put the JMS reply to property thanks to the client number
            requestMessage.setJMSReplyTo(replyJMS.get(clientNumber));
        } catch (JMSException e){
            e.printStackTrace();
        }
        // Send the message
        String messageID = sender.send(requestMessage);
        // Put the booking request into a hashmap with the message ID as key
        bookingRequests.put(messageID, request);
    }
}