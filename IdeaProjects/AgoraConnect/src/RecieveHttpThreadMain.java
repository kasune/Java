import java.net.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 22, 2008
 * Time: 6:41:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecieveHttpThreadMain {

    String urlStr = "http://192.168.0.60:8080/mdp-http-responder/httpresponder?account=smscon&password=password&timeout={60}";


    public void getRes() {
        try {
            URL mdp = new URL(urlStr);
            BufferedReader in = new BufferedReader(new InputStreamReader(mdp.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                in.close();
            }
            System.out.println("=================================================================================");


        } catch (java.net.MalformedURLException me) {
            System.out.println(me.toString());
        } catch (java.io.IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String args []) {
        ReceiveHttpThread rht=new ReceiveHttpThread("kasun1");
        rht.start();
        ReceiveHttpThread rht2=new ReceiveHttpThread("kasun2");
        rht.start();
        ReceiveHttpThread rht3=new ReceiveHttpThread("kasun3");
        rht.start();

    }
}
