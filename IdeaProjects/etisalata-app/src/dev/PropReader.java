package dev;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 28, 2011
 * Time: 5:48:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropReader {
    CronScheduler cron1;
    static String url;
    static String appid;
    static String password;
    static String database_url;
    static String database_user;
    static String database_pwd ;
    static String cronTimer ;
    static String reg_resp_success;
    static String reg_resp_failed;
    static String unreg_resp_success;
    static String unreg_resp_failed;
    static String app_keyword;
    static String zodiac_signs;
    static String zodiac_register_success;
    static String zodiac_register_fail;
    
    static FileInputStream fil;

    private static Logger logger = Logger.getLogger(PropReader.class);
    Properties prop;


    public static Properties reader(){

        Properties prop = new Properties();
    try{


        // File directory = new File(".\\..\\webapps\\dawasa\\WEB-INF\\");
        //logger.info ("Current directory's canonical path: "+ directory.getPath());
        //logger.info ("Current directory's absolute  path: " + directory.getAbsolutePath());
       if (isWindows()) {
			logger.info("This is Windows");
            fil = new FileInputStream(".\\..\\webapps\\dawasa\\WEB-INF\\main.conf");

		} else{
			logger.info("This is Unix or Linux");
            fil = new FileInputStream("./../webapps/dawasa/WEB-INF/main.conf");
		}


        prop.load(fil);
        fil.close();
       }
       catch(Exception e){
            logger.info(e.toString());

       }
        return prop;
    }

    public void setAttr(){
       Properties prop = reader();
       //logger.info(prop.getProperty("database.url"));
       url=prop.getProperty("mt.url");
       appid=prop.getProperty("appid");
       password=prop.getProperty("password");

       database_url=prop.getProperty("database.url");
       database_user=prop.getProperty("database.user");
       database_pwd=prop.getProperty("database.pwd");

       cronTimer = prop.getProperty ("message.send.cron");

       reg_resp_success = prop.getProperty("register.response.success");
       reg_resp_failed = prop.getProperty("register.response.failed");
       unreg_resp_success = prop.getProperty("unregister.response.success");
       unreg_resp_failed = prop.getProperty("unregister.response.failed");
       zodiac_signs = prop.getProperty("zodiac_signs");
       zodiac_register_success = prop.getProperty("zodiac.register.success");
       zodiac_register_fail = prop.getProperty("zodiac.register.fail");

       app_keyword = prop.getProperty("app.keyword");

    }

    public void setLog4j(){
        if (isWindows()) {
			logger.info("This is Windows");
            PropertyConfigurator.configure(".\\..\\webapps\\dawasa\\WEB-INF\\log4j.properties");

		} else{
			logger.info("This is Unix or Linux");
            PropertyConfigurator.configure("./../webapps/dawasa/WEB-INF/log4j.properties");
		}

    }

    public void createScheduleJobs(){
        try{
            cron1 = new CronScheduler();
            cron1.run();
        }catch(Exception e){
            logger.info(e.toString());
        }
    }


    public static boolean isWindows() {

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}
    public static boolean isUnix() {

            String os = System.getProperty("os.name").toLowerCase();
            // linux or unix
            return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

        }


}
