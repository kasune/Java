/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Feb 26, 2007
 * Time: 11:26:33 AM
 * To change this template use File | Settings | File Templates.
 */
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.activation.*;



import java.util.Properties;
import java.util.Date;


public class SimpleMail {

    private static final String SMTP_HOST_NAME = "192.168.0.22";
    private static final String SMTP_AUTH_USER = "root";
    private static final String SMTP_AUTH_PWD  = "Bav#3hig";

//    private static final String SMTP_HOST_NAME = "mail.beyondm.net";
//    private static final String SMTP_AUTH_USER = "dilan@beyondm.net";
//    private static final String SMTP_AUTH_PWD  = "dilan123";



    public void test() throws Exception{

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);

       // Transport transport = mailSession.getTransport();
        Transport transport = mailSession.getTransport("smtp");

        MimeMessage message = new MimeMessage(mailSession);
        message.setContent("Hi k.. server os working well", "text/plain");
        message.setSender(new InternetAddress("tes@test.org"));
        message.setSubject("hi");
        message.setSentDate(new Date());
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("kasune@beyondm.net"));

       transport.connect(SMTP_HOST_NAME,SMTP_AUTH_USER,SMTP_AUTH_PWD);
        message.saveChanges();
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();

//        Transport tr = session.getTransport("smtp");
//         tr.connect(smtphost, username, password);
//         msg.saveChanges();      // don't forget this
//        tr.sendMessage(msg, msg.getAllRecipients());
//         tr.close()
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = SMTP_AUTH_USER;
           String password = SMTP_AUTH_PWD;
           return new PasswordAuthentication(username, password);
        }
    }

     public static void main(String[] args) throws Exception{
       SimpleMail sm=new SimpleMail();
         try{
         sm.test();
         }catch(AuthenticationFailedException af){
           // af.fillInStackTrace() ;
             af.printStackTrace();
         }catch(Exception e){
             e.printStackTrace();

         }
    }
}
