/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 24, 2008
 * Time: 1:15:20 AM
 * To change this template use File | Settings | File Templates.
 */
import network365.agora.api.connector.ConnectionFactory;
import network365.agora.api.connector.Connection;
import network365.agora.api.connector.Session;
import network365.agora.api.*;
import network365.agora.api.extensions.sms.SMSAddress;

import java.util.Vector;

class ThreadMain extends Thread {

    public static String acc[];
    public static String pwd[] ;
    public static String fromNo;
    private static String toNo;
    private static String msg;
    private static int noMsgs;
    public static String accountName;
    public static String pwdName;
    public static SendManyToMany s1;
    public static int sentAll=0;



    ThreadMain(String name) {
        super(name);
        s1 = new SendManyToMany();
        s1.confLoad();
        s1.propertyLoad();
        noMsgs= s1.getMsgNo();
        fromNo = s1.getFromNo();
        toNo = s1.getToNo();
        msg = s1.getMsg();

        /*System.out.println(noMsgs);
        System.out.println(fromNo);
        System.out.println(toNo);
        System.out.println(msg);
        ystem.out.println(s1.getAccounts().length);*/

        acc = new String[s1.getAccounts().length];
        pwd = new String[s1.getPwds().length];

        //System.out.println(s1.getAccounts().length);
    }

    public  void run() {

        connect();
    }

    public void connect() {

        for (int count=1;count<=noMsgs;count++){
            s1.connect(accountName,pwdName,fromNo,toNo,msg);
            System.out.println(this.getName() +" --- "+count);
            System.out.println(accountName +" "+ pwdName +" "+ fromNo +" "+ toNo +" "+ msg);

            sentAll+=1;
        }
    }
}

public class SMTMThread {

    public static void main(String args[]) {

        int count = 0;

        try{


        for (; count < new ThreadMain("test").acc.length; count++) {

            ThreadMain tm = new ThreadMain(SendManyToMany.getAccounts()[count]);


            tm.accountName = SendManyToMany.getAccounts()[count];
            tm.pwdName = SendManyToMany.getPwds()[count];

            System.out.println("Thread name "+tm.accountName+" starting....."+tm.pwdName);
            tm.start();
            System.out.println("Thread name "+tm.accountName+" started.");
            //sentAll+=1;
        }
        }catch(ExceptionInInitializerError e){
            System.out.println(e.toString());
            e.printStackTrace();
        }

        //System.out.println(sentAll);

    }


}
