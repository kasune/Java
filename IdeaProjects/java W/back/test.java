import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class test{
  public static void main(String[] args) throws IOException {
    /*File inputFile = new File("sig.txt");
    File outputFile = new File("outagain.txt");

    FileReader in = new FileReader(inputFile);
    FileWriter out = new FileWriter(outputFile);
    int c;

    while ((c = in.read()) != -1)
      out.write(c);

    in.close();
    out.close();
  }*/
     
     	StringTokenizer st = new StringTokenizer(readTextFile("my.txt"));
try{
     while (st.hasMoreTokens()) {
	System.out.println(Double.parseDouble(st.nextToken()));
	}
     }
     catch(NumberFormatException e){}
     }

	public static String readTextFile(String fullPathFilename) throws IOException {
	StringBuffer sb = new StringBuffer(1024);
	BufferedReader reader = new BufferedReader(new FileReader(fullPathFilename));
			
	char[] chars = new char[1024];
	int numRead = 0;
	while( (numRead = reader.read(chars)) > -1){
		sb.append(String.valueOf(chars));	
	}

	reader.close();

	return sb.toString();
}



}
