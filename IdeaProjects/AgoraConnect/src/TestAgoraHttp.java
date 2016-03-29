/* All materials herein: Copyright (c) 2001-2005 Valista Ltd. All Rights Reserved.
 *
 * These materials are owned by Valista Ltd. and are protected by copyright laws
 * and international copyright treaties, as well as other intellectual property laws
 * and treaties.
 *
 * All right, title and interest in the copyright, confidential information,
 * patents, design rights and all other intellectual property rights of
 * whatsoever nature in and to these materials are and shall remain the sole
 * and exclusive property of Valista Ltd.
 *
 * DISCLAIMER OF WARRANTY :
 *
 * THIS SAMPLE CODE IS PROVIDED "AS IS," AND VALISTA MAKES NO REPRESENTATIONS
 * OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO, WARRANTIES
 * OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT THE USE OF
 * THE SAMPLE CODE WILL NOT INFRINGE ANY THIRD PARTY PATENTS, COPYRIGHTS,
 * TRADEMARKS OR OTHER RIGHTS. VALISTA WILL NOT BE LIABLE FOR ANY DIRECT,
 * INDIRECT,SPECIAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE
 * SOFTWARE OR DOCUMENTATION.
 *
 * You may modify the sample source code ("Sample Code") to design, develop and
 * test software for use solely with the Valista  products  ("Applications").
 * You may also reproduce and distribute the Sample Code in object code form
 * along with any modifications you make to the Sample Code, provided you comply
 * with the Redistribution Requirements described below.
 *
 * Redistribution Requirements. You may reproduce and distribute an unlimited
 * number of copies of the Sample Code as described above, provided that:
 * (i) you distribute the Sample Code only in conjunction with and as a part of
 * your Applications; (ii) the Sample Code  is used only in conjunction with
 * Valista products ; (iii) you do not use Valista's name, logo, or
 * trademarks to advertise, market or promote your Applications without the
 * express written permission of Valista; (iv) you include the above
 * copyright notice and  disclaimer of warranty on any copies of the
 * Sample Code ; (v) you agree to indemnify, hold harmless, and defend
 * Valista from and against any claims or lawsuits, including attorney's
 * fees,that arise or result from the use or distribution of your Applications.
 */

// import standard java packages
package test;
import java.io.*;
import java.util.*;

// import java servlet packages
import javax.servlet.*;
import javax.servlet.http.*;

// import mzone agora packages
import network365.agora.api.*;
import network365.agora.api.connector.*;
import network365.agora.api.extensions.sms.*;

/**
 * This demonstration application uses the Agora Connector SDK to send a message
 * entered from a HTML form using Java Servlet technology.
 */
public class TestAgoraHttp extends HttpServlet {
    /**
     * The location and name of the Agora connection factory configuration file
     */
    private static String configurationFile;
    /**
     * The username required to open an Agora connection from the connection
     * factory
     */
    private static String username;
    /**
     * The password required to open an Agora connection from the connection
     * factory
     */
    private static String password;

    /**
     * Initialise the servlet
     *
     * @param config Servlet configuration which can be used to pass in
     * initialisation parameters to the servlet
     * @exception ServletException If an error occurred initialising the servlet
     */
    public void init(ServletConfig config) throws ServletException {
        // Call the HttpServlet init method
        super.init(config);

        // Get the configurationFile parameter which is the relative location
        // of the configuration file
        String configurationFileName =
	    config.getInitParameter("configurationFile");
        // Throw an UnavailableException if the configurationFile parameter has
        // not been specified. UnavailableException is a sub-class of
        // ServletException
        if (configurationFileName == null) {
            throw new UnavailableException(
                "Required Parameter \"configurationFile\" Has Not Been " +
                "Specified");
        }

        // Get the absolute location of the configuration file
        configurationFile =
            getServletContext().getRealPath(configurationFileName);

        // Get the username required to open a connection from the connection
        // factory
        username = config.getInitParameter("username");
        // Throw an UnavailableException if the username parameter has not been
        // specified
        if (username == null) {
            throw new UnavailableException(
                "Required Parameter \"username\" Has Not Been Specified" );
        }

        // Get the password required to open a connection from the connection
        // factory
        password = config.getInitParameter("password");
        // Throw an UnavailableException if the password parameter has not been
        // specified
        if (password == null) {
            throw new UnavailableException(
                "Required Parameter \"password\" Has Not Been Specified" );
        }
    }

