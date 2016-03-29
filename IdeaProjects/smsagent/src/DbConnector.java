/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 24, 2010
 * Time: 10:43:23 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DbConnector {
    // Debuglogger for trace log
    //Translogger for trans log
    private static Logger DebugLogger = Logger.getLogger(DbConnector.class);
    private static Logger TransLogger = Logger.getLogger("Trans");

    String Names[];
    public static void main(String[] args) {

     DbConnector d = new DbConnector();
     Connection c = d.getconRead("localhost",3306,"smsagent","kasun","kasun");
        System.out.println("hmmm");
        //long unixTime = System.currentTimeMillis() / 1000L;

     ResultSet r = d.getData(c,"select UNIX_TIMESTAMP()");
        try{
     while (r.next()) {
                String CityName = r.getString("UNIX_TIMESTAMP()");
                System.out.println(CityName);

            }
        }catch(Exception e){

        }

    } // main()

    public void closeConnection(Connection con){
        try{
        con.close();
        }catch(Exception ex){
           DebugLogger.debug("Exception: " + ex.getMessage()); 
        }
    }

    public Connection getconWrite() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            /* Get a connection to the database to write */
            conn = DriverManager.getConnection("jdbc:mysql://"+LogLoader.database_host+":"+LogLoader.database_port+"/"+LogLoader.database_schema+"?" + "user="+LogLoader.database_user+"&password="+LogLoader.database_password+"");
            DebugLogger.debug("connection created");
        } catch (Exception e) {
            DebugLogger.debug("Exception: " + e.getMessage());
        }
        return conn;
    }

    public Connection getconRead(String host, int port, String schema, String user, String password) {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            /* Get a connection to the database to read */
            conn = DriverManager.getConnection("jdbc:mysql://"+LogLoader.database_host+":"+LogLoader.database_port+"/"+LogLoader.database_schema+"?" + "user="+LogLoader.database_user+"&password="+LogLoader.database_password+"");
            //conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+schema+"?" + "user="+user+"&password="+password+"");

        } catch (Exception e) {
            DebugLogger.debug("Exception: " + e.getMessage());
            //System.out.println(e.getMessage());
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
            //rs.last();
            //int rowCount = rs.getRow();
            //rs.beforeFirst();

           /* while (rs.next()) {
                String CityName = rs.getString("Name");
                System.out.println(CityName);
                count +=1;
            }*/

            //System.out.println("Retrieved " + rowCount + " row(s).\n");

            //System.out.println("Name\n--------");

            /* Retrieve the data from the result set */

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

            DebugLogger.debug("SQLException: " + ex.getMessage());
            DebugLogger.debug("SQLState: " + ex.getSQLState());
            DebugLogger.debug("VendorError: " + ex.getErrorCode());
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
            DebugLogger.debug("SQLException: " + se.getMessage());
        }
        return res;
    }

} // MySQLclient

