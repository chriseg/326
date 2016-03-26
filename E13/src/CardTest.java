package poke.test;

import poke.*;
import java.util.*;

public class CardTest {

    public static void main(String[] args) {
        ArrayList<Card> cards = new ArrayList<Card>();
        Scanner in = new Scanner(System.in);
        int index = 0;
        Card card, prevCard;
        while(in.hasNext()) {
            String c = in.next();
            try {
                cards.add(new Card(c));
                card = cards.get(index);
                System.out.print("current card: " + card);
                if(index > 0) {
                    prevCard = cards.get(index-1);
                    System.out.println(" || previous card: " + prevCard);
                    System.out.print(card.compareTo(prevCard));
                }
                System.out.println();
                ++index;
            } catch(IllegalArgumentException e) {
                System.err.println(e);
                System.err.println("Invalid: " + c);
            }
        }
    }
}
