import java.util.ArrayList;
import edu.duke.*;
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author Brienna Herold
 * @version (a version number or a date)
 */
public class CharactersInPlay {
    private ArrayList<String> characters;
    private ArrayList<Integer> counts;
    
    public CharactersInPlay() {
        characters = new ArrayList<String>();
        counts = new ArrayList<Integer>();
    }
    
    /** 
     * Updates the two ArrayLists, adding the character’s name if it is not already there, 
     * and counts this line as one speaking part for this person.
     * @param person is a String representing the character's name
     */
    public void update(String person) {
        if (!characters.contains(person)) {
            characters.add(person);
            counts.add(1);
        } else {
            int index = characters.indexOf(person);
            int count = counts.get(index);
            counts.set(index, count + 1);
        }
    }
    
    /**
     * Opens a file, and reads the file line-by-line. For each line, if there is a period 
     * on the line, extract the possible name of the speaking part, and call update to count 
     * it as an occurrence for this person.
     */
    public void findAllCharacters() {
        // Clear ArrayLists before opening a new file
        characters.clear();
        counts.clear();
        
        // Open a file and iterate over its words
        FileResource fr = new FileResource();
        for (String line : fr.lines()) {
            if (line.contains(".")) {
                String person = line.split("\\.")[0].trim();
                update(person);
            }
        }
        
    }
    
    public void tester() {
        findAllCharacters();
        
        // Print main character and the number of speaking parts this character has
        System.out.println("Number of characters: " + characters.size());
        for (int k = 0; k < characters.size(); k++) {
            int count = counts.get(k);
            // NOTE: A main character is one who has greater than 2 speaking parts
            if (count > 50) {
                System.out.println(characters.get(k) + " has " + count + " speaking parts");
            }    
        }
        
        charactersWithNumParts(10, 15);
    }
    
    /**
     * Prints out the names of all those characters that have exactly number speaking parts, 
     * where number is greater than or equal to num1 and less than or equal to num2. Assume that
     * num1 is less than or equal to num2. 
     * @parameter num1 is an int representing the minimum of speaking parts
     * @parameter num2 is an int representing the maximum of speaking parts
     */
    public void charactersWithNumParts(int num1, int num2) {
        System.out.println("Characters who have exactly " + num1 + "-" + num2 + " speaking parts:");
        for (int k = 0; k < characters.size(); k++) {
            int count = counts.get(k);
            if (count >= num1 && count <= num2) {
                System.out.println(characters.get(k));
            }
        }
    }

}
