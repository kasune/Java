/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Oct 17, 2010
 * Time: 3:47:49 PM
 * To change this template use File | Settings | File Templates.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class M1 {
    public static void main(String[] args) {
        Session session = null;

        try {
            // This step will read hibernate.cfg.xml and prepare hibernate for use
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            //Create new instance of Contact and set values in it by reading them from form object
            System.out.println("Inserting Record");
            Contact contact = new Contact();
            contact.setId(2);
            contact.setFirstName("kasun");
            contact.setLastName("ekanayake");
            contact.setEmail("kasune@gmail.com");
            session.save(contact);
            tx.commit();
            System.out.println("Done");



        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            
            System.out.println("goint to flush");
            //session.flush();
            //session.close();
            // Actual contact insertion will happen at this step

        }

    }
}

