package dev;
/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 28, 2011
 * Time: 3:03:03 PM
 * To change this template use File | Settings | File Templates.
 */

import hsenidmobile.sdp.rest.servletbase.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class MOListener extends MchoiceAventuraSmsMoServlet {

    String url;
    String appid;
    String pwd;
    String dbUrl;
    String dbUser;
    String dbPwd;

    Connection connection;
    Registration reg;
    PropReader prop;
    CronScheduler example;

    MchoiceAventuraResponse mresponse;

    String reg_resp_success;
    String reg_resp_failed;
    String unreg_resp_success;
    String unreg_resp_failed;
    String zodiac_signs;
    String zodiac_register_success;
    String zodiac_register_fail;
    String app_keyword;

    private static Logger logger = Logger.getLogger(MOListener.class);
    @Override
    protected void onMessage(MchoiceAventuraSmsMessage msg) {

        String message = msg.getMessage();
        String msisdn = msg.getAddress();
        logger.debug("Message : " + message);
        logger.info("Message : " + message);
        logger.info("Mobile: " + msisdn);

        StringTokenizer msgTokenizer = new StringTokenizer(message);
        String regMessage = msgTokenizer.nextToken();
        String zodiac = msgTokenizer.nextToken();
        Dbconnector dbc = new Dbconnector();
        connection = dbc.getconWrite(dbUrl, dbUser, dbPwd);
        try {
            processMsg(regMessage, msisdn, zodiac, message);
        } catch (SQLException sqle) {
            logger.info(sqle.toString());
            logger.info(sqle.toString());
            dbc = new Dbconnector();
            connection = dbc.getconWrite(dbUrl, dbUser, dbPwd);
            try {
                processMsg(regMessage, msisdn, zodiac, message);
            } catch (Exception sqlex) {
                logger.info(sqlex.toString());
            }
        }
        catch(Exception ex){
            logger.info(ex.toString());
            logger.info(ex.toString());
        }
    }

    private void processMsg(String regMessage, String msisdn, String zodiac, String message) throws SQLException,Exception {

        if (regMessage.equalsIgnoreCase("reg")) {
            logger.info("register new user to " + app_keyword + " " + msisdn);
            int res = reg.doRegister(msisdn, connection);

            if (res == 1) {
                logger.info("user added to the table-" + msisdn);
                String regResponse = reg_resp_success;
                mresponse = reg.sendReply(url, appid, pwd, msisdn, regResponse);
            } else {
                logger.info("Error occured. Subscription failed. ");
                String regResponse = reg_resp_failed;
                mresponse = reg.sendReply(url, appid, pwd, msisdn, regResponse);
            }
        } else if (regMessage.equalsIgnoreCase("unreg")) {
            logger.info("unsubscribe user from " + app_keyword + " " + msisdn);
            int res = reg.removeRegister(msisdn, connection);

            if (res == 1) {
                logger.info("user removed to the table-" + msisdn);
                String regResponse = unreg_resp_success.concat(app_keyword);
                mresponse = reg.sendReply(url, appid, pwd, msisdn, regResponse);
            } else {
                logger.info("Error occured. unsubscription failed.");
                String regResponse = unreg_resp_failed;
                mresponse = reg.sendReply(url, appid, pwd, msisdn, regResponse);
            }
        } else {
                logger.info(msisdn + zodiac + zodiac_signs);
            if (reg.registerZodiac(msisdn, zodiac, zodiac_signs, connection)) {
                logger.info(msisdn + zodiac + zodiac_signs);
                logger.info("msidn updated with zodiac sign");
                mresponse = reg.sendReply(url, appid, pwd, msisdn, zodiac_register_success);
            } else {
                logger.info("Message going to ignore....[" + message + "]-" + msisdn);
                mresponse = reg.sendReply(url, appid, pwd, msisdn, zodiac_register_fail);
            }
        }


        logger.info(mresponse.toString());

    }

    public void init(ServletConfig config) throws ServletException {
        logger.info("init called...");
        super.init(config);
        this.setProp();
        //logger.info(reg_resp_success);
        //logger.info(reg_resp_failed);
        //logger.info(unreg_resp_success);
        //logger.info(unreg_resp_failed);
        reg = new Registration();
        //Dbconnector dbc = new Dbconnector();
        //connection = dbc.getconWrite(dbUrl, dbUser, dbPwd);
        //logger.info("Database connection initialized....");
        prop.createScheduleJobs();
    }

    private void setProp() {
        prop = new PropReader();
        prop.setLog4j();
        logger.debug("configuration loaded....");
        prop.setAttr();
        url = prop.url;
        appid = prop.appid;
        pwd = prop.password;
        dbUrl = prop.database_url;
        dbUser = prop.database_user;
        dbPwd = prop.database_pwd;

        reg_resp_success = prop.reg_resp_success;
        reg_resp_failed = prop.reg_resp_failed;
        unreg_resp_success = prop.unreg_resp_success;
        unreg_resp_failed = prop.unreg_resp_failed;

        zodiac_signs = prop.zodiac_signs;
        zodiac_register_success = prop.zodiac_register_success;
        zodiac_register_fail = prop.zodiac_register_fail;

        app_keyword = prop.app_keyword;


    }
}
