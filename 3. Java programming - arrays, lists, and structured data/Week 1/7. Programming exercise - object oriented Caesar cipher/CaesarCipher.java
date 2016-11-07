
/**
 * Encrypts input using the Caesar Cipher algorithm, which works with all letters (both uppercase
 * and lowercase), and decrypts using the same key.
 * 
 * NOTE: This class utilizes OO concepts.
 * 
 * @author Brienna Herold
 * @version Nov. 6, 2016
 */
public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;
    
    public CaesarCipher(int key) {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
        mainKey = key;
    }
    
    public String encrypt(String input) {
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
                int pos = alphabet.indexOf(chUpper);
                char replacee = shiftedAlphabet.charAt(pos);
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
    
    public String decrypt(String input) {
        CaesarCipher cc = new CaesarCipher(26 - mainKey); 
        return cc.encrypt(input);
    }
}
