

import junit.framework.TestCase;

/**
 * Test Class
 */
public class INTester extends TestCase {
    HttpClient requestSender;
    String MSISDN;
    String PAY_AMOUNT;
    String PARTNER_ID;
    String PAY_ID;

    public INTester(String string) {
        super(string);
    }

    public void setUp() {

    }

    public void tearDown() {
//        Auditor.releaseLoggers();
    }

//    public void testCheckMSISDN() {
//        MSISDN = "7234564";
//        requestSender = new HttpClient(MSISDN);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }

//    public void testCheckEmptyCMSISDN() {
//        MSISDN = "";
//        requestSender = new HttpClient(MSISDN);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }

//    public void testCheckMSISDNChar() {
//        MSISDN = "test123";
//        requestSender = new HttpClient(MSISDN);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }

//    public void testCheckNonCeltellMSISDN() {
//        MSISDN = "0776529527";
//        requestSender = new HttpClient(MSISDN);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }

    /**
     * Less than 10 digits
     * More than 10 digits
     */
//    public void testCheckInvalidMSISDN() {
//        MSISDN = "07765295";
//        requestSender = new HttpClient(MSISDN);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }

//    public void testUpdateSubscriber() {
//        MSISDN = "7234564";
//        PAY_AMOUNT = "23";
//        PARTNER_ID = "2";
//        PAY_ID = "12";
//
//        requestSender = new HttpClient(MSISDN, PAY_AMOUNT, PARTNER_ID, PAY_ID);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//
//    }

//    public void testUpdateSubscriberEmptyMSISDN() {
//        MSISDN = "";
//        PAY_AMOUNT = "23.00";
//        PARTNER_ID = "2";
//        PAY_ID = "12";
//
//        requestSender = new HttpClient(MSISDN, PAY_AMOUNT, PARTNER_ID, PAY_ID);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//
//    }

//    public void testUpdateSubscriberEmptyAmount() {
//        MSISDN = "";
//        PAY_AMOUNT = " ";
//        PARTNER_ID = "2";
//        PAY_ID = "12";
//
//        requestSender = new HttpClient(MSISDN, PAY_AMOUNT, PARTNER_ID, PAY_ID);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }

//    public void testUpdateSubscriberInvalidAmount() {
//        MSISDN = "536536365";
//        PAY_AMOUNT = "-0.0 ";
//        PARTNER_ID = "2";
//        PAY_ID = "12";
//
//        requestSender = new HttpClient(MSISDN, PAY_AMOUNT, PARTNER_ID, PAY_ID);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }

//    public void testUpdateSubscriberInvalidPartnerId() {
//        MSISDN = "536536365";
//        PAY_AMOUNT = "120.0 ";
//        PARTNER_ID = "00000";
//        PAY_ID = "12";
//
//        requestSender = new HttpClient(MSISDN, PAY_AMOUNT, PARTNER_ID, PAY_ID);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }

//    public void testUpdateSubscriberInvalidPayIdd() {
//        MSISDN = "536536365";
//        PAY_AMOUNT = "120.0 ";
//        PARTNER_ID = "4567";
//        PAY_ID = "00";
//
//        requestSender = new HttpClient(MSISDN, PAY_AMOUNT, PARTNER_ID, PAY_ID);
//        try {
//            requestSender.sendRequestToIN();
//        } catch (Exception e) {
//            fail(e.toString());
//        }
//
//    }
    /**
     * //todo
     * Invalid Id
     * Dupliacate Pay Id
     * Empty Id
     * With Strings
     */
}
