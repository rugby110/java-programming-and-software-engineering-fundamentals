
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
public class Debugging_2 {
    public void findAbc(String input) {
        int index = input.indexOf("abc");
        while (true) {
            if (index == -1) {
                break;
            }
            if (index >= input.length() - 3) {
                break;
            }
            System.out.println("index+1: " + (index+1));
            System.out.println("index+4: " + (index+4));
            String found = input.substring(index+1, index+4);
            System.out.println("index before updating: " + index);
            System.out.println(found);
            index = input.indexOf("abc", index+4);
            //Switching to the below line will fix the error
            //index = input.indexOf("abc",index+3);
            System.out.println("index after updating: " + index);
        }
    }
       public void test() {
         //findAbc("abcdkfjsksioehgjfhsdjfhksdfhuwabcabcajfieowj");  // error 
         //findAbc("qwertyabcuioabcp");  // no error
         findAbc("abcabcabcabca");  // error
         /* When one occurrence of “abc” is followed immediately by another occurrence of “abc”, 
          * the method does not find the second “abc” because it starts searching at the “b” rather 
          * than at the “a” following the first “abc”
          */
    }
}
