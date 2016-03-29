package test;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jan 4, 2008
 * Time: 5:39:29 PM
 * To change this template use File | Settings | File Templates.
 */
class test {
   public static void main (  String [] args ) {
      //Map<Integer, String> map = new LinkedHashMap<Integer, String> ();
      //Map<Integer, String> sap = new HashMap<Integer, String> ();
     // populate( map );
      //populate( sap );
      // try{
      //System.out.println( map.get(1) + sap.get(1) );}
       //catch(ClassCastException e){}
   }

   static void populate ( Map  m ) {
      for ( int i = 0 ; i < 10 ; i++ ) {
         m.put(i,i);
      }
   }
}
