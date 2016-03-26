package poke;

import java.util.*;

public class WinningHand {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int read_i = 0;
        Hand[] hands;
        ArrayList<Hand> inhands = new ArrayList<Hand>();
        // ArrayList<String> input = new ArrayList<String>();

        while(in.hasNextLine()) {
            String line = in.nextLine();
            if(line.length() == 0) { break; }
            if(line.charAt(0) != '#') {
                try {
                    Hand hand = new Hand(line);
                    // line = hand.toString();
                    inhands.add(hand);
                } catch(IllegalArgumentException e) {
                    // System.err.println(e);
                    // line = "Invalid: " + line;
                }
                // input.add(line);
            } // else { System.err.println(line); }
        }

        // for(String s : input) {
        //     System.out.println(s);
        // }
        // System.out.println();

        hands = new Hand[inhands.size()];
        inhands.toArray(hands);
        hands = Hand.sortHands(hands);

        System.out.println(hands[0]);
    }
}
