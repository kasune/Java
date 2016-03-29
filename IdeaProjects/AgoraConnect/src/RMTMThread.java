/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 25, 2008
 * Time: 6:31:48 PM
 * To change this template use File | Settings | File Templates.
 */
class ReceiveThread extends Thread{

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



    ReceiveThread(String name) {
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

        //acc = new String[s1.getAccounts().length];
        //pwd = new String[s1.getPwds().length];

        //System.out.println(s1.getAccounts().length);
    }

    public  void run() {

        connect();
    }

    public void connect() {

        for (int count=1;count<=noMsgs;count++){
            s1.getMessage(accountName,pwdName,fromNo,toNo,msg);
            System.out.println(this.getName() +" --- "+count);
            System.out.println(accountName +" "+ pwdName +" "+ fromNo +" "+ toNo +" "+ msg);

            sentAll+=1;
        }
    }

}


public class RMTMThread {

    public static void main(String args[]) {

        int count = 0;

        try{


        for (; count < new ReceiveThread("test").s1.getAccounts().length; count++) {

            ReceiveThread tm = new ReceiveThread(SendManyToMany.getAccounts()[count]);


            tm.accountName = SendManyToMany.getAccounts()[count];
            tm.pwdName = SendManyToMany.getPwds()[count];

            System.out.println("Receive thread name "+tm.accountName+" starting....."+tm.pwdName);
            tm.start();
            System.out.println("Receive thread name "+tm.accountName+" started.");
            //sentAll+=1;
        }
        }catch(ExceptionInInitializerError e){
            System.out.println(e.toString());
            e.printStackTrace();
        }

        //System.out.println(sentAll);

    }

}
