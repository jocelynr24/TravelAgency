package booking.broker.gateway.serializers;

import booking.broker.model.client.ClientBookingReply;
import booking.broker.model.client.ClientBookingRequest;
import com.owlike.genson.Genson;

/**
 * Class used to serialize and deserialize the client booking requests.
 * @author Jocelyn Routin
 */

public class ClientSerializer {
    // Genson object
    private Genson genson = new Genson();

    /**
     * Empty constructor.
     */
    public  ClientSerializer(){}

    /**
     * Serializes a ClientBookingRequest object to a string.
     * @param request ClientBookingRequest needed to be serialized.
     * @return String of the serialized ClientBookingRequest object.
     */
    public String requestToString(ClientBookingRequest request){
        return genson.serialize(request);
    }

    /**
     * Deserializes a string to a ClientBookingRequest object.
     * @param string String needed to be deserialized.
     * @return ClientBookingRequest object from the string.
     */
    public ClientBookingRequest requestFromString(String string){
        return genson.deserialize(string, ClientBookingRequest.class);
    }

    /**
     * Serializes a ClientBookingReply object to a string.
     * @param reply ClientBookingReply needed to be serialized.
     * @return String of the serialized ClientBookingReply object.
     */
    public String replyToString(ClientBookingReply reply){
        return genson.serialize(reply);
    }

    /**
     * Deserializes a string to a ClientBookingReply object.
     * @param string String needed to be deserialized.
     * @return ClientBookingReply object from the string.
     */
    public ClientBookingReply replyFromString(String string){
        return genson.deserialize(string, ClientBookingReply.class);
    }
}