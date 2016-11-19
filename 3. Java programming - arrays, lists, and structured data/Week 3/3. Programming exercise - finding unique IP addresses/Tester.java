 


/**
 * Write a description of class Tester here.
 * 
 * @author Brienna Herold
 * @version Nov. 19, 2016
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file short-test_log
        analyzer.readFile("short-test_log");
        // Print all the web logs
        analyzer.printAll();
    }
    
    public void testUniqueIPs() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file short-test_log
        analyzer.readFile("short-test_log");
        // Test the method countUniqueIPs
        int numOfUniqueIPs = analyzer.countUniqueIPs();
        System.out.println("Unique IPs: " + numOfUniqueIPs);
    }
    
    public void testPrintAllHigherThanNum() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
        analyzer.readFile("weblog1_log");
        // Test the method printAllHigherThanNum
        int num = 400;
        analyzer.printAllHigherThanNum(num);
    }
    
    public void testUniqueIPVistsOnDay() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file weblog-short_log
        analyzer.readFile("weblog1_log");
        // Test the method uniqueIPVisitsOnDay
        String someday = "Mar 24";
        ArrayList<String> addresses = analyzer.uniqueIPVisitsOnDay(someday);
        System.out.println("There were " + addresses.size() + " visits on " + someday);
    }
    
    public void testUniqueIPsInRange() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file short-test_log
        analyzer.readFile("weblog1_log");
        // Test the method countUniqueIPsInRange
        int low = 200;
        int high = 299;
        int num = analyzer.countUniqueIPsInRange(low, high);
        System.out.println("Number of unique IPs in range " + low + "-" + high + ": " + num);
        
        low = 300;
        high = 399;
        num = analyzer.countUniqueIPsInRange(low, high);
        System.out.println("Number of unique IPs in range " + low + "-" + high + ": " + num);
    }
}
