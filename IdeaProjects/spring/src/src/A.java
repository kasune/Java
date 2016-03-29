package src;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 7, 2011
 * Time: 12:58:26 PM
 * To change this template use File | Settings | File Templates.
 */
class B{
    void nonstat(){
       System.out.println("B-non");
     }
     static void method(){
       System.out.println("B");

     }

}

public class A extends B{


    static void method(){
       System.out.println("A");
     }
     void nonstat(){
       System.out.println("A-non");                             
     }

         static int inte =10;
    public static void main(String args[]){

          int x =20;

         // B b1 = new A();
          A b2= new A();
         // System.out.println("non-static - "+x);
         // System.out.println("static - "+inte);
          b2.calculate(x,inte);
          System.out.println("non-static - "+x);
          System.out.println("static - "+inte);
         // b1.method();
         // b1.nonstat();

    }
    public void calculate(int x, int y){
        x+=2;
        y+=2;
        ///System.out.println("static - "+inte);
    }
}
