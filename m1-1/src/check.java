import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 12, 2010
 * Time: 12:19:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class check {

    public static void main(String args[]){

        /* Pattern p = Pattern.compile("[a-zA-Z0-9]{4,}");
        Matcher m = p.matcher("a119");
        boolean b = m.matches();
        System.out.println(b); */

        check c =new check();
        //c.octet2decimal();
        //c.decimal2Octet();
        c.unicodeTest();
    }

    private void octet2decimal(){

    String result;
    StringBuffer sb = new StringBuffer();
    String hexs =
"54:68:65:20:46:52:55:20:50:6f:77:65:72:20:4c:65:76:65:6c:20:69:73:20:31:20:43:6f:6e:73:75:6d:69:6e:67:20:31:20:57:61:74:74:73:20:0a";
    
    String[] sa = hexs.split(":");
    for (String s : sa){
      char c = (char)(Integer.parseInt(s, 16));
      sb.append(c);
    }
    result = new String(sb);
    System.out.print(result);

    }

    private void decimal2Octet(){
        char c=(char)(Integer.parseInt("65",16));
        System.out.println(c);
        String a = "e";
        byte a1[] =a.getBytes();
        for (int d=0;d<a1.length;d++){
        System.out.println(a1[d]);

        }
    }

    private void unicodeTest(){
        //String str = "\u00F6";
        //char c = '\u00F6';
       // Character letter = new Character('\u00F6');
        OutputStreamWriter out = new OutputStreamWriter(new ByteArrayOutputStream());
        System.out.println(out.getEncoding());
        
    }
}
