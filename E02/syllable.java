/**
 * Syllable.java
 * @author Chris Eathorne-Gould
 * @author Chris Lynch
 * 6th July 2015
 */

import java.util.Scanner;


/**
 * A class that will determine the number of syllables from an input from stdin.
 * Works on the assumption that the number of syllables is roughly the number
 * of vowels in a word.
 */

public class syllable {

    /* An array containing the vowels and 'y' */
    public static char [] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};

    /**
     * Main method
     */
    public static void main(String [] args) {
        int vowel_count = 0;
        Scanner input = new Scanner(System.in);
        while(input.hasNext()) {
            char [] chars = input.next().toCharArray();
            vowel_count = 0;
            for (int i = 0; i < chars.length; ++i) {
                /* Exception to cover the cases where the word is too short to
                 * contain more than one syllable. */
                if (chars.length <= 4) {
                    vowel_count = 1;
                    break;
                }
                if (isVowel(chars[i])) {
                    if (i == 0) {
                        ++vowel_count;
                    }
                    /* Exception to cover the case where two or more vowels
                     * occur together */
                    else if (!isVowel(chars[i-1])) {
                        ++vowel_count;
                    }
                }
            }
            System.out.print(vowel_count + "\n" );
        }
    }

    /**
     * Checks if the given charater is a vowel
     * @param c character to check
     * @return true if the character c is a vowel
     */
    public static boolean isVowel(char c) {
        for (char vowel : vowels) {
            if(c == vowel) {
                return true;
            }
        }
        return false;
    }
}
