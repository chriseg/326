/**
 * File: poke.java
 * @author Chris Lynch
 */

import java.util.*;

public class poke {

    private static char[] suit = {'C', 'D', 'H', 'S'};
    private static String[] std_cards = {"2", "3", "4", "5", "6", "7", "8",
                                       "9", "10", "J", "Q", "K", "A"};
    private static String [] sorted = new String[5];
    
    /**
     * Takes input from stdin and attempts to interpret it as a standard
     * 5-card poker hand. A 5-card hand will be printed to stdout in a 
     * standard format with cards consisting of capital alphanumeric
     * character pairs indicating card value and card suit respectively, 
     * with each seperated by single spaces.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
	String word = null;
	boolean invalid = false;
        Scanner input = new Scanner(System.in);
        while(input.hasNextLine()) {
	    word = input.nextLine(); 
            String delimiter = null;
            invalid = false;
            /* If the first or last character is a seperator then the input is
               assumed to be invalid */
            int e = word.length() - 1;
            if((word.charAt(0) == ' ' || word.charAt(0) == '/' ||
                word.charAt(0) == '-') ||
               (word.charAt(e) == ' ' || word.charAt(e) == '/' ||
                word.charAt(e) == '-')) {
                //System.err.println("Hand preceeded by a seperator");
                invalid = true;
            }
            /* Only continue if input is valid so far */
            if(!invalid) {
                /* Search input for a valid seperator and break as soon as one
                   is found */
                for(int i = 1; i < word.length() - 1; i++) {
                    if(word.charAt(i) == ' ' || word.charAt(i) == '/' ||
                       word.charAt(i) == '-') {
                        delimiter = String.valueOf(word.charAt(i));
                        break;
                    }
                }
                /* If there is no clear seperator then the input is assumed to
                   be invalid */
                if(delimiter == null) {
                    //System.err.println("Valid seperator not found");
                    invalid = true;
                } else {
                    Scanner hand = new Scanner(word);
                    hand.useDelimiter(delimiter);
                    String[] cards = new String[5];
                    int card_no = 0;
                    /* Assumes extra input is not to be tolerated */
                    while(hand.hasNext()) { 
                        String card = hand.next().toUpperCase();
                        String card_val = "";
                        String card_suit = "";
                        if(card.length() == 2) {
                            card_val += card.charAt(0);
                            card_suit = card.substring(1);
                        } else if(card.length() == 3) {
                            card_val = card.substring(0, 2);
                            card_suit = card.substring(2);
                        } else {
                            /* If the token is more than three characters of
                               less than two then it cannot be a card */
                            invalid = true;
                        }
                        /* Only continue if input is valid so far */
                        if(!invalid) {
                            switch(card_val) {
                                case "1":
                                    card = card.replace("1", "A");
                                    break;
                                case "T":
                                    card = card.replace("T", "10");
                                    break;
                                case "11":
                                    card = card.replace("11", "J");
                                    break;
                                case "12":
                                    card = card.replace("12", "Q");
                                    break;
                                case "13":
                                    card = card.replace("13", "K");
                                    break;
                                    /* If the string is not one of the above
                                       then the input is assumed to be invalid
                                       unless it is shown to be equal to one of
                                       the characters in the std_cards array */
                                default:
                                    invalid = true;
                                    for(int i = 0; i < std_cards.length; i++) {
                                        if(card_val.equals(std_cards[i])) {
                                            invalid = false;
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                        /* Only continue if input is valid so far */
                        if(!invalid) {
                            switch(card_suit) {
                                case "C":
                                case "D":
                                case "H":
                                case "S":
                                    break;                            
                                /* If the string is not one of the above then
                                   the input is assumed to be invalid unless
                                   it is shown to be equal to one of the
                                   characters in the std_cards array */
                                default:
                                    invalid = true;
                                    break;
                            }
                        }
                        /* The check here is not required if the program does
                           not need to be strict about extra input */
                        if(card_no < 5) cards[card_no] = card;
                        card_no++;
                    }
                    /* Checks that five values have been added to the array */
                    if(card_no != 5) {
                        invalid = true;
                    } else {
                        int index = 0;
                        String key;
                        /* Sorts cards by suit. Makes steadily decreasing sized
                           swaps as the index of sorted cards increases */
                        for(int k = 0; k < suit.length; k++) {
                            for(int j = 0; j < cards.length; j++) {
                                if(cards[j].charAt(cards[j].length()-1)
                                   == suit[k]) {
                                    key = cards[j];
                                    cards[j] = cards[index];
                                    cards[index] = key;
                                    index++;
                                }
                            }
                        }
                        /* Adds cards into a new array based on the value of the
                           card */
                        index = 0;
                        for(int k = 0; k < std_cards.length; k++) {
                            for(int j = 0; j < cards.length; j++) {
                                if(index < sorted.length) {
                                    if(cards[j].charAt(0) == std_cards[k].charAt(0)) {
                                        sorted[index] = cards[j];
                                        index++;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                        /* Checks the sorted array to ensure there are no
                           repeated cards */
                        for(int k = 1; k < sorted.length; k++) {
                            if(sorted[k-1] != null && sorted[k] != null) {
                                if(sorted[k-1].equals(sorted[k])) {
                                    invalid = true;
                                    break;
                                }
                            } else {
                                invalid = true;
                                break;
                            }
                        }
                    }
                }
	    }
	    if(invalid) {
		System.out.println("Invalid: " + word);
	    } else {
		for(String card : sorted) {
		    System.out.print(card + " ");
		}
		System.out.println();
	    }
	}
    }

}
