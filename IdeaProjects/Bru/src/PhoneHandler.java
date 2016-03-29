/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 6, 2007
 * Time: 4:59:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhoneHandler {
            static int add=0;
            static int views=0;
            static int searches=0;
            static int averageCal=0;

    public void addPhone(String PIn [][],int NoOfPhone,String No, String Bname,String Price,String Ot1,String Ot2){

        Phone p1=new Phone(NoOfPhone);
        p1.storeAttr(add,PIn,No,Bname,Price,Ot1,Ot2);
        add+=1;
    }

    public void viewPhone(String PIn [][],int NoOfPhone){
        Phone p1=new Phone(NoOfPhone);
        p1.display(PIn);
        views+=1;
    }

    public void searchNumber(String PIn [][],int NoOfPhone,String Number){
        Phone p1=new Phone(NoOfPhone);
        p1.searchNumber(Number,PIn);
        searches+=1;

    }

    public void priceAverage(String PIn [][],int NoOfPhone){
        Phone p1=new Phone(NoOfPhone);
        p1.averagePrice(PIn);
        averageCal+=1;

    }

    public void stat(){
        System.out.println("-------------Statistics------------------");
        System.out.println("No Of Phone Add  : "+add);
        System.out.println("No Of Views      : "+views);
        System.out.println("No Of Searched   : "+searches);
        System.out.println("No Of averageCal : "+averageCal);
        System.out.println("-------------Thank You-------------------");

    }
}
