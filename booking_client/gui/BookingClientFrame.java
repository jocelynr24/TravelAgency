/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package booking.client.gui;

import javax.swing.*;

import booking.client.gateway.applications.BrokerAppGateway;
import booking.client.model.client.Address;
import booking.client.model.client.ClientBookingReply;
import booking.client.model.client.ClientBookingRequest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main class running the frame of the booking client and using the gateways.
 * @author mpesic
 * @author Jocelyn Routin
 */

public class BookingClientFrame extends JFrame {
    // Objects of the frame
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JList<ClientListLine> jList1;
    private JScrollPane jScrollPane2;
    private JButton jbSend;
    private JCheckBox jcbTransfer;
    private JTextField tfFromAirport;
    private JTextField tfNrTravellers;
    private JTextField tfToAirport;
    private JTextField tfTransferCity;
    private JTextField tfTransferHouseNumber;
    private JTextField tfTransferStreet;
    // List of ClientListLine linking a booking request to a booking reply
    private DefaultListModel<ClientListLine> listModel = new DefaultListModel<>();
    // Run a new BrokerAppGateway to receive the booking replies
    BrokerAppGateway brokerAppGateway = new BrokerAppGateway(){
        @Override
        public void onBookingReplyArrived(ClientBookingRequest request, ClientBookingReply reply) {
            // When a new reply arrives
            super.onBookingReplyArrived(request, reply);
            // Retrieve the ClientListLine object thanks to the booking request
            ClientListLine clientListLine = getClientListLine(request);
            // Set the reply in the previously retrieved ClientListLine object
            clientListLine.setReply(reply);
            // Find the index thanks to the booking request in the list
            int listModelIndex = getListModelIndex(request);
            // Set the booking reply corresponding to the previous ClientListLine object thanks to the index
            listModel.setElementAt(clientListLine, listModelIndex);
        }
    };

    /**
     * Creates new form NewJFrame.
     */
    public BookingClientFrame() {
        // Initialize all the components of the frame
        initComponents();
        // Enables the transfer fields only if the box is checked
        setTransfer(this.jcbTransfer.isSelected());
        // Start the listener for agency booking replies
        brokerAppGateway.setBookingListener();
    }

    /**
     * Returns a ClientListLine object contained in the list if it exists.
     * @param request The booking request we want to find in the list.
     * @return Returns the ClientListLine object if the booking request is in the list, else a null object.
     */
    private ClientListLine getClientListLine(ClientBookingRequest request) {
        // In all the elements of the list
        for (int i = 0; i < listModel.getSize(); i++) {
            // We get the ClientListLine object of the list which has the index "i"
            ClientListLine clientListLine = listModel.get(i);
            // If the booking request associated with the ClientListLine object is equal to the booking request in input parameter
            if (clientListLine.getRequest() == request) {
                // We return this ClientListLine object
                return clientListLine;
            }
        }
        // If we found nothing, we return a null object
        return null;
    }

    /**
     * Returns the index in the ClientListLine list thanks to the booking request.
     * @param request The booking request we want to find in the list.
     * @return Returns the index corresponding to a
     */
    private int getListModelIndex(ClientBookingRequest request){
        // In all the elements of the list
        for (int i = 0; i < listModel.getSize(); i++){
            // We get the ClientListLine object of the list which has the index "i"
            ClientListLine clientListLine = listModel.get(i);
            // If the booking request associated with the ClientListLine object is equal to the booking request in input parameter
            if (clientListLine.getRequest() == request){
                // We return the index of the ClientListLine object in the list
                return i;
            }
        }
        // If we found nothing, we return a null object
        return 0;
    }

