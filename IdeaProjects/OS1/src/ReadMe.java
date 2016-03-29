import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Feb 18, 2012
 * Time: 8:18:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadMe {
    public static void main(String[] args) {
        while(true){
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            input = in.readLine();
        }
        catch (IOException e) {
        }
        System.out.println(input);
     }
    }
}


