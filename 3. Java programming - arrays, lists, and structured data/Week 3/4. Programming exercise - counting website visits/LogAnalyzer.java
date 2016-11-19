 


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
     
     /**
      * Maps an IP address to the number of times this IP address visited the website.
      * @returns a HashMap<String, Integer>
      */
     public HashMap<String, Integer> countVisitsPerIP() {
         HashMap<String, Integer> visits = new HashMap<String, Integer>();
         // For every record
         for (LogEntry le : records) {
             // Obtain ip address
             String ip = le.getIpAddress();
             // If address exists in map, add 1 to its visits
             // Otherwise add address and a value of 1 visit
             if (visits.containsKey(ip)) {
                 int num = visits.get(ip);
                 visits.put(ip, num + 1);
             } else {
                 visits.put(ip, 1);
             }
         }
         return visits;
     }
     
     /**
      * Returns the maximum number of visits to this website by a single IP address.
      * @parameter visits is a HashMap<String, Integer> that maps an IP address 
      * to the number of times that IP address appears in the web log file
      */
     public int mostNumberVisitsByIP(HashMap<String, Integer> visits) {
         int max = 0;
         // For every ip address
         for (String ip : visits.keySet()) {
             // Obtain its number of visits
             int curr = visits.get(ip);
             if (curr > max) {
                 max = curr; 
             }
         }
         return max;
     }
     
     /**
      * Returns an ArrayList of Strings of IP addresses that all have the maximum number of 
      * visits to this website.
      * @parameter visits is a HashMap<String, Integer> that maps an IP address 
      * to the number of times that IP address appears in the web log file
      */
     public ArrayList<String> iPsMostVisits(HashMap<String, Integer> visits) {
         ArrayList<String> maxIPs = new ArrayList<String>();
         int max = mostNumberVisitsByIP(visits);
         for (String ip : visits.keySet()) {
             // If current IP address' number of visits equals max
             if (visits.get(ip) == max) {
                 // Add IP to maxIPs
                 maxIPs.add(ip);
             }
         }
         return maxIPs;
     }
     
     /**
      * Returns a HashMap<String, ArrayList<String>> that uses records and maps days from web logs 
      * to an ArrayList of IP addresses that occurred on that day (including repeated IP addresses).
      * A day is in the format “MMM DD” where MMM is the first three characters of the month name
      * with the first letter capital and the others in lowercase, and DD is the day in two digits 
      * (examples are “Dec 05” and “Apr 22”).
      */
     public HashMap<String, ArrayList<String>> iPsForDays() {
         HashMap<String, ArrayList<String>> dateMap = new HashMap<String, ArrayList<String>>();
         // For every record
         for (LogEntry le : records) {
             // Obtain ip and date 
             String ip = le.getIpAddress();
             String d = le.getAccessTime().toString().substring(4,10);
             // If dateMap doesn't contain date yet
             if (!dateMap.containsKey(d)) {
                 // Create ArrayList<String> and add IP address
                 ArrayList<String> ips = new ArrayList<String>();
                 ips.add(ip);
                 // Update dateMap
                 dateMap.put(d, ips);
             } else {
                 // Obtain ArrayList of IP addresses
                 ArrayList<String> ips = dateMap.get(d);
                 ips.add(ip);
                 dateMap.put(d, ips);
             }
         }
         return dateMap;
     }
     
     /**
      * Determines the day that has the most IP address visits.
      * @parameter dateMap is a HashMap<String, ArrayList<String>> that maps days from web logs 
      * to an ArrayList of IP addresses that occurred on that day
      * @returns a String representing a day in the format “MMM DD” where MMM is the first three 
      * characters of the month name with the first letter capital and the others in lowercase, 
      * and DD is the day in two digits (examples are “Dec 05” and “Apr 22”)
      */
     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> dateMap) {
         String maxDay = "";
         // For each day in dateMap
         for (String date : dateMap.keySet()) {
             // Retrieve its ArrayList of IP addresses
             ArrayList<String> ips = dateMap.get(date);
             // If on first day or if current day has more visits than on maxDay
             if (maxDay.equals("") || ips.size() > dateMap.get(maxDay).size()) {
                 maxDay = date;
             }
         }
         return maxDay;
     }
     
     /**
      * Returns an ArrayList<String> of IP addresses that had the most accesses on the given day.
      * @parameter dateMap is a HashMap<String, ArrayList<String>> that maps days from web logs 
      * to an ArrayList of IP addresses that occurred on that day
      * @parameter date is a String representing a day in the format “MMM DD” where MMM is the first three 
      * characters of the month name with the first letter capital and the others in lowercase, 
      * and DD is the day in two digits (examples are “Dec 05” and “Apr 22”)
      */
     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> dateMap, String date) {
         // Get IP addresses for given date
         ArrayList<String> ips = dateMap.get(date);
         
         // Map each IP address to the number of times this IP address visited the website on given date
         HashMap<String, Integer> visits = new HashMap<String, Integer>();
         for (String ip : ips) {
             if (visits.containsKey(ip)) {
                 int num = visits.get(ip);
                 visits.put(ip, num + 1);
             } else {
                 visits.put(ip, 1);
             }
         }
         
         return iPsMostVisits(visits);
     }
}
