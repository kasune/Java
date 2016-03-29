/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 10/7/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReference;

public class ReadXML {

    public static void main(String argv[]) {


        try {
            String csv = argv[1];
            //String csv = "D:\\report.csv";
            final PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(csv)));
            printHeader(out);


            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            AtomicReference<DefaultHandler> handler = new AtomicReference<DefaultHandler>(new DefaultHandler() {

                boolean bfname = false;
                boolean blname = false;

                String temp = "";
                String last = "";
                String prev = "";

                //These two will be ignore in result sheet
                String str1 = "Date";
                String str2 = "GSM";
                String startPoint = "GLR201";


                boolean writeEnable = false;
                int counter = 0;
                //first 3 lines will be ignored
                int skipno = 3;

                //use for operator and country store
                String operator = "";
                String country = "";

                String opFlag = "Fallback";
                //in case if you need to add date separately
                boolean dateFlag = false;
                String dateT = "";


                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) throws SAXException {

                    //only checking elements with Row tag
                    if (qName.equalsIgnoreCase("Row")) {
                        //System.out.println("Start Element :" + qName);
                        last = last.concat(temp);
                        temp = "";
                    }
                     //checking the Data tag and content
                    if (qName.equalsIgnoreCase("Data")) {
                        blname = true;
                    }


                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("Row")) {
                        //System.out.println("End Element :" + qName);


                    }
                    if (qName.equalsIgnoreCase("Data")) {
                        //System.out.println("End Element :" + qName);
                    }

                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    try {

                        //if Data tag true
                        if (blname) {
                            String n = new String(ch, start, length);
                            temp = temp.concat(",").concat(n);
                            boolean next = false;


                            StringTokenizer stTemp = new StringTokenizer(temp, ",");
                            // if(!(temp.contains(str1)) && !(temp.contains(str2)) && (st.countTokens() ==1)){
                            StringTokenizer stTempdate = new StringTokenizer(temp, ",");

                            //when str2(Date) is in the previous line, current line has the datetime
                            if (prev.contains(str2)) {
                                //datetime is stored
                                dateT = stTempdate.nextToken();
                                dateFlag = true;

                            }
                            //only when no of tokens is 1, not taking the duplicates
                            if (stTemp.countTokens() == 1) {
                                //System.out.println(temp);

                                //ignore the ignored the strings
                                if (!(temp.contains(str1)) && !(prev.contains(str1)) && !(prev.contains(str2)) && prev.contains(",")) {
                                    counter++;
                                    // first skipno (3) lines will be ignored
                                    if (counter > skipno) {
                                        StringTokenizer stPrev = new StringTokenizer(prev, ",");
                                        //when no of tokens is equal to 1 in both previous and current lines
                                        if (stPrev.countTokens() == 1) {
                                            operator = temp;
                                            country = prev;
                                            System.out.println(temp + " -- " + prev);
                                        } else {
                                       /* if(stTemp.countTokens() == 1){
                                            operator = temp;
                                        }*/
                                            //if is for lines with date and else for without it. So need to concatenate datetime again
                                            if (dateFlag) {


                                                String parts[] = country.split(",");
                                                String ncountry = parts[1];

                                                //split the lines in order to *100 to convert values to percentage
                                                String percValues[] = prev.split(",");
                                                percValues[7] = roundTwoDecimal(percValues[7])  ;
                                                percValues[8] = roundTwoDecimal(percValues[8])  ;
                                                percValues[13] = roundTwoDecimal(percValues[13])  ;
                                                percValues[14] = roundTwoDecimal(percValues[14])  ;

                                                percValues[1] = convertDate(percValues[1]);
                                                //System.out.println(prev);
                                                prev = percValues[0].concat(",").concat(percValues[1]).concat(",").concat(percValues[2]).concat(",").concat(percValues[3]).concat(",").concat(percValues[4]).concat(",").concat(percValues[5]).concat(",").concat(percValues[6]).concat(",").concat(percValues[7]).concat(",").concat(percValues[8]).concat(",").concat(percValues[9]).concat(",").concat(percValues[10]).concat(",").concat(percValues[11]).concat(",").concat(percValues[12]).concat(",").concat(percValues[13]).concat(",").concat(percValues[14]);
                                                /*System.out.println(percValues[0]);System.out.println(percValues[1]);System.out.println(percValues[2]);
                                                System.out.println(percValues[3]);System.out.println(percValues[4]);System.out.println(percValues[5]);
                                                System.out.println(percValues[6]);System.out.println(percValues[7]);System.out.println(percValues[8]);
                                                System.out.println(percValues[9]);System.out.println(percValues[10]);System.out.println(percValues[11]);
                                                System.out.println(percValues[12]);System.out.println(percValues[13]); System.out.println(percValues[14]); */
                                                //System.out.println(prev);
                                                // concatenate country , operator , and the read line  . dateTime is already there as this is the first line for this operator
                                                String toPrint = ncountry.concat(operator).concat(prev);
                                                //toPrint = toPrint.replace("," , " , " );

                                                out.println(toPrint);

                                                System.out.println(toPrint);
                                                dateFlag = false;
                                            } else {

                                                String parts[] = country.split(",");
                                                String ncountry = parts[1];

                                                String percValues[] = prev.split(",");
                                                percValues[6] = roundTwoDecimal(percValues[6])  ;
                                                percValues[7] = roundTwoDecimal(percValues[7])  ;
                                                percValues[12] = roundTwoDecimal(percValues[12])  ;
                                                percValues[13] = roundTwoDecimal(percValues[13])  ;

                                                dateT = convertDate(dateT);
                                                //System.out.println(prev);
                                                prev = percValues[0].concat(",").concat(percValues[1]).concat(",").concat(percValues[2]).concat(",").concat(percValues[3]).concat(",").concat(percValues[4]).concat(",").concat(percValues[5]).concat(",").concat(percValues[6]).concat(",").concat(percValues[7]).concat(",").concat(percValues[8]).concat(",").concat(percValues[9]).concat(",").concat(percValues[10]).concat(",").concat(percValues[11]).concat(",").concat(percValues[12]).concat(",").concat(percValues[13]);
                                                // concatenate country , operator , dateTime and the read line
                                                String toPrint = ncountry.concat(operator).concat(",").concat(dateT).concat(prev);
                                                //toPrint = toPrint.replace("," , " , " );
                                                /*System.out.println(percValues[0]);System.out.println(percValues[1]);System.out.println(percValues[2]);
                                                System.out.println(percValues[3]);System.out.println(percValues[4]);System.out.println(percValues[5]);
                                                System.out.println(percValues[6]);System.out.println(percValues[7]);System.out.println(percValues[8]);
                                                System.out.println(percValues[9]);System.out.println(percValues[10]);System.out.println(percValues[11]);
                                                System.out.println(percValues[12]);System.out.println(percValues[13]);*/
                                                out.println(toPrint);

                                                System.out.println(toPrint);
                                            }
                                        }
                                    }
                                }
                                ///when opFlag(Fallback) is on previous line current line has the operator value
                                if (prev.contains(opFlag)) {
                                    operator = temp;
                                }


                            }

                            //setting previous in order to use on next cycle
                            prev = temp;
                            blname = false;
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }


            });

