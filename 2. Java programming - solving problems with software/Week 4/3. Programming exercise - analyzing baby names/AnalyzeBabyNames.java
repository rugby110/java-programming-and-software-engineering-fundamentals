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
 * @author Brienna Herold
 * @version Oct. 22, 2016
 */
public class AnalyzeBabyNames {
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        // Determine rank of name in the year they were born
        int rank = getRank(year, name, gender); 
        // Determine name born in newYear that is at the same rank and gender
        String newName = getName(newYear, rank, gender);
        System.out.println(name + " born in " + year + " would be " 
                            + newName + " if she was born in " + newYear);
    }
    
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Isabella", 2012, 2014, "F");
    }
    
    public String getName(int year, int rank, String gender) {
        FileResource fr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
        // For every name in the file
        for (CSVRecord rec : fr.getCSVParser(false)) {
            String name = rec.get(0);
            // Get its rank if gender matches param
            if (rec.get(1).equals(gender)) {
                int currentRank = getRank(year, name, gender);
                // Return name if rank matches param
                if (rank == currentRank) {
                    return name;
                }
            }
        }
        return "NO NAME";
    }
    
    public void testGetName() {
        int year = 2012;
        int rank = 2;
        String gender = "M";
        String name = getName(year, rank, gender);
        System.out.println("In " + year + ", the " + gender + " at rank " + rank + " was " + name);
    }
    
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
            // If gender matches param AND current number of births is greater than that retrieved
            if (record.get(1).equals(gender) && currentNumOfBirthsInt > births) {
                // Increment rank only if the number of births is not found in nums
                // cuz a rank may encompass several names with the same number of births
                if (!nums.contains(currentNumOfBirths)) {
                    rank++;
                }
                nums.add(currentNumOfBirths);
            }
        } 
        
        // If no name met conditions, return -1, otherwise return rank
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
        rank = getRank(2012, "Sophia", "M");
        System.out.println("Rank of 2012, Sophia, M: " + rank);  
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
