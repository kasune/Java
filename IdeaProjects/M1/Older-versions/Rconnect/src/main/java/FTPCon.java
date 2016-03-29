import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 11/14/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTPCon {

    String FTPServer;
    int FTPPort;
    String FTPUser;
    String FTPPasswd;
    String RDir;
    String LDir;
    boolean FileNameMatch;
    String CurrentDay;
    int NoOfDaysBack;
    boolean RemoteFileDelete;
    boolean LocalFileDelete;
    boolean LocalFileRename;
    String PreFix;
    boolean OverWriteFile;
    static Logger logger;
    /*static String FTPServer = "10.82.105.16";
    static String FTPUser = "ftpuser";
    static String FTPPasswd = "1234";
    static String RDir = "kasun";
    static String LDir = "D:\\tmp\\tmp\\"; */

    public static void main(String args[]) {
        System.setProperty("log4j.configurationFile", "conf"+File.separator+"log4j2.xml");
        logger = LogManager.getLogger(FTPCon.class.getName());
        logger.debug("Starting ftp connector");
        FTPCon f1 = new FTPCon();
        File localDir = f1.setPara(args);
        logger.debug("class initialized parameters loaded");
        f1.dateType();


        try {
            f1.emptyLocalDir(localDir);
            FTPClient ftpc = f1.connectToServer();

            FTPFile list[] = f1.getRemoteFileList(ftpc);
            f1.downloadFiles(list, ftpc);
            f1.ftpDisconnect(ftpc);
            logger.debug("Stopping ftp connector");
        } catch (IOException ioe) {
            logger.debug(ioe.getMessage());
            logger.error(ioe.toString());
        }catch (Exception ex){
            logger.debug(ex.getMessage());
            logger.error(ex.toString());
        }
    }

    private File setPara(String args[]) {
        /*FTPServer = args[0];
        FTPUser = args[1];
        FTPPasswd = args[2];
        RDir = args[3];
        LDir = args[4];*/
        File LocalDir = null;
        Properties prop = new Properties();

        try {
            //load a properties file
            prop.load(new FileInputStream("conf" + File.separator + "main"));

            //get the property value and print it out
            FTPServer = prop.getProperty("FTPServer.host");
            FTPPort = Integer.parseInt(prop.getProperty("FTPServer.port"));
            FTPUser = prop.getProperty("FTPUser");
            FTPPasswd = prop.getProperty("FTPPassword");
            RDir = prop.getProperty("RemoteDirectory");
            LDir = prop.getProperty("LocalDirectory");
            LocalDir = new File(LDir);
            FileNameMatch = Boolean.parseBoolean(prop.getProperty("FileNameMatch"));
            NoOfDaysBack = Integer.parseInt(prop.getProperty("DaysBack"));
            RemoteFileDelete = Boolean.parseBoolean(prop.getProperty("RemoteFileDelete"));
            LocalFileDelete = Boolean.parseBoolean(prop.getProperty("LocalFolderEmpty"));
            LocalFileRename = Boolean.parseBoolean(prop.getProperty("LocalFileRename"));
            PreFix = prop.getProperty("LocalFilePreFix");
            OverWriteFile = Boolean.parseBoolean(prop.getProperty("OverwriteFileEnable"));
        } catch (IOException ex) {
            logger.debug(ex.getMessage());
            logger.error(ex.toString());
        }
        return LocalDir;
    }

    private void dateType() {
        if (FileNameMatch) {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -NoOfDaysBack);
            CurrentDay = dateFormat.format(cal.getTime());
            logger.debug("Matching date type -" + CurrentDay);

        } else {
            CurrentDay = "";
            logger.debug("Full file list will be downloaded");
        }
    }

    public FTPClient connectToServer() throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(FTPServer, FTPPort);
        if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            ftp.disconnect();
            logger.debug("FTP not connected");
            System.exit(0);
        }
        logger.debug("Connecting to server ....");
        ftp.login(FTPUser, FTPPasswd);
        logger.debug(ftp.getReplyString());

        //enter passive mode
        ftp.enterLocalPassiveMode();
        logger.debug("Changing working directory");
        ftp.changeWorkingDirectory(RDir);
        logger.debug(ftp.getReplyString());
        //ftp.makeDirectory("somedir");
        //ftp.changeWorkingDirectory("somedir");
        ftp.setFileType(FTPClient.LOCAL_FILE_TYPE);
        //logger.debug(ftp.doCommand("pwd",""));
        //logger.debug(ftp.getReplyString());
        return ftp;
    }

    private FTPFile[] getRemoteFileList(FTPClient ftp) throws IOException {

        FTPFile n[] = ftp.listFiles();
        logger.debug("Getting remote file list");
        logger.debug(ftp.getReplyString());
        for (FTPFile a : n) {
            logger.debug(a.getName() + " - " + a.getSize());
        }
        return n;

    }

    /*java.io.File srcFolder = new java.io.File("test");
    FileInputStream fis = new FileInputStream(srcFolder);
    ftp.storeFile ("test", fis);*/
    private void renameFile(FTPClient ftp, FTPFile file, long remoteFileSize) throws IOException {
        if (LocalFileRename) {
            logger.debug("Local file rename enabled.");
            logger.debug("File will be rename with pre-fix. " + PreFix);
            newFileName();
            String localFileName = LDir + File.separator + PreFix.concat(file.getName());
            logger.debug("New file name " + localFileName);
            File NewFile = new File(localFileName);
            if (NewFile.exists() && !OverWriteFile) {
                logger.debug("New file exist and over write disable. download will be skipped");

            } else {
                logger.debug("New File already exist-"+NewFile.exists()+". Over write -"+OverWriteFile);
                actualDownload(ftp, file, localFileName, remoteFileSize);
            }
        } else {
            logger.debug("Rename file disable");
        }
    }
       private void newFileName(){

           if(PreFix.equals("yyyyMMddHHmm")){
           logger.debug("new file name will prefix with current date time");
           DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
           Calendar cal = Calendar.getInstance();
           cal.add(Calendar.DATE,0);
           PreFix = dateFormat.format(cal.getTime()).concat("-");
           }
       }

    private void downloadFiles(FTPFile n[], FTPClient ftp) throws FileNotFoundException, IOException {


        for (FTPFile file : n) {
            logger.debug("-------------------------------------------------------------");

            long remoteFileSize = file.getSize();
            logger.debug("remote file -" + file.getName() + " size -" + remoteFileSize);

            //get the file from the remote system
            if (file.getName().contains(CurrentDay)) {


                //String localFileName = LDir.concat(file.getName());

                String localFileName = LDir + File.separator + file.getName();
                File tempFile = new File(localFileName);


                if (tempFile.exists()) {
                    logger.debug(tempFile + " - local file exist in same name. downloading paused......");
                    renameFile(ftp, file, remoteFileSize);
                } else {

                    logger.debug("Local file not exist. Downloading file");
                    actualDownload(ftp, file, localFileName, remoteFileSize);

                }

            } else {
                logger.debug("File name not matched. skip.... ");
            }
        }
        //close output stream
    }

    private void actualDownload(FTPClient ftp, FTPFile file, String localFileName, long remoteFileSize) throws IOException {
        long localFileSize = -1;
        OutputStream output = new FileOutputStream(localFileName);
        logger.debug("Download starting......");
        ftp.retrieveFile(file.getName(), output);
        output.close();

        File localFile = new File(localFileName);

        if (localFile.exists()) {
            localFileSize = localFile.length();
        }

        logger.debug("Local " + localFileName + " file size -" + localFileSize);
        if (RemoteFileDelete) {
            if (localFileSize == remoteFileSize) {
                logger.debug("Deleting remote file as the size matches");
                ftp.deleteFile(file.getName());
            } else {
                logger.debug("Didn't delete the remote file" + file.getName() + " as sizes are different");
            }
        } else {
            logger.debug("Remote delete disable");
        }

    }

    private void ftpDisconnect(FTPClient ftp) throws IOException {
        ftp.disconnect();
        logger.debug("-------------------------------------------------------------");
        logger.debug("FTP disconnected");
    }

    private void emptyLocalDir(File folder) throws IOException {
        if (LocalFileDelete) {
            logger.debug("cleaning local directory before coping....");
            File[] files = folder.listFiles();
            if (files != null) { //some JVMs return null for empty dirs
                for (File f : files) {
                    if (f.isDirectory()) {
                        logger.debug(f.getName() + " is a folder ignore");
                    } else {
                        logger.debug("deleting file in local tmp folder - " + f.getName());
                        f.delete();
                    }
                }
            } else {
                logger.debug("local folder is empty ");
            }
            //folder.delete();
        } else {
            logger.debug("local folder empty disable");
        }
    }
}
