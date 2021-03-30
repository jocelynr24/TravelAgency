package booking.broker.model.google;

/**
 * Class used by the class Rows to transform the JSON of the Google Distance API to an object.
 * @author Jocelyn Routin
 * @see booking.broker.model.google.Rows
 * @see booking.broker.model.google.DistanceAPI
 */

public class Elements {
    private Distance distance;
    private Duration duration;
    private String status;

    public Elements(){ }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}