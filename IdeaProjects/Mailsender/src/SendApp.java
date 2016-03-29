
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SendApp {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_AUTH_USER = "kasune";
    private static final String SMTP_AUTH_PWD  = "K@sun4396";
    private static final String SMTP_PORT  = "587";

    public static void main(String[] args) {
        SendApp s1= new SendApp();
        s1.run();
    }

    void run(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        try{
        // Transport transport = mailSession.getTransport();
        Transport transport = mailSession.getTransport("smtp");

        MimeMessage message = new MimeMessage(mailSession);
        message.setContent("Hi Kasun", "text/plain");
        message.setSender(new InternetAddress("admin@dawasa.com"));
        message.setSubject("Hello");
        message.setSentDate(new Date());
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("kasune@gmail.com"));
        System.out.println("connecting........................");
        transport.connect(SMTP_HOST_NAME,SMTP_AUTH_USER,SMTP_AUTH_PWD);
        //message.saveChanges();

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
                    }
                });

            //message = new MimeMessage(session);
            //message.setFrom(new InternetAddress("kasune@gmail.com"));
            //message.setRecipients(Message.RecipientType.TO,
            //        InternetAddress.parse("emkasun@m1.com.sg"));
            //message.setSubject("Testing Subject");
            //message.setText("PFA");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = "D:\\tmp\\HugobossElement.jpg";
            String fileName = "attachmentName";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            message.saveChanges();
            System.out.println("Sending...............");

            Transport.send(message);

            System.out.println("Done");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

private class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PWD;
        return new PasswordAuthentication(username, password);
    }
} }

