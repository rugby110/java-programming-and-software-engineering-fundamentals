
/**
 * ParseWeatherData parses CSV files containing weather data. 
 * 
 * coldestHourInFile() returns the CSVRecord with the coldest temperature in the 
 * file and thus all the information about the coldest temperature, such as the 
 * hour of the coldest temperature.
 * @param CSVParser parser is an object that accesses contents of an open file
 * 
 * testColdestHourInFile() tests coldestHourInFile() and prints out information 
 * about that coldest temperature.
 * 
 * @author Brienna Herold
 * @version Oct. 12, 2016
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class ParseWeatherData {
    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldestHour = null;
        
        // For each row in the CSV file
        for (CSVRecord record : parser) {
            // If on first row,
            if (coldestHour == null) {
                coldestHour = record;
            } else {
                double currentTemp = Double.parseDouble(record.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestHour.get("TemperatureF"));
                // If there wasn't a valid reading at a specific hour
                if (currentTemp == -9999) {
                    break;
                } 
                // Otherwise, if current temp is coldest, update coldestHour to current hour
                else if (currentTemp < coldestTemp) {
                    coldestHour = record;
                }
            }
        }
        
        return coldestHour;
    }
    
    public void testColdestHourInFile() {
        // Open CSV file
        FileResource fr = new FileResource("nc_weather/2012/weather-2012-01-01.csv");
        // Create a parser object to access file contents
        CSVParser parser = fr.getCSVParser();
        
        CSVRecord coldestHour = coldestHourInFile(parser);
        System.out.println("Coldest hour occurred at " + coldestHour.get("TimeEST"));
        
    }
}
