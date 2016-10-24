import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
/**
 * totalBirths() prints the number of unique boy names, the number of unique girl names, the total
 * number of names, total number of births, total number of girl births, and total number of boy 
 * births in the file. 
 * 
 * getRank() returns the rank of the name in the file for the given gender, where rank 1 is the 
 * name with the largest number of births. If the name is not in the file, then -1 is returned.
 * @param year is an int representing the year of the file to parse
 * @param name is a String representing the name to determine a rank for
 * @param gender is a String representing the gender the name needs to be
 * 
 * getName() returns the name of the person in the file at this rank, for the given gender, 
 * where rank 1 is the name with the largest number of births. If the rank does not exist in the file, 
 * then “NO NAME” is returned.
 * @param year is an int representing the year of the file to parse
 * @param rank is an int representing the rank of the name to return
 * @param gender is a String representing the gender the name needs to be
 * 
 * whatIsNameInYear() determines what name would have been named if the given name were born in a 
 * different year, based on the same popularity.
 * @param name is a String representing the name to determine a rank for
 * @param year is an int representing the year the name was born in
 * @param newYear is an int representing the year to determine the new name
 * @param gender is a String representing the gender both names need to be
 * 
 * yearOfHighestRank() selects a range of files to process and returns an integer, the year with 
 * the highest rank for the name and gender. If the name and gender are not in any of the selected 
 * files, it returns -1.
 * @param name is a String representing the name
 * @param gender is a String representing the gender
 * 
 * getAverageRank()  selects a range of files to process and returns a double representing the 
 * average rank of the name and gender over the selected files. It should return -1.0 if the name 
 * is not ranked in any of the selected files.
 * @param name is a String representing the name
 * @param gender is a String representing the gender
 * 
 * getTotalBirthsRankedHigher() returns an integer, the total number of births of those names with 
 * the same gender and same year who are ranked higher than name.
 * @param year is an int representing the year
 * @param name is a String representing the name
 * @param gender is a String representing the gender
 * 
 * @author Brienna Herold
 * @version Oct. 23, 2016
 */
