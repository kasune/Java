import java.io.*;
/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 6, 2007
 * Time: 4:31:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class PStore {
         static int  NoOfPhone=100;
     //
    public static void main(String a[]){


         String PhoneNum [] []= new String [NoOfPhone] [5];

         PStore p = new PStore();
         PhoneHandler ph =new PhoneHandler() ;
         System.out.println("------------Welcome to the Array Phone Store------------");
         p.selectMain(PhoneNum,ph,p);
         System.out.println("---------Sucessfully Exit---------");
        //PhoneNum [0] [0]="94713217117"; // Store one number with attributes  Part A
        //PhoneNum [0][1]="Nokia";
        //PhoneNum [0][2]="10000";
        //PhoneNum [0][3]="3G";
        //PhoneNum [0][4]="Bluetooth";


        //Phone p1= new Phone(NoOfPhone);
        //p1.storeAttr(1,PhoneNum,"94713217118","Erccson","20000.5","3G","bluetooth");

        //p1.display(P1
         // honeNum);
        //p1.averagePrice(PhoneNum);
        //p1.searchNumber("94713217118",PhoneNum);
    }

    public void selectMain(String PhoneNum [] [],PhoneHandler ph,PStore p){
            System.out.println("------Main Menu--------");
            System.out.println("Add Phone Details   : 1");
            System.out.println("View Phone Details  : 2");
            System.out.println("Search Phone Number : 3");
            System.out.println("Average Price       : 4");
            System.out.println("Statistics          : 5");
            System.out.println("Exit : any");
            System.out.print("Number : ");
         try {

             BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             String FSelect=in.readLine();

                    if (FSelect.equals("1")){

                         p.selectOne(PhoneNum,ph,p);

                     }
                    if (FSelect.equals("2")){

                         p.selectTwo(PhoneNum,ph,p);

                     }
                    if (FSelect.equals("3")){

                         p.selectThree(PhoneNum,ph,p);

                     }
                    if (FSelect.equals("4")){

                         p.selectFour(PhoneNum,ph,p);

                     }
                    if (FSelect.equals("5")){

                         p.selectFive(PhoneNum,ph,p);

                     }


         }catch (Exception e) {

                System.out.println(e.getMessage());

         }

    }

    public void selectOne(String PIn [][], PhoneHandler ph,PStore p){
       try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("\nEnter the Phone Number : ");
                String Number=in.readLine();
                System.out.print("\nEnter the Brand Name   : ");
                String BName=in.readLine();
                System.out.print("\nEnter the Price        : ");
                String Price=in.readLine();
                System.out.print("\nEnter the Other 1      : ");
                String Other1=in.readLine();
                System.out.print("\nEnter the Other 2      : ");
                String Other2=in.readLine();

                ph.addPhone(PIn,NoOfPhone,Number,BName,Price,Other1,Other2);

                System.out.println("To Continue :  1 ");
                System.out.println("To Exit     :  Any");
                System.out.print("Number      : ");
                String SecOp=in.readLine();

                if(SecOp.equals("1")){
                    p.selectMain(PIn,ph,p);
                }

                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }


    }

    public void selectTwo(String PIn [][], PhoneHandler ph,PStore p){
               System.out.print("----------------------");
               ph.viewPhone(PIn,NoOfPhone);
               try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                    System.out.println("To Continue :  1");
                    System.out.println("To Exit     :  Any");
                    System.out.print("Number      : ");
                    String SecOp=in.readLine();
                    System.out.println("");
                    if(SecOp.equals("1")){
                        p.selectMain(PIn,ph,p);
                    }

               }catch(Exception e){
                System.out.println(e.getMessage());
               }

    }

    public void selectThree(String PIn [][], PhoneHandler ph,PStore p){
                System.out.print("Enter the Phone Number : ");

               try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                    String number=in.readLine();
                    ph.searchNumber(PIn,NoOfPhone,number);

                    System.out.println("To Continue :  1");
                    System.out.println("To Exit     :  Any");
                    System.out.print("Number      : ");
                    String SecOp=in.readLine();

                    if(SecOp.equals("1")){
                        p.selectMain(PIn,ph,p);
                    }

               }catch(Exception e){
                System.out.println(e.getMessage());
               }


    }


    public void selectFour(String PIn [][], PhoneHandler ph,PStore p){

                ph.priceAverage(PIn,NoOfPhone);

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                    System.out.println("To Continue :  1");
                    System.out.println("To Exit     :  Any");
                    System.out.print("Number      : ");
                    String SecOp=in.readLine();

                    if(SecOp.equals("1")){
                        p.selectMain(PIn,ph,p);
                    }

               }catch(Exception e){
                System.out.println(e.getMessage());
               }

    }


    public void selectFive(String PIn [][], PhoneHandler ph,PStore p){
                 ph.stat();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                    System.out.println("To Continue :  1");
                    System.out.println("To Exit     :  Any");
                    System.out.print("Number      : ");
                    String SecOp=in.readLine();

                    if(SecOp.equals("1")){
                        p.selectMain(PIn,ph,p);
                    }

               }catch(Exception e){
                System.out.println(e.getMessage());
               }


    }
}
