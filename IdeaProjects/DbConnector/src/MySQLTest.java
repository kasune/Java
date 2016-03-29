/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 8, 2011
 * Time: 2:04:53 PM
 * To change this template use File | Settings | File Templates.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.io.*;

public class MySQLTest {


    public static void main(String[] args) throws Exception, IOException, SQLException {
        MySQLTest mt1 = new MySQLTest();

        DbCon t1 = new DbCon();
        Connection conn = t1.getconRead();
        System.out.println("con created");
        mt1.writeToDB(conn);
        conn = t1.getconRead();
        mt1.writeToFile(conn);
        mt1.printList();
    }

    void printList(){
        int writeFileCount =0 ;
        File dir = new File("F:\\study\\java\\IdeaProjects\\DbConnector");
        System.out.println("Going to read the directory files");
        System.out.println("---------------------------------");
        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (int i = 0; i < children.length; i++) {
                // Get filename of file or directory
                String filename = children[i];
                //System.out.println(filename +"--------"+"test"+i+".jpg");
                if (filename.equalsIgnoreCase("test"+writeFileCount+".jpg")){
                    System.out.println("Matched file -------"+filename);
                    writeFileCount++;
                }
            }
}

    }

    void writeToDB(Connection conn) {
        FileInputStream fis = null;
        PreparedStatement ps = null;

        String INSERT_PICTURE = "INSERT INTO test(picture_blob) VALUES(?)";

        try {
            conn.setAutoCommit(false);
            File file = new File("818C.jpg");
            fis = new FileInputStream(file);

            ps = conn.prepareStatement(INSERT_PICTURE);


            //ps.setInt(1, 5);
            ps.setBinaryStream(1, fis, (int) file.length());
            ps.executeUpdate();
            System.out.println("INSERT Query executed");
            conn.commit();

            ps.close();
            fis.close();
            conn.close();
        } catch (SQLException sqle) {
             System.out.println("INSERT Query failed");
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
        finally {

        }
    }


    void writeToFile(Connection connection) {
             int count = 0;

        FileOutputStream fos = null;
        InputStream is = null;
        String selectQuery = "SELECT picture_blob FROM test";
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            System.out.println("SELET Query executed");
            System.out.println("Going to write the files to the disk");
            System.out.println("---------------------------------");
            while (rs.next()) {
                String fileName = "test" + count + ".jpg";
                File image = new File(fileName);
                count++;
                fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                is = rs.getBinaryStream(1);
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                System.out.println("File " + fileName + " written to the disk");
                fos.close();
            }
            count = 0;
            connection.close();
            System.out.println("---------------------------------");

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {

        }
    }

}
