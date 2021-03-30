/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking.agency.gui;


import booking.agency.gateway.applications.BrokerAppGateway;
import booking.agency.model.agency.AgencyReply;
import booking.agency.model.agency.AgencyRequest;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class where the booking agency frames are defined.
 * @author mpesic
 */

public class BookingAgencyFrame extends JFrame {
    // Objects of the frame
    private JLabel jLabel1;
    private JList<BookingAgencyListLine> jList1;
    private JPanel jPanel1;
    private JScrollPane jScrollPane2;
    private JButton jbSendAgencyReply;
    private JTextField jtfTotalPrice;
    // List of BookingAgencyListLine linking an agency request to a agency reply
    private DefaultListModel<BookingAgencyListLine> listModel = new DefaultListModel<>();
    // Variable used for agency name
    private String agencyName;
    // Run a new BrokerAppGateway to receive the booking requests
    BrokerAppGateway brokerAppGateway = new BrokerAppGateway(){
        @Override
        public void onAgencyRequestArrived(AgencyRequest request) {
            super.onAgencyRequestArrived(request);
            BookingAgencyListLine bookingAgencyListLine = new BookingAgencyListLine(request, null);
            listModel.addElement(bookingAgencyListLine);
        }
    };

    /**
     * Creates new BookingAgencyFrame frame.
     * @param agencyName The name of the agency.
     */
    public BookingAgencyFrame(String agencyName) {
        initComponents();
        setTitle(agencyName);
        this.agencyName = agencyName;
        brokerAppGateway.setAgencyListener(agencyName);
    }

    /**
     * Initialises all the components of the agency frame.
     */
    @SuppressWarnings("unchecked")
     private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jbSendAgencyReply = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfTotalPrice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 4, 0, 4, 0, 4, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        jList1.setModel(listModel);
        jScrollPane2.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 2.0;
        getContentPane().add(jScrollPane2, gridBagConstraints);

        jbSendAgencyReply.setText("send price offer");
        jbSendAgencyReply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbSendAgencyReplyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        getContentPane().add(jbSendAgencyReply, gridBagConstraints);

        jLabel1.setText("total price for this booking:");
        jPanel1.add(jLabel1);
        jLabel1.getAccessibleContext().setAccessibleName("total price:");

        jtfTotalPrice.setMinimumSize(new java.awt.Dimension(100, 100));
        jtfTotalPrice.setName("");
        jtfTotalPrice.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(jtfTotalPrice);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }

    /**
     * When a user clicks on the button to send the agency request.
     * @param evt The ActionEvent object.
     */
    private void jbSendAgencyReplyActionPerformed(ActionEvent evt) {
        // Retrieve the texts of the inputs
        BookingAgencyListLine jListLine = jList1.getSelectedValue();
        double price = Double.parseDouble(this.jtfTotalPrice.getText());

        // Create a new agency reply object with the previous information
        AgencyReply reply = new AgencyReply(this.agencyName, price);
        if (jListLine != null) {
            jListLine.setReply(reply);
            jList1.repaint();
            // Create an agency request object
            AgencyRequest request = jListLine.getRequest();
            // Send booking request to the broker
            brokerAppGateway.sendAgencyReply(request, reply);
        }
    }
}