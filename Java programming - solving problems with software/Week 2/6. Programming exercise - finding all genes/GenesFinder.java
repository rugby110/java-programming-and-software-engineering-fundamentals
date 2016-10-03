
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
    public String findStopIndex(String dna, int index) {
        // create array for possible stop codons
        
        // while traversing dna
            // if reached end of sequence
                // return, no codon has been found
            // look for indexOf("tag")
            // if multiple of 3 from start, is possible stop codon
            
            // look for indexOf("taa")
            // if multiple of 3 from start, is possible stop codon
            
            // look for indexOf("tga")
            // if multiple of 3 from start, is possible stop codon
            
            // return smallest index of possible stop codons
    }
    
    public void printAll(String dna) {
        // while traversing dna
            // find start codon
            // find stop codon based on index of start codon
            // if stop codon is found, 
                // print gene
            // update traversal 
    }
    
    public void testFinder() {
        // pseudocode not complete
        
        // Test 1
        //String dna = "ATGAAATGAAAA";
        //String gene = "ATGAAATGA";
        
        // Test 2
        String dna = "ccatgccctaataaatgtctgtaatgtaga";
        String result = printAll(x);
    }
}
