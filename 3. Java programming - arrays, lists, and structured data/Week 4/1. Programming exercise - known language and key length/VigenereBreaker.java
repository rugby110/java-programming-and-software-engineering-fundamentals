import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    /**
     * Returns a String consisting of every totalSlices-th character from message, 
     * starting at the whichSlice-th character.
     * @parameter message - a String representing the encrypted message
     * @parameter whichSlice - an int indicating the index the slice should start from
     * @parameter totalSlices - an int indicating the length of the key
     */
    public String sliceString(String message, int whichSlice, int totalSlices) {
        String sliced = "";
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            char letter = message.charAt(i);
            sliced += letter;
        }
        return sliced;
    }
    
    public void testSliceString() {
        System.out.println(sliceString("abcdefghijklm", 0, 3).equals("adgjm"));
        System.out.println(sliceString("abcdefghijklm", 1, 3).equals("behk"));
        System.out.println(sliceString("abcdefghijklm", 2, 3).equals("cfil"));
        System.out.println(sliceString("abcdefghijklm", 0, 4).equals("aeim"));
        System.out.println(sliceString("abcdefghijklm", 1, 4).equals("bfj"));
        System.out.println(sliceString("abcdefghijklm", 2, 4).equals("cgk"));
        System.out.println(sliceString("abcdefghijklm", 3, 4).equals("dhl"));
        System.out.println(sliceString("abcdefghijklm", 0, 5).equals("afk"));
        System.out.println(sliceString("abcdefghijklm", 1, 5).equals("bgl"));
        System.out.println(sliceString("abcdefghijklm", 2, 5).equals("chm"));
        System.out.println(sliceString("abcdefghijklm", 3, 5).equals("di"));
        System.out.println(sliceString("abcdefghijklm", 4, 5).equals("ej"));
    };

    /**
     * Finds the shift for each index in the key, fills in the key (which is an array of 
     * integers) and return it. Makes use of the CaesarCracker class, as well as the 
     * sliceString method. 
     * @parameter encrypted - a String representing the encrypted message
     * @parameter klength - an int representing the key length 
     * @parameter mostCommon - an int representing the most common character in the language 
     * of the message
     */
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        for (int i = 0; i < klength; i++) {
            String sliced = sliceString(encrypted, i, klength);
            CaesarCracker cc = new CaesarCracker(mostCommon);
            key[i] = cc.getKey(sliced);
        }
        return key;
    }
    
    public void testTryKeyLength() {
        FileResource fr = new FileResource("VigenereTestData/athens_keyflute.txt");
        String message = fr.asString();
        int klength = "flute".length();
        int[] key = tryKeyLength(message, klength, 'e');  // [5, 11, 20, 19, 4]
    }
    
    /**
     * Put everything together, so that you can create a new VigenereBreaker in BlueJ, 
     * call this method on it, and crack the cipher used on a message.
     */
    public void breakVigenere() {
        // Test this method on the text file athens_keyflute.txt, using key length 5. 
        // The first line should be "SCENE II. Athens. QUINCE'S house."
        FileResource fr = new FileResource();
        String message = fr.asString();
        int[] key = tryKeyLength(message, 5, 'e');
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(message);
        System.out.println(decrypted);
    }
    
}
