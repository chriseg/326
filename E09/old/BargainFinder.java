package value;

import java.util.ArrayList;

/**
 * The class for bargain finders.
 * 
 * @author MichaelAlbert
 */
public class BargainFinder {
    
    private SiteInfo site;
    private CustomerInfo customer;
    private int budget;

    private int [][] knapsack;
    private int[] prices;

    public BargainFinder(SiteInfo site, CustomerInfo customer, int budget) {
        this.site = site;
        this.customer = customer;
        this.budget = budget;
    }
    
    public ArrayList<String> shoppingList() {
        ArrayList<String> trolley = new ArrayList<String>();
        ArrayList<String> wanted = customer.getItems();
        wanted.add(0, "dummy");
        knapsack = new int[wanted.size()][budget+1];
        prices = getPrices(wanted);
        for(int j = 0; j <= budget; ++j) {
            knapsack[0][j] = 0;
            //System.out.print(knapsack[0][j] + " ");
        }
        //System.out.println();

        for(int i = 1; i < wanted.size(); ++i) {
            trolley.clear();
            for(int j = 0; j <= budget; ++j) {
                String item = wanted.get(i);
                int val = customer.getValue(item);
                // System.out.println("i: " + i + " j: " + j + " price: " + prices[i]
                //                    + " item: " + item);
                if(prices[i] <= j) {
                    // System.out.print("a: " + knapsack[i-1][j]);
                    // System.out.print(" b: " + knapsack[i-1][j-prices[i]] + val);
                    // System.out.println();
                    knapsack[i][j] = Math.max(knapsack[i-1][j],
                                              knapsack[i-1][j-prices[i]] + val);
                } else {
                    knapsack[i][j] = knapsack[i-1][j];
                }
                //System.out.print(knapsack[i][j] + " ");
            }
            //System.out.println();
        }

        int i = wanted.size() - 1;
        int j = budget;
        while(i > 0 && j >= 0) {
            if(knapsack[i][j] != knapsack[i-1][j]) {
                trolley.add(wanted.get(i));
                j -= prices[i];
            }
            --i;
        }
        return trolley;
    }

    private int[] getPrices(ArrayList<String> items) {
        ArrayList<String> trolley = new ArrayList<String>();
        int[] prices = new int[items.size()];
        for(int i = 0; i < prices.length; ++i) {
            trolley.add(items.get(i));
            prices[i] = site.getCost(trolley);
            //System.err.println("item: " + items.get(i) + " price: " + prices[i]);
            trolley.remove(0);
        }
        return prices;
    }

}