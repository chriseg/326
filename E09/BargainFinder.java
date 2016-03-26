package value;

import java.util.*;

/**
 * The class for bargain finders.
 * 
 * @author MichaelAlbert
 */
public class BargainFinder {
    
    private SiteInfo site;
    private CustomerInfo customer;
    private int budget;

    ArrayList< ArrayList<String>> trolleys = new ArrayList<ArrayList<String>>();

    public BargainFinder(SiteInfo site, CustomerInfo customer, int budget) {
        this.site = site;
        this.customer = customer;
        this.budget = budget;
    }
    
    public ArrayList<String> shoppingList() {

        ArrayList<String> trolley = new ArrayList<String>();
        ArrayList<String> items = new ArrayList<String>();
        String[] ite = sortValue();

        for (int i = 0; i < ite.length; ++i) {
            items.add(ite[i]);
        }

        int j = budget + 1;
        int z = 0;
        ArrayList<Integer> indexs = new ArrayList<Integer>();

        while (z < items.size()) {
            trolley.add(items.get(z));
            ArrayList<String> temp = new ArrayList<String>();
            for (String s : temp) {
                temp.add(trolley.remove(z));
            }
            trolleys.add(temp);
            indexs.add(0);
            z++;
        }
        for (int k = 0; k < trolleys.size(); ++k) {
            ArrayList<String> temp = trolleys.get(k);
            int index = indexs.get(k);
            while (index < items.size()) {
                String it = items.get(index);
                if (!temp.contains(it)) {
                    temp.add(it);
                    if (site.getCost(temp) <= budget) {
                        ArrayList<String> blah = new ArrayList<String>();
                        for (String s : temp) {
                            blah.add(s);
                        }
                        trolleys.add(blah);
                        indexs.add(index);
                    }
                    temp.remove(it);
                }
                                    index++;
            }
        }

        return findBestArray();    
    }

    private ArrayList<String> findBestArray() {
        int TopVal = 0 ;
        ArrayList<String> best = new ArrayList<String>();
        for (ArrayList<String> trolley : trolleys) {
            int val = getValue(trolley);
            if (val > TopVal) {
                TopVal = val;
                best = trolley;
            }
        }
        return best;
    }
    
    private int getValue(ArrayList<String> items) {
        int sum = 0;
        for (String item : items) {
            sum += customer.getValue(item);
        }
        return sum;
    }

    private String[] sortValue() {
        String[] items = new String[customer.getItems().size()];
        customer.getItems().toArray(items);
        for(int i = 1; i < items.length; ++i) {
            String key = items[i];
            int j = i;
            while(j > 0 && customer.getValue(items[j-1]) > customer.getValue(key)) {
                items[j] = items[j-1];
                --j;
            }
            items[j] = key;
        }
        return items;
    }
}