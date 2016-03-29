import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 11/26/13
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class Starter extends Thread {
    static Logger logger;
    static FTPFile list[];
    static int count = 0;
    static int fileCount;
    static String FTPServer;
    static int FTPPort;
    static String FTPUser;
    static String FTPPasswd;
    static int RetryCount;
    static int RetrySleep;
    static String RDir;
    static String LDir;
    static long ThreadSleepTime;
    static boolean FileNameMatch;
    static String CurrentDay;
    static int NoOfDaysBack;
    static boolean RemoteFileDelete;
    static boolean LocalFileDelete;
    static boolean LocalFileRename;
    static String PreFix;
    static boolean OverWriteFile;
    static int NoOfThreads;

    public static void main(String args[]) {

        System.setProperty("log4j.configurationFile", "conf" + File.separator + "log4j2.xml");
        logger = LogManager.getLogger(Starter.class.getName());

        logger.debug("------------- starting ftp connector -------------");
        Configurator confs = new Configurator();
        confs.setPara();
        Starter st = new Starter();
        st.getParameters();

        try {
            File LocalDirectory = new File(LDir);
            FTPCon objGetFiles = new FTPCon();
            objGetFiles.emptyLocalDir(LocalDirectory, LocalFileDelete);
            FTPClient ftp = st.ftpConnection(0, objGetFiles);
            FTPCon objCon = new FTPCon();
            list = objCon.getRemoteFileList(ftp);
            fileCount = list.length;
            objCon.ftpDisconnect(ftp);

        } catch (IOException ioe) {
            logger.debug(ioe.toString());
        } catch (NullPointerException npe) {
            logger.debug(npe.toString());
            logger.debug("exiting main thread as the connection object is null");
            System.exit(0);
        }

        logger.debug("starting ftp connector downloading.....");

        for (int in = 0; in < NoOfThreads; in++) {

            Starter s1 = new Starter();
            logger.debug("starting download thread - " + s1.getName());
            s1.start();

        }
        logger.debug("------------- ftp connector stopped -------------");
    }

    private static synchronized FTPFile fileRemoveFromList() {
        FTPFile getFile = null;
        if (count < fileCount) {
            getFile = list[count];
            logger.debug("file is going to download " + getFile.getName());
            list[count] = null;
            count++;
        } else {
            logger.debug("no files pending");
        }
        return getFile;
    }

    private void getParameters() {
        Configurator conf = new Configurator();
        FTPServer = conf.getFTPServer();
        FTPPort = conf.getFTPPort();
        FTPUser = conf.getFTPUser();
        FTPPasswd = conf.getFTPPwd();
        RetryCount = conf.getRetryCount();
        RetrySleep = conf.getRetrySleep();
        RDir = conf.getRemoteDir();
        FileNameMatch = conf.getFileMatch();
        CurrentDay = conf.getDayPattern();
        NoOfDaysBack = conf.getNoOfDaysBack();
        RemoteFileDelete = conf.getRemoteFileDelete();
        LDir = conf.getLocalDir();
        LocalFileDelete = conf.getLocalFileDelete();
        LocalFileRename = conf.getLocalFileRename();
        PreFix = conf.getPrefix();
        OverWriteFile = conf.getOverWriteFile();
        ThreadSleepTime = conf.getThreadSleep();
        NoOfThreads = conf.getNoOfThreads();
    }

    private void ftpDisconnect(FTPClient ftp, FTPCon ftpCon) throws IOException {
        ftpCon.ftpDisconnect(ftp);
        logger.debug("ftp disconnected");
    }

    private FTPClient ftpConnection(int in, FTPCon objGetFiles) {

        FTPClient ftp = null;

        if (in < RetryCount) {
            try {
                in = in + 1;
                logger.debug("connecting .........");
                ftp = objGetFiles.connectToServer(FTPServer, FTPPort, FTPUser, FTPPasswd, RDir);

            } catch (IOException ioe) {
                logger.debug(ioe.toString());
                logger.debug("sleeping for " + (RetrySleep / 1000) + " sec");
                try {
                    Thread.sleep(RetrySleep);

                } catch (InterruptedException iex) {
                    logger.debug(iex.toString());
                }
                logger.debug("retrying connection");
                ftpConnection(in, objGetFiles);
            }
        } else {
            logger.debug("maximum retry exceed.");
        }
        return ftp;

    }

    public void run() {
        try {

            FTPCon connection = new FTPCon();
            FTPClient ftpClient = ftpConnection(0, connection);
            logger.debug("ftp connection completed");
            for (FTPFile n : list) {

                FTPFile getFile = fileRemoveFromList();
                long startTime = System.currentTimeMillis();
                if (getFile != null) {
                    connection.downloadFiles(getFile, ftpClient, LocalFileRename, LDir, CurrentDay, PreFix, OverWriteFile, RemoteFileDelete);

                    long endTime = System.currentTimeMillis();
                    logger.debug("time taken to download (sec)- " + ((startTime - endTime) / 1000));
                    logger.debug("sleeping for (sec)- " + ThreadSleepTime);
                }
                Thread.sleep(ThreadSleepTime);
            }
            connection.ftpDisconnect(ftpClient);
        } catch (IOException ioe) {
            logger.debug(ioe.toString());
        } catch (InterruptedException ie) {
            logger.debug(ie.toString());
        }
    }
}
