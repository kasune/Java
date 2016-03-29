package back;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 5, 2007
 * Time: 8:08:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class readtext {

    public static void main(String a[]){

        String fullPathFilename="my.txt";
    try{

    BufferedReader reader = new BufferedReader(new FileReader(fullPathFilename));

     System.out.println(reader.readLine());

    }catch(Exception e){
        System.out.println(e.toString());

    }

    }
}
