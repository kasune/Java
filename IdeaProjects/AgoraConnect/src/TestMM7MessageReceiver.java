// Import the standard java packages
/*import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import network365.agora.api.Address;
import network365.agora.api.AgoraException;
import network365.agora.api.Message;
import network365.agora.api.Prop;
import network365.agora.api.PropertySet;
import network365.agora.api.connector.Connection;
import network365.agora.api.connector.ConnectionFactory;*/
//import network365.agora.api.connector.Session;
//import network365.agora.api.extensions.mm7.MM7Prop;

/**
 * This sample demonstrates how to receive an SMS message using
 * the Agora Application Connector API.
 */
//public class TestMM7MessageReceiver {
    
    /** 
     * The main method for the sample.
     * Usage: ReceiveOnce
     *
     * @param args The command line arguments.
     */
    /*public static void main(String args[]) {
        
        try {
        
            // Create a Properties object containing the config for the 
            // Connection Factory
            Properties config = new Properties();
            
            // Load the connection properties from the 'connector.conf' file
            FileInputStream in = new FileInputStream("connector.conf");
            config.load(in);
            in.close();
            
            // Initialise the connection factory using the 
            // default connection factory (passing the null as the connection
            // factory name to the factory method)
            ConnectionFactory connectionFactory = 
                    ConnectionFactory.getConnectionFactory(null, config);
            
            // Open a connection from the connection factory
            // using username "mm7con" and password "password".
            Connection connection =
                    connectionFactory.openConnection("mm7con", "password");
            
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
            
            // Get the list of recipients
//            ArrayList toList = new ArrayList();
//            message.getToList(toList);
        
            //get the transacion id and the subject
            System.out.println(message.getString(Prop.SUBJECT));
            System.out.println(message.getString(MM7Prop.TRANSACTION_ID));

            //print the recipients
            ArrayList list=new ArrayList();
			message.getToList(list);
			Address recipient1=(Address)list.get(0);
			Address recipient2=(Address)list.get(1);
			System.out.println(recipient1.getString(Prop.ADDRESS));
			System.out.println(recipient2.getString(Prop.ADDRESS));
			
			//get the content
			PropertySet[] props=message.getPropertySetArrayReadOnly(Prop.MULTIPART_CONTENT);
			for(int i=0;i<props.length;i++){
				if(props[i].containsProperty(Prop.BINARY_CONTENT)){
					byte[] b=props[i].getBytes(Prop.BINARY_CONTENT);
					//System.out.println("DRM "+props[i].getString(MM7Prop.DRM_CONTENT));
				}else if(props[i].containsProperty(Prop.TEXT_CONTENT)){
					System.out.println("Text content :"+props[i].getString(Prop.TEXT_CONTENT));
				}
			}
            
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
*/