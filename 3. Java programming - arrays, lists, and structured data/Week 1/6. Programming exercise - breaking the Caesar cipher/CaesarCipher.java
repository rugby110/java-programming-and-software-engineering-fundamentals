import edu.duke.*;
/**
 * Encrypts input using the Caesar Cipher algorithm, which works with all letters (both uppercase
 * and lowercase), and encrypts using one or two keys. 
 * 
 * @author Brienna
 * @version Nov. 1, 2016
 */
public class CaesarCipher {
    /**
     * Encrypts input using the Caesar Cipher algorithm, 
     * handling both uppercase and lowercase letters.
     * @param input is the String to be encrypted
     * @param key is an int representing the number of positions the shift is
     * @returns encrypted String input
     */
    public String encrypt(String input, int key) {
        // Encrypt alphabet with key 
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String abcEncrypted = abc.substring(key) + abc.substring(0, key);

        // Make passed String dynamically mutable
        StringBuilder encrypted = new StringBuilder(input);
        // For each character
        for (int i = 0; i < encrypted.length(); i++) {
            // Get character
            char ch = encrypted.charAt(i);
            // If char is a letter
            if (Character.isLetter(ch)) {
                // Convert to uppercase (regardless if it is already)
                char chUpper = Character.toUpperCase(ch);
                // Find its position in normal and encrypted abc
                int pos = abc.indexOf(chUpper);
                char replacee = abcEncrypted.charAt(pos);
                // Replace it with letter found in encrypted abc (in correct case)
                if (chUpper == ch) {
                    encrypted.setCharAt(i, replacee);
                } else {
                    encrypted.setCharAt(i, Character.toLowerCase(replacee));
                }
            }
        }
        
        return encrypted.toString();
    }
    
    public void testEncrypt() {
        String input = "Just a test string with lots of eeeeeeeeeeeeeeeees";
        int key = 23;
        String encrypted = encrypt(input, key);
        System.out.println(input + " encrypted with a key of " 
            + key + " is " + encrypted);
        
        input = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        key = 15;
        encrypted = encrypt(input, key);
        System.out.println(input + " encrypted with a key of " 
            + key + " is \n" + encrypted);
    }
    
    /**
     * Reads a file and encrypts the complete file using the Caesar Cipher algorithm, 
     * printing the encrypted message.
     */
    public void testCaesar() {
        int key = 2;
        FileResource fr = new FileResource();
        String message = fr.asString();
        System.out.println(message);
        String encrypted = encrypt(message, key);
        System.out.println("key is " + key + "\n" + encrypted);
    }
    
    /**
     * Encrypts input using the Caesar algorithm with two keys instead of one.
     * @parameter key1 is an int representing the alphabet shift used to encrypt every other character, 
     * starting with the first character
     * @parameter key2 is an int representing the alphabet shift used to encrypt every other character, 
     * starting with the second character
     * @returns encrypted String input
     */
    public String encryptTwoKeys(String input, int key1, int key2) {
        // Encrypt alphabet with key s
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String abcEncrypted1 = abc.substring(key1) + abc.substring(0, key1);
        String abcEncrypted2 = abc.substring(key2) + abc.substring(0, key2);
        
        // Make passed String dynamically mutable
        StringBuilder encrypted = new StringBuilder(input);
        // For each character
        for (int i = 0; i < encrypted.length(); i++) {
            // Get character
            char ch = encrypted.charAt(i);
            // If char is a letter
            if (Character.isLetter(ch)) {
                // Convert to uppercase (regardless if it is already)
                char chUpper = Character.toUpperCase(ch);
                // Find its position in normal abc
                int pos = abc.indexOf(chUpper);
                // Find it in one of the encrypted abcs, depending on index 
                char replacee;
                if (i % 2 == 0) {
                    replacee = abcEncrypted1.charAt(pos);
                } else {
                    replacee = abcEncrypted2.charAt(pos);
                }
                // Replace it with letter found in encrypted abc (in correct case)
                if (chUpper == ch) {
                    encrypted.setCharAt(i, replacee);
                } else {
                    encrypted.setCharAt(i, Character.toLowerCase(replacee));
                }
            }
        }
        
        return encrypted.toString();
    }
    
    public void testEncryptTwoKeys() {
        System.out.println(encryptTwoKeys("At noon be in the conference room with your hat" 
            + " on for a surprise party. YELL LOUD!", 8, 21));
    }
}
