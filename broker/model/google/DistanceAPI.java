package booking.broker.model.google;

/**
 * Class used to transform the JSON of the Google Distance API to an object.
 * @author Jocelyn Routin
 */

public class DistanceAPI {
    private String[] destination_addresses;
    private String[] origin_addresses;
    private Rows[] rows;
    private String status;

    public DistanceAPI(){ }

    public String[] getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(String[] destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public String[] getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(String[] origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public Rows[] getRows() {
        return rows;
    }

    public void setRows(Rows[] rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}