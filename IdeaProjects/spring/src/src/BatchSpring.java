package src;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: May 27, 2011
 * Time: 3:35:47 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

class BatchSpring {
    public static void main(String args[]) {
        try {
            ApplicationContext ac = new ClassPathXmlApplicationContext("../batch_update.xml",BatchSpring.class);
            DataSource source = (DataSource) ac.getBean("dataSource");
            JdbcTemplate jt = new JdbcTemplate(source);
            jt.batchUpdate(new String[]{"update test set first_name = 'Great_K'", "delete from test where id =1"});
            System.out.println("Data updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
