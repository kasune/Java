import network365.agora.api.connector.ConnectionFactory;
import network365.agora.api.connector.Connection;
import network365.agora.api.connector.Session;
import network365.agora.api.*;
import network365.agora.api.extensions.sms.SMSAddress;

import java.util.Properties;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Dec 6, 2007
 * Time: 5:37:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgoraConnector {
    public void test(){
        System.out.print("testing ....");
    }

    public void connect(){

        try {

            String fromNo="6738885555";
            String toNo="0";
            String msg="Wonderfull life";

            // Create a Properties object containing the config for the
            // Connection Factory
            Properties config = new Properties();

            // Load the connection properties from the 'connector.conf' file

          //  FileInputStream in = new FileInputStream("/home/kasun/IdeaProjects/AgoraConnect/connector.conf");
          FileInputStream in = new FileInputStream("."+File.separator+"connector.conf");
            config.load(in);
            in.close();
            System.out.println("connector properties  ok");

            // Initialise the connection factory using the null as the
            // connection factory class - this will pick up the default
            // connection factory.
            ConnectionFactory connectionFactory =
                    ConnectionFactory.getConnectionFactory(null, config);
            System.out.println("connector  ok connect Factory");

            // Open a connection from the connection factory
            // using username "appcon" and password "password".
            Connection connection =
                    connectionFactory.openConnection("appcon", "password");

            //Connection connection =
                   // connectionFactory.openConnection("chat", "chat");

             System.out.println("connector  ok open connection");
            // Open a session from the connection. We are not receiving, so
            // we do not need to specify a receive queue.
            Session session = connection.openSession(Session.Mode.SEND, null);
            System.out.println("connector  ok open session");

            // Create an SMS Message to send using the connectionFactory
            Message message = MessageFactory.createMessage();
            System.out.println("create MSG factory");

            // Create a list of the originator addresses
            ArrayList fromAddressList = new ArrayList();

            // Create an SMS address for the originator
            //SMSAddress fromAddress = new SMSAddress(args[0]);

            SMSAddress fromAddress = new SMSAddress(fromNo);

            // Add the originator SMS address to the list of
            // originator addresses
            fromAddressList.add(fromAddress.toAddress());

            // Create the list of recipient addresses
            ArrayList toAddressList = new ArrayList();

            // Create an SMS Address for the recipient
            //SMSAddress toAddress = new SMSAddress(args[1]);

            SMSAddress toAddress = new SMSAddress(toNo);

            // Add the recipient SMS address to the list of recipient addresses
            toAddressList.add(toAddress.toAddress());

            // Set the originator address for the message
            message.setFromList(fromAddressList);
            System.out.println("MSG setFromList");

            // Set the recipient address for the message
            message.setToList(toAddressList);

            // Set the content type of the message
            // The content type for a message is a single valued property
            // Usual SMS message has text/plain content type and you can use
            // the constant defined in the Prop.ContentType interface
            message.setString(Prop.CONTENT_TYPE, Prop.ContentType.TEXT_PLAIN);

            // Set the text content for the message
            // The text content for a message is a single valued property
            //message.setString(Prop.TEXT_CONTENT, args[2]);

            message.setString(Prop.TEXT_CONTENT, msg);
            message.setBoolean(Prop.DELIVERY_STATUS_REQUESTED, true);
            System.out.println("MSG set String");

            // Send the message using the session
            Address[] failedRecipients = session.send(message);

            // Check the addresses we failed to deliver to
            if (failedRecipients.length > 0) {
                System.out.println( "<<< Failed to deliver to:" );
                for (int j = 0; j < failedRecipients.length; j++ ) {
                    try {
                        System.out.println(
                                SMSAddress.fromAddress(
                                        failedRecipients[j]));
                    } catch (InvalidAddressFormatException iafe) {
                        System.out.println("Unrecognized recipient");
                    }
                }
            }
            //System.out.println("test1");

           // Perform the commit on this session
            session.commit();

            // Close the session
            session.close();

            // Close the connection
            connection.close();

            // Shut down the connection factory
            connectionFactory.shutdown();


        } catch (AgoraException ae) {
            ae.printStackTrace();
            System.exit(-1);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        }



    }
}