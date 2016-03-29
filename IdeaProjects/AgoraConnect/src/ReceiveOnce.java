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
 * This sample demonstrates how to receive an SMS message using
 * the Agora Application Connector API.
 */
public class ReceiveOnce {
    
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
            FileInputStream in = new FileInputStream("."+File.separator+"connector.conf");
            config.load(in);
            in.close();
            
            // Initialise the connection factory using the 
            // default connection factory (passing the null as the connection
            // factory name to the factory method)
            ConnectionFactory connectionFactory = 
                    ConnectionFactory.getConnectionFactory(null, config);
            
            // Open a connection from the connection factory
            // using username "smscon" and password "password".
            Connection connection =
                    connectionFactory.openConnection("edimen", "f4im5n");



            // Open a session from the connection using the default queue
            // Note that the default queue used is inbox.
            Session session = connection.openSession(Session.Mode.RECEIVE, 
                    "inbox");
            System.out.println();
            // Print out the message for the user
            System.out.println("Waiting for the message...");
            
            // Receive an SMS message
            Message message = session.receive();
            
            // Print out the message for the user
            System.out.println("Message received.");
            
            // Get the list of originators
            ArrayList fromList = new ArrayList();
            message.getFromList(fromList);
        
            // Get the list of recipients
            ArrayList toList = new ArrayList();
            message.getToList(toList);
        
            // Get the text of the message
            String textContent = message.getString(Prop.TEXT_CONTENT);
            
            // Print out the message originators 
            System.out.println("From:");
            for (int i = 0; i < fromList.size(); i++) {
                System.out.println(
                        SMSAddress.fromAddress(
                                (Address)fromList.get(i)).getAddress());
            }
            
            // Print out the message recipients
            System.out.println("To:");
            for (int i = 0; i < toList.size(); i++) {
                System.out.println(
                        SMSAddress.fromAddress(
                                (Address)toList.get(i)).getAddress());
            }
            
            // Print out the message content
            System.out.println("Text:");
            System.out.println(textContent);
            
            // Commit the exchange
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
