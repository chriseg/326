/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forsale;

//import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author MichaelAlbert
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // A null strategy - never bid, always play your top card.
        Strategy s = new Strategy() {

            @Override
            public int bid(PlayerRecord p, AuctionState a) {
                return -1;
            }

            @Override
            public Card chooseCard(PlayerRecord p, SaleState s) {
                return p.getCards().get(0);
            }
            
        };
        
        // A random strategy - make a random bid up to your amount remaining,
        // choose a rand card to sell.
        Strategy r = new Strategy() {

            @Override
            public int bid(PlayerRecord p, AuctionState a) {
                return (int) (1 + (Math.random()*p.getCash()));
            }

            @Override
            public Card chooseCard(PlayerRecord p, SaleState s) {
                return p.getCards().get((int) (Math.random()*p.getCards().size()));
            }
            
        };

        Strategy simple = new Strategy() {

                @Override
                public int bid(PlayerRecord p, AuctionState a) {
                    ArrayList<Card> cards = a.getCardsInAuction();
                    //Card max = Collections.max(cards);
                    //Card min = Collections.min(cards);
                    //int dif = max.getQuantity() - min.getQuantity();
                    if(a.getPlayersInAuction().size() > 2) {
                        if(a.getCurrentBid() == 0) {
                            return 1;
                        } else {
                            if(a.getCurrentBid() >= (p.getCash()/2)) {
                                return p.getCurrentBid();
                            } else {
                                return a.getCurrentBid() + 1;
                            }
                        }
                    } else {
                        return p.getCurrentBid();
                    }
                }

                @Override
                public Card chooseCard(PlayerRecord p, SaleState s) {
                    ArrayList<Card> cards = p.getCards();
                    Collections.sort(cards);
                    ArrayList<Integer> cheques = s.getChequesAvailable();
                    int highestPropertyIndex = cards.size() - 1;
                    //int min = Collections.min(cheques);
                    //int max = Collections.max(cheques);
                    //int dif = max - min;
                    if(min == 0) {
                        return cards.get(highestPropertyIndex);
                    } else if(min > 10) {
                        return cards.get(0);
                    } else {
                        /*
                        if(dif > 10) {
                            return p.getCards().get(highestPropertyIndex/2);
                        } else if(dif > 5) {
                            return cards.get(highestPropertyIndex);
                        } else {
                            return cards.get(0);
                        }
                        */
                        //return p.getCards().get((int) (Math.random()*p.getCards().size()));
                        return p.getCards().get(highestPropertyIndex/2);
                    }
                }
            
            };
        
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
        //for(int i = 0; i < 2; i++) {
            //players.add(new Player("N" + ((char) ('A' + i)), s));
            players.add(new Player("R"+ ((char) ('A' + i)), r));
            players.add(new Player("S" + ((char) ('A' + i)), simple));
        }
        GameManager g = new GameManager(players);
        g.run();
        System.out.println(g.getLog());
    }

}
