package booking.broker.gateway.serializers;

import booking.broker.model.agency.AgencyReply;
import booking.broker.model.agency.AgencyRequest;
import com.owlike.genson.Genson;

/**
 * Class used to serialize and deserialize the agency requests.
 * @author Jocelyn Routin
 */

public class AgencySerializer {
    // Genson object
    private Genson genson = new Genson();

    /**
     * Empty constructor.
     */
    public AgencySerializer(){}

    /**
     * Serializes an AgencyRequest object to a string.
     * @param request AgencyRequest needed to be serialized.
     * @return String of the serialized AgencyRequest object.
     */
    public String requestToString(AgencyRequest request){
        return genson.serialize(request);
    }

    /**
     * Deserializes a string to an AgencyRequest object.
     * @param string String needed to be deserialized.
     * @return AgencyRequest object from the string.
     */
    public AgencyRequest requestFromString(String string){
        return genson.deserialize(string, AgencyRequest.class);
    }

    /**
     * Serializes an AgencyReply object to a string.
     * @param reply AgencyReply needed to be serialized.
     * @return String of the serialized AgencyReply object.
     */
    public String replyToString(AgencyReply reply){
        return genson.serialize(reply);
    }

    /**
     * Deserializes a string to an AgencyReply object.
     * @param string String needed to be deserialized.
     * @return AgencyReply object from the string.
     */
    public AgencyReply replyFromString(String string){
        return genson.deserialize(string, AgencyReply.class);
    }
}