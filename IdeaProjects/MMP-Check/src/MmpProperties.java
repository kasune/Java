import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 3, 2008
 * Time: 2:05:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MmpProperties {



        private static Properties config;
        private static FileInputStream in;
        private static String confFile="E:\\beyondm-back\\IdeaProjects\\MMP-Check\\conf\\mmp.conf";
        private static String accountPrp="account.name";
        private static String pwdPrp="account.password";
        private static String queuePrp="account.queue";
        private static String scriptPathPrp="script.paths";


        public void confLoad(){
        try {
            config = new Properties();
            in = new FileInputStream(confFile);
            config.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("error in conf load");
            System.out.println(e.toString());
          }
        }

        public String getAccount(){
            return config.getProperty(accountPrp);
        }

        public String getPassword(){
            return config.getProperty(pwdPrp);
        }

        public String getQueue(){
            return config.getProperty(queuePrp);
        }

        public String[] getScripts(){
            StringTokenizer st = new StringTokenizer(config.getProperty(queuePrp),",");
            String scripts[] = new String[100];
            int count =0;
                 while (st.hasMoreTokens()) {
                     scripts[count]=st.nextToken();
                     count+=1;
                 }

            return scripts;
        }

}


