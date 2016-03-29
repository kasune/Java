/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Mar 28, 2008
 * Time: 12:12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class divider {

    public static void main (String args []){
        long x;
        long count=0;
        long z=Long.MAX_VALUE ;

        System.out.println("------------");
        for (x=1;x<(Math.round(Math.sqrt(z)));x++){


            if(z%x==0){
               System.out.println("X values "+x);
               System.out.println("Y Value "+z/x);
               System.out.println("------------");
               count+=1;
            }
          }
          System.out.println("No Of Pairs "+count);

    }
}
