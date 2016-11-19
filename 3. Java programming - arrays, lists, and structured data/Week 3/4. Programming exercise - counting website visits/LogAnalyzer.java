 


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
         FileResource fr = new FileResource(filename);
         // For each line in file
         for (String line : fr.lines()) {
             // Create a new LogEntry object by parsing the file with WebLogParser
             LogEntry le = WebLogParser.parseEntry(line);
             // Store LogEntry object in the records ArrayList
             records.add(le);
         }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     /**
      * Returns an integer representing the number of unique IP addresses.
      */
     public int countUniqueIPs() {
         ArrayList<String> uniques = new ArrayList<String>();
         for (LogEntry le : records) {
             String ip = le.getIpAddress();
             if (!uniques.contains(ip)) {
                 uniques.add(ip);
             }
         }
         return uniques.size();
     }
     
     /**
      * Examines all the web log entries in records and print those LogEntrys that have 
      * a status code greater than num.
      */
     public void printAllHigherThanNum(int num) {
         for (LogEntry le : records) {
             if (le.getStatusCode() > num) {
                 System.out.println(le);
             }
         }
     }
     
     /**
      * Accesses the web logs in records and returns an ArrayList of Strings of unique IP 
      * addresses that had access on the given day.
      * @parameter someday is a String in the format "MMM DD" where MMM is the first three
      * characters of the month name with the first letter capitalized, and DD is the day 
      * in two digits (ex. "Dec 05" and "Apr 22")
      */
     public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
         ArrayList<String> addresses = new ArrayList<String>();
         for (LogEntry le : records) {
             // Obtain String representation of date from LogEntry object
             String d = le.getAccessTime().toString();
             String ip = le.getIpAddress();
             if (d.contains(someday) && !addresses.contains(ip)) {
                 addresses.add(ip);
             }
         }
         return addresses;
     }
     
     /**
      * Returns the number of unique IP addresses in records that have a status 
      * code in the range from low to high, inclusive.
      */
     public int countUniqueIPsInRange(int low, int high) {
         HashMap<String, Integer> uniques = new HashMap<String, Integer>();
         // For every record
         for (LogEntry le : records) {
             // Obtain ip address and status code
             String ip = le.getIpAddress();
             int status = le.getStatusCode();
             // If key doesn't already exist in map and status code is within range
             if (!uniques.containsKey(ip) && status >= low && status <= high) {
                 // Add unique IP address to map
                 uniques.put(ip, status);
             }
         }
         return uniques.size();
     }
}
