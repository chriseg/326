/**
 * File: syll.java
 * @author Chris Lynch
 */

import java.util.Scanner;

public class syll {

    public char vowels[] = ['a', 'e', 'i', 'o', 'u', 'y'];
    
    /**
     * Takes a list of words as input and counts the number of syllables in each
     */
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        while(input.hasNext()) {
            String word = input.next();
            System.out.println(word + "  " + word.length());
        }
    
    }

}
