import java.io.*;
import java.util.*;

// Import mzone agora packages
import network365.agora.api.connector.*;
import network365.agora.api.extensions.sms.*;
import network365.agora.api.*;

/**
 * This sample class demonstrates how to send single SMS message
 * using Agora Application Connector API.
 */
public class Send {

    /**
     * The main method for the sample.
     * Usage: SendOnce &lt;from address&gt; &lt;to address&gt; &lt;message&gt;
     *
     * @param args The command line arguments. The first argument is the
     * originator address of the message. The second argument is the
     * recipient address of the message. The third argument is the text
     * content of the message.
     */
    public static void main(String args[]) {

        // Check the arguments
            if (args.length < 3) {
            System.out.println("Usage : SendOnce <from address> " +
                    "<to address> <message>");
                                System.exit(-1);
                            }

                            try {

                                // Create a Properties object containing the config for the
                                // Connection Factory
                                Properties config = new Properties();

                                // Load the connection properties from the 'connector.conf' file
                                FileInputStream in = new FileInputStream("connector.conf");
                                config.load(in);
                                in.close();

                                // Initialise the connection factory using the null as the
                                // connection factory class - this will pick up the default
                                // connection factory.
                                ConnectionFactory connectionFactory =
                                        ConnectionFactory.getConnectionFactory(null, config);

                                // Open a connection from the connection factory
                                // using username "appcon" and password "password".
                                Connection connection =
                                        connectionFactory.openConnection("smppcon", "smppcon");
//              connectionFactory.openConnection("dstest", "dst123");
//              connectionFactory.openConnection("quickcell", "qu1ckc3LL");

                                // Open a session from the connection. We are not receiving, so
                                // we do not need to specify a receive queue.
                                Session session = connection.openSession(Session.Mode.SEND, null);
                                System.out.println("open con");
                                            // Create an SMS Message to send using the connectionFactory
                                            Message message = MessageFactory.createMessage();
                                            System.out.println("msg create");
                                            // Create a list of the originator addresses
                                            ArrayList fromAddressList = new ArrayList();

                                            // Create an SMS address for the originator
                                            SMSAddress fromAddress = new SMSAddress(args[0]);

                                            // Add the originator SMS address to the list of
                                            // originator addresses
                                            fromAddressList.add(fromAddress.toAddress());

                                            // Create the list of recipient addresses
                                            ArrayList toAddressList = new ArrayList();

                                            // Create an SMS Address for the recipient
                                            SMSAddress toAddress = new SMSAddress(args[1]);

                                            // Add the recipient SMS address to the list of recipient addresses
                                            toAddressList.add(toAddress.toAddress());

                                            // Set the originator address for the message
                                            message.setFromList(fromAddressList);
                                 
