package booking.broker.model.client;

/**
 * This class stores all information about a client booking reply.
 * @author mpesic
 * @author Jocelyn Routin
 */

public class ClientBookingReply {
    // Variables and objects used in the class
    private String agencyName;
    private double totalPrice;

    /**
     * Constructor of the class (to create an "empty" object).
     */
    public ClientBookingReply() {
        super();
        setAgencyName(null);
        setTotalPrice(0);
    }

    /**
     * Constructor of the class (to create a full object).
     * @param agencyName The name of the agency which answers.
     * @param totalPrice The total price of the booking.
     */
    public ClientBookingReply(String agencyName, double totalPrice) {
        super();
        setAgencyName(agencyName);
        setTotalPrice(totalPrice);
    }

    /**
     * Get the agency name string of the ClientBookingReply object.
     * @return The agency name string.
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * Set the agency name string of the ClientBookingReply object.
     * @param agencyName The agency name string.
     */
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    /**
     * Get the total price value of the ClientBookingReply object.
     * @return The total price value.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Set the total price value of the ClientBookingReply object.
     * @param costs The total price value.
     */
    public void setTotalPrice(double costs) {
        this.totalPrice = costs;
    }

    /**
     * Return a string containing the booking reply information.
     * @return A string containing the booking reply information.
     */
    @Override
    public String toString() {
        return "Best price from " + agencyName + ": " + Double.toString(totalPrice) + "€";
    }
}