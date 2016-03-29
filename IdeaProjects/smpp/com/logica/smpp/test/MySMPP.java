package com.logica.smpp.test;

import java.io.*;
import java.util.Properties;

import com.logica.smpp.pdu.*;
import com.logica.smpp.*;
import com.logica.smpp.util.Queue;
import com.logica.smpp.debug.Debug;
import com.logica.smpp.debug.FileDebug;
import com.logica.smpp.debug.Event;
import com.logica.smpp.debug.FileEvent;

/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Feb 13, 2008
 * Time: 12:02:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class MySMPP {


    static final String dbgDir = "./";
    static Debug debug = new FileDebug(dbgDir,"test.dbg");
    static Event event = new FileEvent(dbgDir,"test.evt");

    static String propsFilePath = "./smpptest.cfg";

    static BufferedReader keyboard =
        new BufferedReader(new InputStreamReader(System.in));

    Properties prop;
    static Session session = null;
    Properties properties = new Properties();
    boolean bound = false;
    private boolean keepRunning = true;
    String ipAddress = null;
    int port = 0;
    String systemId = null;
    String password = null;
    String bindOption = "t";
    boolean asynchronous = false;
    AddressRange addressRange = new AddressRange();
    SMPPTestPDUEventListener pduListener = null;
    String systemType = "";
    String serviceType = "";
    Address sourceAddress = new Address();
    Address destAddress = new Address();
    String scheduleDeliveryTime = "";
    String validityPeriod = "";
    String shortMessage = "";
    int numberOfDestination = 1;
    String messageId     = "";
    byte esmClass               = 0;
    byte protocolId             = 0;
    byte priorityFlag           = 0;
    byte registeredDelivery     = 0;
    byte replaceIfPresentFlag   = 0;
    byte dataCoding           = 0;
    byte smDefaultMsgId         = 0;
    long receiveTimeout = Data.RECEIVE_BLOCKING;

    public static void main(String args []){
        MySMPP ms= new MySMPP();
        ms.confLoad(propsFilePath);
        

    }
   /* private void bind()
    {
        debug.enter(this, "SMPPTest.bind()");
        try {

            if (bound) {
                System.out.println("Already bound, unbind first.");
                return;
            }

            BindRequest request = null;
            BindResponse response = null;
            String syncMode = (asynchronous ? "a" : "s");

            // type of the session
            syncMode = getParam("Asynchronous/Synchronnous Session? (a/s)",
                                syncMode);
            if (syncMode.compareToIgnoreCase("a")==0) {
                asynchronous = true;
            } else if (syncMode.compareToIgnoreCase("s")==0) {
                asynchronous = false;
            } else {
                System.out.println("Invalid mode async/sync, expected a or s, got "
                                   + syncMode +". Operation canceled.");
                return;
            }

            // input values
            bindOption = getParam("Transmitter/Receiver/Transciever (t/r/tr)",
                                  bindOption);

            if  (bindOption.compareToIgnoreCase("t")==0) {
                request = new BindTransmitter();
            } else if (bindOption.compareToIgnoreCase("r")==0) {
                request = new BindReceiver();
            } else if (bindOption.compareToIgnoreCase("tr")==0) {
                request = new BindTransciever();
            } else {
                System.out.println("Invalid bind mode, expected t, r or tr, got " +
                                   bindOption + ". Operation canceled.");
                return;
            }

            ipAddress = getParam("IP address of SMSC", ipAddress);
            port = getParam("Port number", port);

            TCPIPConnection connection = new TCPIPConnection(ipAddress, port);
            connection.setReceiveTimeout(20*1000);
            session = new Session(connection);

            systemId = getParam("Your system ID", systemId);
            password = getParam("Your password", password);

            // set values
            request.setSystemId(systemId);
            request.setPassword(password);
            request.setSystemType(systemType);
            request.setInterfaceVersion((byte)0x34);
            request.setAddressRange(addressRange);

            // send the request
            System.out.println("Bind request " + request.debugString());
            if (asynchronous) {
                pduListener = new SMPPTestPDUEventListener(session);
                response = session.bind(request,pduListener);
            } else {
                response = session.bind(request);
            }
            System.out.println("Bind response " + response.debugString());
            if (response.getCommandStatus() == Data.ESME_ROK) {
                bound = true;
            }

        } catch (Exception e) {
            event.write(e,"");
            debug.write("Bind operation failed. " + e);
            System.out.println("Bind operation failed. " + e);
        } finally {
            debug.exit(this);
        }
    } */

    private int getParam(String prompt, int defaultValue)
        {
            return Integer.parseInt(getParam(prompt,Integer.toString(defaultValue)));
        }

    private byte getParam(String prompt, byte defaultValue)
    {
        return Byte.parseByte(getParam(prompt,Byte.toString(defaultValue)));
    }

    private String getParam(String prompt, String defaultValue)
    {
        String value = "";
        String promptFull = prompt;
        promptFull += defaultValue == null ? "" : " ["+defaultValue+"] ";
        System.out.print(promptFull);
        try {
            value = keyboard.readLine();
        } catch (IOException e) {
            event.write(e,"");
            debug.write("Got exception getting a param. "+e);
        }
        if (value.compareTo("") == 0) {
            return defaultValue;
        } else {
            return value;
        }
    }

    private void confLoad(String fileName){
      try
        {
        prop= new Properties();
        FileInputStream filein=new FileInputStream(fileName);
        prop.load(filein);
        }catch (FileNotFoundException e){ System.out.println(e.toString());}
         catch(IOException ex){System.out.println(ex.toString());}

        byte ton;
        byte npi;
        String addr;
        String bindMode;
        int rcvTimeout;
        String syncMode;

        ipAddress = properties.getProperty("ip-address");
        port = Integer.parseInt(properties.getProperty("port"));
        systemId = properties.getProperty("system-id");
        password = properties.getProperty("password");

        ton = getByteProperty("addr-ton",addressRange.getTon());
        npi = getByteProperty("addr-npi",addressRange.getNpi());
        addr = properties.getProperty("address-range",
                                      addressRange.getAddressRange());
        addressRange.setTon(ton);
        addressRange.setNpi(npi);
        try {
            addressRange.setAddressRange(addr);
        } catch (WrongLengthOfStringException e) {
            System.out.println("The length of address-range parameter is wrong.");
        }


        ton = getByteProperty("source-ton",sourceAddress.getTon());
        npi = getByteProperty("source-npi",sourceAddress.getNpi());
        addr = properties.getProperty("source-address",
                                      sourceAddress.getAddress());
        setAddressParameter("source-address",sourceAddress,ton,npi,addr);

        ton = getByteProperty("destination-ton",destAddress.getTon());
        npi = getByteProperty("destination-npi",destAddress.getNpi());
        addr = properties.getProperty("destination-address",
                                      destAddress.getAddress());
        setAddressParameter("destination-address",destAddress,ton,npi,addr);

        serviceType = properties.getProperty("service-type",serviceType);
        systemType = properties.getProperty("system-type",systemType);
        bindMode = properties.getProperty("bind-mode",bindOption);
        if (bindMode.equalsIgnoreCase("transmitter")) {
            bindMode = "t";
        } else if (bindMode.equalsIgnoreCase("receiver")) {
            bindMode = "r";
        } else if (bindMode.equalsIgnoreCase("transciever")) {
            bindMode = "tr";
        } else if (!bindMode.equalsIgnoreCase("t") &&
                   !bindMode.equalsIgnoreCase("r") &&
                   !bindMode.equalsIgnoreCase("tr")) {
            System.out.println("The value of bind-mode parameter in "+
                               "the configuration file "+fileName+" is wrong. "+
                               "Setting the default");
            bindMode = "t";
        }
        bindOption = bindMode;

        // receive timeout in the cfg file is in seconds, we need milliseconds
        // also conversion from -1 which indicates infinite blocking
        // in the cfg file to Data.RECEIVE_BLOCKING which indicates infinite
        // blocking in the library is needed.
        if (receiveTimeout == Data.RECEIVE_BLOCKING) {
            rcvTimeout = -1;
        } else {
            rcvTimeout = ((int)receiveTimeout)/1000;
        }
        rcvTimeout = getIntProperty("receive-timeout",rcvTimeout);
        if (rcvTimeout == -1) {
            receiveTimeout = Data.RECEIVE_BLOCKING;
        } else {
            receiveTimeout = rcvTimeout * 1000;
        }

        syncMode = properties.getProperty("sync-mode", (asynchronous ? "async":"sync"));
        if (syncMode.equalsIgnoreCase("sync")) {
            asynchronous = false;
        } else if (syncMode.equalsIgnoreCase("async")) {
            asynchronous = true;
        } else {
            asynchronous = false;
        }

        /*
        scheduleDeliveryTime
        validityPeriod
        shortMessage
        numberOfDestination
        messageId
        esmClass
        protocolId
        priorityFlag
        registeredDelivery
        replaceIfPresentFlag
        dataCoding
        smDefaultMsgId
        */

    }

        private byte getByteProperty(String propName, byte defaultValue)
    {
        return Byte.parseByte(properties.getProperty(propName,Byte.toString(defaultValue)));
    }

    /**
     * Gets a property and converts it into integer.
     */
    private int getIntProperty(String propName, int defaultValue)
    {
        return Integer.parseInt(properties.getProperty(propName,Integer.toString(defaultValue)));
    }

    private void setAddressParameter(String descr, Address address, byte ton, byte npi, String addr)
    {
        address.setTon(ton);
        address.setNpi(npi);
        try {
            address.setAddress(addr);
        } catch (WrongLengthOfStringException e) {
            System.out.println("The length of "+descr+" parameter is wrong.");
        }
    }


    private class SMPPTestPDUEventListener
    extends SmppObject
    implements ServerPDUEventListener
    {
        Session session;
        Queue requestEvents = new Queue();

        public SMPPTestPDUEventListener(Session session)
        {
            this.session = session;
        }

        public void handleEvent(ServerPDUEvent event)
        {
            PDU pdu = event.getPDU();
            if (pdu.isRequest()) {
                System.out.println("async request received, enqueuing "+
                                   pdu.debugString());
                synchronized (requestEvents) {
                    requestEvents.enqueue(event);
                    requestEvents.notify();
                }
            } else if (pdu.isResponse()) {
                System.out.println("async response received "+
                                   pdu.debugString());
            } else {
                System.out.println("pdu of unknown class (not request nor "+
                                   "response) received, discarding "+
                                   pdu.debugString());
            }
        }

    }
}
