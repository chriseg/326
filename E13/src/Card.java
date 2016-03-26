package poke;

import java.util.*;

enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES;

            @Override
            public String toString() {
                switch(this) {
                    case CLUBS:
                        return "C";
                    case DIAMONDS:
                        return "D";
                    case HEARTS:
                        return "H";
                    case SPADES:
                        return "S";
                }
                return this.name();
            }
};

enum Rank { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN,
            KING, ACE;

            @Override
            public String toString() {
                switch(this) {
                    case TWO:
                        return "2";
                    case THREE:
                        return "3";
                    case FOUR:
                        return "4";
                    case FIVE:
                        return "5";
                    case SIX:
                        return "6";
                    case SEVEN:
                        return "7";
                    case EIGHT:
                        return "8";
                    case NINE:
                        return "9";
                    case TEN:
                        return "10";
                    case JACK:
                        return "J";
                    case QUEEN:
                        return "Q";
                    case KING:
                        return "K";
                    case ACE:
                        return "A";
                }
                return this.name();
            }
};

public class Card implements Comparable<Card> {
    
    private final Suit SUIT;
    private final Rank RANK;
    
    public Card(String card) {
        String card_val;// = "";
        String card_suit;

        card = card.toUpperCase();
        if(card.length() == 2) {
            card_val = "" + card.charAt(0);
            card_suit = card.substring(1);
        } else if(card.length() == 3) {
            card_val = card.substring(0, 2);
            card_suit = card.substring(2);
        } else {
            throw new IllegalArgumentException("Input is not valid");
        }

        switch(card_suit) {
            case "C":
                this.SUIT = Suit.CLUBS;
                break;
            case "D":
                this.SUIT = Suit.DIAMONDS;
                break;
            case "H":
                this.SUIT = Suit.HEARTS;
                break;
            case "S":
                this.SUIT = Suit.SPADES;
                break;
            default:
                throw new IllegalArgumentException("Input is not valid");
        }

        switch(card_val) {
            case "2":
                this.RANK = Rank.TWO;
                break;
            case "3":
                this.RANK = Rank.THREE;
                break;
            case "4":
                this.RANK = Rank.FOUR;
                break;
            case "5":
                this.RANK = Rank.FIVE;
                break;
            case "6":
                this.RANK = Rank.SIX;
                break;
            case "7":
                this.RANK = Rank.SEVEN;
                break;
            case "8":
                this.RANK = Rank.EIGHT;
                break;
            case "9":
                this.RANK = Rank.NINE;
                break;
            case "10": case "T":
                this.RANK = Rank.TEN;
                break;
            case "11": case "J":
                this.RANK = Rank.JACK;
                break;
            case "12": case "Q":
                this.RANK = Rank.QUEEN;
                break;
            case "13": case "K":
                this.RANK = Rank.KING;
                break;
            case "1": case "A":
                this.RANK = Rank.ACE;
                break;
            default:
                throw new IllegalArgumentException("Input is not valid");
        }
    }

    public Suit getSuit() {
        return this.SUIT;
    }

    public Rank getRank() {
        return this.RANK;
    }
    
    public int compareTo(Card other) {
        if(this.RANK.compareTo(other.RANK) == 0) {
            return this.SUIT.compareTo(other.SUIT);
        } else {
            return this.RANK.compareTo(other.RANK);
        }
    }
    
    public int compareRank(Card other) {
        return this.RANK.compareTo(other.RANK);
    }

    public boolean sameSuit(Card other) {
        return this.SUIT.compareTo(other.SUIT) == 0;
    }

    public Card copy() {
        return new Card(this.toString());
    }

    public static Card[] sortCards(Card[] cards) {
        for(int i = 1; i < cards.length; ++i) {
            Card key = cards[i];
            int j = i;
            while(j > 0 && cards[j-1].compareTo(key) > 0) {
                cards[j] = cards[j-1];
                --j;
            }
            cards[j] = key;
        }
        return cards;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append(RANK + " of " + SUIT);
        sb.append(RANK);
        sb.append(SUIT);
        return sb.toString();
    }
}
