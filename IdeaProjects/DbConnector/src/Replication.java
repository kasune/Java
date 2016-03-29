/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package np;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import com.mysql.jdbc.ReplicationDriver;


/**
 *
 * @author Ruchith
 */
public class Replication {

       static  String driverURL = "jdbc:mysql://192.168.0.19:3306,localhost:3306/mimos?autoReconnect=true&roundRobinLoadBalance=true&user=root&password=password";
//   static  String driverURL = "jdbc:mysql://localhost,localhost/hsenid?autoReconnect=true&roundRobinLoadBalance=true&user=hsenid&password=password";

//      jdbc:mysql:loadbalance://172.16.161.40:3306,172.16.161.40:3306/p1_subscribers?loadBalanceStrategy=bestResponseTime&failOverReadOnly=false&autoReconnectForPools=true&roundRobinLoadBalance=true

  public static void readSQL() throws Exception{
      System.out.println(">>>READ method");

    ReplicationDriver driver = new ReplicationDriver();

      Properties props = new Properties();
      //Class.forName("com.mysql.jdbc.ReplicationDriver").newInstance();

    Connection conn =driver.connect(driverURL,props);

      conn.setReadOnly(false);
      ResultSet rs =         



      conn.createStatement().executeQuery("SELECT count(*) FROM emp");
      rs.next();
      String count = rs.getString(1);
      System.out.println(">>>count : "+count);
      rs.close();
      conn.close();

  }

    public static void updateSQL() throws Exception{
        System.out.println(">>>WRITE  method");

      ReplicationDriver driver = new ReplicationDriver();

        Properties props = new Properties();

      Connection conn =
          driver.connect(driverURL,props);

        conn.setReadOnly(true);
        conn.setAutoCommit(false);
        conn.createStatement().executeUpdate("insert into  org_group (grp_name, grp_description) values ('"+System.currentTimeMillis()+"','Northport POC data ')");
        conn.commit();
        conn.close();

    }


  public static void main(String[] args) throws Exception {
      System.out.println("JDBC tester ::::");

      //String arg = args[0];
      //int argInt = Integer.parseInt(arg);

      //System.out.println(" entered option:"+argInt);
      //switch (argInt) {
        //  case 2 : updateSQL();break;
          //case 1 : readSQL();break;
        readSQL();

      }




  }


