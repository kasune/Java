//package hello;
import org.hibernate.Transaction;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

import java.util.List;
import java.util.Iterator;
import util.HibernateUtil;


/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 19, 2008
 * Time: 3:25:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class testrun {
    public static void main(String args[]) {
       testrun t = new testrun() ;
        t.fromtute();

    }

    public void find(){

        SessionFactory sessionFactory;


        try {
            // Create the SessionFactory from hibernate.cfg.xml
            System.out.println("Session factory goign to be created");
            sessionFactory = new Configuration().configure().buildSessionFactory();
            //Session newSession = getSessionFactory().openSession();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }


        Session newSession = sessionFactory.openSession();
        Transaction newTransaction = newSession.beginTransaction();
        System.out.println("transaction begins here");
        List messages =
                newSession.find("from Message");
        System.out.println(messages.size() + " message(s) found:");
        for (Iterator iter = messages.iterator(); iter.hasNext();) {
            Message message = (Message) iter.next();
            System.out.println(message.getText());
        }
        newTransaction.commit();
        newSession.close();
    }

    public void fromtute(){

        //Message message = new Message("Hello World");
        //System.out.println( message.getText() );
        //Session session = getSessionFactory().openSession();
        try{
            System.out.println("sessionfactory created");
        //SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            

            System.out.println("going to create session here");
        //Session session = sessionFactory.openSession();
            System.out.println("transaction begins here");
        Transaction tx = session.beginTransaction();
        Message message = new Message("Hello World");
        session.save(message);
            System.out.println("message save here");
        tx.commit();
            System.out.println("message commit here");
        session.close();
            System.out.println("session close here");
        }catch(Exception s){
          System.out.println(s.toString());
            s.printStackTrace();
    }
    }

   /* private SessionFactory getSessionFactory() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }*/
}
