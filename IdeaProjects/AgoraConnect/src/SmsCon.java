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

// import standard java packages
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

// import mzone agora packages
import network365.agora.api.*;
import network365.agora.api.connector.*;
import network365.agora.api.extensions.sms.*;

/**
 * This demonstration application uses the Agora Connector SDK to send and
 * receive messages in the same way a production Agora application would.
 * It demonstrates opening multiple sessions to the agora server. One session 
 * is used to send messages, and the other is used to receive messages.
 *
 */
public class SmsCon {
    
    /** 
     * Number of messages to be sent
     */
    public static final int MAX_MESSAGES = 4;
    
    
    /**
     * Entry point.
     *
     * @param args Command line arguments
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
            
            // Initialise the connection factory using the default connection 
            // factory class name (passing null as the factory class name)
            ConnectionFactory connectionFactory = 
                    ConnectionFactory.getConnectionFactory(null, config);
            
            // Open a connection from the connection factory
            // using username "smscon" and password "password".
            Connection connection =
                   connectionFactory.openConnection("smscon", "password");
            
            // Open a session from the connection. We are not receiving, so
            // we do not need to specify a receive queue.
            Session sendSession = 
                    connection.openSession(Session.Mode.SEND, null);

            for (int i = 0; i < MAX_MESSAGES; i++) {
                
                // Create an SMS Message to send using the session
                Message message = MessageFactory.createMessage();                
                
                // Create an SMS Address for the originator
                SMSAddress fromAddress = new SMSAddress("35386000000" + i);
                
                // Add the originator address to the list of originators
                ArrayList fromAddressList = new ArrayList();
                fromAddressList.add(fromAddress.toAddress());
                                                
                // Create an SMS Address for the recipient
                SMSAddress toAddress = new SMSAddress("353862468000");
                
                // Add the recipient address to the list of recipients
                ArrayList toAddressList = new ArrayList();
                toAddressList.add(toAddress.toAddress());

                // Create the message text
                String msgText = new String("message " + ( i + 1 ));
                
                // Set the originator address for the message
                message.setFromList(fromAddressList);
                
                // Set the recipient address for the message
                message.setToList(toAddressList);

                // Set the content type of the message. Use the constant
                // defined in the "Prop.ContentType"
                message.setString(Prop.CONTENT_TYPE, 
                        Prop.ContentType.TEXT_PLAIN);
                
                // Set the text content for the message
                message.setString(Prop.TEXT_CONTENT, msgText);
                
                // Send the message using the session
                Address[] failedRecipients = sendSession.send(message);
                
                // Check the addresses we failed to deliver to 
                if (failedRecipients.length > 0) {
                    System.out.println( "<<< Failed to deliver to:" );
                    for (int j = 0; j < failedRecipients.length; j++) {
                        try {
                            System.out.println(
                                    SMSAddress.fromAddress(
                                            failedRecipients[j]));
                        } catch (InvalidAddressFormatException iafe) {
                            System.out.println("Unrecognized recipient");
                        }
                    }
                }
                
                // Commit the session
                sendSession.commit();
                
                // Print out message
                System.out.println("<<< Sending Message " + ( i + 1 ) + " : ");
                System.out.println("<<< From : " + fromAddress);
                System.out.println("<<< To : " + toAddress);
                System.out.println("<<< Message : " + msgText);
                System.out.println();
            }            
            // Close the session
            sendSession.close();
         
            // Open a session from the connection that listens on the 'inbox'
            // queue
            Session receiveSession = 
                    connection.openSession(Session.Mode.RECEIVE, "inbox");
            
            for (int i = 0; i < MAX_MESSAGES; i++) {
                
                // Receive an SMS message
                Message message = receiveSession.receive();
                
                // Get the list of originators
                ArrayList fromList = new ArrayList();
                message.getFromList(fromList);

                // Get the originator address
                SMSAddress fromAddress = 
                    SMSAddress.fromAddress((Address) fromList.get(0));
                
                // Get the list of recipients
                ArrayList toList = new ArrayList();
                message.getToList(toList);
        
                // Get the recipient address
                SMSAddress toAddress = 
                        SMSAddress.fromAddress((Address)toList.get(0));

                // Get the content type of the message
                String msgContentType = message.getString(Prop.CONTENT_TYPE);
                
                // Get the text content of the message
                String msgText = message.getString(Prop.TEXT_CONTENT);
                
                // Commit the session
                receiveSession.commit();
                
                // Print out message                
                System.out.println(">>> Message Received " + (i + 1) + " : ");
                System.out.println(">>> From : " + fromAddress);
                System.out.println(">>> To : " + toAddress);
                System.out.println(">>> Message : " + msgText);
                System.out.println(">>> Content Type : " + msgContentType);
                System.out.println();
            }     
            
            // Close the session
            receiveSession.close();
            
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