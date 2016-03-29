import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 16, 2009
 * Time: 5:22:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Manipulator {

    public static void main(String arg[]) {

        try{
        Manipulator mp= new Manipulator();

        DbCon d = new DbCon();

        if (arg[0].equalsIgnoreCase("in")){
        //d.showData(c, "SELECT * FROM Emp","Name");
        Connection c = d.getconWrite();
        System.out.println(d.writeData(c,"INSERT INTO Emp(Name,Balance) VALUES('oh',100)"));
        c.close();
        }

        else{

        long l1=System.nanoTime();

        Connection c2 = d.getconRead();
        ResultSet rs = d.getData(c2, "SELECT * FROM Emp");
        //System.out.println("-------------------");
        //mp.showData(rs);
        //System.out.println("-------------------");
        c2.close();

        long l2=System.nanoTime();


        System.out.println("Time elapse to retreive from DB - "+(l2-l1)/1000000);
        //System.out.println("Data storing in MemCach");
        MemCach mc= new MemCach();
        mc.connectMemCach();
        mc.mcc.set("EmpNames", rs);
        //System.out.println("Data stored in MemCach");

        System.out.println("Reading from MemCach");
        long l3=System.nanoTime();
        MemCach mc2= new MemCach();
        mc2.connectMemCach();
        Object objArray = mc2.mcc.get("EmpNames");
        long l4=System.nanoTime();
        System.out.println("Time elapse to retreive from Memchached - "+(l2-l1)/1000000);
        /*ResultSet rs2 = (ResultSet)objArray;
        System.out.println("Data retrived from MemCach");
        System.out.println("-------------------");
        mp.showData(rs2);
        System.out.println("-------------------");*/
        }
        }catch(Exception e){
            System.out.println(e.toString());
    }


    }

    private void showData(ResultSet rs){
        try{
         while (rs.next()) {
                String CityName = rs.getString("Name");
                System.out.println(CityName);
         }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
