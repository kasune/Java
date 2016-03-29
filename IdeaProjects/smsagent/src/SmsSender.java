import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 26, 2010
 * Time: 11:46:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmsSender {

     private static Logger DebugLogger = Logger.getLogger(SmsSender.class);
    private static Logger TransLogger = Logger.getLogger("Trans");

    public static void main(String[] args) throws Exception {

        /*URL SenderUrl = new URL("http://www.yahoo.com/");
      BufferedReader in = new BufferedReader(new InputStreamReader(SenderUrl.openStream()));

      String inputLine;

      while ((inputLine = in.readLine()) != null)
          System.out.println(inputLine);

      in.close(); */
        SmsSender s = new SmsSender();
        s.sendUrl("hello", 93266858);
    }

    public int sendUrl(String text, int to) {
        int status = 0;
        try {

            String Sender_url = LogLoader.sender_url.concat("username=").concat(LogLoader.sender_username).concat("&password=")
                    .concat(LogLoader.sender_password).concat("&text=")
                    .concat(text).concat("&from=").concat(LogLoader.sender_from)
                    .concat("&dlr-mask=").concat(LogLoader.sender_dlrmask)
                    .concat("&dlr-url=").concat(LogLoader.sender_dlrurl).concat("&to=").concat(Integer.toString(to));
            DebugLogger.debug(Sender_url);
            URL SenderUrlObj = new URL(Sender_url);
            BufferedReader in = new BufferedReader(new InputStreamReader(SenderUrlObj.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                DebugLogger.debug(inputLine);
            
            Sender_url =null;
            in.close();
            status = 1;

        } catch (MalformedURLException mue) {
              DebugLogger.debug("cannot connect to kannel");
              status = 0;
        } catch (IOException ioe) {
              DebugLogger.debug("cannot connect to kannel");
              status=0;
        }
        return status;
    }
}

