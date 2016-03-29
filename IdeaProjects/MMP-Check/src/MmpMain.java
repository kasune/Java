/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 3, 2008
 * Time: 2:42:37 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.util.*;

import network365.agora.api.connector.*;
import network365.agora.api.*;

public class MmpMain {
    //static Session s;
    //static Connection c;
    // static MmpCon m1;
    MmpCon m1 = new MmpCon();
    Session s;
    Connection c;

    public static void main(String args[]) {
        MmpProperties mp= new MmpProperties();
        mp.confLoad();
        String account=mp.getAccount();
        String pwd=mp.getPassword();
        String queue=mp.getQueue();
        MmpMain m = new MmpMain();
        m.getMmp(account,pwd,queue);


    }

    private void getMmp(String account,String pwd,String queue) {
        try {
            System.out.println("Starting getMmp");

            c = m1.getCon(account, pwd);
            System.out.println("Connection returned.");
            s = m1.getSession(c, queue);
            System.out.println("Session returned.");
            System.out.println("Going to receive messages.");


            while (true) {
                String in[] = new String[3];
                in = m1.getMessage(s);
                System.out.println(in[0]);
                System.out.println(in[1]);
                System.out.println(in[2]);

            }

        } catch (AgoraException ae) {
            System.out.println(ae.toString());
            ae.printStackTrace();
            m1.closeConnection(c);
            m1.closeSession(s);
            getMmp(account,pwd,queue);
        }
        catch (IOException io) {
            System.out.println(io.toString());
            io.toString();
            m1.closeConnection(c);
            m1.closeSession(s);
            getMmp(account,pwd,queue);

        } catch (Exception e) {
            System.out.println(e.toString());
            e.toString();
            m1.closeConnection(c);
            m1.closeSession(s);
            getMmp(account,pwd,queue);
        }
    }
}
