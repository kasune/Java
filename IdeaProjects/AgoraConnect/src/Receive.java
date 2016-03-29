// Import the standard java packages
import java.io.*;
import java.util.*;

// Import mzone agora packages
import network365.agora.api.connector.*;
import network365.agora.api.extensions.sms.*;
import network365.agora.api.*;

/**
 * This sample demonstrates how to receive an SMS message using
 * the Agora Application Connector API.
 */
public class Receive {

    /**
     * The main method for the sample.
     * Usage: ReceiveOnce
     *
     * @param args The command line arguments.
     */
    public static void main(String args[]) {

        try {

            // Create a Properties object containing the config for the
            // Connection Factory
            Properties config = new Properties();

            // Load the connection properties from the 'connector.conf' file
            FileInputStream in = new FileInputStream("connector.conf");
            config.load(in);
            in.close();
            // FileOutputStream out = new FileOutputStream("msg.log");
	    // PrintStream p = new PrintStream( out );

            // Initialise the connection factory using the
            // default connection factory (passing the null as the connection
            // factory name to the factory method)
            ConnectionFactory connectionFactory =
                    ConnectionFactory.getConnectionFactory(null, config);

            // Open a connection from the connection factory
            // using username "smscon" and password "password".
            System.out.println("Connecting to the MDP.....");
            Connection connection =
            //        connectionFactory.openConnection("mach", "7mK0ry");
	    //	      connectionFactory.openConnection("edimen", "f4im5n");

            connectionFactory.openConnection("DBAcc", "password");
	        System.out.println("Connected to the MDP.....");

            // Open a session from the connection using the default queue
            // Note that the default queue used is inbox.
            System.out.println("going to create the session.....");
            Session session = connection.openSession(Session.Mode.RECEIVE,
                    "DBAccQ");
	        System.out.println("Session created with MDP.....");
            // Print out the message for the user
            //p.println("Waiting for the message...");
            System.out.println("Waiting for the message.....");
            // Receive an SMS message
            Message message = session.receive();

            // Print out the message for the user
            // p.println("Message received.");
            System.out.println("Message received from MDP.....");
            // Get the list of originators
            ArrayList fromList = new ArrayList();
            message.getFromList(fromList);

            // Get the list of recipients
            ArrayList toList = new ArrayList();
            message.getToList(toList);
            // Get the text of the message
            /*if(message.getString(Prop.CONTENT_TYPE).equals(Prop.ContentType.DELIVERY_STATUS)){
                System.out.println("delivery message");
            }*/

                String textContent = message.getString(Prop.TEXT_CONTENT);
                System.out.println("Message Received :" + textContent);


            // Print out the message originators
            // p.println("From:");
            for (int i = 0; i < fromList.size(); i++) {
                System.out.println(
                        SMSAddress.fromAddress(
                                (Address)fromList.get(i)).getAddress());
            }

            // Print out the message recipients
            //p.println("To:");
            for (int i = 0; i < toList.size(); i++) {
               System.out.println(
                        SMSAddress.fromAddress(
                                (Address)toList.get(i)).getAddress());
            }

            // Print out the message content
            // p.println("Text:" + textContent);
            System.out.println(textContent);

            // Commit the exchange
            session.commit();

            // Close the session
            session.close();

            // Close the connection
            connection.close();

            // Shut down the connection factory
            connectionFactory.shutdown();
            // p.close();
        } catch (AgoraException ae) {
            ae.printStackTrace();
            System.exit(-1);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        }
    }
}
