import org.apache.log4j.*;
import org.apache.log4j.BasicConfigurator;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SimpleLogging {
    static Logger logger = Logger.getLogger(SimpleLogging.class);
    Properties prop;

    private Properties getLog4jProperties(){

        try{
        prop = new Properties();
        FileInputStream fil = new FileInputStream("D:\\beyondm-back\\IdeaProjects\\logs\\conf\\log4j.properties");
        prop.load(fil);
        fil.close();
        //System.out.println(prop.getProperty("LOG.LEVEL"));
        logger.debug("properties loaded.");
       }catch(FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
            logger.debug(fnfe.toString());
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            logger.debug(ioe.toString());
        }
        return prop;
    }

  public static void main(String[] args) {
      logger.setLevel(Level.DEBUG);
      BasicConfigurator.configure();
      logger.debug("Going to load log4j configurations.");
      logger.debug("property file loaded.");
      logger.debug("getting log Level - ");

      logger.info("");
    }
}