import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 11/11/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mysql_connect_c3p {
    public static void main(String[] args) {
        BoneCP connectionPool = null;
        Connection connection = null;

        try {
            // load the database driver (make sure this is in your classpath!)
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            // setup the connection pool
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl("jdbc:mysql://10.82.100.61/kasun"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
            config.setUsername("kasun");
            config.setPassword("kasun123");
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(1);
            connectionPool = new BoneCP(config); // setup the connection pool

            connection = connectionPool.getConnection(); // fetch a connection

            if (connection != null) {
                System.out.println("Connection successful!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM t1"); // do something with the connection.
                while (rs.next()) {
                    System.out.println("Column One - " + rs.getString(1));
                    System.out.println("Column Two - " + rs.getString(2));
                }
            }
            Thread.sleep(30000);
            connectionPool.shutdown(); // shutdown connection pool.
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException iex) {
            iex.printStackTrace();

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
