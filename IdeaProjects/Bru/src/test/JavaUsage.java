package test;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Dec 8, 2007
 * Time: 10:47:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class JavaUsage {

        int convertMegabyte=1000;
        String scale="KB";

    public void usage()throws RuntimeException,Exception{
        Runtime rt=Runtime.getRuntime();

        double freeMemory    = rt.freeMemory()/convertMegabyte;
        double totalMemory   = rt.totalMemory()/convertMegabyte;
        double usedMemory    = (totalMemory - freeMemory)/convertMegabyte;
        double maxMemory     = rt.maxMemory()/convertMegabyte;

        System.out.println("Java Runtime Total Memory :"+totalMemory+scale);
        System.out.println("Java Runtime Free Memory  :"+freeMemory+scale);
        System.out.println("Java Runtime Used Memory  :"+usedMemory+scale);
        System.out.println("Max Memory Attemp To Use  :"+maxMemory+scale);

    }

    public static void main(String args[]){
        try{
            JavaUsage obj1= new JavaUsage();
            obj1.usage();
        }catch(RuntimeException e){
            System.out.println(e.toString());
        }catch(Exception ex){
            System.out.println(ex.toString());
        }

    }
}