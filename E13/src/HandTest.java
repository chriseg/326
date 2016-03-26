package poke.test;

import poke.*;
import java.util.*;

public class HandTest {

    public static void main(String[] args) {
        ArrayList<Hand> hands = new ArrayList<Hand>();
        Scanner in = new Scanner(System.in);
        int index = 0;
        Hand hand, prevHand;
        while(in.hasNextLine()) {
            String h = in.nextLine();
            try {
                hands.add(new Hand(h));
                hand = hands.get(index);
                System.out.print("current hand: " + hand);
                if(index > 0) {
                    prevHand = hands.get(index-1);
                    System.out.println(" || previous hand: " + prevHand);
                    System.out.print(hand.compareTo(prevHand));
                }
                System.out.println();
                ++index;
            } catch(IllegalArgumentException e) {
                System.err.println(e);
                System.err.println("Invalid: " + h);
            }
        }
    }
}
