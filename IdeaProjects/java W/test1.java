import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class test5{
  public static void main(String[] args) throws IOException {
	
     try{
       	StringTokenizer st = new StringTokenizer(readTextFile("my.txt"));

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
int count=0;
	while( (numRead = reader.read(chars)) > -1){
		sb.append(String.valueOf(chars));	
		count++;
	}
	System.out.println(count);
	reader.close();

	return sb.toString();
}



}
