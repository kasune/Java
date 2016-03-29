import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jPropertyFile {
    private static Logger logger = Logger.getLogger(Log4jPropertyFile.class);
    public static void main (String args[]){
        PropertyConfigurator.configure("D:\\beyondm-back\\IdeaProjects\\logs\\conf\\log4j.properties");
        logger.debug("test debug");
        logger.info("Test Log");
        RemoteCaller rc= new RemoteCaller();
        rc.setAmount();
    }
}