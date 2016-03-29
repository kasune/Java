

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DbCon {

    String Names[];
    public static void main(String[] args) {

        DbCon t1 = new DbCon();
        Connection con = t1.getconRead();
        int res= t1.writedataPrepared(con);
        System.out.println(res);

    } // main()

    public Connection getconWrite() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            /* Get a connection to the database */
            conn = DriverManager.getConnection("jdbc:mysql://localost:3306/mimos?" + "user=root&password=password");

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return conn;
    }

    public Connection getconRead() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            /* Get a connection to the database */
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kasun?" + "user=kasun&password=kasun");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?" + "user=kasun&password=kasun");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return conn;
    }

    public ResultSet getData(Connection con, String query) {

        int count=0;
        ResultSet rs=null;
        try {
            Statement stmt = con.createStatement();

            /* Execute the query */
            rs = stmt.executeQuery(query);

            /* The following 3 lines are for finding out the size of the result set */
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

           /* while (rs.next()) {
                String CityName = rs.getString("Name");
                System.out.println(CityName);
                count +=1;
            }*/
           
            //System.out.println("Retrieved " + rowCount + " row(s).\n");

            //System.out.println("Name\n--------");

            /* Retrieve the data from the result set */

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
           return rs;
    }

    public int writeData(Connection con, String query){
        int res=0;
        try{
            Statement stmt = con.createStatement();

            /* Execute the query */
            res = stmt.executeUpdate(query) ;
        }catch(SQLException se){
            System.out.println("SQLException: " + se.getMessage());
        }
        return res;
    }

    public int writedataPrepared(Connection con){
         int res =0 ;
         String query = "INSERT INTO test values(12,?,?)";
        try{
            PreparedStatement pstmp = con.prepareStatement(query);
            pstmp.setString(1,"kasun");
            pstmp.setString(2,"ekanayake");
            res = pstmp.executeUpdate();
        }catch(SQLException sqle){
            System.out.println();

        }
       return res;
    }



} // MySQLclient
