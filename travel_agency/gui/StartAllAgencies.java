package booking.agency.gui;

import java.awt.EventQueue;

/**
 * Class used to start the three agencies.
 * @author mpesic
 */

public class StartAllAgencies {

	/**
	 * Main method of the agency frame, initialize all the elements.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		// Start the Book Fast agency frame
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingAgencyFrame bookingAgencyFrame = new BookingAgencyFrame ("Book Fast");
					bookingAgencyFrame.setBounds(1200, 10, 500, 320);
					bookingAgencyFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// Start the Book Cheap agency frame
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingAgencyFrame bookingAgencyFrame = new BookingAgencyFrame ("Book Cheap");
					bookingAgencyFrame.setBounds(1200, 340, 500, 320);
					bookingAgencyFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// Start the Book Good Service agency frame
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingAgencyFrame bookingAgencyFrame = new BookingAgencyFrame ("Book Good Service");
					bookingAgencyFrame.setBounds(1200, 670, 500, 320);
					bookingAgencyFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
