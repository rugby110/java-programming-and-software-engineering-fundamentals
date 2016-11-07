import edu.duke.*;
/**
 * @author Brienna Herold
 * @version Nov. 6, 2016
 */
public class TestCaesarCipher {
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Creates an array of letter frequencies in parameter String s 
     * and returns the index of the largest letter frequency.
     */
    public int countLetters(String s) {
        int[] freqs = new int[26];
        // For every character in the string
        for (int i = 0; i < s.length(); i++) {
            // Extract character
            char ch = s.charAt(i);
            // If character is a letter
            if (Character.isLetter(ch)) {
                // Identify its position in the alphabet
                int index = alphabet.indexOf(Character.toUpperCase(ch));
                // Use this position to update its freq in the freqs array 
                freqs[index]++;
            }
        }
        // Return the index of the largest letter frequency
        return maxIndex(freqs);
    }
    
    /**
     * Calculates the index of the largest letter frequency from an array of all letter frequencies.
     * @parameter freqs is an array containing letter frequencies, each index representing 
     * the corresponding index in the alphabet (e.g. 0 represents the 0th letter, a)
     * @returns the index as an int
     */
    public int maxIndex(int[] values) {
        // Set max to the first index
        int max = 0;
        // For every freq after the first freq,
        for (int i = 1; i < values.length; i++) {
            // If freq is larger than freq at max index, set max index to current index
            if (values[max] < values[i]) {
                max = i;
            }
        }
        return max;
    }
    
    /**
     * Decrypts and prints String input after calculating the key, based on the shift between 
     * the most common letter in s, assumed to be E, and E. 
     */ 
    public void breakCaesarCipher(String input) {
        // Calculate the index of the encrypted letter in String s that is most likely to be E
        int maxIndex = countLetters(input);
        // Calculate the shift between indexes of encrypted letter and E in the alphabet
        int shift = maxIndex - alphabet.indexOf('E');
        
        int key;
        if (shift > 0) {
            key = shift;
        } else {
            key = 26 + shift;
        }
        
        CaesarCipher cc = new CaesarCipher(key);
        String decrypted = cc.decrypt(input);
        
        System.out.println("The decrypted message: ");
        System.out.println(decrypted);
    }
    
    public void simpleTests() {
        FileResource fr = new FileResource();
        CaesarCipher cc = new CaesarCipher(18);
        String encryptedFile = cc.encrypt(fr.asString());
        System.out.println("The encrypted file: ");
        System.out.println(encryptedFile);
        
        // Decrypt file using the same key it was encrypted with
        String decryptedFile = cc.decrypt(encryptedFile);
        System.out.println("The decrypted file: ");
        System.out.println(decryptedFile);
        
        // Decrypt file by determining the key it was encrypted with
        breakCaesarCipher(encryptedFile);
    }
}
