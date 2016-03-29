package dev;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Dec 4, 2011
 * Time: 9:25:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Dbconnector {

    String Names[];
    private static Logger logger = Logger.getLogger(Dbconnector.class);

    public static void main(String[] args) {

        Dbconnector t1 = new Dbconnector();
        //Connection con = t1.getconRead();

        //logger.info(res);

    } // main()

    public Connection getconWrite(String url,String user, String pwd) {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
         /* Get a connection to the database */
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/astro?" + "user=kasun&password=kasun");
            conn = DriverManager.getConnection(url + "user="+user+"&password="+pwd);
            logger.info("connection created " );
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage());
        }
        return conn;
    }

    public void closeConnection(Connection con){
        try{
        con.close();
            logger.info("DB connection closed");
        }catch(Exception e){
            logger.info(e.toString());
        }
        
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
                logger.info(CityName);
                count +=1;
            }*/

            //logger.info("Retrieved " + rowCount + " row(s).\n");

            //logger.info("Name\n--------");

            /* Retrieve the data from the result set */

        } catch (SQLException ex) {
            logger.info("SQLException: " + ex.getMessage());
            logger.info("SQLState: " + ex.getSQLState());
            logger.info("VendorError: " + ex.getErrorCode());
        }
           return rs;
    }
    
}