            saxParser.parse(argv[0], handler.get());
            //saxParser.parse("D:\\GLR201_Reports\\GLR201_20131006.xls", handler.get());
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // header of the outfile
    static boolean printHeader(PrintWriter out) {
        boolean status = true;
        try {
            out.println("Country," +
                    "Network," +
                    "Date," +
                    "Transaction type," +
                    "GSM UL Attempts," +
                    "GPRS UL Attempts," +
                    "GSM UL Success," +
                    "GPRS UL Success," +
                    "GSM UL Success (%)," +
                    "GPRS UL Success (%)," +
                    "GSM Unique Roamers UL Attempts," +
                    "GPRS Unique Roamers UL Attempts," +
                    "GSM Unique Roamers UL Success," +
                    "GPRS Unique Roamers UL Success," +
                    "GSM Unique Roamers UL Success Rate(%)," +
                    "GPRS Unique Roamers UL Success Rate(%),");

        } catch (Exception ex) {
            status = false;
        }
        return status;
    }
    // number like 35.2121212121 covert to to 35.21
    static String roundTwoDecimal(String in){
        Double d    = Double.parseDouble(in) * 100.0;
        d = Math.round(d * 100.0) / 100.0 ;
        in = String.valueOf(d);

        return in;

    }

    // 2013-10-06T00:00:00.000 will convert to 2013-10-06
    static String convertDate(String in){
        String datePart [] = in.split("T");
        return  datePart[0];

    }

}