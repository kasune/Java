import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 11/26/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Configurator {
    static String FTPServer;
    static int FTPPort;
    static String FTPUser;
    static String FTPPasswd;
    static int RetryCount;
    static int RetrySleep;
    static String RDir;
    static String LocalDir;
    static boolean FileNameMatch;
    static String CurrentDay;
    static int NoOfDaysBack;
    static boolean RemoteFileDelete;
    static boolean LocalFileDelete;
    static boolean LocalFileRename;
    static String PreFix;
    static boolean OverWriteFile;
    static Logger logger;
    static int NoOfThreads;
    static long ThreadSleep;

    public Configurator() {
        logger = LogManager.getLogger(Configurator.class.getName());
        logger.debug("configurations initialized ");
    }

    public void setPara() {


        Properties prop = new Properties();

        try {
            //load a properties file
            prop.load(new FileInputStream("conf" + File.separator + "main"));

            //get the property value and print it out
            FTPServer = prop.getProperty("FTPServer.host");
            FTPPort = Integer.parseInt(prop.getProperty("FTPServer.port"));
            FTPUser = prop.getProperty("FTPUser");
            FTPPasswd = prop.getProperty("FTPPassword");
            RetryCount = Integer.parseInt(prop.getProperty("RetryCount"));
            RetrySleep = Integer.parseInt(prop.getProperty("RetrySleep"));
            RDir = prop.getProperty("RemoteDirectory");
            LocalDir = prop.getProperty("LocalDirectory");
            FileNameMatch = Boolean.parseBoolean(prop.getProperty("FileNameMatch"));
            NoOfDaysBack = Integer.parseInt(prop.getProperty("DaysBack"));
            RemoteFileDelete = Boolean.parseBoolean(prop.getProperty("RemoteFileDelete"));
            LocalFileDelete = Boolean.parseBoolean(prop.getProperty("LocalFolderEmpty"));
            LocalFileRename = Boolean.parseBoolean(prop.getProperty("LocalFileRename"));
            PreFix = prop.getProperty("LocalFilePreFix");
            OverWriteFile = Boolean.parseBoolean(prop.getProperty("OverwriteFileEnable"));
            NoOfThreads = Integer.parseInt(prop.getProperty("MaxThreadCount"));
            ThreadSleep = Long.parseLong(prop.getProperty("ThreadSleep"));

            dateType();

        } catch (IOException ex) {
            logger.debug(ex.getMessage());
            logger.error(ex.toString());
        }

    }
    public int getNoOfThreads(){
        return NoOfThreads;
    }
    public long getThreadSleep(){
        return ThreadSleep;
    }
    public String getFTPServer(){
    return FTPServer;
}
    public int getFTPPort(){
        return FTPPort;
    }
    public String getFTPUser(){
        return FTPUser;
    }
    public String getFTPPwd(){
        return FTPPasswd;
    }
    public int getRetryCount(){
        return RetryCount;
    }
    public int getRetrySleep(){
        return RetrySleep;
    }
    public String getRemoteDir(){
        return RDir;
    }
    public String getLocalDir(){
        return LocalDir;
    }
    public boolean getFileMatch(){
        return FileNameMatch;
    }
    public String getDayPattern(){
        return CurrentDay;
    }
    public int getNoOfDaysBack(){
        return NoOfDaysBack;
    }
    public boolean getRemoteFileDelete(){
        return RemoteFileDelete;
    }
    public boolean getLocalFileDelete(){
        return LocalFileDelete;
    }
    public boolean getLocalFileRename(){
        return LocalFileRename;
    }
    public String getPrefix(){
        return PreFix;
    }
    public boolean getOverWriteFile(){
        return OverWriteFile;
    }


    private void dateType() {
        if (FileNameMatch) {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -NoOfDaysBack);
            CurrentDay = dateFormat.format(cal.getTime());
            logger.debug("matching date type -" + CurrentDay);

        } else {
            CurrentDay = "";
            logger.debug("full file list will be downloaded");
        }
    }

}
