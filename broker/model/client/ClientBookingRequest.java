package booking.broker.model.client;

/**
 * This class stores all information about a client booking request.
 * @author mpesic
 * @author Jocelyn Routin
 */

public class ClientBookingRequest {
    // Variables and objects used in the class
    private String originAirport;
    private String destinationAirport;
    private Address transferToAddress;
    private int numberOfTravellers;

    /**
     * Constructor of the class (to create an "empty" object).
     */
    public ClientBookingRequest() {
        super();
        setOriginAirport("");
        setDestinationAirport("");
        setTransferToAddress(null);
        setNumberOfTravellers(1);
    }

    /**
     * Constructor of the class (to create a full object).
     * @param originAirport The origin airport.
     * @param destinationAirport The destination airport.
     * @param nrTravellers The number of travellers.
     * @param transfer The Address object of the transfer address.
     */
    public ClientBookingRequest(String originAirport, String destinationAirport, int nrTravellers, Address transfer) {
        super();
        setOriginAirport(originAirport);
        setDestinationAirport(destinationAirport);
        setTransferToAddress(transfer);
        setNumberOfTravellers(nrTravellers);
    }

    /**
     * Constructor of the class (without the transfer address).
     * @param originAirport The origin airport.
     * @param destinationAirport The destination airport.
     * @param nrTravellers The number of travellers.
     */
    public ClientBookingRequest(String originAirport, String destinationAirport, int nrTravellers) {
        super();
        setOriginAirport(originAirport);
        setDestinationAirport(destinationAirport);
        setTransferToAddress(null);
        setNumberOfTravellers(nrTravellers);
    }

    /**
     * Get the origin airport string of the ClientBookingRequest object.
     * @return The origin airport string.
     */
    public String getOriginAirport() {
        return originAirport;
    }

    /**
     * Set the origin airport string of the ClientBookingRequest object.
     * @param originAirport The origin airport string.
     */
    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    /**
     * Get the destination airport string of the ClientBookingRequest object.
     * @return The destination airport string.
     */
    public String getDestinationAirport() {
        return destinationAirport;
    }

    /**
     * Set the destination airport string of the ClientBookingRequest object.
     * @param destinationAirport The destination airport string.
     */
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    /**
     * Get the Address object of the transfer address of the ClientBookingRequest object.
     * @return The Address object of the transfer address.
     */
    public Address getTransferToAddress() {
        return transferToAddress;
    }

    /**
     * Set the Address object of the transfer address of the ClientBookingRequest object.
     * @param transferToAddress The Address object of the transfer address.
     */
    public void setTransferToAddress(Address transferToAddress) {
        this.transferToAddress = transferToAddress;
    }

    /**
     * Get the number of travellers integer of the ClientBookingRequest object.
     * @return The number of travellers integer.
     */
    public int getNumberOfTravellers() {
        return numberOfTravellers;
    }

    /**
     * Set the number of travellers integer of the ClientBookingRequest object.
     * @param numberOfTravellers The number of travellers integer.
     */
    public void setNumberOfTravellers(int numberOfTravellers) {
        this.numberOfTravellers = numberOfTravellers;
    }

    /**
     * Return a string containing the booking request information.
     * @return A string containing the booking request information.
     */
    @Override
    public String toString() {
        return "From " + originAirport + " to " + destinationAirport + " - Travellers: " + numberOfTravellers + (transferToAddress != null ? (" - Transfer: " + transferToAddress) : "");
    }
}