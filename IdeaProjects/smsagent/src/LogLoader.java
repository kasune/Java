/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 24, 2010
 * Time: 11:03:28 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;
import java.util.Date;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;

public class LogLoader {
    // Debuglogger for trace log
    //Translogger for trans log
    private static Logger DebugLogger = Logger.getLogger(LogLoader.class);
    private static Logger TransLogger = Logger.getLogger("Trans");

    public static String database_host;
    public static String database_port;
    public static String database_schema;
    public static String database_user;
    public static String database_password;

    public static String cron_timer;

    public static String sender_url;
    public static String sender_username;
    public static String sender_password;
    public static String sender_text;
    public static String sender_from;
    public static String sender_dlrmask;
    public static String sender_dlrurl;
    public static String sender_to;

    private static Connection dbconnection;

    public static void main (String args[]){
        loadConfiguration();
        DbConnector d = new DbConnector();
        dbconnection = d.getconWrite();
        /*ResultSet r = d.getData(dbconnection,"select * from transaction");
        try{
        while (r.next()) {
                String CityName = r.getString("amount");
                System.out.println(CityName);

            }
        }catch(Exception e){    }
        */
        try{
        CronScheduler example = new CronScheduler();
        example.run();

        //SmsSender s=new SmsSender();
        //s.sendUrl("hello",93266858);
        }catch(Exception e){
           DebugLogger.debug(e.toString());
        }
    }

    public static Connection getDBConnection(){
        return dbconnection;
    }

    private static void loadConfiguration(){
        try{

        PropertyConfigurator.configure("D:\\beyondm\\study\\java\\IdeaProjects\\smsagent\\conf\\log4j.properties");
        Properties MainProperties = new Properties();

        DebugLogger.debug("going to load main.conf properties");
        FileInputStream FIMainConf = new FileInputStream("D:\\beyondm\\study\\java\\IdeaProjects\\smsagent\\conf\\main.conf");
        MainProperties.load(FIMainConf);
        FIMainConf.close();

        database_host = MainProperties.getProperty("database.host");
        database_port = MainProperties.getProperty("database.port");
        database_schema = MainProperties.getProperty("database.schema");
        database_user = MainProperties.getProperty("database.name");
        database_password  = MainProperties.getProperty("database.password");
        DebugLogger.debug("database configurations loaded.");

        cron_timer = MainProperties.getProperty("cronscheduler.job1");
        DebugLogger.debug("cron scheduler configurations loaded.");

        sender_url = MainProperties.getProperty("sender.url");
        sender_username = MainProperties.getProperty("sender.username");
        sender_password = MainProperties.getProperty("sender.password");
        //sender_text = MainProperties.getProperty("sender.text");
        sender_from = MainProperties.getProperty("sender.from");
        sender_dlrmask = MainProperties.getProperty("sender.dlrmask");
        sender_dlrurl = MainProperties.getProperty("sender.dlrurl");
        //sender_to = MainProperties.getProperty("sender.to");
        DebugLogger.debug("sms sender configurations loaded.");

        }catch(IOException io){
            System.out.println(io.toString());
        }
    }
}

