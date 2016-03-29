import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 3, 2008
 * Time: 3:00:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScriptExecutor {

    public String excuteScript(String cmd) throws IOException, InterruptedException {
        String result = null;
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while ((line = buf.readLine()) != null) {
            result = (line);
        }
        return result;

    }
}
