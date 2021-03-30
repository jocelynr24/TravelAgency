package booking.broker.model.client;

/**
 * A booking may include a transfer from destination airport to a specific Address.
 * @author mpesic
 */

public class Address {
	// Variables and objects used in the class
	private String street;
	private int number;
	private String city;

	/**
	 * Constructor of the class (to create an "empty" object).
	 */
	public Address() {
		setStreet(null);
		setNumber(0);
		setCity(null);
	}

	/**
	 * Constructor of the class (to create a full object).
	 * @param street The street name.
	 * @param number The building number.
	 * @param city The city name.
	 */
	public Address(String street, int number, String city) {
		setStreet(street);
		setNumber(number);
		setCity(city);
	}

	/**
	 * Get the street name string of the Address object.
	 * @return The street name string.
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Set the street name string of the Address object.
	 * @param street The street name string.
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Get the building number integer of the Address object.
	 * @return The building number integer.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Set the building number integer of the Address object.
	 * @param number The building number integer.
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Get the city name string of the Address object.
	 * @return The city name string.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set the city name string of the Address object.
	 * @param city The city name string.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Return a string containing the transfer address information.
	 * @return A string containing the transfer address information.
	 */
	@Override
	public String toString() {
		return street + " " + number + ", " + city;
	}
}