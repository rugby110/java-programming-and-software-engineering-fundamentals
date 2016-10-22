import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.ArrayList;
/**
 * Write a description of AnalyzeBabyNames here.
 * 
 * @author Brienna Herold
 * @version Oct. 21, 2016
 */
public class AnalyzeBabyNames {
    public int getRank(int year, String name, String gender) {
        FileResource fr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
        
        int births = 0;
        // Loop through every row in the file
        for (CSVRecord rec : parser) {
            // Retrieve number of births if name AND gender match
            if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                System.out.println(rec);
                births = Integer.parseInt(rec.get(2));
                break;
            }
        }
        
        int rank = 1;
        parser = fr.getCSVParser(false);
        ArrayList<Integer> ranks = new ArrayList<Integer>();
        // Loop through every row again
        for (CSVRecord row : parser) {
            int currentNumOfBirths = Integer.parseInt(row.get(2));
            // If same gender AND current number of births is greater than that retrieved
            if (row.get(1).equals(gender) && currentNumOfBirths > births) {
                // Increment rank by 1 if number of births is unique
                // cuz every rank may have more than 1 record with the same number of births
                // and we don't want to rank down for those duplicates
                if (!ranks.contains(currentNumOfBirths)) {
                    rank++;
                }
                ranks.add(currentNumOfBirths);
            }
        } 
        
        if (births == 0) {
            return -1;
        } else {
            return rank;
        }
    }
    
    public void testGetRank() {
        int rank = getRank(2012, "Mason", "M");
        System.out.println("Rank of 2012, Mason, M: " + rank);  
        rank = getRank(2012, "Mason", "F");
        System.out.println("Rank of 2012, Mason, F: " + rank);  
    }

    public void totalBirths () {
        FileResource fr = new FileResource();
        
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        ArrayList<String> uniqueGirlNames = new ArrayList<String>();
        ArrayList<String> uniqueBoyNames = new ArrayList<String>();
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            totalNames++;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                String name = rec.get(0);
                uniqueBoyNames.add(name);
            }
            else {
                totalGirls += numBorn;
                String name = rec.get(0);
                uniqueGirlNames.add(name);
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