    /**
     * Initialises all the components of the booking client frame.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jLabel1 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        tfFromAirport = new JTextField();
        tfToAirport = new JTextField();
        tfNrTravellers = new JTextField();
        tfTransferStreet = new JTextField();
        tfTransferHouseNumber = new JTextField();
        tfTransferCity = new JTextField();
        jbSend = new JButton();
        jScrollPane2 = new JScrollPane();
        jList1 = new JList<>();
        jcbTransfer = new JCheckBox();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Booking Client");
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jLabel1.setText("FLIGHT");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel3.setText("from airport");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setText("street");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setText("to airport");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setText("number");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(jLabel6, gridBagConstraints);

        jLabel7.setText("number of travellers");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(jLabel7, gridBagConstraints);

        jLabel8.setText("city");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(jLabel8, gridBagConstraints);

        tfFromAirport.setText("Schiphol");
        tfFromAirport.setName("");
        tfFromAirport.setPreferredSize(new Dimension(100, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(tfFromAirport, gridBagConstraints);

        tfToAirport.setText("Heathrow");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(tfToAirport, gridBagConstraints);

        tfNrTravellers.setText("3");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(tfNrTravellers, gridBagConstraints);

        tfTransferStreet.setText("Portman Square");
        tfTransferStreet.setPreferredSize(new Dimension(100, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(tfTransferStreet, gridBagConstraints);

        tfTransferHouseNumber.setText("30");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(tfTransferHouseNumber, gridBagConstraints);

        tfTransferCity.setText("London");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(tfTransferCity, gridBagConstraints);

        jbSend.setText("send booking request");
        jbSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbSendActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(jbSend, gridBagConstraints);

        jList1.setModel(listModel);
        jScrollPane2.setViewportView(jList1);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane2, gridBagConstraints);

        jcbTransfer.setFont(new Font("Tahoma", 1, 11));
        jcbTransfer.setText("TRANSFER ADDRESS");
        jcbTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jcbTransferActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jcbTransfer, gridBagConstraints);

        pack();
    }

    /**
     * When a user clicks on the button to send the booking request.
     * @param evt The ActionEvent object.
     */
    private void jbSendActionPerformed(ActionEvent evt) {
        // Retrieve the texts of the inputs
        String fromAirport = tfFromAirport.getText();
        String toAirport = tfToAirport.getText();
        int nrTravellers = Integer.parseInt(this.tfNrTravellers.getText());

        // If the transfer address button is checked, we create a Address object
        Address transferAddress = null;
        if (this.jcbTransfer.isSelected()) {
            String street = tfTransferStreet.getText();
            int number = Integer.parseInt(tfTransferHouseNumber.getText());
            String city = tfTransferCity.getText();
            transferAddress = new Address(street, number, city);
        }

        // Create a new booking request object with the previous information
        ClientBookingRequest request = new ClientBookingRequest(fromAirport, toAirport, nrTravellers, transferAddress);

        // Add the booking request object to the list (with a null booking reply)
        listModel.addElement(new ClientListLine(request, null));

        // Send booking request to the broker
        brokerAppGateway.applyForBooking(request);
    }

    /**
     * Enable the transfer address fields only if the button is checked.
     * @param withTransfer Determine if the transfer address button is checked.
     */
    private void setTransfer(boolean withTransfer) {
        this.tfTransferStreet.setEnabled(withTransfer);
        this.tfTransferHouseNumber.setEnabled(withTransfer);
        this.tfTransferCity.setEnabled(withTransfer);
    }

    /**
     * Detect a change of state of the check box.
     * @param evt The ActionEvent object.
     */
    private void jcbTransferActionPerformed(ActionEvent evt) {
        JCheckBox cbTransfer = (JCheckBox) evt.getSource();
        setTransfer(cbTransfer.isSelected());
    }

    /**
     * Main method of the booking client frame, initialize all the elements.
     * @param args The command line arguments.
     */
    public static void main(String args[]) {
        // Create and display the form
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // We start the first booking client
                BookingClientFrame bookingClientFrame1 = new BookingClientFrame();
                bookingClientFrame1.setBounds(10, 10, 500, 320);
                bookingClientFrame1.setTitle(bookingClientFrame1.getTitle() + " 1");
                bookingClientFrame1.setVisible(true);
                // We start the second booking client
                BookingClientFrame bookingClientFrame2 = new BookingClientFrame();
                bookingClientFrame2.setBounds(10, 340, 500, 320);
                bookingClientFrame2.setTitle(bookingClientFrame2.getTitle() + " 2");
                bookingClientFrame2.setVisible(true);
                // We start the second booking client
                BookingClientFrame bookingClientFrame3 = new BookingClientFrame();
                bookingClientFrame3.setBounds(10, 670, 500, 320);
                bookingClientFrame3.setTitle(bookingClientFrame3.getTitle() + " 3");
                bookingClientFrame3.setVisible(true);
            }
        });
    }
}
