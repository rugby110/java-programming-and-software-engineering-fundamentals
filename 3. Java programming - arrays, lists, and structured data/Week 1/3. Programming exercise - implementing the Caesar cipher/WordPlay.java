
/**
 * Transforms words from a file into another form, such as replacing vowels with an asterisk.
 * 
 * @author Brienna Herold
 * @version Oct. 31, 2016
 */
public class WordPlay {
    /** 
     * Tests if ch is a vowel (one of 'a', 'e', 'i', 'o', or 'u' or 
     * the uppercase versions).
     * @parameter ch is a String representing the vowel
     * @returns a Boolean
     */
    public Boolean isVowel(char ch) {
        ch = Character.toLowerCase(ch);
        Boolean bool = false;
        String vowels = "aieou";
        if (vowels.indexOf(ch) != -1) {
            bool = true;
        }
        return bool;
    }
    
    public void testIsVowel() {
        char ch = 'f';
        Boolean answer = isVowel(ch);
        System.out.println(ch + " is a vowel: " + answer);
    }
    
    /**
     * Replaces all the vowels (uppercase or lowercase) in the String phrase with char ch. 
     * @param phrase is the String to replace vowels in
     * @param ch is the char to replace the vowels with
     * @returns the String phrase with replaced vowels. 
     */
    public String replaceVowels(String phrase, char ch) {
        // Make passed String dynamically mutable
        StringBuilder replaced = new StringBuilder(phrase);
        // For each character
        for (int i = 0; i < replaced.length(); i++) {
            // If character is a vowel, 
            if (isVowel(replaced.charAt(i))) {
                // Replace with passed ch
                replaced.setCharAt(i, ch);
            }
        }
        // Convert back to String and return
        return replaced.toString();
    }
    
    public void testReplaceVowels() {
        String phrase = "Hello World";
        char ch = '*';
        String replaced = replaceVowels(phrase, ch);
        System.out.println("The phrase \"" + phrase + "\" with its vowels replaced with '" 
            + ch + "' is " + replaced);
    }
    
    /**
     * Replaces char ch in the String phrase with '*' or '+', depending on location (not index) of ch:
     * ‘*’ if it is in an odd number location in the string (e.g. first character is odd)
     * ‘+’ if it is in an even number location in the string 
     * @param phrase is the String to replace characters in
     * @param ch is the char to replace
     * @returns the String phrase with replaced characters
     */
    public String emphasize(String phrase, char ch) {
        // Make passed String dynamically mutable
        StringBuilder replaced = new StringBuilder(phrase);
        // For each character
        for (int i = 0; i < replaced.length(); i++) {
            // If character (upper or lower case) equals ch 
            if (replaced.charAt(i) == ch || Character.toLowerCase(replaced.charAt(i)) == ch) {
                // If location is odd (even index), replace with *
                if (i % 2 == 0) {
                    replaced.setCharAt(i, '*');
                } 
                // If location is even (odd index), replace with +
                else {
                    replaced.setCharAt(i, '+');
                }
            }
        }
        // Convert back to String and return
        return replaced.toString();
    }
    
    public void testEmphasize() {
        String phrase = "dna ctgaaactga";
        char ch = 'a';
        String replaced = emphasize(phrase, ch);
        System.out.println("Phrase '" + phrase + "' replaced, " + replaced.equals("dn* ctg+*+ctg+") 
            + ": " + replaced);
        
        phrase = "Mary Bella Abracadabra";
        replaced = emphasize(phrase, ch);
        System.out.println("Phrase '" + phrase + "' replaced, " + replaced.equals("M+ry Bell+ +br*c*d*br+") 
            + ": " + replaced);
    }
}
