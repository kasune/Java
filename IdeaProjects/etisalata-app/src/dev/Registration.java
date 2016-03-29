package dev;

import hsenidmobile.sdp.rest.servletbase.MchoiceAventuraSmsSender;
import hsenidmobile.sdp.rest.servletbase.MchoiceAventuraMessagingException;
import hsenidmobile.sdp.rest.servletbase.MchoiceAventuraResponse;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Dec 4, 2011
 * Time: 9:00:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Registration {
      MchoiceAventuraResponse mres ;
    private static Logger logger = Logger.getLogger(Registration.class);

    public int doRegister(String msisdn, Connection connection)throws SQLException{
        logger.info("Inserting msisdn to the list ");


        String insertSQL = "INSERT INTO subscribers (msisdn,inserttime,astro_sign,extra) values (?,NOW(),?,?)";
        int response = 0;

            PreparedStatement pstmp = connection.prepareStatement(insertSQL);
            pstmp.setString(1,msisdn);
            pstmp.setString(2,"");
            pstmp.setString(3,"");
            response = pstmp.executeUpdate();
            logger.info("msisdn inserted to the list");

       return response; 
    }

     public int removeRegister(String msisdn, Connection connection){
        logger.info("Deleting msisdn from the list ");


        String insertSQL = "DELETE FROM subscribers WHERE msisdn=?";
        int response = 0;
        try{
            PreparedStatement pstmp = connection.prepareStatement(insertSQL);
            pstmp.setString(1,msisdn);

            response = pstmp.executeUpdate();
            logger.info("msisdn removed from the list");
        }catch(SQLException sqle){
            logger.info(sqle.toString());
        }
       return response;
    }

    public MchoiceAventuraResponse  sendReply(String url,String appid,String pwd, String msisdn,String sendMsg){


        try {
             logger.info("Message sending to SDP ="+url +"-" +appid + "-"+pwd+"-"+msisdn);
             MchoiceAventuraSmsSender smsSender = new MchoiceAventuraSmsSender(new URL(url), appid, pwd);
             mres = smsSender.sendMessage(sendMsg, msisdn);

         }
            catch (MchoiceAventuraMessagingException ex) {
                logger.info(ex.toString());
            } catch (MalformedURLException mex) {
                logger.info(mex.toString());
            }
        return mres;
    }

    public boolean registerZodiac(String msisdn,String zodiac, String zodiac_list, Connection connection)throws SQLException{
        int response=0;
        logger.info(msisdn +"-number, "+ zodiac + "-zodiac ");

        try{
       if(zodiac_list.contains(zodiac)){

           String updateSQL = "UPDATE subscribers SET astro_sign=? where msisdn=?" ;
           PreparedStatement pstmp = connection.prepareStatement(updateSQL);
           pstmp.setString(1,zodiac);
           pstmp.setString(2,msisdn);
           response = pstmp.executeUpdate();
           logger.info(updateSQL);
           //logger.info("msisdn updated with zodiac sign "+msisdn+"-"+zodiac);
           return true;
       }
        }catch(SQLException sqle){
           logger.info(sqle.toString());
            return false;
        }
        if(response==1){return true;}
        else{
            return false;
        }
    }
}
