import edu.duke.*;
/**
 * Keeps track of how many words from a file are of each possible length, 
 * and figures out the most common word length.
 * 
 * @author Brienna Herold
 * @version Nov. 5, 2016
 */
public class WordLengths {
    /**
     * Reads in the words from resource and counts the number of words of each length.
     * @parameter resource is the FileResource to read words from
     * @parameter counts is an array containing counts of each length, the indexes being the lengths
     */
    public void countWordLengths(FileResource resource, int[] counts) {
        // For every word in the file
        for (String word : resource.words()) {
            int len = 0;
            // For every character in the word
            for (int i = 0; i < word.length(); i++) {
                // If character is not a first or last non-character, increment len
                char ch = word.charAt(i);
                if ((i == 0 || i == word.length() - 1) && !Character.isLetter(ch)) {
                    break;
                }
                len++;
            }
            // Add 1 to the element in counts that has the same length 
            // If len is larger than the last index, then count it as the largest index
            int lastIndex = counts.length - 1;
            if (len > lastIndex) {
                counts[lastIndex]++;
            } else {
                counts[len]++;
            }
        }
    }
    
    public void testCountWordLengths() {
        FileResource resource = new FileResource();
        int[] counts = new int[31];
        countWordLengths(resource, counts);
        
        // Print information about the word lengths
        System.out.println("Note this file has words that are:");
        for (int k = 0; k < counts.length; k++) {
            if (counts[k] > 0) {
                System.out.println(counts[k] + " words of length " + k);
            }
        }
        System.out.println("The most common word length in the file is " + indexOfMax(counts));
    }
    
    /**
     * Returns the index position of the largest element in values.
     * @parameter values is the array to search
     */
    public int indexOfMax(int[] values) {
        // Set max to the first value
        int max = values[0]; 
        
        // For every value after the first,
        for (int i = 1; i < values.length; i++) {
            // If the value is larger than max, update max
            if (max < values[i]) {
                max = values[i];
            }
        }
        
        return max;
    }
}
