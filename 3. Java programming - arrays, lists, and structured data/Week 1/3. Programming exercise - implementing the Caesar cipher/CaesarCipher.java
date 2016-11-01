/**
 * Encrypts input using the Caesar Cipher algorithm.
 * 
 * @author Brienna
 * @version Oct. 31, 2016
 */
public class CaesarCipher {
    /**
     * Encrypts input using the Caesar Cipher algorithm.
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
                // Find its position in abc
                int pos = abc.indexOf(ch);
                // Replace it with letter at the same position in encrypted abc
                encrypted.setCharAt(i, abcEncrypted.charAt(pos));
            }
        }
        
        return encrypted.toString();
    }
    
    public void testEncrypt() {
        String input = "FIRST LEGION ATTACK EAST FLANK!";
        int key = 23;
        String encrypted = encrypt(input, key);
        System.out.println(input + " encrypted with a key of " 
            + key + " is " + encrypted);
    }
}
