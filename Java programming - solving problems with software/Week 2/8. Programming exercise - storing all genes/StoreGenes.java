
/**
 * StoreGenes stores and processes genes as strings.
 * 
 * testStorageGenes reads a file and calls the functions below on it.
 * 
 * storeAll finds genes in seq and stores them.
 * @param seq is String being searched
 * @return store, the StorageResource object containing genes
 * 
 * findStopIndex finds closest valid stop codon in dna that occurs 
 * after index. 
 * @param dna is String being searched
 * @param index is index where search starts
 * @return index of beginning of closest valid stop codon, 
 * or -1 if no valid codon
 * 
 * cgRatio calculates the ratio of Cs and Gs in dna as a fraction of 
 * the entire strand of DNA.
 * @param dna is String being searched
 * @return ratio 
 *
 * printGenes processes all the genes in a given StorageResource object.
 * @param sr is a StorageResource of strings
 * 
 * @author Brienna Herold
 * @version Oct. 6, 2016
 */

import edu.duke.*;

public class StoreGenes {
    public void testStorageGenes() {
        // Read file (which is a long string of DNA)
        FileResource file = new FileResource("brca1line.fa");
        // Find and store all genes in file
        String sequence = file.asString();
        StorageResource genes = storeAll(sequence);
        // Print the number of genes found
        System.out.println("Number of genes found: " + genes.size());
        printGenes(genes);
    }
   
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
    
    public double cgRatio(String dna) {
        dna = dna.toLowerCase();
        
        // Find all Cs and Gs
        double cg = 0.0;
        for (int i = 0; i < dna.length(); i++) {
            if (dna.charAt(i) == 'c' || dna.charAt(i) == 'g') {
                cg++;
            }
        }
        
        // Return number of Cs and Gs as a fraction of the entire strand of DNA
        double ratio = cg/dna.length();
        return ratio;
    }
    
    public StorageResource storeAll(String seq) {
        // Create StorageResource object
        StorageResource store = new StorageResource();
        // Region of seq to begin looking at
        int loc = 0;
        String dna = seq.toLowerCase();
        
        // While traversing dna
        while (true) {
            // Find start codon in specified region of seq
            int start = dna.indexOf("atg", loc);
            System.out.println("start: " + start);
            
            if (start == -1) {
                // If no start codon is found, quit
                System.out.println("no more start codons");
                break;
            }
            
            // Find stop codon based on index of start codon
            int stop = findStopIndex(dna, start);
            // If stop codon is found, save gene as a variable in correct case
            // and add gene to StorageResource object
            if (stop != -1) {
                if (seq == dna) {
                    String gene = dna.substring(start, stop + 3);
                    store.add(gene);
                } else {
                    String gene = dna.substring(start, stop + 3).toUpperCase();
                    store.add(gene);
                }
            }
 
            // Update region of dna to look for codon in
            loc = start + 3;
            
        }
        
        return store;
    }
    
    public void printGenes(StorageResource sr) {
        // Print all strings that are longer than 60 characters
        int count = 0;
        System.out.println("Printing strings that are longer than 60 characters...");
        for (String str : sr.data()) {
            if (str.length() > 60) {
                count++;
                System.out.println(str);
            }
        }
        System.out.println("Printed " + count + " strings that are longer than 60 characters");
        
        // Print strings whose C-G ratio is higher than 0.35
        int total = 0;
        System.out.println("Printing strings whose C-G ratio is higher than 0.35...");
        for (String str : sr.data()) {
            double ratio = cgRatio(str);
            if (ratio > 0.35) {
                total++;
                System.out.println(str);
            }
        }
        System.out.println("Printed " + total + " strings whose C-G ratio is higher than 0.35");
    }
}

/* NOTE: There is a maximum limit of number of lines which can be printed to console by BlueJ. 
 * To overcome this and see all of my prints, go to the console, then Options > Unlimited Buffering.
 */

