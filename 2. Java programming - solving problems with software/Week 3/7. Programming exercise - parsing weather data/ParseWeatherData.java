
/**
 * ParseWeatherData parses CSV files containing weather data. 
 * 
 * averageTemperatureInFile() returns a double that represents the average 
 * temperature in the file.
 * 
 * testAverageTemperatureInFile() tests averageTemperatureInFile().
 * 
 * lowestHumidityInManyFiles() returns a CSVRecord that has the lowest humidity 
 * over all the files. If there is a tie, then return the first such record 
 * that was found. 
 * 
 * testLowestHumidityInManyFiles() tests lowestHumidityInManyFiles().
 * 
 * lowestHumidityInFile() returns the CSVRecord that has the lowest humidity. 
 * If there is a tie, then return the first such record that was found.
 * 
 * testLowestHumidityInFile() tests lowestHumidityInFile().
 * 
 * fileWithColdestTemperature() returns a string that is the name of the file 
 * from selected files that has the coldest temperature.
 * 
 * testFileWithColdestTemperature() tests fileWithColdestTemperature().
 * 
 * coldestHourInFile() returns the CSVRecord with the coldest temperature in the 
 * file and thus all the information about the coldest temperature, such as the 
 * hour of the coldest temperature.
 * @param CSVParser parser is an object that accesses contents of an open file
 * 
 * testColdestHourInFile() tests coldestHourInFile().
 * 
 * @author Brienna Herold
 * @version Oct. 16, 2016
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class ParseWeatherData {
    public double averageTemperatureInFile(CSVParser parser) {
        double sum = 0;
        double count = 0;
        
        // For each row in the CSV file
        for (CSVRecord record : parser) {
            double temp = Double.parseDouble(record.get("TemperatureF"));
            // If there wasn't a valid reading
            if (temp == -9999) {
                break;
            }
            // Update variables
            sum += temp;
            count++;
        }
        
        // Calculate average
        double avg = sum/count;
        return avg;
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double average = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " + average);
    }
    
    
    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord record = null;
        double lowestHumidity = -9999;
        // Allow user to choose one or more files from a directory 
        DirectoryResource dir = new DirectoryResource();
        // For each file the user chose
        for (File f : dir.selectedFiles()) {
            // Create a parser object to access contents
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            // Get lowest humidity
            CSVRecord rec = lowestHumidityInFile(parser);
            double lowestHumidityInFile = Double.parseDouble(rec.get("Humidity"));
            // If on first file, update variables
            if (record == null) {
                lowestHumidity = lowestHumidityInFile;
                record = rec;
            // Otherwise, if current file contains lowest humidity so far, update variables
            } else if (lowestHumidityInFile < lowestHumidity) {
                lowestHumidity = lowestHumidityInFile;
                record = rec;
            }
        }
        
        return record;
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord row = lowestHumidityInManyFiles();
        System.out.println("Lowest humidity was " + row.get("Humidity") + " at " + row.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestHumidity = null;
        // For each row in the CSV file
        for (CSVRecord record : parser) {
            // Continue only if there was a valid reading
            if (record.get("Humidity") != "N/A") {
                // If on first row,
                if (lowestHumidity == null) {
                    lowestHumidity = record;
                } else {
                    // Get coldest temp and current temp
                    double current = Double.parseDouble(record.get("Humidity"));
                    double lowest = Double.parseDouble(lowestHumidity.get("Humidity"));
                    // if current row contains lowest humidity so far, update variable
                    if (current < lowest) {
                        lowestHumidity = record;
                    }
                }
            }
        }
        return lowestHumidity;
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public String fileWithColdestTemperature() {
        String name = null;
        double coldestTemp = -9999;
        
        // Allow user to choose one or more files from a directory 
        DirectoryResource dir = new DirectoryResource();
        // For each file the user chose
        for (File f : dir.selectedFiles()) {
            // Create a parser object to access contents
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            // Get coldest temperature in file
            CSVRecord coldestHour = coldestHourInFile(parser);
            double coldestTempInFile = Double.parseDouble(coldestHour.get("TemperatureF"));
            // If on first file,
            if (name == null) {
                name = f.getPath();
                coldestTemp = coldestTempInFile;
            } 
            // Otherwise, if current file contains coldest temp so far, update variables
            else {
                if (coldestTemp > coldestTempInFile) {
                    name = f.getPath();
                    coldestTemp = coldestTempInFile;
                }
            }
        }
  
        return name;
    }
    
    public void testFileWithColdestTemperature() {
        // Determine which file contains coldest temperature
        String name = fileWithColdestTemperature();
        System.out.println("Coldest day was in file " + name);
        // Determine coldest temperature in that file
        FileResource fr = new FileResource(name);
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldestHour = coldestHourInFile(parser);
        System.out.println("Coldest temperature on that day was " + coldestHour.get("TemperatureF"));
        // Reset parser
        parser = fr.getCSVParser();
        // Print out all temperatures in that file
        System.out.println("All the temperatures on the coldest day were: ");
        for (CSVRecord record : parser) {
            System.out.println(record.get("DateUTC") + ": " + record.get("TemperatureF"));
        }
    }
    
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
        // Allow user to open a CSV file
        FileResource fr = new FileResource();
        // Create a parser object to access file contents
        CSVParser parser = fr.getCSVParser();
        // Test
        CSVRecord coldestHour = coldestHourInFile(parser);
        System.out.println("Coldest hour occurred at " + coldestHour.get("TimeEST"));
    }
}
