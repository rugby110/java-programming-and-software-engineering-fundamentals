 


/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author Brienna
 * @version Nov. 19, 2016
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         FileResource fr = new FileResource();
         String ip;
         Date time; 
         String req;
         int status;
         int bytes;
         // For each line in file
         for (String line : fr.lines()) {
             // Create a new LogEntry object by parsing the file with WebLogParser
             WebLogParser parser = new WebLogParser();
             LogEntry le = parser.parseEntry(line);
             // Store LogEntry object in the records ArrayList
             records.add(le);
         }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     
}
