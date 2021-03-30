package booking.broker.gui;

import booking.broker.gateway.applications.AgencyAppGateway;
import booking.broker.gateway.applications.ClientAppGateway;
import booking.broker.model.agency.AgencyReply;
import booking.broker.model.agency.AgencyRequest;
import booking.broker.model.client.ClientBookingReply;
import booking.broker.model.client.ClientBookingRequest;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import javax.jms.Destination;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class running the frame of the broker and using the gateways.
 * @author mpesic
 * @author Jocelyn Routin
 */

public class BrokerFrame extends JFrame {
    // Objects of the frame
    private JPanel contentPane;
    private JList<BrokerListLine> list;
    // List of BrokerListLine linking a booking request, an agency request, and an agency reply
    private DefaultListModel<BrokerListLine> listModel = new DefaultListModel<BrokerListLine>();
    // Hashmap storing the booking requests with agency request object as key
    private Map<AgencyRequest, ClientBookingRequest> bookingRequests = new HashMap<>();
    // Hashmap storing an array of integers (one for the expected answer number, the other for the number of answers) with the aggregation ID as key
    private Map<Integer, Integer[]> answersNumbers = new HashMap<>();
    // Hashmap storing the agency replies with the aggregation ID as key
    private Map<Integer, ArrayList<AgencyReply>> answersReplies = new HashMap<>();
    // Hashmap storing the destination address of a JMS message as correlation ID as key
    private Map<String, Destination> replyDestination = new HashMap<>();
    // Integer value for the aggregation ID
    private Integer aggregationID = 0;
    // Run a new ClientAppGateway to receive the booking requests
    private ClientAppGateway clientAppGateway = new ClientAppGateway(){
        @Override
        public void onBookingRequestArrived(String corrID, Destination replyTo, ClientBookingRequest request) {
            // When a new request arrives
            super.onBookingRequestArrived(corrID, replyTo, request);
            // Add this request to the BrokerListLine list
            add(request);
            // Create an AgencyRequest object thanks to the received request
            AgencyRequest agencyRequest = getAgencyRequest(request);
            // Add the previously created object to the BrokerListLine list
            add(request, agencyRequest);
            // Add the booking request to the bookingRequests hashmap with the agency request as key
            bookingRequests.put(agencyRequest, request);
            // Add the reply JMS address to the replyDestination hashmap with the correlation ID as key
            replyDestination.put(corrID, replyTo);

            // Initialization of an incrementer to determine the number of answers the broker will need to have
            Integer incrementer = 0;
            // Initialization of a string to temporally store the result of the rule for one agency
            String result;
            // Initialization and declaration (default on false) of booleans to store the result of each agency rule in boolean form
            boolean bookFastRule, bookCheapRule, bookGoodServiceRule;
            bookFastRule = bookCheapRule = bookGoodServiceRule = false;
            // We write the rules for each agency
            String bookFast        = "#{distance} == 0";                        // Book Fast         -> No transfer distance
            String bookCheap       = "#{distance} >= 10 && #{distance} <= 50";  // Book Cheap        -> Between 10km and 50km
            String bookGoodService = "#{distance} >= 0  && #{distance} <= 40";  // Book Good Service -> Between 0km and 40km
            // Declaration and instantiation of a new evaluator
            Evaluator evaluator = new Evaluator();
            // We put the variable "distance" as the transfer distance value in the evaluator
            evaluator.putVariable("distance", Double.toString(agencyRequest.getTransferDistance()));
            // We test the tree rules
            try {
                // We evaluate the Book Fast rule
                result = evaluator.evaluate(bookFast);
                // If the result equals "1.0", the rule is respected (true) else not (false)
                bookFastRule = result.equals("1.0");
                // We evaluate the Book Cheap rule
                result = evaluator.evaluate(bookCheap);
                // If the result equals "1.0", the rule is respected (true) else not (false)
                bookCheapRule = result.equals("1.0");
                // We evaluate the Book Good Service rule
                result = evaluator.evaluate(bookGoodService);
                // If the result equals "1.0", the rule is respected (true) else not (false)
                bookGoodServiceRule = result.equals("1.0");
            } catch (EvaluationException e) {
                e.printStackTrace();
            }

            // We send the message on the rules which was "true"
            if(bookFastRule){
                // Send a request to Book Fast agency
                agencyAppGateway.sendAgencyRequest("Book Fast", corrID, aggregationID, agencyRequest);
                // Add one to the incrementer
                incrementer++;
            }
            if(bookCheapRule){
                // Send a request to Book Cheap agency
                agencyAppGateway.sendAgencyRequest("Book Cheap", corrID, aggregationID, agencyRequest);
                // Add one to the incrementer
                incrementer++;
            }
            if(bookGoodServiceRule){
                // Send a request to Book Good Service agency
                agencyAppGateway.sendAgencyRequest("Book Good Service", corrID, aggregationID, agencyRequest);
                // Add one to the incrementer
                incrementer++;
            }

            // Add an array of two integer (one with the incrementer value, the other to 0 and set later) in the answersNumbers hashmap with the aggregation ID as key
            answersNumbers.put(aggregationID, new Integer[]{incrementer, 0});
            // Increment the aggregation ID of one for the future requests
            aggregationID++;
        }
    };

