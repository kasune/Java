/* All materials herein: Copyright (c) 2001-2003 Network365 Ltd. All Rights Reserved.
 *
 * These materials are owned by Network365 Ltd. and are protected by copyright laws
 * and international copyright treaties, as well as other intellectual property laws
 * and treaties.
 *
 * All right, title and interest in the copyright, confidential information,
 * patents, design rights and all other intellectual property rights of
 * whatsoever nature in and to these materials are and shall remain the sole
 * and exclusive property of Network365 Ltd.
 *
 * DISCLAIMER OF WARRANTY :
 *
 * THIS SAMPLE CODE IS PROVIDED "AS IS," AND NETWORK365 MAKES NO REPRESENTATIONS
 * OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO, WARRANTIES
 * OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT THE USE OF
 * THE SAMPLE CODE WILL NOT INFRINGE ANY THIRD PARTY PATENTS, COPYRIGHTS,
 * TRADEMARKS OR OTHER RIGHTS. NETWORK365 WILL NOT BE LIABLE FOR ANY DIRECT,
 * INDIRECT,SPECIAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE
 * SOFTWARE OR DOCUMENTATION.
 *
 * You may modify the sample source code ("Sample Code") to design, develop and
 * test software for use solely with the Network365  products  ("Applications").
 * You may also reproduce and distribute the Sample Code in object code form
 * along with any modifications you make to the Sample Code, provided you comply
 * with the Redistribution Requirements described below.
 *
 * Redistribution Requirements. You may reproduce and distribute an unlimited
 * number of copies of the Sample Code as described above, provided that:
 * (i) you distribute the Sample Code only in conjunction with and as a part of
 * your Applications; (ii) the Sample Code  is used only in conjunction with
 * Network365 products ; (iii) you do not use Network365's name, logo, or
 * trademarks to advertise, market or promote your Applications without the
 * express written permission of Network365; (iv) you include the above
 * copyright notice and  disclaimer of warranty on any copies of the
 * Sample Code ; (v) you agree to indemnify, hold harmless, and defend
 * Network365 from and against any claims or lawsuits, including attorney's
 * fees,that arise or result from the use or distribution of your Applications.
 */

// Import the standard java packages
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
public class SendOnce {

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
//	    if (args.length < 3) {
//            System.out.println("Usage : SendOnce <from address> " +
//                "<to address> <message>");
//            System.exit(-1);
//        }
//
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
                    //connectionFactory.openConnection("smppcon", "smppcon");
                    //connectionFactory.openConnection("mach", "7mK0ry");
                    connectionFactory.openConnection("smscon", "password");

            // Open a session from the connection. We are not receiving, so
            // we do not need to specify a receive queue.
            Session session = connection.openSession(Session.Mode.SEND, null);

            // Create an SMS Message to send using the connectionFactory
            Message message = MessageFactory.createMessage();

            // Create a list of the originator addresses
            ArrayList fromAddressList = new ArrayList();

            // Create an SMS address for the originator
            //SMSAddress fromAddress = new SMSAddress("RIPAS", SMSAddress.TON.ALPHANUMERIC , SMSAddress.NPI.UNKNOWN);
            SMSAddress fromAddress = new SMSAddress("67388882222");
            //SMSAddress fromAddress = new SMSAddress("6738885555");
            //SMSAddress fromAddress = new SMSAddress("BODYSHOP");
            //System.out.println("From Address NPI: " + fromAddress.getNPI() + " ,TON: " + fromAddress.getTON());
            // Add the originator SMS address to the list of
            // originator addresses
            fromAddressList.add(fromAddress.toAddress());

            // Create the list of recipient addresses
            ArrayList toAddressList = new ArrayList();

            // Create an SMS Address for the recipient
            //SMSAddress toAddress = new SMSAddress("6738600105", SMSAddress.TON.INTERNATIONAL , SMSAddress.NPI.ISDN);
            SMSAddress toAddress = new SMSAddress("600600");
            //System.out.println("To Address NPI: " + toAddress.getNPI() + " ,TON: " + toAddress.getTON());
            // Add the recipient SMS address to the list of recipient addresses
            toAddressList.add(toAddress.toAddress());

            // Set the originator address for the message
            message.setFromList(fromAddressList);

            // Set the recipient address for the message
            message.setToList(toAddressList);

            // Set the content type of the message
            // The content type for a message is a single valued property
            // Usual SMS message has text/plain content type and you can use
            // the constant defined in the Prop.ContentType interface
            message.setString(Prop.CONTENT_TYPE, Prop.ContentType.TEXT_PLAIN);

            // Set the text content for the message
            // The text content for a message is a single valued property
            message.setString(Prop.TEXT_CONTENT, "thisisatest msg from agora API");

            // Send the message using the session
            Address[] failedRecipients = session.send(message);
            System.out.println("Message sent...");
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