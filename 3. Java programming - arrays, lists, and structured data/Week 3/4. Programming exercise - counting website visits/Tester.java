 


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
        // Read the data file
        analyzer.readFile("short-test_log");
        // Print all the web logs
        analyzer.printAll();
    }
    
    public void testUniqueIPs() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
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
        // Read the data file
        analyzer.readFile("weblog1_log");
        // Test the method uniqueIPVisitsOnDay
        String someday = "Mar 24";
        ArrayList<String> addresses = analyzer.uniqueIPVisitsOnDay(someday);
        System.out.println("There were " + addresses.size() + " visits on " + someday);
    }
    
    public void testUniqueIPsInRange() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
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
    
    public void testCountVisitsPerIP() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
        analyzer.readFile("short-test_log");
        // Test the method countVisitsPerIP
        HashMap<String, Integer> visits = analyzer.countVisitsPerIP();
        System.out.println(visits);
    }
    
    public void testMostNumberVisitsByIP() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
        analyzer.readFile("weblog3-short_log");
        // Test the method mostNumberVisitsByIP
        HashMap<String, Integer> visits = analyzer.countVisitsPerIP();
        int max = analyzer.mostNumberVisitsByIP(visits);
        System.out.println("Maximum number of visits to this website by a single IP address: " + max);
    }
    
    public void testIPsMostVisits() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
        analyzer.readFile("weblog3-short_log");
        // Test the method iPsMostVisits
        HashMap<String, Integer> visits = analyzer.countVisitsPerIP();
        ArrayList maxIPs = analyzer.iPsMostVisits(visits);
        System.out.println(maxIPs);
    }
    
    public void testIPsForDays() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
        analyzer.readFile("weblog3-short_log");
        // Test the method iPsForDays
        String date = "Sep 14";
        HashMap<String, ArrayList<String>> dateMap = analyzer.iPsForDays();
        System.out.println(date + " maps to " + dateMap.get(date).size() + " IP addresses");
        
        date = "Sep 21";
        dateMap = analyzer.iPsForDays();
        System.out.println(date + " maps to " + dateMap.get(date).size() + " IP addresses");
        
        date = "Sep 30";
        dateMap = analyzer.iPsForDays();
        System.out.println(date + " maps to " + dateMap.get(date).size() + " IP addresses");
    }
    
    public void testDayWithMostIPVisits() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
        analyzer.readFile("weblog3-short_log");
        // Test the method dayWithMostIPVisits
        HashMap<String, ArrayList<String>> dateMap = analyzer.iPsForDays();
        String day = analyzer.dayWithMostIPVisits(dateMap);
        System.out.println("The day with the most IP visits: " + day); 
    }
    
    public void testIPsWithMostVisitsOnDay() {
        // Create a LogAnalyzer object
        LogAnalyzer analyzer = new LogAnalyzer();
        // Read the data file
        analyzer.readFile("weblog3-short_log");
        // Test the method iPsWithMostVisitsOnDay
        HashMap<String, ArrayList<String>> dateMap = analyzer.iPsForDays();
        String date = "Sep 30";
        ArrayList<String> ips = analyzer.iPsWithMostVisitsOnDay(dateMap, date);
        System.out.println(ips);
    }
}
