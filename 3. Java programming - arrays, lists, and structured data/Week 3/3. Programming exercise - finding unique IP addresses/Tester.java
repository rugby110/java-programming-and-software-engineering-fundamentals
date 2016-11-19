 


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
}
