import network365.agora.api.connector.Connection;

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
    public static String inbox[] ;

    public static String fromNo;
    private static String toNo;
    private static String msg;
    private static int noMsgs;

    public String accountName;
    public String pwdName;
    public String inboxName;

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
        int count=1;
        Connection conUse = s1.getCon(accountName,pwdName);
        //for (;count<=100000;){

        try{
        while(true){
            //s1.getMessage(accountName,pwdName,fromNo,toNo,msg);

            int res=s1.getMessage(fromNo,toNo,msg,conUse,inboxName);
            sleep((long)1000);
            if (res==0){
              connect();

            }else{

            System.out.println(this.getName() +" --- "+count);
            //System.out.println(accountName +" "+ pwdName +" "+ fromNo +" "+ toNo +" "+ msg);
            count+=1;
            sentAll+=1;
            }
        }
        //s1.closeCon();  //use this when the loop is not infinite
        }catch(InterruptedException ie){
            ie.printStackTrace();
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
            tm.inboxName = SendManyToMany.getInboxes()[count];

            System.out.println("Receive thread name "+tm.accountName+" starting....."+tm.pwdName+" - "+tm.inboxName);
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
