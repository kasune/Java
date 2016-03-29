package src;

import java.util.Map;
import java.util.TreeMap;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.Charset;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 30, 2011
 * Time: 2:16:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class test {

    public static void main (String args[]){


        test t = new test();
        //t.simpleTest();
        //test.testing();
        test.boxUn();

        /* String str = "\u00F6";
        char c = '\u00F6';
        Character letter = new Character('\u00F6');
        OutputStreamWriter out = new OutputStreamWriter(new ByteArrayOutputStream());
        System.out.println(out.getEncoding()); */

        // Create the encoder and decoder for US-ASCII, ISO-8859-1, UTF-8, UTF-16 , UTF-16BE, UTF-16LE
        Charset charset = Charset.forName("UTF-16");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();
        try {
            ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("6C34"));

            CharBuffer cbuf = decoder.decode(bbuf);
            String s = cbuf.toString();
            System.out.println(s);
        } catch (CharacterCodingException e) {

        }
    }

    public void simpleTest(){
        String st[] = new String[5];
        String a =st[0].toUpperCase();
        System.out.println(a);
    }

    public static void testing(){
        int b[] = {1,2,3,4,5};
        int res = 1;
        try{
             for (int a : b){
                 res+=a;
                 }
            System.out.println(res);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
     public static void boxUn(){
    String input[] =  {"me", "to", "do", "the", "work"};
    Map<String, Integer> m = new TreeMap<String, Integer>();
         for (String word : input) {
             Integer freq = m.get(word);
             m.put(word, (freq == null ? 1 : freq + 1));
         }
         System.out.println("ammo");
         //System.exit(0);
         System.out.println(m);
         System.out.println(Integer.MAX_VALUE);
     }


}
