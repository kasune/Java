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

import java.io.*;
import java.util.*;

import network365.agora.api.connector.*;
import network365.agora.api.extensions.sms.*;
import network365.agora.api.*;

/**
 * This sample class demonstrates how to send single SMS message
 * using Agora Application Connector API.
 */
public class MmpCon {
    /*private String account;
    private String password;
    private String queue;*/


    /*public static void main(String args[]) {
        try{
    MmpCon m=new MmpCon();
    Connection co=m.getCon("edimen","edimen");
    Session s=m.getSession(co,"inbox");
    String in[]=new String [3];
    in=m.getMessage(s);
    System.out.println(in[0]);
    System.out.println(in[1]);
    System.out.println(in[2]);
        }catch(AgoraException ae){

        }catch(IOException ie){}

    }*/

    public void sendMessage(Connection connection, Session session, String fromNo, String toNo, String msg) throws AgoraException {

        boolean out = false;

        Message message = MessageFactory.createMessage();
        ArrayList fromAddressList = new ArrayList();

        SMSAddress fromAddress = new SMSAddress(fromNo);
        fromAddressList.add(fromAddress.toAddress());
        ArrayList toAddressList = new ArrayList();

        SMSAddress toAddress = new SMSAddress(toNo);
        toAddressList.add(toAddress.toAddress());
        message.setFromList(fromAddressList);
        System.out.println("MSG setFromList");
        message.setToList(toAddressList);
        message.setString(Prop.CONTENT_TYPE, Prop.ContentType.TEXT_PLAIN);
        message.setString(Prop.TEXT_CONTENT, "msg");
        message.setBoolean(Prop.DELIVERY_STATUS_REQUESTED, true);
        System.out.println("MSG set String");
        
        Address[] failedRecipients = session.send(message);

        System.out.println("Msg Sent");

        if (failedRecipients.length > 0) {
            System.out.println("<<< Failed to deliver to:");
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
        session.commit();

        out = true;
    }

    public String[] getMessage(Session session) throws AgoraException {
        String msg[] = new String [3];

        System.out.println("Waiting for the message...");
        Message message = session.receive();
        System.out.println("Message received.");
        ArrayList fromList = new ArrayList();
        message.getFromList(fromList);
        ArrayList toList = new ArrayList();
        message.getToList(toList);

        msg[0] = message.getString(Prop.TEXT_CONTENT);

        // Print out the message originators

        for (int i = 0; i < fromList.size(); i++) {
            msg[1] = SMSAddress.fromAddress((Address) fromList.get(i)).getAddress();
        }

        // Print out the message recipients

        for (int i = 0; i < toList.size(); i++) {
            msg[2] = SMSAddress.fromAddress((Address) toList.get(i)).getAddress();
        }

        session.commit();
        return msg;

    }

    public Session getSession(Connection connection, String queue) throws AgoraException {
        System.out.println("Going to initiate the session.");
        Session session = connection.openSession(Session.Mode.SEND_RECEIVE,
                queue);
        System.out.println("Session initiated.");
        return session;
    }

    public Connection getCon(String account,String password) throws AgoraException, IOException {

        Properties config = new Properties();

        FileInputStream in = new FileInputStream("E:\\beyondm-back\\IdeaProjects\\MMP-Check\\conf\\connector.conf");

        config.load(in);
        in.close();
        System.out.println("connector properties  ok");


        ConnectionFactory connectionFactory =
                ConnectionFactory.getConnectionFactory(null, config);
        System.out.println("connector  ok connect Factory");


        Connection connection = connectionFactory.openConnection(account, password);

        return connection;

    }

    public void closeConnection(Connection con) {
        con.close();
    }

    public void closeFactory(ConnectionFactory confact) {
        confact.shutdown();
    }

    public void closeSession(Session ses) {
        ses.close();
    }

}

//}
