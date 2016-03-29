
/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Feb 2, 2008
 * Time: 9:17:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sort {
    public static void main(String []args){
        long CurrentTime=System.currentTimeMillis();
        CurrentTime+=2000;
        java.util.Date CurrentDate=new java.util.Date(CurrentTime);
        Work w=new Work();

        java.util.Timer tt=new java.util.Timer();
        tt.schedule(w,CurrentDate,3000);

    }
}
