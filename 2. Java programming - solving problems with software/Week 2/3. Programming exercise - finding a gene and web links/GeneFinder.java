/**
 * GeneFinder is a program that finds and prints a gene in a 
 * strand of DNA, and also prints the stop codon that identifies 
 * the gene. If there is not a gene in the DNA string, then 
 * both print the empty string. 
 * 
 * GeneFinder is based on TagFinder (from earlier this week).
 * GeneFinder answers Assignment 1: Finding a Gene.
 * 
 * @author Brienna Herold
 * @version Oct. 1, 2016
 */
import edu.duke.*;
import java.io.*;

public class GeneFinder {
    public String findProtein(String dna) {
        dna = dna.toLowerCase();
        
        // Identify the first start codon ATG in the string
        int start = dna.indexOf("atg");
        if (start == -1) {
            return "";
        }
        
        // Identify the first occurrence of the stop codon TAG after the start codon
        int stop = dna.indexOf("tag", start + 3);
        if (stop != -1 && ((stop + 3) - start) % 3 == 0) {
            /* If the length of the substring between the start and stop codon is a 
             * multiple of three, the gene is the string from the beginning of the 
             * start codon to the end of the stop codon 
             */
            return dna.substring(start, stop + 3);
        } 
        
        stop = dna.indexOf("tga", start + 3);
        if (stop != -1 && ((stop + 3) - start) % 3 == 0) {
            return dna.substring(start, stop + 3);
        } 
        
        stop = dna.indexOf("taa", start + 3);
        if (stop != -1 && ((stop + 3) - start) % 3 == 0) {
            return dna.substring(start, stop + 3);
        }
        
        // If no gene is found yet, then the gene returned is the empty string
        return "";
    }
    
    public String stopCodon(String gene) {
        if (gene.length() > 0) {
            return gene.substring(gene.length() - 3);
        } else {
            return "No stop codon";
        }
    }
    
    public void testing() {
        // Test 1, success
        //String x = "ataaactatgttttaaatgt";
        //String y = "atgttttaa";
        // Test 2, success
        //String x = "AATGCTAGTTTAAATCTGA";  
        //String y = "ATGCTAGTTTAAATCTGA";
        // Test 3, success
        String x = "acatgataacctaag";
        String y = "";
        String result = findProtein(x);
        if (y.toLowerCase().equals(result)) {
            System.out.println("Success for " + y + " length " + y.length());
            System.out.println("Stop codon: " + stopCodon(result));
        } else {
            System.out.println("mistake for input: " + x);
            System.out.println("got: " + result);
            System.out.println("not: " + y);
        }
    }
}
