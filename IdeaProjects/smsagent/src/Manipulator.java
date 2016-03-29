/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 24, 2010
 * Time: 9:40:37 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;

public class Manipulator implements Job {
    // Debuglogger for trace log
    //Translogger for trans log

    private static Logger DebugLogger = Logger.getLogger(Manipulator.class);
    private static Logger TransLogger = Logger.getLogger("Trans");

    private Connection connection;
    private ResultSet resultset;
    private static DbConnector Dbcon = new DbConnector();
    private static String unixTime;
    private static String unixLast;

    public void execute(JobExecutionContext JExeContext) throws JobExecutionException {
        DebugLogger.debug("------- smsagent-cron going to start ---" + new Date());
        connection = LogLoader.getDBConnection();
        //DebugLogger.debug("get database connection");
        DebugLogger.debug("getting unix time stamp");
        getLastCron(connection);
        getUnixTime(connection);
        Dbcon.writeData(connection, "INSERT INTO agent_status (status,time_info) VALUES('STARTED',"+unixTime+");");


        resultset = Dbcon.getData(connection, "select account,amount,account_details.mobile,message.message_body,trx_id from account_details,transaction,message where transaction.account=account_details.acc_number and transaction.type=message.id and time_stamp > " + unixLast + ";");
        DebugLogger.debug("data extracted from database");

        Manipulator manipulatorObj = new Manipulator();
        manipulatorObj.sendMessageCore(resultset);
        manipulatorObj =null;

        getUnixTime(connection);
        Dbcon.writeData(connection, "INSERT INTO agent_status (status,time_info) VALUES('COMPLETED','"+unixTime+"');");

        DebugLogger.debug("sms sending completed");

        DebugLogger.debug("------- smsagent-cron completed ---" + new Date());
    }

    private static void getUnixTime(Connection con) {

        String unixTimeSql = "SELECT UNIX_TIMESTAMP()";
        ResultSet rs1 = Dbcon.getData(con, unixTimeSql);

        try {
            while (rs1.next()) {
                unixTime = rs1.getString("UNIX_TIMESTAMP()");
                //System.out.println(unixTime);

            }
            rs1.close();
        } catch (Exception e) {
            DebugLogger.debug(e.toString());
        }

    }

    private void sendMessageCore(ResultSet rst) {
        int state = 0;
        try {
            while (rst.next()) {
                String Acc_number = rst.getString("account");
                double Amount = rst.getDouble("amount");
                int Mobile = rst.getInt("mobile");
                String Message_body = rst.getString("message_body");
                int Trx_id = rst.getInt("trx_id");
                SmsSender SS1 = new SmsSender();
                state = SS1.sendUrl(Message_body.concat("-").concat(Acc_number), Mobile);
                if (state == 1) {
                    TransLogger.info(Trx_id+"|"+Acc_number + "|" + Amount + "|" + Mobile + "|" + Message_body + "|sent");
                } else {
                    TransLogger.info(Trx_id+"|"+Acc_number + "|" + Amount + "|" + Mobile + "|" + Message_body + "|failed");
                }
                Acc_number = null;
                Message_body = null;
                Amount = 0;
                Mobile = 0;
                Trx_id = 0;
            }
            rst.close();

        } catch (Exception e) {
            DebugLogger.debug(e.toString());
        }
    }

    private static String getLastCron(Connection con) {

        String unixTimeSql = "SELECT max(time_info) from agent_status";
        ResultSet rs1 = Dbcon.getData(con, unixTimeSql);

        try {
            while (rs1.next()) {
                unixLast = rs1.getString("max(time_info)");
                System.out.println(unixLast);

            }
            rs1.close();
        } catch (Exception e) {
            DebugLogger.debug(e.toString());
        }
        return unixLast;
    }

}
