import edu.duke.*;
import org.apache.commons.csv.*;
/**
 * Write a description of AnalyzeBabyNames here.
 * 
 * @author Brienna Herold
 * @version Oct. 21, 2016
 */
public class AnalyzeBabyNames {
    	public void printNames () {
		FileResource fr = new FileResource();
		// .getCSVParser receives false, indicating the file lacks a header row
		// the data begins at row one. and so we will need to use numbers in .get(x) 
		// to access each column (due to lack of named columns which comes with a header row)
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (numBorn <= 100) {
				System.out.println("Name " + rec.get(0) +
						   " Gender " + rec.get(1) +
						   " Num Born " + rec.get(2));
			}
		}
	}

	public void totalBirths (FileResource fr) {
		int totalBirths = 0;
		int totalBoys = 0;
		int totalGirls = 0;
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
			}
			else {
				totalGirls += numBorn;
			}
		}
		
		System.out.println("total births = " + totalBirths);
		System.out.println("female girls = " + totalGirls);
		System.out.println("male boys = " + totalBoys);
	}

	public void testTotalBirths () {
		//FileResource fr = new FileResource();
		FileResource fr = new FileResource("data/yob2014.csv");
		totalBirths(fr);
	}
}
