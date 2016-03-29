package test;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Feb 2, 2008
 * Time: 9:01:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sort {
    public static void main(String []args){
        int a[]={2,4,5,7,3,23,7,12,};
        Sort s=new Sort();
        s.bubbleSort(a);
    }

    public void bubbleSort(int in[]){
        System.out.println(" --Bubble Sort-- ");
        System.out.println(" Original Values");
        origalValues(in);

    }

    private void origalValues(int in[]){

        for(int i=0;i<in.length;i++){
            System.out.println(in[i]);
        }

    }
}
