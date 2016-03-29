/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jan 17, 2007
 * Time: 10:46:36 AM
 * To change this template use File | Settings | File Templates.
 */

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class ReadWrite {

    public static void main(String args[]) {
        try {
            //String messageOut = "E:\\beyondm-back\\IdeaProjects\\kasun_property1\\conf\\message.out";
            String messageOut = args[0];
            int maxCount = Integer.parseInt(args[1]);
            ReadWrite rw = new ReadWrite();
            int state = rw.limitSnmp(messageOut,maxCount);
            System.out.println(state);
        }
        catch (Exception e) {
            e.printStackTrace();

        }


    }

    private synchronized int limitSnmp(String messageOut, int maxCount) throws IOException {
        int status;

        Properties prop = new Properties();

        FileInputStream fil = new FileInputStream(messageOut);

        prop.load(fil);
        fil.close();

        Writer output = null;
        File file = new File(messageOut);
        output = new BufferedWriter(new FileWriter(file));

        int filedate = Integer.parseInt(prop.getProperty("LAST_DATE"));
        int snmpCount = Integer.parseInt(prop.getProperty("SNMP_COUNT"));

        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf =
                new SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance(); // today
        //System.out.println("Today is " + sdf.format(c1.getTime()));
        int curDate = Integer.parseInt(sdf.format(c1.getTime()));


        if (filedate == curDate) {
            if (snmpCount < maxCount) {

                output.write("LAST_DATE=" + curDate);
                output.write("\n");
                output.write("SNMP_COUNT=" + (snmpCount + 1));

                //System.out.println("Your file has been written");
                status = 1;
            } else {
                output.write("LAST_DATE=" + curDate);
                output.write("\n");
                output.write("SNMP_COUNT=" + snmpCount);
                status = 0;
            }

        } else {

            output.write("LAST_DATE=" + curDate);
            output.write("\n");
            output.write("SNMP_COUNT=" + 1);

            status = 1;

        }
        output.close();
        return status;

    }


}
