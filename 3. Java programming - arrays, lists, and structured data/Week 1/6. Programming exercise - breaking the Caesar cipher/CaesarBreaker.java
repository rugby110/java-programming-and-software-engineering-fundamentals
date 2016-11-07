import edu.duke.*;
/**
 * Decrypts a message that was encrypted with one or two keys, 
 * using statistical letter frequencies of English text.
 * 
 * @author Brienna Herold
 * @version Nov. 6, 2016
 */
public class CaesarBreaker {
    /**
     * Creates an array of letter frequencies in parameter String s 
     * and returns the index of the largest letter frequency.
     */
    public int countLetters(String s) {
        int[] freqs = new int[26];
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
     * Returns the key to later decrypt parameter String s after calculating 
     * the shift between the most common letter in s, assumed to be E, and E.
     */
    public int getKey(String s) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Calculate the index of the encrypted letter in String s that is most likely to be E
        int maxIndex = countLetters(s);
        // Calculate the shift between indexes of encrypted letter and E in the alphabet
        int key = maxIndex - alphabet.indexOf('E');

        System.out.println("The encrypted string: " + s);
        System.out.println("The letter with the highest frequency: " + alphabet.charAt(maxIndex));
        
        // Return key
        if (key > 0) {
            return key;
        } else {
            return 26 + key;
        }
        
    }
    
    /**
     * Returns a new String that is every other character from message 
     * starting with the start position.
     */
    public String halfOfString(String message, int start) {
        String halved = "";
        for (int i = start; i < message.length(); i += 2) {
            halved += message.charAt(i);
        }
        return halved;
    }
    
    public void testHalfOfString() {
        String message = "Qbkm Zgis";
        String halved = halfOfString(message,0);
        if (halved.equals("Qk gs")) {
            System.out.println(message + " halved correctly: " + halved);
        } else {
            System.out.println(message + " halved incorrectly: " + halved);
        }
        halved = halfOfString(message,1);
        if (halved.equals("bmZi")) {
            System.out.println(message + " halved correctly: " + halved);
        } else {
            System.out.println(message + " halved incorrectly: " + halved);
        }
    }
    
    /**
     * Creates a CaesarCipher object in order to encrypt encrypted string. 
     * @parameter encrypted is a String representing the encrypted message to encrypt
     */
    public void decrypt(String encrypted, int key) {
        CaesarCipher cc = new CaesarCipher();
        String message = cc.encrypt(encrypted, key);
        System.out.println(message);
    }
    
    public void testDecrypt() {
        String encrypted = "Grpq x qbpq pqofkd tfqe ilqp lc bbbbbbbbbbbbbbbbbp";
        int key = getKey(encrypted);
        decrypt(encrypted, 26 - key);
    }
    
    /**
     * Attempts to determine the two keys used to encrypt the message, prints the two keys, 
     * and then returns the decrypted String with those two keys.
     * @parameter encrypted is a String representing the encrypted message
     */
    public String decryptTwoKeys(String encrypted) {
        String halved1 = halfOfString(encrypted, 0);
        String halved2 = halfOfString(encrypted, 1);
        int key1 = getKey(halved1);
        int key2 = getKey(halved2);
        System.out.println("The two keys found: " + key1 + ", " + key2);
        CaesarCipher cc = new CaesarCipher();
        String message = cc.encryptTwoKeys(encrypted, 26 - key1, 26 - key2);
        return message;
    }
    
    public void testDecryptTwoKeys() {
        String encrypted = "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        String message = decryptTwoKeys(encrypted);
        System.out.println("The decrypted message is " + message);
        
        // To decrypt a specific example because decryption currently only works based on
        // the assumption that the most frequent letter is always E
        CaesarCipher cc = new CaesarCipher();
        message = cc.encryptTwoKeys(encrypted, 26 - 14, 26 - 24);
        System.out.println("The decrypted message is " + message);
        
        // To decrypt an encrypted file
        FileResource fr = new FileResource();
        message = decryptTwoKeys(fr.asString());
        System.out.println("The decrypted message is " + message);
    }
}
