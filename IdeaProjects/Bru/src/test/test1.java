package test;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 9, 2007
 * Time: 2:52:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class test1 {
    public static void main(String args []){
        outerLoop: for (int i=0;i<3;i++){
            for(int j=0;j<1;j++){
             if (i==2){
                //continue;
                break outerLoop;
             }
             System.out.println(" J = "+j);
            }
             System.out.println("I = "+i);
         }


    }
}