    private AgencyAppGateway agencyAppGateway = new AgencyAppGateway(){
        @Override
        public void onAgencyReplyArrived(String corrID, Integer aggrID, AgencyReply reply, AgencyRequest request) {
            // When a new reply arrives
            super.onAgencyReplyArrived(corrID, aggrID, reply, request);
            // Each time we receive new a reply, we store it
            if(answersReplies.get(aggrID) == null){
                // If it is the first answer, we have to instantiate a new list of AgencyReply
                ArrayList<AgencyReply> replies = new ArrayList<AgencyReply>();
                // We add the reply to the list of replies
                replies.add(reply);
                // We add the replies to the AgencyReply list in the answersReplies hashmap
                answersReplies.put(aggrID, replies);
                // We add one to the number of given answers (and we don't touch to the number of expected answers)
                answersNumbers.put(aggrID, new Integer[]{answersNumbers.get(aggrID)[0], answersNumbers.get(aggrID)[1] + 1});
            } else {
                // If it is not the first answer, we can use the already instantiated list of AgencyReply
                ArrayList<AgencyReply> replies = answersReplies.get(aggrID);
                // We add the reply to the list of replies
                replies.add(reply);
                // We add the replies to the AgencyReply list in the answersReplies hashmap
                answersReplies.put(aggrID, replies);
                // We add one to the number of given answers (and we don't touch to the number of expected answers)
                answersNumbers.put(aggrID, new Integer[]{answersNumbers.get(aggrID)[0], answersNumbers.get(aggrID)[1] + 1});
            }

            // If the number of replies is equal to the number of expected replies, we answer to the client with the best one
            if(answersNumbers.get(aggrID)[0].equals(answersNumbers.get(aggrID)[1])){
                // We declare and instantiate a new AgencyReply object
                AgencyReply bestReply = new AgencyReply();
                // We get the first agency reply in the previously defined object to compare with the others
                bestReply = answersReplies.get(aggrID).get(0);
                // We compare all the answers with the first one as reference
                for(int index = 0; index < answersNumbers.get(aggrID)[0]; index++){
                    // If the next answer is better than the first one, it becomes the new better answer
                    if(answersReplies.get(aggrID).get(index).getTotalPrice() < bestReply.getTotalPrice()){
                        bestReply = answersReplies.get(aggrID).get(index);
                    }
                }
                // We add the best answer to the list of replies
                add(bookingRequests.get(request), bestReply);
                // We create a booking reply object with the best agency reply
                ClientBookingReply bookingReply = getBookingReply(bestReply);
                // We send the booking reply to the client
                clientAppGateway.sendBookingReply(corrID, replyDestination.get(corrID), bookingReply);
            }
        }
    };

    /**
     * Main method of the broker frame, initialize all the elements.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // We start the broker frame
                    BrokerFrame brokerFrame = new BrokerFrame();
                    brokerFrame.setBounds(600, 250, 500, 500);
                    brokerFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialises all the components of the booking client frame.
     */
    public BrokerFrame() {
        setTitle("Broker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{46, 31, 86, 30, 89, 0};
        gbl_contentPane.rowHeights = new int[]{233, 23, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 7;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        contentPane.add(scrollPane, gbc_scrollPane);

        list = new JList<BrokerListLine>(listModel);
        scrollPane.setViewportView(list);

        // Start the listener for client request
        clientAppGateway.setBookingListener();
        // Start the listener for agency reply
        agencyAppGateway.setAgencyListener();
    }

    /**
     * Get a BrokerListLine object thanks to the booking request.
     * @param request The booking request object.
     * @return The BrokerListLine object if the booking request exists, else a null object.
     */
    private BrokerListLine getRequestReply(ClientBookingRequest request) {
        for (int i = 0; i < listModel.getSize(); i++) {
            BrokerListLine rr = listModel.get(i);
            if (rr.getBookingRequest() == request) {
                return rr;
            }
        }
        return null;
    }

    /**
     * Add a booking request object to the BrokerListLine list.
     * @param bookingRequest The booking request object.
     */
    public void add(ClientBookingRequest bookingRequest) {
        listModel.addElement(new BrokerListLine(bookingRequest));
    }

    /**
     * Add an agency request object to the BrokerListLine list thanks to the agency request object.
     * @param bookingRequest The booking request object.
     * @param agencyRequest The agency request object.
     */
    public void add(ClientBookingRequest bookingRequest, AgencyRequest agencyRequest) {
        // We get the BrokerListLine object thanks to the booking request object
        BrokerListLine brokerListLine = getRequestReply(bookingRequest);
        // If the previous object is not null and the agency request is not null
        if (brokerListLine != null && agencyRequest != null) {
            // We set the agency request in the BrokerListLine object
            brokerListLine.setAgencyRequest(agencyRequest);
            // We repaint the list on the broker frame
            list.repaint();
        }
    }

    /**
     * Add an agency reply object to the BrokerListLine list thanks to the booking request object.
     * @param bookingRequest
     * @param agencyReply
     */
    public void add(ClientBookingRequest bookingRequest, AgencyReply agencyReply) {
        // We get the BrokerListLine object thanks to the booking request object
        BrokerListLine rr = getRequestReply(bookingRequest);
        // If the previous object is not null and the agency reply is not null
        if (rr != null && agencyReply != null) {
            // We set the agency reply in the BrokerListLine object
            rr.setAgencyReply(agencyReply);
            // We repaint the list on the broker frame
            list.repaint();
        }
    }
}