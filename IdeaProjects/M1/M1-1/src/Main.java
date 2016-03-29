/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 10/7/13
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Main {

    private String[][] csvHeaders;
    private Properties properties;

    public static void main(String[] args) throws Exception {
        String inputFile ="C:\\Users\\emkasun\\Desktop\\IPN_Reports\\IPN108-20130930-110021_0.858-9845.xls";
        final String  config_file= "C:\\Users\\emkasun\\IdeaProjects\\M1-1\\src\\config.conf";
        //System.out.println("Reading file[" + inputFile + "] property file[" + args[0] + "]");
        System.out.println("paths hard coded");
        Main main = new Main();
        final Properties prop = new Properties();
        //prop.load(new FileInputStream(args[0]));
        prop.load(new FileInputStream(config_file));
                main.properties = prop;
        main.csvHeaders();

        main.read(inputFile, prop.getProperty("csv.file.creation.location"));
        final File file = new File(inputFile);
        file.renameTo(new File(prop.getProperty("archive.location") + "/" + file.getName()));
        System.out.println("File read, decoded and moved successfully");
    }

    private void csvHeaders() {
        final String[] split = properties.getProperty("csv.header.information").split(",");
        csvHeaders = new String[split.length][2];
        for (int i = 0; i < split.length; i++) {
            final String[] val = split[i].split(":");
            csvHeaders[i][0] = val[0];
            csvHeaders[i][1] = val[1];
        }
    }

    public void read(String inputFile, String fileWriteLocation) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(inputFile));
        final String dateCell = findDate(doc);
        final BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(fileWriteLocation + "\\" + properties.getProperty("file.prefix.value") + dateCell + ".csv")));
        writeHeader(outputStream);
        writeBody(doc, outputStream, dateCell);
        outputStream.flush();
        outputStream.close();
    }

    private String findDate(Document doc) throws Exception {
        XPathExpression expr = XPathFactory.newInstance().newXPath().compile(properties.getProperty("date.pull.command"));
        //String text = ((String) expr.evaluate(doc, XPathConstants.STRING)).trim();
        String text = "Oct 01, 2013";
        System.out.println("Date value [" + text + "] ");

        final Date date = new SimpleDateFormat("MMM dd, yyyy").parse(text);
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    private void writeBody(Document document, BufferedOutputStream outputStream, String dateCell) throws IOException, XPathExpressionException, ParseException {
        Node node = findStartPoint(document);
        final SimpleDateFormat format1 = new SimpleDateFormat("yyyy/mm/dd");
        final SimpleDateFormat format2 = new SimpleDateFormat("yyyymmdd");
        while ((node = node.getNextSibling()) != null) {
            StringBuilder builder = new StringBuilder("");
            final NodeList childNodes = node.getChildNodes();
//            for (int i = 0; i < childNodes.getLength(); i++) {
//                System.out.println("[" + childNodes.item(i).getTextContent().trim() + "]");
//            }
            if (childNodes.getLength() != Integer.parseInt(properties.getProperty("expected.column.count"))) {
                continue;
            }
            for (String[] csvHeader : csvHeaders) {
                final String header = csvHeader[0];
                final String position = csvHeader[1];
                String value = childNodes.item(Integer.parseInt(position)).getTextContent().trim();
                if (header.equals("Date")) {
                    if (position.equals("DATE"))
                        value = dateCell;
                    else
                        value = format2.format(format1.parse(value));

                } else if (header.startsWith("%")) {
                    value = String.format("%.2f", Float.parseFloat(value) * 100);
                }
                builder.append(value).append(',');
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append('\n');
            outputStream.write(builder.toString().getBytes());
        }
    }

    private void writeHeader(BufferedOutputStream outputStream) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        for (String[] csvHeader : csvHeaders) {
            headerBuilder.append(csvHeader[0]).append(',');
        }
        headerBuilder.deleteCharAt(headerBuilder.length() - 1);
        headerBuilder.append('\n');
        outputStream.write(headerBuilder.toString().getBytes());
    }

    private Node findStartPoint(Document document) throws XPathExpressionException {

        XPathExpression expr = XPathFactory.newInstance().newXPath().compile(properties.getProperty("report.generation.point"));
        return ((Node) expr.evaluate(document, XPathConstants.NODE));
    }
}

