import java.util.*;
import edu.duke.*;
import java.io.File;

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
        HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
        
        // All the languages we have dictionaries for
        String[] dictionaries = new String[8];
        dictionaries[0] = "Danish";
        dictionaries[1] = "Dutch";
        dictionaries[2] = "English";
        dictionaries[3] = "French";
        dictionaries[4] = "German";
        dictionaries[5] = "Italian";
        dictionaries[6] = "Portuguese";
        dictionaries[7] = "Spanish";
        
        // For each dictionary file
        for (int i = 0; i < dictionaries.length; i++) {
            String languageName = dictionaries[i];
            FileResource dictFile = new FileResource("dictionaries/" + languageName);
            HashSet<String> dictionary = readDictionary(dictFile);
            languages.put(languageName, dictionary);
            System.out.println("Just added " + languageName);
        }
        
        FileResource fr = new FileResource("secretmessage3.txt");
        String message = fr.asString();
        breakForAllLangs(message, languages);
    }
    
    /**
     * Parses a file (which contains exactly one dictionary word per line) and returns a HashSet 
     * consisting of all the parsed words.
     */
    public HashSet<String> readDictionary(FileResource fr) {
        // Make a new HashSet of Strings
        HashSet<String> dictionaryWords = new HashSet<String>();
        // Read each line in fr (which should contain exactly one word per line)
        for (String line : fr.lines()) {
            // Convert line to lowercase
            line = line.toLowerCase();
            // Put the line into the HashSet
            dictionaryWords.add(line);
        }
        return dictionaryWords;
    }
    
    /**
     * Split the message into words, iterates over those words, and sees how many of them are 
     * "real words" — that is, how many appear in the dictionary.
     */
    public int countWords(String message, HashSet<String> dictionary) {
        int realWords = 0;
        // Split the message into words
        String[] words = message.split("\\W+");
        // Iterate over those words
        for (String word : words) {
            word = word.toLowerCase();
            if (dictionary.contains(word)) {
                realWords++;
            }
        }
        return realWords;
    }
    
    /**
     * Figures out which decryption gives the largest count of real words, 
     * and returns that String decryption.
     */
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int maxRealWords = 0; 
        int finalKeyLength = 1;
        String decryptionWithMostRealWords = "";

        // Find the most common char in the dictionary
        char mostCommonChar = mostCommonCharIn(dictionary);
        System.out.println("The most common character in the dictionary is " + mostCommonChar);
        
        // For each key length from 1 to 100,
        // NOTE: There's nothing special about 100, could iterate all the way to encrypted.length()
        for (int keylength = 1; keylength <= 100; keylength++) {
            // Decrypt the message
            int[] key = tryKeyLength(encrypted, keylength, mostCommonChar);
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            // Count how many real words decrypted message contains
            int realWords = countWords(decrypted, dictionary);
            if (realWords > maxRealWords) {
                maxRealWords = realWords;
                decryptionWithMostRealWords = decrypted;
                finalKeyLength = keylength;
            }
        }
        
        System.out.println("Message contains " + maxRealWords + " valid words");
        System.out.println("Message decoded with keylength of " + finalKeyLength);
        return decryptionWithMostRealWords;
    }
    
    /**
     * Returns character, of the letters in the English alphabet, that appears 
     * most often in the words in dictionary.
     */
    public char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<Character, Integer> charFreq = new HashMap<Character, Integer>();
        // For each character in each dictionary word
        for (String word : dictionary) {
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                // If character is not a letter in the English alphabet, break
                if (!Character.isLetter(ch)) {
                    break;
                }
                // If charFreq HashMap contains char, 
                if (charFreq.containsKey(ch)) {
                    int freq = charFreq.get(ch);
                    charFreq.put(ch, freq + 1);
                } else {
                    charFreq.put(ch, 1);
                }
            }
        }
        // Find most common character in dictionary
        char mostCommonChar = ' ';
        int maxCount = 0;
        for (char ch : charFreq.keySet()) {
            int freq = charFreq.get(ch);
            // If on first loop, initialize tracker variables 
            if (Character.isSpaceChar(mostCommonChar)) {
                mostCommonChar = ch;
                maxCount = freq;
            } else {
                if (freq > maxCount) {
                    mostCommonChar = ch;
                    maxCount = freq;
                }
            }
        }
        return mostCommonChar;
    }
    
    /**
     * Breaks the encryption for each language, and prints the message in the 
     * language that gives the best results.
     * @parameter encrypted is a String representing the encrypted message
     * @parameter languages is a HashMap that maps a String representing the name of a language 
     * to a HashSet of Strings containing the words in that language 
     */
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        // Iterate over HashMap languages to get the name of each language
        for (String language : languages.keySet()) {
            // Get the language's mapped HashSet dictionary of words
            HashSet<String> dictionary = languages.get(language);
            System.out.println("Analyzing the text with " + language);
            // Call breakForLanguage with the message and the dictionary
            String decrypted = breakForLanguage(encrypted, dictionary);
            // Print the decrypted message
            System.out.println(decrypted);
        }
    }
}
