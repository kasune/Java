import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Sep 9, 2008
 * Time: 2:34:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentType {

    File file = new File("E:\\beyondm-back\\IdeaProjects\\mmp1\\input");
    FileWriter fstream = null;

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;
    StringTokenizer st = null;
    String accname;
    String tagno;

    public static void main(String a[]) {
        ContentType c = new ContentType();
        c.readFile();

    }

    public void readFile() {
        try {
            fis = new FileInputStream(file);
            fstream =new FileWriter("E:\\beyondm-back\\IdeaProjects\\mmp1\\output");
            BufferedWriter out = new BufferedWriter(fstream);
            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // dis.available() returns 0 if the file does not have more lines.
            while (dis.available() != 0) {

                // this statement reads the line from the file and print it to
                // the console.
                //System.out.println(dis.readLine());
                st = new StringTokenizer(dis.readLine(), ",");
                int count =0;
                while (st.hasMoreTokens()) {

                    if (count==0)
                        accname = st.nextToken();

                    if (count==1)
                        tagno = st.nextToken();

                    if (count>1){
                    for (int a =0;a<st.countTokens();a++){
                    out.write(accname + "," +tagno +"," + st.nextToken() + ",2008-09-09,true" + "\n");
                    }
                    }
                   count+=1;
                   // out.write(st.nextToken());
                }
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();
            fstream.flush();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


