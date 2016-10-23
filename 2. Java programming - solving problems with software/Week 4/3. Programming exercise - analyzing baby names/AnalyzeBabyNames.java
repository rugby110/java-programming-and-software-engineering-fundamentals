import edu.duke.*;
import org.apache.commons.csv.*;
/**
 * totalBirths() prints the number of unique boy names, the number of unique girl names, the total
 * number of names, total number of births, total number of girl births, and total number of boy 
 * births in the file. 
 * 
 * getRank() returns the rank of the name in the file for the given gender, where rank 1 is the 
 * name with the largest number of births. If the name is not in the file, then -1 is returned.
 * @param year is an int representing the year of the file to parse
 * @param name is a String representing the name to calculate a rank for
 * @param gender is a String representing the gender the name needs to be
 * 
 * @author Brienna Herold
 * @version Oct. 21, 2016
 */
public class AnalyzeBabyNames {
    public int getRank(int year, String name, String gender) {
        FileResource fr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");

        int births = 0;
        // For every name in the file
        for (CSVRecord rec : fr.getCSVParser(false)) {
            // Retrieve and convert number of births if name AND gender match
            if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                births = Integer.parseInt(rec.get(2));
                break;
            }
        }
        
        int rank = 1;
        StorageResource nums = new StorageResource();
        // For every name in the file
        for (CSVRecord record : fr.getCSVParser(false)) {
            // Retrieve current number of births (leaving as String so it's addable to StorageResource)
            String currentNumOfBirths = record.get(2);
            // Convert current number of births so it's useable in numeric comparison
            int currentNumOfBirthsInt = Integer.parseInt(currentNumOfBirths);
            // If gender matches argument AND current number of births is greater than that retrieved
            if (record.get(1).equals(gender) && currentNumOfBirthsInt > births) {
                // Increment rank only if the number of births is not found in nums
                // cuz a rank may encompass several names with the same number of births
                if (!nums.contains(currentNumOfBirths)) {
                    rank++;
                }
                nums.add(currentNumOfBirths);
            }
        } 
        
        // If no name met given conditions, return -1, otherwise return rank
        if (births == 0) {
            return -1;
        } 
        return rank;
    }
    
    public void testGetRank() {
        int rank = getRank(2012, "Mason", "M");
        System.out.println("Rank of 2012, Mason, M: " + rank);  
        rank = getRank(2012, "Mason", "F");
        System.out.println("Rank of 2012, Mason, F: " + rank);  
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
}
