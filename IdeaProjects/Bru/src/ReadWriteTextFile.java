import java.io.*;
/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 7, 2007
 * Time: 11:31:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReadWriteTextFile {


	  /**
	  * Fetch the entire contents of a text file, and return it in a String.
	  * This style of implementation does not throw Exceptions to the caller.
	  *
	  * @param aFile is a file which already exists and can be read.
	  */
	  static public String getContents(File aFile) throws FileNotFoundException, IOException {
	    if (aFile == null) {
	      throw new IllegalArgumentException("File should not be null.");
	    }
	    if (!aFile.exists()) {
	      throw new FileNotFoundException ("File does not exist: " + aFile);
	    }
	    if (!aFile.isFile()) {
	      throw new IllegalArgumentException("Should not be a directory: " + aFile);
	    }
	    if (!aFile.canRead()) {
	      throw new IllegalArgumentException("File cannot be read: " + aFile);
	    }

	    //...checks on aFile are elided
	    StringBuffer contents = new StringBuffer();

	    //declared here only to make visible to finally clause
	    BufferedReader input = null;
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      input = new BufferedReader( new FileReader(aFile) );
	      String line = null; //not declared within while loop
	      /*
	      * readLine is a bit quirky :
	      * it returns the content of a line MINUS the newline.
	      * it returns null only for the END of the stream.
	      * it returns an empty String if two newlines appear in a row.
	      */
	      while (( line = input.readLine()) != null){
	        contents.append(line);
	        contents.append(System.getProperty("line.separator"));
	      }
	    }
	    catch (FileNotFoundException ex) {
	      ex.printStackTrace();
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    finally {
	      try {
	        if (input!= null) {
	          //flush and close both "input" and its underlying FileReader
	          input.close();
	        }
	      }
	      catch (IOException ex) {
	        ex.printStackTrace();
	      }
	    }
	    return contents.toString();
	  }

	  /**
	  * Change the contents of text file in its entirety, overwriting any
	  * existing text.
	  *
	  * This style of implementation throws all exceptions to the caller.
	  *
	  * @param aFile is an existing file which can be written to.
	  * @throws IllegalArgumentException if param does not comply.
	  * @throws FileNotFoundException if the file does not exist.
	  * @throws IOException if problem encountered during write.
	  */
	  static public void setContents(File aFile, String aContents)
	                                throws FileNotFoundException, IOException {
	    if (aFile == null) {
	      throw new IllegalArgumentException("File should not be null.");
	    }
	    if (!aFile.exists()) {
	      throw new FileNotFoundException ("File does not exist: " + aFile);
	    }
	    if (!aFile.isFile()) {
	      throw new IllegalArgumentException("Should not be a directory: " + aFile);
	    }
	    if (!aFile.canWrite()) {
	      throw new IllegalArgumentException("File cannot be written: " + aFile);
	    }

	    //declared here only to make visible to finally clause; generic reference
	    Writer output = null;
	    try {
	      //use buffering
	      //FileWriter always assumes default encoding is OK!
	      output = new BufferedWriter( new FileWriter(aFile) );
	      output.write( aContents );
	    }
	    finally {
	      //flush and close both "output" and its underlying FileWriter
	      if (output != null) output.close();
	    }
	  }

	  /**
	  * Simple test harness.
	  */
	  public static void main (String... aArguments) throws IOException {
	    File testFile = new File("F:\\contacts\\test.txt");
	    System.out.println("Original file contents: " + getContents(testFile));
	    //setContents(testFile, "J'ai d'autres chats à fouetter, moi...");
        insertLineToFile(testFile,2,"kushan");
        //System.out.println("New file contents: " + getContents(testFile));
	  }

    public static void insertLineToFile (File inputFile, int lineNumber, String lineToBeInserted)
   {
	     // temp file
	     File outFile = new File("temp.tmp");
	     try{
		     // input
		     FileInputStream fis  = new FileInputStream(inputFile);
		     BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		     // output
		     FileOutputStream fos = new FileOutputStream(outFile);
		     PrintWriter out = new PrintWriter(fos);

		     String thisLine = "";
		     int i =1;

		     while ((thisLine = in.readLine()) != null) {
			       if(i == lineNumber)
                       System.out.println(lineToBeInserted);
			           System.out.println(thisLine);
			           i++;
		       }

		     // Flush and close
			 out.flush();
			 out.close();
			 in.close();

		     inputFile.delete();
		     outFile.renameTo(inputFile);
	     }
	     catch (Exception e){
	    	 System.out.println("File read error");
	     }
   }

   public static void deleteLineFromFile (File inputFile, int lineNumber)
   {
	     // temp file
	     File outFile = new File("temp.tmp");
	     try{
		     // input
		     FileInputStream fis  = new FileInputStream(inputFile);
		     BufferedReader in = new BufferedReader(new InputStreamReader(fis));

		     // output
		     FileOutputStream fos = new FileOutputStream(outFile);
		     PrintWriter out = new PrintWriter(fos);

		     String thisLine = null;
		     int i =1;

		     while ((thisLine = in.readLine()) != null) {
			       if(!(i == lineNumber))out.println(thisLine);
			       i++;
		       }

		     // Flush and close
			 out.flush();
			 out.close();
			 in.close();

		     inputFile.delete();
		     outFile.renameTo(inputFile);
	     }
	     catch (Exception e){
	    	 System.out.println("File read error");
	     }
   }

}
