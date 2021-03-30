package booking.agency.gui;

import booking.agency.model.agency.AgencyReply;
import booking.agency.model.agency.AgencyRequest;

/**
 * This class links a agency request and a agency reply together.
 * @author mpesic
 */

class BookingAgencyListLine {
	// Agency request and client objects
	private AgencyRequest request;
	private AgencyReply reply;

	/**
	 * Constructor of the class, take a agency request and reply in parameter.
	 * @param request The agency request object.
	 * @param reply The agency reply object.
	 */
	public BookingAgencyListLine(AgencyRequest request,  AgencyReply reply) {
		setRequest(request);
		setReply(reply);
	}

	/**
	 * Get the agency request object of the BookingAgencyListLine object.
	 * @return The agency request object.
	 */
	public AgencyRequest getRequest() {
		return request;
	}

	/**
	 * Set the agency request object of the BookingAgencyListLine object.
	 * @param request The agency request object.
	 */
	private void setRequest(AgencyRequest request) {
		this.request = request;
	}

	/**
	 * Get the agency reply object of the BookingAgencyListLine object.
	 * @return The agency reply object.
	 */
	public AgencyReply getReply() {
		return reply;
	}

	/**
	 * Set the agency reply object of the BookingAgencyListLine object.
	 * @param reply The agency reply object.
	 */
	public void setReply(AgencyReply reply) {
		this.reply = reply;
	}

	/**
	 * Return a string containing the agency request and reply information.
	 * @return A string containing the agency request and reply information.
	 */
	@Override
	public String toString() {
	   return request.toString() + "  --->  " + ((reply!=null)?reply.toString():"waiting for reply...");
	}
}