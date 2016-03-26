/**
 * strat.java
 * @author Chris Eathorne-Gould
 * @author Chris Lynch
 * @author Mike Tamis
 * @author Jos Hua
 * September 2015
 */
package forsale;

import java.util.*;

public class strat implements Strategy {

    public static int HighCard = 24;
    public static int lowCard = 8;
    public static int lowDiff = 9;
    public static int highestBid = 6;
    public static int lowHighBid = 0;

    public int bid(PlayerRecord p, AuctionState a) {
        ArrayList<Card> cards = a.getCardsInAuction();
        ArrayList<Integer> values = new ArrayList<Integer>();
        int max = 0;
        int min = 0;
        boolean lessThenLow = false;
        boolean allGreaterThenHigh = true;

        for (int c = 0; c < cards.size(); ++c) {
            values.add(cards.get(c).getQuality());
        }

        max = Collections.max(values);
        min = Collections.min(values);
                    
        for (int i = 0; i < values.size(); ++i) {                     
            if (values.get(i) < lowCard) {
                lessThenLow = true;
            }
            if (values.get(i) < HighCard) {
                allGreaterThenHigh = false;
            }
        }

        if (a.getPlayersInAuction().size() < 2) {
            return -1;
        }

        if (a.getCurrentBid() >= highestBid) {
            return (-1);
        }
                    
        int diff = max - min;
                    
        if (lessThenLow) {
            return (a.getCurrentBid() + 1);
        }
        if (diff <= lowDiff) {
            if (a.getCurrentBid() > lowHighBid) {
                return (-1);
            } else {
                return (a.getCurrentBid() + 1);
            }
        } else if (allGreaterThenHigh) {
            return (-1);
        } else {
            return (a.getCurrentBid() + 1);
        }
    }

    

    public Card chooseCard(PlayerRecord p, SaleState s) {
        ArrayList<Card> cards = p.getCards();
        Collections.sort(cards);
        ArrayList<Integer> cheques = s.getChequesAvailable();
        int highestPropertyIndex = cards.size() - 1;
        int min = Collections.min(cheques);
        int max = Collections.max(cheques);
        int dif = max - min;
        if(min == 0) {
            return cards.get(highestPropertyIndex);
        } else if(min > 10) {
            return cards.get(0);
        } else {
            if(dif > 10) {
                return cards.get(highestPropertyIndex);
            } else if(dif > 5) {
                return cards.get(highestPropertyIndex/2);
            } else {
                return cards.get(0);
            }

        }
    }
}