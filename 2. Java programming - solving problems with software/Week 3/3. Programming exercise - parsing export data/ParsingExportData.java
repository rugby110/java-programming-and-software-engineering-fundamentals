
/**
 * ParsingExportData parses a CSV file containing export data about countries. 
 * 
 * tester creates the CSVParsers and calls each of the methods below.
 * 
 * listExportersTwoProducts prints the names of all the countries that
 * have both exportItem1 and exportItem2 as export items.
 * @param CSVParser parser is an object that accesses contents of an open file
 * @param exportItem1 is a String representing an export item
 * @param exportItem2 is a String representing another export item
 * 
 * numberOfExporters returns the number of countries that export exportItem.
 * @param CSVParser parser is an object that accesses contents of an open file
 * @param exportItem is a String representing an export item
 * 
 * bigExporters prints the names of countries and their Value amount for all 
 * countries whose "Value (dollars)" string is longer than the amount string.
 * @param CSVParser parser is an object that accesses contents of an open file
 * @param amount is a String representing export value in dollars
 * 
 * countryInfo returns a string of information about the country or returns 
 * “NOT FOUND” if there is no information about the country.
 * @param CSVParser parser is an object that accesses contents of an open file
 * @param country is a String representing the name of the country
 * 
 * @author Brienna Herold
 * @version Oct. 9, 2016
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class ParsingExportData {
    public void tester() {
        FileResource fr = new FileResource("exportdata.csv");
        CSVParser parser = fr.getCSVParser();
        
        // Print info about a country
        String info = countryInfo(parser, "Nauru");
        System.out.println(info);
        
        // Print countries that export certain items
        // Reset parser
        parser = fr.getCSVParser();
        String exportItem1 = "fish";
        String exportItem2 = "nuts";
        System.out.println("Countries that export " + exportItem1 + " and " + exportItem2 + ":");
        listExportersTwoProducts(parser, exportItem1, exportItem2);
        
        // Print number of countries that export a certain item
        // Reset parser
        parser = fr.getCSVParser();
        String exportItem = "sugar";
        int num = numberOfExporters(parser, exportItem);
        System.out.println("Number of countries that export " + exportItem + ": " + num);
        
        // Print countries and their Value amount for all countries whose Value (dollars) string is longer than amount string
        // Reset parser
        parser = fr.getCSVParser();
        String amount = "$999,999,999,999";
        System.out.println("Countries whose \"Value (dollars)\" string is longer than " + amount + ":");
        bigExporters(parser, amount);
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        // Parse the contents of the file record-wise
        for (CSVRecord record : parser) {
            // If there exists a record containing a "Value (dollars)" string longer than amount,
            if (record.get("Value (dollars)").length() > amount.length()) {
                // Print country and value amount
                System.out.println(record.get("Country") + " " + record.get("Value (dollars)"));
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int num = 0;
        // Parse the contents of the file record-wise
        for (CSVRecord record : parser) {
            // If there exists a record containing the export item,
            if (record.get("Exports").contains(exportItem)) {
                // Increment count by one
                num++;
            }
        }
        return num;
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        // Parse the contents of the file record-wise
        for (CSVRecord record : parser) {
            // If there exists a record containing both export items,
            if (record.get("Exports").contains(exportItem1) && record.get("Exports").contains(exportItem2)) {
                // Print the country associated with the record
                System.out.println(record.get("Country"));
            }
        }
    }
    
    public String countryInfo(CSVParser parser, String country) {
        // NOTE: Can't be declared inside the for loop?
        String info = "";
        // Parse the contents of the file record-wise
        for (CSVRecord record : parser) {
            // If there exists a record for the country,
            if (record.get("Country").equals(country)) {
                // Return a string of info about the country
                info = record.get("Country") + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");
                return info;
            }
        }
        // If there is no information about the country
        return "NOT FOUND";
    }
}