    /**
     * Handles Http GET request and response
     *
     * @param req Http request
     * @param res Http response
     * @throws ServletException If a servlet error occurred processing the GET
     * request and response
     * @throws IOException If an input/output error occurred processing the GET
     * request and response
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        // Redirect to doPost method
        doPost(req, res);
    }

    /**
     * Handles Http POST request and response
     *
     * @param req Http request
     * @param res Http response
     * @throws ServletException If a servlet error occurred processing the POST
     * request and response
     * @throws IOException If an input/output error occurred processing the POST
     * request and response
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        // Get the address of the originator of the message
        String originatorAddress = req.getParameter("originator");
        // Get the address of the recipient of the message
        String recipientAddress = req.getParameter("recipient");
        // Get the plain text message to send to the recipient from the
        // originator
        String messageContent = req.getParameter("message");

        String messageToDisplay;
        try {
            // Send the message via Agora
            messageToDisplay = send(originatorAddress, recipientAddress,
                messageContent);

        } catch (AgoraException ae) {
            messageToDisplay = ae.toString();
        }

        // Set the response type to plain text
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(messageToDisplay);
    }

    /**
     * Sends a message from an originator to a recipient through the Agora
     * server
     *
     * @param originatorAddress Address of the originator
     * @param recipientAddress Address of the recipient
     * @param messageContent Content of the message to send from the originator
     * to the recipient
     * @throws AgoraException If an error occurred sending the message to the
     * agora server
     * @throws IOException If an error occurred reading the configuration file
     */
    public String send(String originatorAddress, String recipientAddress,
        String messageContent) throws AgoraException, IOException {

        // Create a Properties object containing the config for the
        // Connection Factory
        Properties config = new Properties();

        // Load the connection properties from the configuration file
        FileInputStream in = new FileInputStream(configurationFile);
        config.load(in);
        in.close();

        // Initialise the connection factory explicitly
        ConnectionFactory connectionFactory =
            ConnectionFactory.getConnectionFactory(null, config);

        // Open a connection from the connection factory using username and
        // password as specified in the servlet initialisation parameters
        Connection connection =
            connectionFactory.openConnection(username, password);

        // Open a session from the connection that sends a message
        // Note: A queue name is not required if opening a session to SEND
        // messages only
        Session session =
            connection.openSession(Session.Mode.SEND, null);

        // Create an SMS address for the originator
        SMSAddress originatorSMSAddress =
            new SMSAddress(originatorAddress);

        // Add the originator address to the list of originators
        ArrayList originatorAddressList = new ArrayList();
        originatorAddressList.add(originatorSMSAddress.toAddress());

        // Create an SMS address for the recipient
        SMSAddress recipientSMSAddress =
            new SMSAddress(recipientAddress);

        // Add the recipient address to the list of recipients
        ArrayList recipientAddressList = new ArrayList();
        recipientAddressList.add(recipientSMSAddress.toAddress());

        // Create a message to send using the session
        Message message = MessageFactory.createMessage();

        // Set the originator addresses for the message
        message.setFromList(originatorAddressList);

        // Set the recipient addresses for the message
        message.setToList(recipientAddressList);

        // Set the text content for the message
        message.setString(Prop.TEXT_CONTENT, messageContent);

        // Set the content type for the message, use the constant defined in
        // the Prop.ContentType interface
        message.setString(Prop.CONTENT_TYPE, Prop.ContentType.TEXT_PLAIN);

        // Send the message using the session
        Address[] failedRecipients = session.send(message);

        // Create the message with the result string
        String statusMessage = "Message has been sent. ";

        // Check the addresses we failed to deliver to
        if (failedRecipients.length > 0) {
            statusMessage += "Failed to deliver to:";
            for (int j = 0; j < failedRecipients.length; j++) {
                try {
                    statusMessage += " " + SMSAddress.fromAddress(
                            failedRecipients[j]).toString();
                } catch (InvalidAddressFormatException iafe) {
                    statusMessage += " Unrecognized recipient";
                }
            }
            statusMessage += ".";
        }

        // Commit the session
        session.commit();

        // Close the session
        session.close();

        // Close the connection
        connection.close();

        // Shut down the connection factory
        connectionFactory.shutdown();

        // Return the result string
        return statusMessage;
    }
}