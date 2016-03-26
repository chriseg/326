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

    public BargainFinder(SiteInfo site, CustomerInfo customer, int budget) {
        this.site = site;
        this.customer = customer;
        this.budget = budget;
    }
    
    public ArrayList<String> shoppingList() {
    	return null;
    }

}
