/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Oct 10, 2010
 * Time: 9:13:31 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Date;
import java.net.InetAddress;


public class FtpClient {


    public static void main(String args[]) {


        try{

    FTPClient client = new FTPClient();
    FileInputStream fis = null;

    client.connect("59.163.254.20");
    //client.connect("ftp.secureftp-test.com");
    client.login("ftpuser", "1234!@#$");
    //client.login("test", "test");

    String filePath = "tetu";
    File file = new File(filePath);
    fis = new FileInputStream(file);

    client.changeWorkingDirectory("/home/ftpuser/m1-sdp/backup/");
    String fname = new Date().getTime() + "-" + file.getName();

    /*client.enterRemotePassiveMode();
    client.enterRemoteActiveMode(InetAddress.getByName(client.getPassiveHost()),client.getPassivePort());

    client.remoteStoreUnique(fname);
    */
    System.out.println("going to store");
    client.storeFile(file.getName().toString(), fis);
    client.logout();
    fis.close();
            
    }catch(Exception e){
        e. printStackTrace();
    }


      /*
        FtpClient fc = new FtpClient();
        System.out.println("creating FTP");
        FTPClient ftp = fc.ftpConnect();

        //FTPFile files[] = fc.remoteDirList(ftp,"/home/ftpuser/m1-sdp");
        //fc.printList(files); // print the file list
        //fc.download(ftp, "/home/ftpuser/m1-sdp/DA_releases/Regular_Release_Winter_2010/Regular_Release_Winter_2010.zip", "C:\\Documents and Settings\\kasun\\Desktop\\Regular_Release_Winter_2010.zip"); // download file remote file name, local file name
        
        System.out.println("going to upload");
        FtpClient.upload(ftp,"/home/ftpuser/m1-sdp/backup/","F:\\m1-sdp\\backup\\app-backup\\2011-06-30\\test\\");

        //files = null;
        fc.disConnectFtp(ftp);
        ftp = null;
        fc = null;
        */

    }

    private FTPClient ftpConnect() {

        int reply;
        FTPClient ftp = new FTPClient();

        try {


            String server = "59.163.254.20";
            //String server = "192.168.0.2";
            ftp.connect(server);
            System.out.println("Connected to " + server + ".");
            System.out.print(ftp.getReplyString());
            ftp.login("ftpuser", "1234!@#$");
            System.out.print(ftp.getReplyString());


            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
        } catch (IOException e) {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                    // do nothing
                }
            }
            System.err.println("Could not connect to server.");
            e.printStackTrace();
            System.exit(1);

        }
        return ftp;
    }


    private FTPFile[] remoteDirList(FTPClient ftp,String location) {

        FTPFile[] tmpFiles = new FTPFile[1000];

        try {

            FTPListParseEngine engine = ftp.initiateListParsing(location);

            while (engine.hasNext()) {
                tmpFiles = engine.getNext(25);  // "page size" you want
                //do whatever you want with these files, display them, etc.
                //expensive FTPFile objects not created until needed.
                System.out.println(tmpFiles[0]);

            }


        } catch (IOException io) {
            io.printStackTrace();
        }

        return tmpFiles;
    }

    private void printList(FTPFile tmpFiles[]) {
        for (int a = 0; a < tmpFiles.length; a++) {
            System.out.println(tmpFiles[a]);
        }


    }

    private void disConnectFtp(FTPClient ftp) {

        try {
            ftp.disconnect();
        } catch (IOException io) {
            io.printStackTrace();
        }


    }

    private void download(FTPClient ftp, String remoteFileName, String localFileName) {


        try {

            File dfile = new File(localFileName);

            if (dfile.exists()) {

                System.out.println("File already exists closing...");

                System.exit(1);
            }

            FileOutputStream fileOut = new FileOutputStream(dfile);

            ftp.retrieveFile(remoteFileName, fileOut);


        } catch (IOException e) {

            System.out.println(e);

        }
    }

    private static void upload(FTPClient ftp, String RemoteLocation,String LocalLocation) {

        File file = new File(LocalLocation);
        File[] files = file.listFiles();
      /* for (int fileInList = 0; fileInList < files.length; fileInList++) {       //
            System.out.println("file"+fileInList+" - "+files[fileInList].toString());
        }*/

        FileInputStream fis = null;
        try {

            for(File filename:files){
            //File filename= new File("F:\\m1-sdp\\backup\\app-backup\\2011-06-30\\test\\mms.tgz");
            fis = new FileInputStream(filename);
                
            // Store file to server
             System.out.println("local file "+filename.toString());
             System.out.println("remote file - "+RemoteLocation.concat(filename.getName().toString()));
             //ftp.changeWorkingDirectory("/home/ftpuser/m1-sdp/backup/");
             ftp.storeFile(RemoteLocation.concat(filename.getName().toString()), fis);

            }

        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
