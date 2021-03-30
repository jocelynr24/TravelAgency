package booking.broker.gateway.applications;

import booking.broker.gateway.messages.MessageReceiverGateway;
import booking.broker.gateway.messages.MessageSenderGateway;
import booking.broker.gateway.serializers.ClientSerializer;
import booking.broker.model.agency.AgencyRequest;
import booking.broker.model.client.ClientBookingReply;
import booking.broker.model.client.ClientBookingRequest;
import booking.broker.model.google.DistanceAPI;
import com.owlike.genson.Genson;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import static java.lang.Math.round;

/**
 * Class used to send and receive message to the client application.
 * @author Jocelyn Routin
 */

public class ClientAppGateway {
    // Objects used to serialize and send the messages
    private MessageSenderGateway sender;
    private MessageReceiverGateway receiver;
    private ClientSerializer serializer = new ClientSerializer();

    /**
     * Listener used to get the requests of the clients.
     */
    public void setBookingListener(){
        // We define the listening queue
        receiver = new MessageReceiverGateway("bookingRequestQueue");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try{
                    // We get the booking request and deserialize it into an ClientBookingRequest object
                    ClientBookingRequest request = serializer.requestFromString(((TextMessage) message).getText());
                    // We send the answer to the broker
                    onBookingRequestArrived(message.getJMSMessageID(), message.getJMSReplyTo(), request);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Called when a booking request arrives, must be overrided in the BrokerFrame class.
     * @param corrID The correlation ID of the received message.
     * @param replyTo The destination address of the received message.
     * @param request The booking request object.
     * @see booking.broker.gui.BrokerFrame
     * @see ClientAppGateway#setBookingListener()
     */
    public void onBookingRequestArrived(String corrID, Destination replyTo, ClientBookingRequest request){
        // Must be keep empty
    }

    /**
     * Get an agency request object thanks to a booking request object.
     * @param request The booking request object.
     * @return The agency request object.
     */
    protected AgencyRequest getAgencyRequest(ClientBookingRequest request){
        AgencyRequest agencyRequest = new AgencyRequest();
        agencyRequest.setFromAirport(request.getOriginAirport());
        agencyRequest.setToAirport(request.getDestinationAirport());
        agencyRequest.setNumberOfTravellers(request.getNumberOfTravellers());
        agencyRequest.setTransferDistance(getTransferDistance(request));
        return agencyRequest;
    }

    /**
     * Communicate to the Google Distance API to get the distance between the destination airport and the transfer address.
     * @param request The booking request object.
     * @return The value of the transfer distance in kilometers.
     */
    private double getTransferDistance(ClientBookingRequest request){
        // Variables for distance in meters and kilometers
        double transferDistanceKilometers = 0.0;
        double transferDistanceMeters = 0.0;

        // If there is a transfer address, else it remains "0"
        if(request.getTransferToAddress() != null){
            try{
                // Connection to the Google Distance API
                URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + request.getDestinationAirport().replaceAll("\\s+", "+") + "&destinations=" + request.getTransferToAddress().getStreet().replaceAll("\\s+", "+") + "+" + request.getTransferToAddress().getNumber() + "," + request.getTransferToAddress().getCity().replaceAll("\\s+", "+") +"&key=***");
                URLConnection urlConnection = url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                // We read the returned JSON
                String inputLine;
                StringBuilder jsonFile = new StringBuilder();
                while ((inputLine = reader.readLine()) != null){
                    jsonFile.append(inputLine);
                }
                reader.close();
                // We deserialize the returned JSON into object
                Genson genson = new Genson();
                DistanceAPI distanceAPI = genson.deserialize(jsonFile.toString(), DistanceAPI.class);
                // If the two status on the JSON is "OK"
                if(distanceAPI.getStatus().equals("OK") && distanceAPI.getRows()[0].getElements()[0].getStatus().equals("OK")){
                    // We get the transfer distance in meters and kilometers
                    transferDistanceMeters = distanceAPI.getRows()[0].getElements()[0].getDistance().getValue();
                    transferDistanceKilometers = transferDistanceMeters / 1000.0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return transferDistanceKilometers;
    }

    /**
     * Send a booking reply to the client.
     * @param corrID The correlation ID of the reply.
     * @param replyTo The JMS destination address of the reply.
     * @param reply The client booking reply object.
     */
    public void sendBookingReply(String corrID, Destination replyTo, ClientBookingReply reply) {
        // We create a new MessageSenderGateway object without channel name (because of the JMS destination address)
        sender = new MessageSenderGateway();
        // We serialize the agency request object
        String body = serializer.replyToString(reply);
        // We put the serialized string into a JMS message object
        Message replyMessage = sender.createTextMessage(body);
        try {
            // We set the correlation ID of the message
            replyMessage.setJMSCorrelationID(corrID);
            // We set the destination address of the message
            replyMessage.setJMSReplyTo(replyTo);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        // We send the message
        sender.send(replyMessage, replyTo);
    }
}