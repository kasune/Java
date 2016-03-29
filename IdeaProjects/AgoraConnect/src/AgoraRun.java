/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Dec 6, 2007
 * Time: 5:42:05 PM
 * To change this template use File | Settings | File Templates.
 */



 class MyThread  extends Thread {
    public MyThread (String name){
           super(name);

    }

    public void run(){
       try{
            AgoraConnector a1 =new AgoraConnector();
            int MaxNoOfMsg=10; // no of messages send by one thread

              for(int count=0;count<MaxNoOfMsg;count++){
                    a1.connect();
                    System.out.println("tst... "+getName()); //for test
                    sleep((long)1000);    // no is in mili second so 1000=1sec , just reduce u need lot of messages in quick time

              }
       }catch(InterruptedException iex){
               System.out.println(iex.toString());
       }

    }
}

 /* main class create as many threads */
public class AgoraRun {
    public static void main(String args[]){
        new MyThread("k1").start(); //thread no 1
        new MyThread("k2").start();  // thread no 2
        new MyThread("k3").start();

    }

}