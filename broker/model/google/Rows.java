package booking.broker.model.google;

/**
 * Class used by the class DistanceAPI to transform the JSON of the Google Distance API to an object.
 * @author Jocelyn Routin
 * @see booking.broker.model.google.DistanceAPI
 */

public class Rows {
    private Elements[] elements;

    public Rows(){ }

    public Elements[] getElements() {
        return elements;
    }

    public void setElements(Elements[] elements) {
        this.elements = elements;
    }
}