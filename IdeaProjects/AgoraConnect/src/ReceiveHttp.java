/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 22, 2008
 * Time: 5:03:50 PM
 * To change this template use File | Settings | File Templates.
 */
import java.net.*;
import java.io.*;
public class ReceiveHttp {



    public static void main(String[] args) throws Exception {
	URL mdp = new URL("http://172.18.18.2:8080/mdp-http-responder/httpresponder?account=DBAcc&password=password&queue=DBAccQ&timeout={60}");
	int count=0;
        for(count=0;count<Integer.parseInt(args[0]);count++){
        BufferedReader in = new BufferedReader(
				new InputStreamReader(
				mdp.openStream()));

	String inputLine;

	while ((inputLine = in.readLine()) != null)
	    System.out.println(inputLine);

	    in.close();


        System.out.println(count);

        }
        //ReceiveHttp r= new ReceiveHttp() ;
        //r.sendPostRequest();
}


    public  void sendPostRequest() {

                //Build parameter string
                String data = "Account=smscon&Password=password&Queue=inbox";
                try {

                    // Send the request
                    URL url = new URL("http://192.168.0.60:8080/mdp-http-responder/httpresponder");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                    //write parameters
                    writer.write(data);
                    writer.flush();

                    // Get the response
                    StringBuffer answer = new StringBuffer();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        answer.append(line);
                    }
                    writer.close();
                    reader.close();

                    //Output the response
                    System.out.println(answer.toString());

                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }



}
