

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;





public class SampleDeliverRequest {
	private MimeMessage msg;
	private MimeMultipart multipart;
	
	public SampleDeliverRequest(){
		multipart=new MimeMultipart("related");
		msg=new MimeMessage(Session.getDefaultInstance(new Properties()));		
	}
	
	public void setUpContent() throws MessagingException, IOException {
		
			File soapFile=new File("content/DeliveryRequest.xml");
			FileInputStream fin=new FileInputStream(soapFile);
			byte[] data=new byte[fin.available()];
			fin.read(data);
			String text=new String(data);
			
			MimeBodyPart soapPart=new MimeBodyPart();
			soapPart.setContent(text,"text/xml");
			soapPart.setHeader("Content-ID","<SOAP-Part>");
			
			File imgFile=new File("content/relax.gif");
			FileDataSource imgDS=new FileDataSource(imgFile);
			DataHandler imgDH=new DataHandler(imgDS);
			
			MimeMultipart imgContent=new MimeMultipart("related");
			ContentType ct=new ContentType(imgContent.getContentType());
			ct.setPrimaryType("application");
			ct.setSubType("vnd.oma.drm.message");
			
			MimeBodyPart drm=new MimeBodyPart();
			drm.setText("Drm content");
			drm.addHeader( "Content-Type", "application/vnd.oma.drm.rights+xml" );
			
			MimeBodyPart imgPart=new MimeBodyPart();
			imgPart.setDataHandler(imgDH);
			imgPart.setHeader("Content-ID","<image>");
			imgPart.setHeader("Content-type",imgDS.getContentType());
			imgPart.setFileName("relax.gif");
			
			
			imgContent.addBodyPart(drm);
			imgContent.addBodyPart(imgPart);
			
			MimeBodyPart drmCont=new MimeBodyPart();
			drmCont.setContent(imgContent);
			drmCont.setHeader("Content-ID","<drm>");
			drmCont.setHeader("Content-type",ct.toString());
			
			MimeBodyPart textPart=new MimeBodyPart();
			textPart.setText("Test Part");
			textPart.setHeader("Content-ID","<text>");
			
			
			
			MimeMultipart contents=new MimeMultipart("related");			
			contents.addBodyPart(drmCont);
			contents.addBodyPart(textPart);
			
			MimeBodyPart content=new MimeBodyPart();
			content.setContent(contents);
			content.setHeader("Content-ID","generic_content_id");
						
			multipart.addBodyPart(soapPart);
			multipart.addBodyPart(content);
			
			msg.setContent(multipart);
			msg.saveChanges();		
	}
	
	public void writeTo(OutputStream os) throws Exception{
		String[] contentType = msg.getHeader( "content-type" );
        Enumeration headers = msg.getAllHeaders();
        
        while( headers.hasMoreElements() ) {
            Header header = ( Header ) headers.nextElement();
            msg.removeHeader( header.getName() );
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream( 4096 );
        msg.writeTo( baos );
        
			os.write( ("POST " + "localhost:8080" + " HTTP/1.0\r\n" ).getBytes() );
            os.write( ("SOAPAction: \"\"\r\n" ).getBytes() );
            os.write( ("Content-Length: " + (baos.size()-2) + "\r\n" ).getBytes() );
            os.write( ("Content-Type: " + contentType[0] + "\r\n" ).getBytes() );
            String val = "Basic " + com.openwave.mms.mm7.util.Base64.encodeString( "newscorp" + ":" + "news123" );
            os.write( ("Authorization: " + val + "\r\n" ).getBytes() );
            baos.writeTo(os);
            os.flush();
            os.close();
	}
	
	public static void main(String arg[]){
		
		try {
			SampleDeliverRequest sampleRequest=new SampleDeliverRequest();
			sampleRequest.setUpContent();
			Socket socket=new Socket("127.0.0.1",8000);
			sampleRequest.writeTo(socket.getOutputStream());
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
