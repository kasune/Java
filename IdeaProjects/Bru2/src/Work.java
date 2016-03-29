import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Feb 3, 2008
 * Time: 5:52:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class Work extends TimerTask {

    public void run(){
          long l2=System.currentTimeMillis();
          int a[]={2,4,5,7,3,23,7,12,};
          arrayValues(a);
          System.out.println(" Sorted values ");
          java.util.Date d=new java.util.Date(l2);
          System.out.println(d.toString());
          bubbleSort(a);
          arrayValues(a);

    }

    private void bubbleSort(int in[]){
        System.out.println(" --Bubble Sort-- ");
        int i,j;
        long l1 = System.currentTimeMillis();

        for(i=in.length-1;i>1;i--){
          for(j=0;j<i;j++){
            if(in[j]>in[i]){
            int temp=in[j];
            in[j]=in[i];
            in[i]=temp;
            }
          }
        }
        long l2 = System.currentTimeMillis();
        System.out.println("Time take to sorting: "+ (l2 - l1) +"(ms)");
    }

     private void arrayValues(int in[]){

        for(int i=0;i<in.length;i++){
            System.out.print(in[i]+",");
        }
     }
}
