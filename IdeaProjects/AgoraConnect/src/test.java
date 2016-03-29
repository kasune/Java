/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 28, 2007
 * Time: 1:33:14 PM
 * To change this template use File | Settings | File Templates.
 */
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.ConsoleAppender;
import com.mysql.jdbc.*;
import com.mysql.jdbc.PreparedStatement;

import java.sql.*;
import java.sql.Connection;
import java.util.Hashtable;
//import com.db.util.ConfigProperties;

public class test {
      static Logger logger = Logger.getLogger(test.class);
      private Connection dbSourceConnection=null;
    private static String LOG_ROOT = "./logs";

    private static final String CATEGORY_CONSOLE = "console";
    private static final String CATEGORY_TRACELOG = "traceLog";
    private static final String CATEGORY_ERRORLOG = "errorLog";
    private static final String CATEGORY_TANSLOG = "transLog";
    private static final String CATEGORY_CDRLOG = "cdrLog";

    private static ConsoleAppender console;
    public static Logger traceLogger;
    public static Logger errorLogger;
    public static Logger transLogger;
    public static Logger cdrLogger;



    private static Hashtable loggers = new Hashtable();

    public static void main(String args []){
       try { //logger.error("huta");
        test t1 = new test();
        //t1.connectDBSource();
        createLoggers();
        //configureLoggers();
        //createAppenders();
        addAppenders(true);
        traceLogger.info("Loggers reconfigured successfully ");
    }catch(Exception e)
       {
           System.out.println(e.getStackTrace());
           System.out.println(e.toString());
       }
    }

    private static void createLoggers() {
        test.traceLogger = createLoggerForName(CATEGORY_TRACELOG);
        test.errorLogger = createLoggerForName(CATEGORY_ERRORLOG);
        test.transLogger = createLoggerForName(CATEGORY_TANSLOG);
        test.cdrLogger   = createLoggerForName(CATEGORY_CDRLOG);
    }

    private static Logger createLoggerForName(String name) {
        Logger logger = Logger.getLogger(name);
        loggers.put(logger.getName(), logger);
        return logger;
    }

    private static void addAppenders(boolean enableConsoleMsgs) {

        if (enableConsoleMsgs) {
            test.traceLogger.addAppender(console);
            test.errorLogger.addAppender(console);
            test.transLogger.addAppender(console);
            test.cdrLogger.addAppender(console);
        }

       /* test.traceLogger.addAppender(traceLog);

        test.cdrLogger.addAppender(cdrLog);

        test.errorLogger.addAppender(traceLog);
        test.errorLogger.addAppender(errorLog);

        test.transLogger.addAppender(traceLog);
        test.transLogger.addAppender(transLog);*/
    }

    public void connectDBSource() {
            logger.info( "Connecting Database Source..." );
            try {
                  Class.forName("org.gjt.mm.mysql.Driver");
                  dbSourceConnection      = DriverManager.getConnection( "jdbc:mysql://localhost/kasun","kasun","kasun");
                  //logger.info( "Connecting Database Source...done" );
                  System.out.println("connected to database");
                  java.sql.PreparedStatement stmt=null;
                  String sql="insert into info_my values(1,'kasun','1982-09-11 22:10:00',0713217117)";
                  stmt=dbSourceConnection.prepareStatement(sql);
                  int res =stmt.executeUpdate();
            }
            catch(SQLException sql) {
                  System.out.println(sql.toString());
                  sql.printStackTrace();
                  //logger.error( "SQLException in connectDBSource(): " + sqle );
            }
            catch(Exception e) {
                  System.out.println(e.toString());
                  e.printStackTrace();
                  //logger.error( "Exception in connectDBSource(): " + e );
            }
      }

}
