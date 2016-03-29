import java.io.IOException;
import java.io.File;
import java.net.MalformedURLException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jibble.simpleftp.*;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Aug 6, 2011
 * Time: 6:00:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class FtpMisc {

    public static void main(String args[]){

        try {
    SimpleFTP ftp = new SimpleFTP();

    // Connect to an FTP server on port 21.
    ftp.connect("59.163.254.20t", 21, "ftpuser", "1234!@#$");

    // Set binary mode.
    ftp.bin();

    // Change to a new working directory on the FTP server.
    ftp.cwd("m1-sdp/backup/");

    // Upload some files.
    ftp.stor(new File("tetu"));
    //ftp.stor(new File("tetu123"));

    // You can also upload from an InputStream, e.g.
    //ftp.stor(new FileInputStream(new File("tetu")), "tetu");
    //ftp.stor(someSocket.getInputStream(), "blah");

    // Quit from the FTP server.
    ftp.disconnect();
}
catch (IOException e) {
    // Jibble.
}

        /*try{
        FtpMisc fm = new FtpMisc();
        String fname = "tetu";
        File f = new File(fname);
        fm.upload("59.163.254.20","ftpuser","1234!@#$","tetu",f);
        }catch(Exception e){
            e.printStackTrace();
        }*/

    }

public void upload( String ftpServer, String user, String password,
         String fileName, File source ) throws MalformedURLException,
         IOException
   {
      if (ftpServer != null && fileName != null && source != null)
      {
         StringBuffer sb = new StringBuffer( "ftp://" );
         // check for authentication else assume its anonymous access.
         if (user != null && password != null)
         {
            sb.append( user );
            sb.append( ':' );
            sb.append( password );
            sb.append( '@' );
         }
         sb.append( ftpServer );
         sb.append( '/' );
         sb.append( fileName );
         /*
          * type ==> a=ASCII mode, i=image (binary) mode, d= file directory
          * listing
          */
         sb.append( ";type=i" );

         BufferedInputStream bis = null;
         BufferedOutputStream bos = null;
         try
         {
            URL url = new URL( sb.toString() );
            URLConnection urlc = url.openConnection();

            bos = new BufferedOutputStream( urlc.getOutputStream() );
            bis = new BufferedInputStream( new FileInputStream( source ) );

            int i;
            // read byte by byte until end of stream
            while ((i = bis.read()) != -1)
            {
               bos.write( i );
            }
         }
         finally
         {
            if (bis != null)
               try
               {
                  bis.close();
               }
               catch (IOException ioe)
               {
                  ioe.printStackTrace();
               }
            if (bos != null)
               try
               {
                  bos.close();
               }
               catch (IOException ioe)
               {
                  ioe.printStackTrace();
               }
         }
      }
      else
      {
         System.out.println( "Input not available." );
      }
   }

}
