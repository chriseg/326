package poke;

import java.util.*;

enum PokerHand { HIGHCARD, ONEPAIR, TWOPAIR, THREEOKIND, STRAIGHT, FLUSH,
                 FULLHOUSE, FOUROKIND, STRAIGHTFLUSH };

public class Hand implements Comparable<Hand> {

    private final Card[] CARDS;
    private final PokerHand HAND;
    
    public Hand(String cards) {
        ArrayList<Card> seen = new ArrayList<Card>();
        Scanner in = new Scanner(cards);
        String delimiter = "";
        Card[] hand = new Card[5];
        Card card;
        int card_no = 0;

        if(isValidDelimiter(cards.charAt(0)) ||
           isValidDelimiter(cards.charAt(cards.length()-1))) {
            throw new IllegalArgumentException("Input is not valid");
        }

        if(isValidDelimiter(cards.charAt(2))) {
            delimiter += cards.charAt(2);
        } else {
            if(isValidDelimiter(cards.charAt(3))) {
                delimiter += cards.charAt(3);
            } else {
                throw new IllegalArgumentException("Input is not valid");
            }
        }
        in.useDelimiter(delimiter);

        while(in.hasNext()) {
            card = new Card(in.next());
            if(card_no < 5) {

                for(Card c : seen) {
                    if(c.compareTo(card) == 0) {
                        throw new IllegalArgumentException("Input is not" +
                                                           " valid");
                    }
                }
                
                hand[card_no] = card;
                seen.add(card);
            } else {
                throw new IllegalArgumentException("Input is not valid");
            }
            ++card_no;
        }

        this.CARDS = Card.sortCards(hand);
        this.HAND = identify(this.CARDS);
    }

    public Hand(Card[] cards) {
        Card[] hand = new Card[5];
        
        if(cards.length < 5) {
            throw new IllegalArgumentException("Input is not valid");
        }

        for(int i = 0; i < cards.length; ++i) {
            if(cards[i] == null) {
                throw new IllegalArgumentException("Input is not valid");
            }
            hand[i] = cards[i].copy();            
        }

        this.CARDS = Card.sortCards(hand);
        this.HAND = identify(this.CARDS);
    }

    private boolean isValidDelimiter(char c) {
        switch(c) {
            case ' ': case '-': case '/':
                return true;
            default:
                return false;
        }
    }

    private PokerHand identify(Card[] hand) {
        int pair, three, four, samesuit, conseqcards;
        pair = three = four = samesuit = conseqcards = 0;
        for(int i = 1; i < hand.length; ++i) {
            if(hand[i].compareRank(hand[i-1]) == 0) {
                if(i+1 < 5 && hand[i].compareRank(hand[i+1]) == 0) {
                    if(three > 0) {
                        --three;
                        ++four;
                    } else {
                        --pair;
                        ++three;
                    }
                } else {
                    ++pair;
                }
            } else {
                if(hand[i].sameSuit(hand[i-1])) {
                    ++samesuit;
                }
                if(hand[i-1].compareRank(hand[i]) == -1) {
                    ++conseqcards;
                }
            }
        }

        if(four == 1) {
            return PokerHand.FOUROKIND;
        } else if(three == 1) {
            if(pair == 1) {
                return PokerHand.FULLHOUSE;
            }
            return PokerHand.THREEOKIND;
        } else if(pair > 0) {
            if(pair > 1) {
                return PokerHand.TWOPAIR;
            }
            return PokerHand.ONEPAIR;
        } else if(samesuit == 4) {
            if(conseqcards == 4) {
                return PokerHand.STRAIGHTFLUSH;
            }
            return PokerHand.FLUSH;
        } else if (conseqcards == 4) {
            return PokerHand.STRAIGHT;
        } else {
            return PokerHand.HIGHCARD;
        }
    }
    
    public Card[] getCards() {
        return this.CARDS;
    }

    public static Hand[] sortHands(Hand[] hands) {
        for(int i = 1; i < hands.length; ++i) {
            Hand key = hands[i];
            int j = i;
            while(j > 0 && hands[j-1].compareTo(key) < 0) {
                hands[j] = hands[j-1];
                --j;
            }
            hands[j] = key;
        }
        return hands;
    }
    
    public int compareTo(Hand other) {
        if(this.HAND.compareTo(other.HAND) == 0) {
            for(int i = this.CARDS.length - 1; i >= 0; --i) {
                if(this.CARDS[i].compareRank(other.CARDS[i]) != 0) {
                    return this.CARDS[i].compareRank(other.CARDS[i]);
                }
            }
        }
        return this.HAND.compareTo(other.HAND);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HAND + "(");
        for(int i = 0; i < CARDS.length; ++i) {
            sb.append(CARDS[i]);
            if(i + 1 != CARDS.length) { sb.append(", "); }
        }
        sb.append(")");
        return sb.toString();
    }
}
