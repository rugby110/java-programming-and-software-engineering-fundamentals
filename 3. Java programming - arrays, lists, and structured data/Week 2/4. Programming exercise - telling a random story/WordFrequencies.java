import edu.duke.*;
import java.util.ArrayList;
/**
 * Determines the word that occurs the most often in a file. If more than one word occurs 
 * as the most often, then return the first such word found.
 * 
 * @author Brienna Herold
 * @version Nov. 11, 2016
 */
public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    /**
     * Initializes the private variables.
     */
    public WordFrequencies() {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique() {
        // Clear both ArrayLists
        myWords.clear();
        myFreqs.clear();
        
        // Select file and iterate over every word
        FileResource fr = new FileResource();
        for (String word : fr.words()) {
            word = word.toLowerCase();
            // If word is unique
            if (!myWords.contains(word)) {
                myWords.add(word);
                myFreqs.add(1);
            } else {
                int index = myWords.indexOf(word);
                int freq = myFreqs.get(index);
                myFreqs.set(index, freq + 1);
            }
        }
    }
    
    /**
     * Returns an int that is the index location of the largest value in myFreqs. 
     * If there is a tie, then return the first such value.
     */
    public int findIndexOfMax() {
        int index = 0;
        for (int i = 0; i < myFreqs.size(); i++) {
            if (myFreqs.get(i) > myFreqs.get(index)) {
                index = i;
            }
        }
        return index;
    }
    
    public void tester() {
        findUnique();
        System.out.println("Number of unique words: " + myWords.size());
        for (int k = 0; k < myWords.size(); k++) {
            System.out.println(myFreqs.get(k) + "\t" + myWords.get(k));
        }
        int index = findIndexOfMax();
        System.out.println("The word that occurs the most often is " + myWords.get(index));
        System.out.println("It occurs " + myFreqs.get(index) + " times");
    }

}
