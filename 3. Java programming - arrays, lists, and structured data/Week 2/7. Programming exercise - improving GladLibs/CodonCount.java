import java.util.HashMap;
import edu.duke.*;
/**
 * Finds out how many times each codon occurs in a strand of DNA based 
 * on different reading frames.
 * 
 * @author Brienna Herold
 * @version Nov. 15, 2016
 */
public class CodonCount {
    private HashMap<String, Integer> map;
    
    /** 
     * Initializes any private variables.
     */
    public CodonCount() {
        map = new HashMap<String, Integer>();
    }
    
    /**
     * Builds a new map of codons mapped to their counts from the string dna 
     * with the specified reading frame.
     * @parameter start is an integer (a value of 0, 1, or 2) representing the 
     * position the reading frame starts at.
     * @parameter dna is the dna string. 
     */
    private void buildCodonMap(int start, String dna) {
        // Ensure map is empty before building it
        map.clear();
        
        for (int i = start; i <= dna.length() - 3; i+=3) {
            String codon = dna.substring(i, i+3);
            if (map.containsKey(codon)) {
                int count = map.get(codon);
                map.put(codon, count + 1);
            } else {
                map.put(codon, 1);
            }
        }
    }
    
    /**
     * Returns a String, the codon in a reading frame that has the largest count. 
     * If there are several such codons, return any one of them.
     */
    private String getMostCommonCodon() {
        int count = 0;
        String mostCommonCodon = "";
        for (String codon : map.keySet()) {
            int curr = map.get(codon);
            if (curr > count) {
                mostCommonCodon = codon;
                count = curr;
            }
        }
        return mostCommonCodon;
    }
    
    /**
     * Prints all the codons in the HashMap along with their counts if their count is 
     * between start and end, inclusive.
     */
    private void printCodonCounts(int start, int end) {
        for (String codon : map.keySet()) {
            int count = map.get(codon);
            if (count >= start && count <= end) {
                System.out.println(codon + " " + count); 
            }
        }
    }
    
    public void tester() {
        FileResource fr = new FileResource();
        String dna = fr.asString().toUpperCase().trim();
        for (int start = 0; start <= 2; start++) {
            buildCodonMap(start, dna);
            System.out.println("Reading frame starting with " + start + " results in " + map.size() + 
                " unique codons");
            String mostCommonCodon = getMostCommonCodon();
            System.out.println("and the most common codon is " + mostCommonCodon +
                " with count " + map.get(mostCommonCodon));
            System.out.println("Counts of codons between 1 and 5 inclusive are: ");
            printCodonCounts(1, 5);
            System.out.println();
        }
    }
}
