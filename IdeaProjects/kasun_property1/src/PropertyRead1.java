/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jan 17, 2007
 * Time: 10:46:36 AM
 * To change this template use File | Settings | File Templates.
 */
import java.util.*;
import java.io.*;

public class PropertyRead1 {

    public static void main(String args[]){
       try{
        Properties prop = new Properties();

        FileInputStream fil = new FileInputStream("/home/kasun/IdeaProjects/kasun_property1/conf/test.conf");

        prop.load(fil);
        fil.close();

           System.out.println(prop.getProperty("age"));

       }
       catch(Exception e){


       }

    }

    


}
