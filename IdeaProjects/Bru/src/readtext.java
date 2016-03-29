/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 5, 2007
 * Time: 8:26:15 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 5, 2007
 * Time: 8:08:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class readtext {

    public static void main(String a[]){

        String fullPathFilename="."+ File.separator+"my.txt";
    /*try{

    BufferedReader reader = new BufferedReader(new FileReader(fullPathFilename));


     System.out.println(reader.readLine());
     System.out.println(reader.readLine());
    }catch(Exception e){
        System.out.println(e.toString());

    } */
     try{
        File f = new File(fullPathFilename);
        RandomAccessFile raf = new RandomAccessFile(f, "rw");

        // Read a character
        //char ch = raf.readChar();
         //for(int i=0;i<2;i++){
            // String ch=raf.readLine();
            // System.out.println(ch);
             //if(ch.equalsIgnoreCase("kas")){
                 raf.writeChars("test");
             //    System.out.println("ok");  }
      //   }
          //System.out .println(ch);
        // Seek to end of file
        raf.seek(f.length());

        // Append to the end
        raf.writeChars("aString");
        raf.close();

     }catch(Exception e){
         System.out.println(e.toString());

     }
    }
}

