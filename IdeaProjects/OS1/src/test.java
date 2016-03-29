/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Mar 14, 2012
 * Time: 11:45:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class test {

    public synchronized void come(){
        int count=0;
        synchronized (this){
            count++;
        }
    }
    
}
