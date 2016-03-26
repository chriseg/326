package poke;

import java.util.*;

public class CreateBestHand {

    public static ArrayList<String> input = new ArrayList<String>();
    public static Card[][] cmatrix = new Card[4][13];
    public static int[] stotals = new int[4];
    public static int[] rtotals = new int[13];
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNextLine()) {
            String line = in.nextLine();
            if(line.length() == 0) { break; }
            if(line.charAt(0) != '#') {
                Scanner tokens = new Scanner(line);
                while(tokens.hasNext()) {
                    input.add(tokens.next());
                }
            } // else { System.err.println(line); }
        }

        if(input.size() <= 5) {
            System.out.println("Too little input");
        } else {
            for(int i = 0; i < input.size(); ++i) {
                try {
                    Card card = new Card(input.get(i));
                    init_matrix(card);
                } catch(IllegalArgumentException e) {
                    // System.err.println(e);
                }
            }

            // for(Card[] a : cmatrix) {
            //     for(Card c : a) {
            //         if(c != null) {
            //             System.err.print(c + " ");
            //         } else {
            //             System.err.print("-- ");
            //         }
            //     }
            //     System.err.println();
            // }

            System.out.println(createBestHand());
        }
    }

    private static Hand createBestHand() {
        HashSet<Hand> handset = new HashSet<Hand>();
        Card[] hand = new Card[5];
        int c_i = 0;

        // BEGIN        
        // looking for FLUSH/STRAIGHTFLUSH
        for(int i = 0; i < stotals.length; ++i) {
            if(stotals[i] >= 5) {
                for(int j = rtotals.length - 1; j >= 0 && c_i < 5; --j) {
                    if(cmatrix[i][j] != null) {
                        hand[c_i] = cmatrix[i][j];
                        ++c_i;
                    }
                }
                handset.add(new Hand(hand));
                c_i = 0;
            }
        }
        c_i = 0;

        // looking for FOUROKIND
        for(int i = rtotals.length - 1; i >= 0; --i) {
            if(rtotals[i] == 4) {
                for(int j = 0; j < stotals.length; ++j) {
                    if(cmatrix[j][i] != null) {
                        hand[c_i] = cmatrix[j][i];
                        ++c_i;
                    }
                }
                break;
            }
        }
        if(c_i == 4) {
            hand = addRemainingCards(hand, c_i);
            handset.add(new Hand(hand));
        }
        c_i = 0;

        // looking for STRAIGHT
        for(int i = rtotals.length - 1; i >= 0 && c_i < 5; --i) {
            if(rtotals[i] > 0) {
                for(int j = 0; j < stotals.length; ++j) {
                    if(cmatrix[j][i] != null) {
                        hand[c_i] = cmatrix[j][i];
                        ++c_i;
                        break;
                    }
                }
            } else {
                c_i = 0;
            }
        }
        if(c_i == 5) { 
            handset.add(new Hand(hand));
        }
        c_i = 0;

        // looking for FULLHOUSE/THREEOKIND
        for(int i = rtotals.length - 1; i >= 0 && c_i < 5; --i) {
            if(rtotals[i] == 3) {
                for(int j = 0; j < stotals.length; ++j) {
                    if(cmatrix[j][i] != null) {
                        hand[c_i] = cmatrix[j][i];
                        ++c_i;
                    }
                }
            } else if(rtotals[i] == 2 && c_i != 2) {
                for(int j = 0; j < stotals.length; ++j) {
                    if(cmatrix[j][i] != null) {
                        hand[c_i] = cmatrix[j][i];
                        ++c_i;
                    }
                }                
            }
        }
        if(c_i < 5) {
            hand = addRemainingCards(hand, c_i);
        }
        handset.add(new Hand(hand));
        c_i = 0;

        // looking for TWOPAIR/ONEPAIR
        for(int i = rtotals.length - 1; i >= 0 && c_i < 4; --i) {
            if(rtotals[i] == 2) {
                for(int j = 0; j < stotals.length; ++j) {
                    if(cmatrix[j][i] != null) {
                        hand[c_i] = cmatrix[j][i];
                        ++c_i;
                    }
                }                
            }
        }
        if(c_i > 0) {
            hand = addRemainingCards(hand, c_i);
            handset.add(new Hand(hand));
        }
        c_i = 0;

        // looking for HIGHCARD
        if(handset.isEmpty()) {
            for(int i = rtotals.length - 1; i >= 0 && c_i < 5; --i) {
                if(rtotals[i] > 0) {
                    for(int j = 0; j < stotals.length; ++j) {
                        if(cmatrix[j][i] != null) {
                            hand[c_i] = cmatrix[j][i];
                            ++c_i;
                            break;
                        }
                    }
                }
            }
            handset.add(new Hand(hand));
        }
        // DONE

        Hand[] hands = new Hand[handset.size()];
        int i = 0;
        for(Hand h : handset) {
            hands[i] = h;
            ++i;
        }
        hands = Hand.sortHands(hands);
        return hands[0];
    }

    private static Card[] addRemainingCards(Card[] hand, int l) {
        for(int i = rtotals.length - 1; i >= 0 && l < 5; --i) {
            for(int j = stotals.length - 1; j >= 0 && l < 5; --j) {
                if(cmatrix[j][i] != null &&
                   !containsCard(hand, cmatrix[j][i])) {
                    hand[l] = cmatrix[j][i];
                    ++l;
                }
            }
        }
        return hand;
    }

    private static boolean containsCard(Card[] cards, Card card) {
        for(Card c : cards) {
            if(c != null && card.compareTo(c) == 0) {
                return true;
            }
        }
        return false;
    }

    private static void init_matrix(Card c) {
        switch(c.getRank()) {
            case TWO:
                ++rtotals[0];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][0] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][0] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][0] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][0] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case THREE:
                ++rtotals[1];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][1] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][1] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][1] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][1] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case FOUR:
                ++rtotals[2];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][2] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][2] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][2] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][2] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case FIVE:
                ++rtotals[3];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][3] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][3] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][3] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][3] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case SIX:
                ++rtotals[4];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][4] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][4] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][4] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][4] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case SEVEN:
                ++rtotals[5];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][5] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][5] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][5] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][5] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case EIGHT:
                ++rtotals[6];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][6] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][6] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][6] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][6] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case NINE:
                ++rtotals[7];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][7] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][7] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][7] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][7] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case TEN:
                ++rtotals[8];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][8] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][8] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][8] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][8] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case JACK:
                ++rtotals[9];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][9] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][9] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][9] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][9] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case QUEEN:
                ++rtotals[10];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][10] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][10] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][10] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][10] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case KING:
                ++rtotals[11];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][11] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][11] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][11] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][11] = c;
                        ++stotals[3];
                        break;
                }
                break;
            case ACE:
                ++rtotals[12];
                switch(c.getSuit()) {
                    case CLUBS:
                        cmatrix[0][12] = c;
                        ++stotals[0];
                        break;
                    case DIAMONDS:
                        cmatrix[1][12] = c;
                        ++stotals[1];
                        break;
                    case HEARTS:
                        cmatrix[2][12] = c;
                        ++stotals[2];
                        break;
                    case SPADES:
                        cmatrix[3][12] = c;
                        ++stotals[3];
                        break;
                }
                break;
        }
    }    
}
