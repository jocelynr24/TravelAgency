package booking.broker.gui;

import booking.broker.model.agency.AgencyReply;
import booking.broker.model.agency.AgencyRequest;
import booking.broker.model.client.ClientBookingRequest;

/**
 * This class links a booking request, an agency request, and a agency reply all together.
 * @author mpesic
 * @author Jocelyn Routin
 */

class BrokerListLine {
    // Objects used in the class
    private ClientBookingRequest bookingRequest;
    private AgencyRequest agencyRequest;
    private AgencyReply agencyReply;

    /**
     * Constructor of the class, take a booking request in parameter.
     * @param clientBookingRequest The booking request object.
     */
    public BrokerListLine(ClientBookingRequest clientBookingRequest){
        this.setBookingRequest(clientBookingRequest);
    }

    /**
     * Get the booking request object of the BrokerListLine object.
     * @return The booking request object.
     */
    public ClientBookingRequest getBookingRequest() {
        return bookingRequest;
    }

    /**
     * Set the booking request object of the BrokerListLine object.
     * @param bookingRequest The booking request object.
     */
    public void setBookingRequest(ClientBookingRequest bookingRequest) {
        this.bookingRequest = bookingRequest;
    }

    /**
     * Get the agency request object of the BrokerListLine object.
     * @return The agency request object.
     */
    public AgencyRequest getAgencyRequest() {
        return agencyRequest;
    }

    /**
     * Set the agency request object of the BrokerListLine object.
     * @param agencyRequest The agency request object.
     */
    public void setAgencyRequest(AgencyRequest agencyRequest) {
        this.agencyRequest = agencyRequest;
    }

    /**
     * Get the agency reply object of the BrokerListLine object.
     * @return The agency reply object.
     */
    public AgencyReply getAgencyReply() {
        return agencyReply;
    }

    /**
     * Set the agency reply object of the BrokerListLine object.
     * @param agencyReply The agency reply object.
     */
    public void setAgencyReply(AgencyReply agencyReply) {
        this.agencyReply = agencyReply;
    }

    /**
     * Return a string containing the booking request and the agency reply information.
     * @return A string containing the booking request and the agency reply information.
     */
    @Override
    public String toString(){
        return bookingRequest.toString() + " ---> " + ((agencyReply != null) ? agencyReply.toString() : "waiting for reply...");
    }
}