public class AnalyzeBabyNames {
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        // Get number of births for given name and gender
        int numOfBirths = 0;
        for (CSVRecord rec : getFileParser(year)) {
            if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                numOfBirths = Integer.parseInt(rec.get(2));
            }
        }
        
        // Add up number of births greater than that for given name and gender
        int totalBirths = 0;
        for (CSVRecord rec : getFileParser(year)) {
            String currentGender = rec.get(1);
            // If name is not given name AND current gender matches param 
            // AND current num of births is higher than for given name, 
            if (!rec.get(0).equals(name) && currentGender.equals(gender) && 
                Integer.parseInt(rec.get(2)) >= numOfBirths) {
                // Add number of births to total
                totalBirths += Integer.parseInt(rec.get(2));
            }
        }
        return totalBirths;
    }
    
    public void testGetTotalBirthsRankedHigher() {
        int year = 1990;
        String name = "Emily";
        String gender = "F";
        int totalBirths = getTotalBirthsRankedHigher(year, name, gender);
        System.out.println("Total number of births of those with the same gender who " +
                            "are ranked higher than " + name + ", " + gender + " in " + year
                            + ": " + totalBirths);
    }
    
    public double getAverageRank(String name, String gender) {
        // Allow user to select a range of files
        DirectoryResource dir = new DirectoryResource();
        double totalRank = 0.0;
        int count = 0;
        for (File f : dir.selectedFiles()) {
            // Extract current year from file name
            int currentYear = Integer.parseInt(f.getName().substring(3,7));
            // Determine rank of name in current year
            int currentRank = getRank(currentYear, name, gender);
            // Add rank to total and increment count
            totalRank += currentRank;
            count++;
        }
        // Return calculated average rank
        if (totalRank == 0) {
            return -1;
        }
        double average = totalRank/count;
        return average;
    }
    
    public void testGetAverageRank() {
        String name = "Robert";
        String gender = "M";
        double avg = getAverageRank(name, gender);
        System.out.println("Average rank of " + name + ", " + gender + ": " + avg);
    }
    
    public int yearOfHighestRank(String name, String gender) {
        // Allow user to select a range of files
        DirectoryResource dir = new DirectoryResource();
        int year = 0;
        int rank = 0;
        // For every file the user selected
        for (File f : dir.selectedFiles()) {
            // Extract current year from file name
            int currentYear = Integer.parseInt(f.getName().substring(3,7));
            // Determine rank of name in current year
            int currentRank = getRank(currentYear, name, gender);
            System.out.println("Rank in year " + currentYear + ": " + currentRank);
            // If current rank isn't invalid
            if (currentRank != -1) {
                // If on first file or if current rank is higher than saved rank
                if (rank == 0 || currentRank < rank) {
                    // Update tracker variables
                    rank = currentRank;
                    year = currentYear;
                } 
            }
        }
        
        if (year == 0) {
            return -1; 
        }
        return year;
    }
    
    public void testYearOfHighestRank() {
        String name = "Mich";
        String gender = "M";
        int year = yearOfHighestRank(name, gender);
        System.out.println("The year with the highest rank for " + name + " (gender " + gender
                            + ") is " + year);
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        // Determine rank of name in the year they were born
        int rank = getRank(year, name, gender); 
        System.out.println("The rank of Owen is " + rank);
        // Determine name born in newYear that is at the same rank and gender
        String newName = getName(newYear, rank, gender);
        System.out.println(name + " born in " + year + " would be " 
                            + newName + " if born in " + newYear);
    }
    
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public String getName(int year, int rank, String gender) {
        int currentRank = 0;
        String name = "";
        // For every name in the file
        for (CSVRecord rec : getFileParser(year)) {
            // Get its rank if gender matches param
            if (rec.get(1).equals(gender)) {
                // Return last name whose rank matches param (file is already in order of rank)
                if (currentRank == rank) {
                    return name;
                }
                name = rec.get(0);
                currentRank++;
            }
        }
        
        return "NO NAME";
    }
    
    public void testGetName() {
        int year = 1980;
        int rank = 350;
        String gender = "F";
        String name = getName(year, rank, gender);
        System.out.println("In " + year + ", the " + gender + " at rank " + rank + " was " + name);
    }
    
    public int getRank(int year, String name, String gender) {
        int rank = 1;
        for (CSVRecord rec : getFileParser(year)) {
            // Increment rank if gender matches param
            if (rec.get(1).equals(gender)) {
                // Return rank if name matches param
                if (rec.get(0).equals(name)) {
                    return rank;
                }
                rank++;
            }
        }
        return -1;
    }
    
    public void testGetRank() {
        int year = 1971;
        String name = "Frank";
        String gender = "M";
        int rank = getRank(year, name, gender);
        System.out.println("Rank of " + name + ", " + gender + ", in " + year + ": " + rank);  
    }

    public void totalBirths () {
        // Allow user to choose file
        FileResource fr = new FileResource();
        // Set variables to initial values
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        // Create empty lists to hold unique girl and boy names
        StorageResource uniqueGirlNames = new StorageResource();
        StorageResource uniqueBoyNames = new StorageResource();
        // For every name
        for (CSVRecord rec : fr.getCSVParser(false)) {
            // Retrieve number of births for that name
            int numBorn = Integer.parseInt(rec.get(2));
            // Add number of births to total number of births
            totalBirths += numBorn;
            // Increment total count of names
            totalNames++;
            // Retrieve name
            String name = rec.get(0);
            // If gender is male
            if (rec.get(1).equals("M")) {
                // Add number of births to total number of births for males
                totalBoys += numBorn;
                // Add name to list of boy names if not already on list
                if (!uniqueBoyNames.contains(name)) {
                    uniqueBoyNames.add(name);
                }
            }
            // Otherwise (if gender is female)
            else {
                // Add number of births to total number of births for females
                totalGirls += numBorn;
                // Add name to list of girl names if not already on list
                if (!uniqueGirlNames.contains(name)) {
                    uniqueGirlNames.add(name);
                }
            }
        }
        System.out.println("Unique boy names: " + uniqueBoyNames.size());
        System.out.println("Unique girl names: " + uniqueGirlNames.size());
        System.out.println("Total names: " + totalNames);
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
    }
    
    public CSVParser getFileParser(int year) {
        // If in testing, use below
        //FileResource fr = new FileResource(String.format("data/us_babynames_test/yob%sshort.csv", year));
        //return fr.getCSVParser(false);
        
        // If in production, use below 
        FileResource fr = new FileResource(String.format("data/us_babynames_by_year/yob%s.csv", year));
        return fr.getCSVParser(false);
    }
}
