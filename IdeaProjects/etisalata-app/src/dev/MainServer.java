package dev;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 28, 2011
 * Time: 6:06:44 PM
 * To change this template use File | Settings | File Templates.
 */



import java.util.Date;
import java.util.Calendar;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Connection;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;

public class MainServer implements Job {
    // Debuglogger for trace log
    //Translogger for trans log
    Dbconnector dbc ;
    Registration reg;
    Connection connection;
    
    ResultSet resultSet;
    String msisdns[];
    String  message;
    String getDailyMessage;
    String stateworking;
    String statecomplete;

    int msg_ID;

    private static Logger logger = Logger.getLogger(MainServer.class);
    private static Logger TransLogger = Logger.getLogger("Trans");


    public void execute(JobExecutionContext JExeContext) throws JobExecutionException {
        logger.info("------- astro-cron going to start ---" + new Date());

        logger.info("test");

       logger.info("started "+System.currentTimeMillis());
        getSubsData();


        logger.info("------- astro-cron completed ---" + new Date());
    }

    private void getSubsData(){
        reg = new Registration();
        dbc = new Dbconnector();
        connection = dbc.getconWrite(PropReader.database_url, PropReader.database_user, PropReader.database_pwd);


        //getDailyMessage = "SELECT * FROM monday_alert WHERE id =(SELECT MAX(id) FROM monday_alert WHERE status='pending'); ";
        getDailySQL();
        String getmsisdns = "SELECT * FROM subscribers;";
        //String stateworking = "UPDATE monday_alert SET status=\"working\" WHERE id=(SELECT MIN(id) FROM sunday_alert WHERE status='pending');";
        //String statecomplete = "UPDATE monday_alert SET status=\"completed\" WHERE id=(SELECT MIN(id) FROM sunday_alert WHERE status='working');";
        //MainServer ms = new MainServer();
        logger.info("update status to working");

        getSubscribers(getmsisdns, connection);
        int statusMessage = getMessage(getDailyMessage, connection);

        if (statusMessage == 0){

        setStatusUpdate();
        logger.info(stateworking);
        updateData(stateworking);

        sendMessage();

        logger.info(statecomplete);
        updateData(statecomplete);

    }   else{
            logger.info("No message sending...");

        }

        dbc.closeConnection(connection);
        //message = null;
        //msisdns = null;

    }

    public void updateData(String updateSQL){
        try{
    Statement stmp = connection.createStatement();
    stmp.executeUpdate(updateSQL);
        }catch(Exception ex){
            logger.info(ex.toString());
        }
    }

    private void setStatusUpdate(){
        Calendar calendar = Calendar.getInstance();

        if(calendar.get(Calendar.DAY_OF_WEEK)==1){
            stateworking = "UPDATE sunday_alert SET status=\"working\" WHERE id="+msg_ID+";";
            statecomplete = "UPDATE sunday_alert SET status=\"completed\" WHERE id="+msg_ID+";";

        }if(calendar.get(Calendar.DAY_OF_WEEK)==2){
            stateworking = "UPDATE monday_alert SET status=\"working\" WHERE id="+msg_ID+";";
            statecomplete = "UPDATE monday_alert SET status=\"completed\" WHERE id="+msg_ID+";";

        }if(calendar.get(Calendar.DAY_OF_WEEK)==3){
            stateworking = "UPDATE tuesday_alert SET status=\"working\" WHERE id="+msg_ID+";";
            statecomplete = "UPDATE tuesday_alert SET status=\"completed\" WHERE id="+msg_ID+";";

        }if(calendar.get(Calendar.DAY_OF_WEEK)==4){
            stateworking = "UPDATE wednesday_alert SET status=\"working\" WHERE id="+msg_ID+";";
            statecomplete = "UPDATE wednesday_alert SET status=\"completed\" WHERE id="+msg_ID+";";

        }if(calendar.get(Calendar.DAY_OF_WEEK)==5){
            stateworking = "UPDATE thursday_alert SET status=\"working\" WHERE id="+msg_ID+";";
            statecomplete = "UPDATE thursday_alert SET status=\"completed\" WHERE id="+msg_ID+";";

        }if(calendar.get(Calendar.DAY_OF_WEEK)==6){
            stateworking = "UPDATE friday_alert SET status=\"working\" WHERE id="+msg_ID+";";
            statecomplete = "UPDATE friday_alert SET status=\"completed\" WHERE id="+msg_ID+";";

        }if(calendar.get(Calendar.DAY_OF_WEEK)==7){
            stateworking = "UPDATE saturday_alert SET status=\"working\" WHERE id="+msg_ID+";";
            statecomplete = "UPDATE saturday_alert SET status=\"completed\" WHERE id="+msg_ID+";";
        }
    }

