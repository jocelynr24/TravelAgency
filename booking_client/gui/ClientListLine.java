package booking.client.gui;

import booking.client.model.client.ClientBookingReply;
import booking.client.model.client.ClientBookingRequest;

/**
 * This class links a booking request and a booking reply together.
 * @author mpesic
 */

class ClientListLine {
    // Booking request and client objects
    private ClientBookingRequest request;
    private ClientBookingReply reply;

    /**
     * Constructor of the class, take a booking request and reply in parameter.
     * @param request The booking request object.
     * @param reply The booking reply object.
     */
    public ClientListLine(ClientBookingRequest request, ClientBookingReply reply) {
        this.request = request;
        this.reply = reply;
    }

    /**
     * Get the booking request object of the ClientListLine object.
     * @return The booking request object.
     */
    public ClientBookingRequest getRequest() {
        return request;
    }

    /**
     * Set the booking request object of the ClientListLine object.
     * @param request The booking request object.
     */
    private void setRequest(ClientBookingRequest request) {
        this.request = request;
    }

    /**
     * Get the booking reply object of the ClientListLine object.
     * @return The booking reply object.
     */
    public ClientBookingReply getReply() {
        return reply;
    }

    /**
     * Set the booking reply object of the ClientListLine object.
     * @param reply The booking reply object.
     */
    public void setReply(ClientBookingReply reply) {
        this.reply = reply;
    }

    /**
     * Return a string containing the booking request and reply information.
     * @return A string containing the booking request and reply information.
     */
    @Override
    public String toString() {
        return request.toString() + "  --->  " + ((reply != null) ? reply.toString() : "waiting for reply...");
    }
}