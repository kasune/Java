import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;



public class DomRead{
       public static String[] cities;
       public static String[] roads;
       public static String[] numbers;


   static public void main(String[] arg){
     try{
         BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
         DomRead dr = new DomRead();
         System.out.print("Enter XML File name: ");
         String xmlFile = bf.readLine();
         dr.parseXML(xmlFile);

         System.out.print("Enter Number: ");
         String num=bf.readLine();
         //System.out.println(num);
         dr.seacrhNumber(num);

     }catch(IOException ioe){
         System.out.println(ioe.toString());
     }



   }
       public void parseXML(String xmlFile){
            try {
        File file = new File(xmlFile);


         // Create a factory
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         // Use the factory to create a builder
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(xmlFile);
         // Get a list of all elements in the document

           doc.getDocumentElement ().normalize ();
                       System.out.println ("Root element of the doc is " +
                            doc.getDocumentElement().getNodeName());


                       NodeList listOfCities = doc.getElementsByTagName("bio");
                       int totalPersons = listOfCities.getLength();
                       System.out.println("Total no of Bios : " + totalPersons);
                       System.out.println("--------------------");
                       cities=new String [totalPersons];
                       roads=new String [totalPersons];
                       numbers=new String [totalPersons];

                       for(int s=0; s<listOfCities.getLength() ; s++){


                           Node firstBioNode = listOfCities.item(s);
                           if(firstBioNode.getNodeType() == Node.ELEMENT_NODE){


                               Element firstBioElement = (Element)firstBioNode;

                               //-------
                               NodeList firstBioList = firstBioElement.getElementsByTagName("city");
                               Element firstNameElement = (Element)firstBioList.item(0);

                               NodeList textFNList = firstNameElement.getChildNodes();
                               cities [s]= ((Node)textFNList.item(0)).getNodeValue().trim();
                               System.out.println("City : " +
                                      ((Node)textFNList.item(0)).getNodeValue().trim());

                               //-------
                               NodeList lastNameList = firstBioElement.getElementsByTagName("road");
                               Element lastNameElement = (Element)lastNameList.item(0);

                               NodeList textLNList = lastNameElement.getChildNodes();
                               roads [s]= ((Node)textLNList.item(0)).getNodeValue().trim();
                               System.out.println("Road : " +
                                      ((Node)textLNList.item(0)).getNodeValue().trim());

                               //----
                               NodeList ageList = firstBioElement.getElementsByTagName("number");
                               Element ageElement = (Element)ageList.item(0);

                               NodeList textAgeList = ageElement.getChildNodes();
                               numbers [s]= ((Node)textAgeList.item(0)).getNodeValue().trim();
                               System.out.println("Number : " +((Node)textAgeList.item(0)).getNodeValue().trim());

                               //------


                           }//end of if clause


                       }//end of for loop with s var


                                System.out.println("--------------------");
                   }catch (SAXParseException err) {
                   System.out.println ("** Parsing error" + ", line "
                        + err.getLineNumber () + ", uri " + err.getSystemId ());
                   System.out.println(" " + err.getMessage ());

                   }catch (SAXException e) {
                   Exception x = e.getException ();
                   ((x == null) ? e : x).printStackTrace ();

                   }catch (Throwable t) {
                   t.printStackTrace ();
                   }

       }

       public void seacrhNumber(String num){
           int count=0;
                     for(;count<numbers.length;count++){
                        if(num.equals(numbers[count])){
                            System.out.println(cities[count]);
                            System.out.println(numbers[count]);
                            System.out.println(roads[count]);
                        }
                     }
                   }
}


