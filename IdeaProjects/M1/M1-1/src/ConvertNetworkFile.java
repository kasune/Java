import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;

import java.text.*;
import java.util.*;


public class ConvertNetworkFile extends DefaultHandler{

	private boolean endLine    = false;
	private boolean endSummary = false;
	private boolean isTitle    = false;
	private boolean isNumber   = false;
	private boolean isDate     = false;
	private boolean isEndCell  = false;
	private Calendar last      = null;

	private LinkedList <String>titles = new LinkedList<String>();
	private String cellType = null;

	private StringBuffer buffer= null;
	private PrintWriter out    = null;


	private String rLine        = null;
	private int columnIndex     = 0;
	private String countryName  = null;
	private String operatorName = null;
	//private String messageName  = null;


	private final String BASE_DIRECTORY   = "D:/GLR201_Reports/";
	private final String SOURCE_DIRECTORY = "excel";
	private final String DEST_DIRECTORY   = "reports";
	private final String BACKUP_DIRECTORY = "archive";
	private final String DELIMITER        = "_";


	public ConvertNetworkFile() {
		// default constructor
	}

	public static void main(String args[]){
		ConvertNetworkFile cf = new ConvertNetworkFile();
		cf.start();
	}

	public void start(){
		File srcDir           = null;
		File files[]          = null;
		FilenameFilter filter = null;
		String sLast          = null;

		SAXParserFactory factory = null;
		SAXParser saxParser      = null;

		srcDir = new File(BASE_DIRECTORY);
		if (!srcDir.isDirectory()){
			System.out.println("Warning: Directory <" + srcDir.getName() + "> does not exist");
			System.exit(10);
		}

		filter = new FilenameFilter() {
			public boolean accept(File file, String name) {
				boolean doProcess = false;
				doProcess = (name.toUpperCase().endsWith(".XLS") && (name.toUpperCase().indexOf("GLR201") > -1));
				return doProcess;
			}
		};

		files = srcDir.listFiles(filter);

		if ((files != null) && (files.length>0)){
			System.out.println("INFO: Found <" + files.length + "> files in the directory");
		}else {
			System.out.println("Warning: No files found in the <" + SOURCE_DIRECTORY + "> directory");
			System.exit(10);
		}

		try {
			for (int index=0; index<files.length;index++){

				out = new PrintWriter(new BufferedWriter(new FileWriter(BASE_DIRECTORY + "report.csv")));
				printHeader(out);

				endSummary=false;
				factory = SAXParserFactory.newInstance();
				saxParser = factory.newSAXParser();

				System.out.println("Analyzing file >>>>> " + files[index].getName());

				saxParser.parse(files[index], this);

				Thread.sleep(1000);
				System.out.println("Archiving file [" + files[index].getName() + "]");
				if ( files[index].renameTo(new File(BASE_DIRECTORY+BACKUP_DIRECTORY+ "/" + files[index].getName()) ) ){
					System.out.println("File [" + files[index].getName() + "] archived SUCCESSFULLY");
				}else{
					System.out.println("Error archiving File [" + files[index].getName() + "]");
				}
				out.close();
				out = null;

				if (last==null){
					System.out.println("Last date in file [" + files[index].getName() + "] is not available");
					System.exit(10);
				}
				sLast = getFileName("GLR201");
				if (new File(BASE_DIRECTORY + "report.csv").renameTo(new File(BASE_DIRECTORY+sLast+".csv") ) ){
					System.out.println("CSV file renamed successfully");
				}else {
					System.out.println("Error renaming CSV file");
				}
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	private boolean printHeader(PrintWriter out){
		boolean status = true;
		try{
			out.println("Date," +
						"Country,"	+
						"Network," +
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
									
		}catch (Exception ex){
			status = false;
		}
		return status;
	}

	private String getFileName(String prefix){

		Calendar calendar     = null;
		String fHour          = null;
		String fMinute        = null;
		String fSecond        = null;
		String fDay           = null;
		String fMonth         = null;
		String fYear          = null;

		String sLast          = null;

		calendar = Calendar.getInstance();
		fHour    = (calendar.get(Calendar.HOUR_OF_DAY)<10)? ("0"+calendar.get(Calendar.HOUR_OF_DAY)): (""+calendar.get(Calendar.HOUR_OF_DAY));
		fMinute  = (calendar.get(Calendar.MINUTE)<10)? ("0"+calendar.get(Calendar.MINUTE)): (""+calendar.get(Calendar.MINUTE));
		fSecond  = (calendar.get(Calendar.SECOND)<10)? ("0"+calendar.get(Calendar.SECOND)): (""+calendar.get(Calendar.SECOND));
		fMonth   = ((last.get(Calendar.MONTH)+1)<10)? ("0"+(last.get(Calendar.MONTH)+1)): (""+(last.get(Calendar.MONTH)+1));
		fDay     = (last.get(Calendar.DAY_OF_MONTH)<10)? ("0"+last.get(Calendar.DAY_OF_MONTH)): (""+last.get(Calendar.DAY_OF_MONTH));

		sLast = (prefix + DELIMITER +
		         last.get(Calendar.YEAR) + fMonth + fDay + DELIMITER +
		         fHour + fMinute + fSecond);
		return sLast;

	}

	public void startElement(String uri, String localName, String qName, Attributes attributes){

		if(qName.equalsIgnoreCase("Row")){
			endLine     = false;
			rLine       ="";
			columnIndex =0;
			isTitle     =true;
		}

		if(qName.equalsIgnoreCase("Data")){
			if (attributes!=null){
				cellType = attributes.getValue(0);
				if (attributes.getValue(0).equals("DateTime")) {
					isTitle=false;
					isDate=true;
					if (titles.size() == 3){
						// This list has the country name & operator name
						countryName  = titles.removeFirst();
						operatorName = titles.removeFirst();
                       // System.out.println(countryName +" -- "+operatorName);
						//messageName  = titles.removeFirst();
					}else if (titles.size()==2) {
                        countryName  = titles.removeFirst();
						operatorName = titles.removeFirst();
                       // System.out.println(countryName + " -- "+operatorName+" size 2 no operator");
						//messageName  = titles.removeFirst();
					}
					//else if (titles.size()==1){
						//messageName  = titles.removeFirst();
					//}
					titles.clear();
				}else {
					isDate=false;
				}

				if (attributes.getValue(0).equals("Number")){
					isNumber=true;
				}else{
					isNumber=false;
				}
			}
		}

	}


	public void characters(char[] ch, int start, int length){

		String data = null;
		NumberFormat nf = null;
		DateFormat df = null;
		buffer = new StringBuffer();

		Calendar calendar = Calendar.getInstance();
		String month      = null;
		String date       = null;
		int year          = 0;


		buffer.append(ch, start, length);
		data = buffer.toString().trim();
		columnIndex++;

        //if (data.equalsIgnoreCase("Gateway Location Register - E164 Bypass")){
		 if (data.equalsIgnoreCase("GLR201 - E164 Bypass")){
			endSummary = true;
		}

		if ((data.length()>0) && (endSummary)){

			if (isNumber) {
				//Conlumn 5 or 7

				if ((columnIndex==11) || (columnIndex==15) || (columnIndex==19) || (columnIndex==23)){
					nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(2);
					try{
						data = ( nf.format(nf.parse(data).doubleValue()*100) );
						rLine+=(data+",");
                        System.out.println("no- "+rLine);

					}catch(Exception ex){
						rLine+=(data+",");
					}
				}else {
					rLine+=(data+",");
                    System.out.println("else - "+rLine);
				}
						
				//rLine+=(data + "[" + cellType + "/" + columnIndex + "],");
			
			}else if (isDate){
				df = DateFormat.getDateInstance();
				try{
					//data = data.substring(0, data.indexOf("T"));
					df = new SimpleDateFormat("yyyy-MM-dd");
					calendar.setTimeInMillis(df.parse(data).getTime());
					year  = calendar.get(Calendar.YEAR);
					month = ((calendar.get(Calendar.MONTH)+1) < 10) ? ("0"+(calendar.get(Calendar.MONTH)+1)) : (""+(calendar.get(Calendar.MONTH)+1));
					date  = (calendar.get(Calendar.DAY_OF_MONTH) <10) ? ("0"+calendar.get(Calendar.DAY_OF_MONTH)) : (""+calendar.get(Calendar.DAY_OF_MONTH)) ;

					rLine += (year + "-" + month + "-" + date + "," + countryName + "," + operatorName + "," );
					//rLine += (year + "-" + month + "-" + date + ",");
					last = (last==null)? calendar:getLast(calendar);

				}catch(Exception date_ex){
					//rLine+=(data+",");
					//date_ex.printStackTrace();
					System.out.println("Warning: Days > "+date_ex.getMessage());
				}
			}else {
				// For text content, there can be more than one cell. This section handle multiple cell content.
				isEndCell=false;
				rLine+=(data);
			}
			
			//rLine+=(data+",");
		}

	}

	private Calendar getLast(Calendar nCal){
		Calendar result = null;
		result = (nCal.after(last))?nCal:last;
		return result;
	}

	public void endElement(String uri, String localName, String qName) {
		if(qName.equalsIgnoreCase("Row")){
			endLine=true;
			if ((endSummary) && (!isTitle)){
			//if (endSummary){
				//out.println(rLine);
				//countryName
				//out.println(countryName + "," + operatorName + "," + messageName + "," + rLine);
				out.println(rLine);
				//System.out.println( rLine);
			}

			if ((endSummary) && (columnIndex <= 5)){
                //if (rLine.indexOf("Gateway Location Register - E164 Bypass")<0){
				if (rLine.indexOf("GLR201 - E164 Bypass")<0){
					titles.addLast(rLine);
					
					//System.out.println("Titles->" + rLine);
				}
			}
		}

		if((qName.equalsIgnoreCase("Data")) && (!isEndCell)){
			isEndCell=true;
			
			//rLine+="[" + columnIndex + "]";
			//rLine+=",";
		}
	}
}