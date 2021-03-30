package booking.agency.model.agency;

/**
 * This class stores information about the agency reply for a booking.
 * @author mpesic
 * @author Jocelyn Routin
 */

public class AgencyReply {
    // Variables and objects used in the class
    private String nameAgency;
    private double totalPrice;

    /**
     * Constructor of the class (to create an "empty" object).
     */
    public AgencyReply() {
        super();
        setNameAgency("");
        setTotalPrice(0);
    }

    /**
     * Constructor of the class (to create a full object).
     * @param nameAgency The agency name.
     * @param price The total price value.
     */
    public AgencyReply(String nameAgency, double price) {
        super();
        setNameAgency(nameAgency);
        setTotalPrice(price);
    }

    /**
     * Get the agency name string of the AgencyReply object.
     * @return The agency name string.
     */
    public String getNameAgency() {
        return nameAgency;
    }

    /**
     * Set the agency name string of the AgencyReply object.
     * @param nameAgency The agency name string.
     */
    public void setNameAgency(String nameAgency) {
        this.nameAgency = nameAgency;
    }

    /**
     * Get the total price value of the AgencyReply object.
     * @return The agency name string.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Set the total price value of the AgencyReply object.
     * @param totalPrice The agency name string.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Return a string containing the agency reply information.
     * @return A string containing the agency reply information.
     */
    @Override
    public String toString() {
        return "Price from " + nameAgency + ": " + Double.toString(totalPrice) + "€";
    }
}