    private void getDailySQL(){
        Calendar calendar = Calendar.getInstance();
        logger.info(calendar.get(Calendar.DAY_OF_WEEK));

        if(calendar.get(Calendar.DAY_OF_WEEK)==1){
            getDailyMessage = "SELECT * FROM sunday_alert WHERE id =(SELECT MIN(id) FROM sunday_alert WHERE status='pending'); ";
        }if(calendar.get(Calendar.DAY_OF_WEEK)==2){
            getDailyMessage = "SELECT * FROM monday_alert WHERE id =(SELECT MIN(id) FROM monday_alert WHERE status='pending'); ";
        }if(calendar.get(Calendar.DAY_OF_WEEK)==3){
            getDailyMessage = "SELECT * FROM tuesday_alert WHERE id =(SELECT MIN(id) FROM tuesday_alert WHERE status='pending'); ";
        }if(calendar.get(Calendar.DAY_OF_WEEK)==4){
            getDailyMessage = "SELECT * FROM wednesday_alert WHERE id =(SELECT MIN(id) FROM wednesday_alert WHERE status='pending'); ";
        }if(calendar.get(Calendar.DAY_OF_WEEK)==5){
            getDailyMessage = "SELECT * FROM thursday_alert WHERE id =(SELECT MIN(id) FROM thursday_alert WHERE status='pending'); ";
        }if(calendar.get(Calendar.DAY_OF_WEEK)==6){
            getDailyMessage = "SELECT * FROM friday_alert WHERE id =(SELECT MIN(id) FROM friday_alert WHERE status='pending'); ";
        }if(calendar.get(Calendar.DAY_OF_WEEK)==7){
            getDailyMessage = "SELECT * FROM saturday_alert WHERE id =(SELECT MIN(id) FROM saturday_alert WHERE status='pending'); ";
        }

    }

    private void getSubscribers(String getSql, Connection con){
       int count =0 ;
       try{
            logger.info(getSql);
            resultSet = dbc.getData(connection,getSql);
            logger.info("msisdn list retrieved");
            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();
            msisdns = new String[rowCount];

           while (resultSet.next()) {
                String msisdn = resultSet.getString("msisdn");
                //logger.info(msisdn);
                msisdns [count] = msisdn;
                logger.info(msisdn);
                count+=1;
            }
           logger.info("MSISDN count " +count);
           resultSet = null;

        }catch(Exception sqle){
            logger.info(sqle.toString());
            sqle.printStackTrace();
        }
    }

    private int getMessage(String getSql, Connection con){
          int status = 1 ;
        try{
            logger.info(getSql);
            resultSet = dbc.getData(connection,getSql);
            resultSet.next();
            message = resultSet.getString("message");
            msg_ID = resultSet.getInt("id");
            logger.info("message retrieved "+message);
            logger.info("ID retrieved "+msg_ID);
            resultSet = null;
            status= 0;
        }catch(SQLException sqle){
            logger.info(sqle.toString());
            status = 1;
        }
       return status;
    }

    private void sendMessage(){
        for(int count = 0; count<msisdns.length;count++){
            reg.sendReply(PropReader.url,PropReader.appid,PropReader.password,msisdns[count],message);
            //logger.info(message);
            logger.info(message +" - message sent to - "+msisdns[count]);
        }
    }

}
