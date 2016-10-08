
/**
 * Your friend is trying to solve the following problem using Java:
 * 
 * Write a method that finds each occurrence of “abc_” in a String 
 * input (where _ is a single character) and prints “bc_” for each 
 * such occurrence. For example, findAbc(“abcdefabcghi”) should print:
 * 
 * bcd
 * bcg
 * 
 * You friend has just finished writing a solution and needs help testing it.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Debugging_1 {
    public void findAbc(String input) {
        int index = input.indexOf("abc");
        while (true) {
            if (index == -1) {
                break;
            }
            //if (index >= input.length() - 3) {
            //    break;
            //}
            System.out.println("index+1: " + (index+1));
            System.out.println("index+4: " + (index+4));
            // NOTE: Make sure that the print command is not concatenating 
            // "index" + "4" by grouping the value (index+4) 
            String found = input.substring(index+1, index+4);
            System.out.println(found);
            index = input.indexOf("abc", index+4);
        }
    }
       public void test() {
         //findAbc("abcd");  // prints "bcd"
         //findAbc("woiehabchi");  // prints "bch"
         findAbc("abcbbbabcdddabc");  // throws a StringIndexOutOfBoundsException 
         // this error can be fixed by adding the commented out if statement in findAbc 
    }
}
