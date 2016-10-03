
/**
 * findStopIndex finds a valid stop codon in dna that occurs after index. 
 * If no valid stop codon is found, return dna.length()
 * @param dna is String being searched
 * @param index is index where search starts
 * @return index of beginning of a valid stop codon, 
 * or dna.length() if no valid codon
 * 
 * @author Brienna Herold
 * @version Oct. 2, 2016
 */
public class GenesFinder {
    public int findStopIndex(String dna, int index) {
        // NOTE: probably needs a better method than using an array 
        // to hold indexes for future comparison for smallest index?
        
        int pos;
        // Declare an array of integers to save hold indexes
        int[] positions;
        // Allocate memory for 3 possible codons
        positions = new int[3];

        // Find first occurrence of TAG past index
        pos = dna.indexOf("tag", index);
        if (pos != -1 && (pos - index) % 3 == 0) {
            // If TAG is found & gene is a multiple of three, hold index
            System.out.println("found TAG: " + pos);
            positions[0] = pos;
        }
        // Find first occurrence of TGA past index
        pos = dna.indexOf("tga", index);
        if (pos != -1 && (pos - index) % 3 == 0) {
            // If TGA is found & gene is a multiple of three, hold index
            System.out.println("found TGA: " + pos);
            positions[1] = pos;
        }
        // Find first occurrence of TAA past index
        pos = dna.indexOf("taa", index);
        if (pos != -1 && (pos - index) % 3 == 0) {
            // If TAA is found & gene is a multiple of three, hold index
            System.out.println("found TAA: " + pos);
            positions[2] = pos;
        }
        
        // Return smallest index (COULD BE IMPROVED)
        if (positions.length > 0) {
            int smallest = Integer.MAX_VALUE;
            for (int i = 0; i < positions.length; i++) {
                if (smallest > positions[i] && positions[i] > 0) {
                    // Note: positions[i] > 0 is included
                    // to circumvent empty array elements being selected
                    smallest = positions[i];
                }
            }
            return smallest;
        } else {
            // No codon is found
            return -1;
        }
    }
    
    public void printAll(String dna) {
        dna = dna.toLowerCase();
        int loc = 0;
        
        // While traversing dna
        while (true) {
            // Find start codon in specified region of dna
            int start = dna.indexOf("atg", loc);
            System.out.println("start: " + start);
            
            if (start == -1) {
                // If no start codon is found, quit
                System.out.println("no more start codons");
                break;
            }
            
            // Find stop codon based on index of start codon
            int stop = findStopIndex(dna, start);
            if (stop != -1) {
                // If stop codon is found, print gene
                System.out.println("Gene: " + dna.substring(start, stop + 3));
            }
            
            // Update region of dna to look for codon in
            loc = start + 3;
        }
    }
    
    public void testFinder() {
        // Test 1
        //String dna = "ATGAAATGAAAA";
        //String gene = "ATGAAATGA";
        
        // Test 2
        //String dna = "ccatgccctaataaatgtctgtaatgtaga";
        
        // Test 3
        String dna = "CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA";
        
        
        System.out.println("DNA: " + dna);
        printAll(dna);
    }
}
