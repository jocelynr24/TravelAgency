package booking.broker.model.agency;

/**
 * This class stores information about the agency request for a booking.
 * @author mpesic
 * @author Jocelyn Routin
 */

public class AgencyRequest {
    // Variables and objects used in the class
    private String fromAirport;
    private String toAirport;
    private int numberOfTravellers;
    private double transferDistance;

    /**
     * Constructor of the class (to create an "empty" object).
     */
    public AgencyRequest() {
        super();
        setToAirport(null);
        setFromAirport(null);
        setTransferDistance(0);
    }

    /**
     * Constructor of the class (to create a full object).
     * @param toAirport The destination airport.
     * @param fromAirport The origin airport.
     * @param transferDistance The transfer distance value in kilometers.
     */
    public AgencyRequest(String toAirport, String fromAirport, double transferDistance) {
        super();
        setToAirport(toAirport);
        setFromAirport(fromAirport);
        setTransferDistance(transferDistance);
    }

    /**
     * Get the origin airport string of the AgencyRequest object.
     * @return The origin airport string.
     */
    public String getFromAirport() {
        return fromAirport;
    }

    /**
     * Set the origin airport string of the AgencyRequest object.
     * @param fromAirport The origin airport string.
     */
    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    /**
     * Get the destination airport string of the AgencyRequest object.
     * @return The destination airport string.
     */
    public String getToAirport() {
        return toAirport;
    }

    /**
     * Set the destination airport string of the AgencyRequest object.
     * @param toAirport The destination airport string.
     */
    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    /**
     * Get the transfer distance value of the AgencyRequest object.
     * @return The transfer distance value.
     */
    public double getTransferDistance() {
        return transferDistance;
    }

    /**
     * Set the transfer distance value of the AgencyRequest object.
     * @param transferDistance The transfer distance value.
     */
    public void setTransferDistance(double transferDistance) {
        this.transferDistance = transferDistance;
    }

    /**
     * Get the number of travellers value of the AgencyRequest object.
     * @return The number of travellers.
     */
    public int getNumberOfTravellers() {
        return numberOfTravellers;
    }

    /**
     * Set the number of travellers value of the AgencyRequest object.
     * @param numberOfTravellers The number of travellers.
     */
    public void setNumberOfTravellers(int numberOfTravellers) {
        this.numberOfTravellers = numberOfTravellers;
    }

    /**
     * Return a string containing the agency request information.
     * @return A string containing the agency request information.
     */
    @Override
    public String toString() {
        return "From " + fromAirport + " to " + toAirport + (transferDistance != 0.0 ? (" - Distance: " + transferDistance + "km") : "") + " - Travellers: " + numberOfTravellers;
    }
}