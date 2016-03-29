import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 22, 2008
 * Time: 5:19:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiveHttpThread extends Thread {

    String name;
    RecieveHttpThreadMain r=new RecieveHttpThreadMain() ;

    public ReceiveHttpThread (String name)
    {
       this.name=name;

    }



    public void run(){

    int noOfReq=10;

        try{
        
        for(int count=0;count<noOfReq;count++){
               r.getRes();
               sleep(1000);
        }
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
