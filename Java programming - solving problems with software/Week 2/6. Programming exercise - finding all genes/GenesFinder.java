
/**
 * GenesFinder finds genes in a DNA string.
 * 
 * testFinder runs printAll with different sequences of dna, 
 * also printing dna.
 * 
 * printAll prints the genes it finds in dna, in the same character 
 * case as dna.
 * @param dna is String being searched
 * 
 * findStopIndex finds closest valid stop codon in dna that occurs 
 * after index. 
 * @param dna is String being searched
 * @param index is index where search starts
 * @return index of beginning of closest valid stop codon, 
 * or -1 if no valid codon
 * 
 * @author Brienna Herold
 * @version Oct. 2, 2016
 */
public class GenesFinder {
    public int findStopIndex(String dna, int index) {
        // Find first occurrence of TAG past index
        int tag = dna.indexOf("tag", index);
        if (tag == -1 || (tag - index) % 3 != 0) {
            // If TAG isn't found or gene isn't a multiple of three, set tag to max
            tag = Integer.MAX_VALUE;
        }
        System.out.println("found TAG: " + tag);
        
        // Find first occurrence of TGA past index
        int tga = dna.indexOf("tga", index);
        if (tga == -1 || (tga - index) % 3 != 0) {
            // If TGA isn't found or gene isn't a multiple of three, set tag to max
            tga = Integer.MAX_VALUE;
        }
        System.out.println("found TGA: " + tga);
        
        // Find first occurrence of TAA past index
        int taa = dna.indexOf("taa", index);
        if (taa == -1 || (taa - index) % 3 != 0) {
            // If TAA isn't found or gene isn't a multiple of three, set tag to max
            taa = Integer.MAX_VALUE;
        }
        System.out.println("found TAA: " + taa);
        
        int smallest = Math.min(taa, Math.min(tag, tga));
        if (smallest < Integer.MAX_VALUE) {
            // Return smallest index
            return smallest;
        } else {
            // No codon is found
            return -1;
        }
    }
    
    public void printAll(String seq) {
        String dna = seq.toLowerCase();
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
                // If stop codon is found, print gene in same case as seq
                if (seq == dna) {
                    System.out.println("GENE: " + dna.substring(start, stop + 3));
                } else {
                    System.out.println("GENE: " + dna.substring(start, stop + 3).toUpperCase());
                }
            }
            
            // Update region of dna to look for codon in
            loc = start + 3;
        }
    }
    
    public void testFinder() {
        // Test 1, should print...
        // ATGAAATGA
        //String dna = "ATGAAATGAAAA";
        
        // Test 2, should print...
        // atgccctaa, atgtctgtaatgtag, atgtag
        String dna = "ccatgccctaataaatgtctgtaatgtaga";
        
        // Test 3, should print...
        // ATGTAA, ATGAATGACTGATAG, ATGCTATGA, ATGTGA
        //String dna = "CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA";
        
        // Test 4, should print...
        // ATGCTGACCTGATAG
        //String dna = "ATGCTGACCTGATAG";
        
        System.out.println("DNA: " + dna);
        printAll(dna);
    }
}
