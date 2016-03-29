/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 6, 2007
 * Time: 2:35:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Phone {
        int NoOfPhone;
    Phone(int No){
        NoOfPhone=No;
    }

    // Part B (ii) adding the particular data to position specified
    public void storeAttr(int Position,String PhoneNum [] [],String Number,String BName,String Price,String Other1,String Other2){
        PhoneNum [Position] [0]=Number;
        PhoneNum [Position][1]=BName;
        PhoneNum [Position][2]=Price;
        PhoneNum [Position][3]=Other1;
        PhoneNum [Position][4]=Other2;

    }

    public void display(String PhoneNum [] []){ //Part B (1)
        String Items [] ={"Number     :","Brand Name :","Price      :","Other 1    :","Other 2    :"};
        for(int i=0;i<NoOfPhone;i++){
            if(PhoneNum[i][0]!=null){
            System.out.println("Phone Number : "+(i+1));
            for(int j=0;j<5;j++){
                System.out.println(Items[j]+ " "+PhoneNum[i][j]);
            }
             System.out.println("----------------------");
            }
        }
    }
    public void searchNumber(String number, String PhoneNum[][]){     // Part B (iii)
        boolean found=false;
        for(int i=0;i<NoOfPhone;i++){
              if(PhoneNum[i][2]!=null && PhoneNum[i][0].equals(number)){
                  System.out.println("---------Details--------");
                  System.out.println("Brand Name : " +PhoneNum[i][1]);
                  System.out.println("Price      : " +PhoneNum[i][2]);
                  System.out.println("Other1     : " +PhoneNum[i][3]);
                  System.out.println("Other2     : " +PhoneNum[i][4]);
                  System.out.println("-----------------\n");
                  //break;
                  found=true;
              }

        }
        if(found==false){
            System.out.println("Sorry the specified Number "+number+" Not Found ");
        }

    }

     public void averagePrice(String PhoneNum[][]){   //Part B (iv)
         Float total=0f;
         int counter=0;
         for(int i=0;i<NoOfPhone;i++){
             if(PhoneNum[i][2]!=null){
              total+=Float.parseFloat(PhoneNum[i][2]) ;
              counter+=1;
             }
         }
         System.out.println("---------Details--------");
         System.out.println("\nAverage Price :  "+ total/counter);
         System.out.println("\n------------------------");
     }

}
