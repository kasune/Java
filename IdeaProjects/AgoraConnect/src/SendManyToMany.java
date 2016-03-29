import network365.agora.api.connector.ConnectionFactory;
import network365.agora.api.connector.Connection;
import network365.agora.api.connector.Session;
import network365.agora.api.*;
import network365.agora.api.extensions.sms.SMSAddress;

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 23, 2008
 * Time: 11:23:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendManyToMany {

    //

    private static Properties config;
    private static FileInputStream in;
    private static String accounts[];
    private static String pwds[];
    private static String host;
    private static String toNo;
    private static String fromNo;
    private static String msg;
    private static String msgNo;

    private static String confFile="E:\\beyondm-back\\IdeaProjects\\AgoraConnect\\src\\multiconnector.conf";
    private static String pwdPrp="mzone.agora.connector.pwds";
    private static String hostPrp="mzone.agora.connector.host";
    private static String accountPrp="mzone.agora.connector.accounts";
    private static String fromNoPrp="mzone.agora.connector.from";
    private static String toNoPrp="mzone.agora.connector.to";
    private static String msgPrp="mzone.agora.connector.msg";
    private static String msgNoPrp="mzone.agora.connector.thread.no";

    public static void confLoad() {

        config = new Properties();

        try {

            in = new FileInputStream(confFile);
            config.load(in);
            in.close();
        } catch (java.io.IOException e) {
            System.out.println("configuration loaded.");
            System.out.println(e.toString());

        }
    }

    public static void main(String args[]) {
        SendManyToMany s1 = new SendManyToMany();
        s1.confLoad();
        //s1.connect("appcon","password");
        s1.propertyLoad();
    }

    public void propertyLoad(){
        SendManyToMany.setAccounts();
        SendManyToMany.setFromNo();
        SendManyToMany.setHost();
        SendManyToMany.setMsg();
        SendManyToMany.setToNo();
        SendManyToMany.setMsgNo();
        //System.out.println(s1.getMsgNo());
    }



    private static void getAllProperty(String sIn, String pIn) {
        StringTokenizer acc = new StringTokenizer(sIn, ",");
        StringTokenizer pwd = new StringTokenizer(pIn, ",");

        int count = 0;
        accounts = new String [acc.countTokens()];
        pwds = new String[pwd.countTokens()];

        while (acc.hasMoreTokens()) {
            accounts[count]=acc.nextElement().toString();
            pwds[count]=pwd.nextElement().toString();
            //System.out.println(accounts[count]);

            count += 1;
        }

    }



    public static int getMsgNo() {

        return Integer.parseInt(msgNo);
    }

    private static void setMsgNo() {
        msgNo = config.getProperty(msgNoPrp);
    }

    private static String getHost() {
        return host;
    }

    private static void setHost() {
        host = config.getProperty(hostPrp);
    }

    public static String [] getAccounts() {
        return accounts;
    }

    private static void setAccounts() {
        String strAccounts = config.getProperty(accountPrp);
        String strPwds = config.getProperty(pwdPrp);
        SendManyToMany.getAllProperty(strAccounts, strPwds);

    }

    public static String[] getPwds() {
        return pwds;
    }



    public static String getToNo() {
        return toNo;
    }

    private static void setToNo() {
        toNo = config.getProperty(toNoPrp);
    }

    public static String getFromNo() {
        return fromNo;
    }

    private static void setFromNo() {
        fromNo = config.getProperty(fromNoPrp);
    }

    public static String getMsg() {
        return msg;
    }

    private static void setMsg() {
        msg = config.getProperty(msgPrp);
    }

    public void connect(String account,String pwd,String fromNo,String toNo,String msg){

        try {
            System.out.println("connector loading to Factory....");
            ConnectionFactory connectionFactory =
                    ConnectionFactory.getConnectionFactory(null, config);
            System.out.println("Factory loaded");
            Connection connection =
                    connectionFactory.openConnection(account, pwd);

            System.out.println("opening connection....");
            Session session = connection.openSession(Session.Mode.SEND, null);
            System.out.println("Connection opened");
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
            message.setString(Prop.TEXT_CONTENT, msg);
            message.setBoolean(Prop.DELIVERY_STATUS_REQUESTED, true);
            System.out.println("MSG set String");

            Address[] failedRecipients = session.send(message);

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
            session.commit();
            session.close();
            connection.close();
            connectionFactory.shutdown();
            System.out.println("done");
        } catch (AgoraException ae) {
            System.out.println("Agora exceptions");
            System.out.println("Reconnecting...");
            connect(account,pwd,fromNo,toNo,msg);
//            ae.printStackTrace();
//            System.exit(-1);

        } catch (Exception ioe) {

            System.out.println("Agora exceptions");
            System.out.println("Reconnecting...");
            connect(account,pwd,fromNo,toNo,msg);
            //ioe.printStackTrace();
            //System.exit(-1);

        }
            finally{
            System.out.println("Exceptions thread going to termiante");
            System.exit(-1);

        }



    }

    public void getMessage(String account,String pwd,String fromNo,String toNo,String msg){


            try{
            // Load the connection properties from the 'connector.conf' file


            // Initialise the connection factory using the
            // default connection factory (passing the null as the connection
            // factory name to the factory method)
            System.out.println("connector loading to Factory....");
            ConnectionFactory connectionFactory =
                    ConnectionFactory.getConnectionFactory(null, config);
            System.out.println("Factory loaded");
            // Open a connection from the connection factory
            // using username "smscon" and password "password".
            System.out.println("opening connection....");
            Connection connection =
                    connectionFactory.openConnection(account, pwd);


            System.out.println("Connection opened");
            // Open a session from the connection using the default queue
            // Note that the default queue used is inbox.
            Session session = connection.openSession(Session.Mode.RECEIVE,
                    null);

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


    }catch(AgoraException ae) {
            System.out.println("Agora exceptions");
            System.out.println("Reconnecting...");
            getMessage(account,pwd,fromNo,toNo,msg);
//            ae.printStackTrace();
//            System.exit(-1);

        } catch (Exception ioe) {

            System.out.println("Agora exceptions");
            System.out.println("Reconnecting...");
            getMessage(account,pwd,fromNo,toNo,msg);
            //ioe.printStackTrace();
            //System.exit(-1);

        }
            finally{
            System.out.println("Exceptions thread going to termiante");
            System.exit(-1);

        }
    }
}
