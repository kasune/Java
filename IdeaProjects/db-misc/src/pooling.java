import com.mchange.v2.c3p0.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Sep 3, 2010
 * Time: 12:20:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class pooling {


    public static void main(String[] argv) {
        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
            cpds.setUser("kasun");
            cpds.setPassword("kasun");
            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);
            Connection con = cpds.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM foo");
            while (rs.next())
                System.out.println(rs.getString(1));

            DataSources.destroy( cpds );
            Thread.sleep(30000);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
        }
    }
}
