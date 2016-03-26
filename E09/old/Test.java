package value;

import java.util.*;

public class Test {

    public static HashSet<String> items = new HashSet<String>();
    //public static HashMap<String, Integer> item_cost = new HashMap<String, Integer>();
    //public static ArrayList<String> customer_items = new ArrayList<String>();
    public static HashMap<String, Integer> item_value = new HashMap<String, Integer>();

    public static void main(String[] args) {

        SiteInfo s = new SiteInfo() {

                @Override
                public int getCost(ArrayList<String> items) {
                    int subtotal = 0;
                    for(String st : items) {
                        if(item_value.containsKey(st)) {
                            subtotal += item_value.get(st).intValue();
                        }
                    }
                    return subtotal;
                }
                
                };

        CustomerInfo c = new CustomerInfo() {

                public ArrayList<String> getItems() {
                    ArrayList<String> itemList = new ArrayList<String>(items);
                    return itemList;
                }

                public int getValue(String item) { // Returns -1 for a non-valid item
                    if(item_value.containsKey(item)) {
                        return item_value.get(item).intValue();
                    } else {
                        return -1;
                    }
                }

            };

        Scanner input = new Scanner(System.in);
        while(input.hasNextLine()) {
            String line = input.nextLine();
            if(line.length() == 0) {
                break;
            } else {
                Scanner scan = new Scanner(line);
                if(scan.hasNext()) {
                    String item = scan.next();
                    if(scan.hasNextInt()) {
                        int value = scan.nextInt();
                        items.add(item);
                        item_value.put(item, value);
                    }
                }
            }
        }

        //SiteInfo s = new LengthSite(items);

        ArrayList<String> cart = new ArrayList<String>();
        BargainFinder b = new BargainFinder(s, c, 30);
        int val = 0;
        cart = b.shoppingList();
         for(String st : cart) {
             val += c.getValue(st);
             System.out.print(st + " ");
         }
        System.out.println();
        int price = s.getCost(cart);
        System.out.println("final cart price: " + price + " and value: " + val);        
    }

}
