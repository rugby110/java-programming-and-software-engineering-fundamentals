import edu.duke.*;
/**
 * @author Brienna Herold
 * @version Nov. 6, 2016
 */
public class TestCaesarCipherTwo {
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Creates an array of letter frequencies in parameter String s 
     * and returns the index of the largest letter frequency.
     */
    private int countLetters(String s) {
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
    private int maxIndex(int[] values) {
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
     * Returns a new String that is every other character from message 
     * starting with the start position.
     */
    private String halfOfString(String message, int start) {
        String halved = "";
        for (int i = start; i < message.length(); i += 2) {
            halved += message.charAt(i);
        }
        return halved;
    }
    
    /**
     * Returns the key to later decrypt parameter String s after calculating 
     * the shift between the most common letter in s, assumed to be E, and E.
     */
    private int getKey(String s) {
        // Calculate the index of the encrypted letter in String s that is most likely to be E
        int index = countLetters(s);
        // Calculate the shift between indexes of encrypted letter and E in the alphabet
        int key = index - alphabet.indexOf('E');
        // Return key
        if (key > 0) {
            return key;
        } else {
            return 26 + key;
        }
    }
    
    public void breakCaesarCipherTwo(String input) {
        String halved1 = halfOfString(input, 0);
        String halved2 = halfOfString(input, 1);
        
        int key1 = getKey(halved1);
        int key2 = getKey(halved2);
        
        CaesarCipherTwo cc2 = new CaesarCipherTwo(key1, key2);
        String decrypted = cc2.decrypt(input);
        
        System.out.println("The decrypted message: ");
        System.out.println(decrypted);
    }
    
    public void simpleTests() {
        //FileResource fr = new FileResource();
        //String message = fr.asString();
        String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        CaesarCipherTwo cc2 = new CaesarCipherTwo(21, 8);
        String encryptedFile = cc2.encrypt(message);
        System.out.println("The encrypted file: ");
        System.out.println(encryptedFile);
        
        // Decrypt file using the same keys it was encrypted with
        String decryptedFile = cc2.decrypt(encryptedFile);
        System.out.println("The decrypted file: ");
        System.out.println(decryptedFile);
        
        // Decrypt file by determining the keys it was encrypted with
        breakCaesarCipherTwo(encryptedFile);
    }
}
