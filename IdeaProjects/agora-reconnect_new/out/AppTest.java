import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Date;

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

    private String name;
    private int count;
    static int all;

    AppTest(String na){
        super();
        name=na;

    }

    /**
     * Entry point - main method of the class.
     *
     * @param args Command Line arguments
     */
    public static void main(String args[]) {

        for (int i = 0; i < THREADS; i++) {
            AppTest appTest = new AppTest(String.valueOf(i));
            // appTest. = i;

            appTest.start();
        }

        try {
            Thread.sleep(5000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    synchronized int getSum(){
        all+=1;
        return all;
    }

    public void run() {
        Session session = null;
        int counter=0;
        try {
            Properties config = new Properties();

            FileInputStream in = new FileInputStream("connector.conf");
            config.load(in);
            in.close();

            ConnectionFactory connectionFactory = ConnectionFactory.getConnectionFactory(null, config);

            Connection connection = connectionFactory.openConnection("smscon", "password");

            session = connection.openSession(Session.Mode.SEND_RECEIVE, "inbox");

            while (true) {

                System.out.println(name + "  : Waiting...");
                Message message = session.receive();

                ArrayList fromList = new ArrayList();
                message.getFromList(fromList);

                SMSAddress fromAddress =
                        SMSAddress.fromAddress((Address)fromList.get(0));

                ArrayList toList = new ArrayList();
                message.getToList(toList);

                SMSAddress toAddress =
                        SMSAddress.fromAddress((Address)toList.get(0));

                String msgText = message.getString(Prop.TEXT_CONTENT);

                // Print out message
                System.out.println(name + "  >>> Message Received");
                System.out.println(name + "  >>> From : " + fromAddress);
                System.out.println(name + "  >>> To : " + toAddress);
                System.out.println(name + "  >>> Message : " + msgText);
                System.out.println();

                session.commit();
                counter+=1;
                System.out.println("Messges for the thread "+Thread.currentThread().getName()+" :"+counter);
                System.out.println("Total messages :"+getSum());
                Date d= new Date();
                System.out.println(d);
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

}

