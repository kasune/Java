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
 * It demonstrates the ability to use the same session to send and receive
 * messages to and from the Agora server. In this sample application, each
 * message is received and processed. After this processing, the demonstration
 * application sends a response message. A commit  is only performed once all
 * messages have been processed.
 */
public class AppTest extends Thread {

    public static final int THREADS = 3000;
    private String account = "lnet";
    private String pwd = "lnet";
    private String queue = "inbox";

    private int name;

    ConnectionFactory connectionFactory;
    Connection connection;


    /**
     * Entry point - main method of the class.
     *
     * @param args Command Line arguments
     */
    public static void main(String args[]) {


        for (int i = 0; i < THREADS; i++) {
            AppTest appTest = new AppTest();
            appTest.name = i;

            appTest.start();
        }

        try {
            Thread.sleep(5000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
     private void connect(){
         try{
        Properties config = new Properties();


        FileInputStream in = new FileInputStream("connector.conf");
        config.load(in);
        in.close();
        connectionFactory = ConnectionFactory.getConnectionFactory(null, config);

        connection = connectionFactory.openConnection(account, pwd);

        Session session = connection.openSession(Session.Mode.SEND_RECEIVE, queue);
         }catch(Exception e){}

     }

    public void getMsg() {
        Session session = null;
        try {

            while (true) {

                System.out.println(name + "  : Waiting...");
                Message message = session.receive();

                ArrayList fromList = new ArrayList();
                message.getFromList(fromList);

                SMSAddress fromAddress =
                        SMSAddress.fromAddress((Address) fromList.get(0));
                ArrayList toList = new ArrayList();
                message.getToList(toList);

                SMSAddress toAddress =
                        SMSAddress.fromAddress((Address) toList.get(0));

                String msgText = message.getString(Prop.TEXT_CONTENT);

                // Print out message
                System.out.println(name + "  >>> Message Received");
                System.out.println(name + "  >>> From : " + fromAddress);
                System.out.println(name + "  >>> To : " + toAddress);
                System.out.println(name + "  >>> Message : " + msgText);
                System.out.println();

                session.commit();
            }

        } catch (Exception ioe) {
            ioe.printStackTrace();
            System.out.println(name + "Closing session");
            try {
                session.close();
            } catch (Exception e) {

            }
        }
    }

    public void run() {

    }

}
