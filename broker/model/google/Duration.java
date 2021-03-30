package booking.broker.model.google;

/**
 * Class used by the class Elements to transform the JSON of the Google Distance API to an object.
 * @author Jocelyn Routin
 * @see booking.broker.model.google.Elements
 * @see booking.broker.model.google.Rows
 * @see booking.broker.model.google.DistanceAPI
 */

public class Duration {
    private String text;
    private Integer value;

    public Duration(){ }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}