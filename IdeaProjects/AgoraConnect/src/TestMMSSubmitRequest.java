

//Import the standard java packages
import java.io.*;
import java.util.*;

// Import mzone agora packages
import network365.agora.api.connector.*;
//import network365.agora.api.extensions.mm7.MM7Prop;
//import network365.agora.api.extensions.mm7.MMSAddress;
//import network365.agora.api.extensions.mm7.MMSProp;
import network365.agora.api.extensions.sms.*;
import network365.agora.api.*;

/**
 * This sample class demonstrates how to send single SMS message 
 * using Agora Application Connector API.
 */
//public class TestMMSSubmitRequest {
    
    /** 
     * The main method for the sample.
     * Usage: SendOnce &lt;from address&gt; &lt;to address&gt;
     	&lt;message&gt;
     *
     * @param args The command line arguments. The first argument is the
     * recipient address of the message. The third argument is the text
     * content of the message.
     */
    /*public static void main(String args[]) {
    	
    	if(args.length<2){
    		System.out.println("Usage :" +
    				"\n1 : Recipient address" +
    				"\n2 : Text content");
    		System.exit(-1);
    	}
       
        
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
                    ConnectionFactory.getConnectionFactory(null,config);
            
            // Open a connection from the connection factory
            // using username "mm7con" and password "password".
            Connection connection =connectionFactory.openConnection("mm7con",
            		"password");
            
            // Open a session from the connection. We are not receiving,so
            // we do not need to specify a receive queue.
            Session session = connection.openSession(Session.Mode.SEND,
            		null);
            
            // Create an MM7 Message to send using the connectionFactory
            Message message = MessageFactory.createMessage();
            
            // Create the list of recipient addresses
            ArrayList toAddressList = new ArrayList();
            
            MMSAddress toAddress = new MMSAddress(Prop.RecipientType.TO,MMSAddress.TORA.EMAIL,
     			args[0]);
            
            
             
            // Add the recipient MMS address to the list of recipient addresses
            toAddressList.add(toAddress.toAddress());
            
            // Set the recipient address for the message
            message.setToList(toAddressList);

            // Set the content type of the message
            // The content type for a message is a single valued property
            // Usual MMS message has multipart content type and you can use
            // the constant defined in the Prop.ContentType interface

            message.setString(Prop.CONTENT_TYPE,Prop.ContentType.MULTIPART);
            
           //set MM7 submit request transaction id,vasp-id,vas id,service code,linked id
           //message class,reply charging size,subject etc.
            message.setString(MM7Prop.TRANSACTION_ID,"vas0001-sub");
        	message.setString(MM7Prop.VASP_ID,"TNN");
        	message.setString(MM7Prop.VAS_ID,"News");
        	message.setInt(MM7Prop.MESSAGE_TYPE,MM7Prop.MesssageType.MM7_SUBMIT_REQ);
        	message.setString(MM7Prop.SERVICE_CODE,"gold-sp33-im42");
        	message.setString(MM7Prop.LINKED_ID,"mms00016666");
        	message.setInt(MM7Prop.MESSAGE_CLASS,MM7Prop.MessageClass.INFORMATIONAL);
        	message.setInt(MM7Prop.REPLY_CHARGING_SIZE,1);
        	message.setString(Prop.SUBJECT,"MDP Test MM7-News for ");
        	message.setBoolean(MM7Prop.READ_REPLY,true);
        	message.setBoolean(MM7Prop.DISTRIBUTION_INDICATOR,true);
        	message.setInt(MM7Prop.CHARGED_PARTY,MM7Prop.ChargedParty.SENDER);
        	message.setInt(Prop.PRIORITY, Prop.Priority.LOW);
        	message.setDate(Prop.DELIVERY_DATE,Calendar.getInstance().getTime());
            
             
            String charSet="utf-8";
            
            
            //set the text content
            Message msg1=MessageFactory.createMessage();
            msg1.setString(Prop.TEXT_CONTENT,args[1]);
            msg1.setString(Prop.CHARACTER_SET,charSet);
            
            //set the content with of type image/gif
            Message msg2=MessageFactory.createMessage();
            File imageFile=new File("content/relax.gif");
            byte[] bytes=createByteArrayFromFile(imageFile);
            msg2.setBytes(Prop.BINARY_CONTENT,bytes);
			msg2.setString(MMSProp.FILE_NAME,"realx.gif");
            
            Message prop[]={msg1,msg2};
            
            //set the content as a array of properties
            message.setPropertySetArray(Prop.MULTIPART_CONTENT,prop);
            
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
    
    private static byte[] createByteArrayFromFile(File file) throws IOException{
		InputStream is=new FileInputStream(file);
		
		long fileLength=file.length();
		
		byte[] bytes=new byte[(int)fileLength];
		
		int offset=0;
		int numRead=0;
		
		
			while(offset<bytes.length && (numRead=is.read(bytes,offset,bytes.length-offset))>=0){
				offset+=numRead;
			}	
			
			if(offset<bytes.length){
				throw new IOException("File read can not be completed "+file.getName());
			}
		is.close();
			
		return bytes;
	
	}*/
    
//}

