import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class test2{  
public static void main(String[] args) throws IOException {
	
      		StringTokenizer st = new StringTokenizer(readTextFile()); 
		while (st.hasMoreTokens()) {
		try{ 
		System.out.println(Double.parseDouble(st.nextToken()));
		}catch(NumberFormatException e){}
	
	}
     }

  public static String readTextFile() throws IOException {
  	File file = new File("my.txt");  	
	int fileLenght= (int)file.length();	
	BufferedReader reader = new BufferedReader(new FileReader(file));
	char[] chars = new char[fileLenght];	
	reader.read(chars,0,fileLenght);		
	reader.close();
	String result = new String(chars);
 	return result;
}
}