package value;

import java.util.*;

public class uglySite implements SiteInfo {
    
    public HashMap<String, Integer> items;

    public uglySite() {
        items = new HashMap<String, Integer>();

        items.put("16e8p7", 4);
        items.put("9ebp3f", 2);
        items.put("lr6450", 6);
        items.put("ril83k", 1);
        items.put("caahhn", 6);
        items.put("spe15b", 5);
        items.put("a1cc6u", 1);
        items.put("93mb6s", 9);
        items.put("cb3rme", 3);
        items.put("uq2css", 5);
        items.put("l1prpp", 9);
        items.put("d30qi5", 3);
        items.put("prit7t", 1);
        items.put("rpbejk", 3);
        items.put("1f9e8n", 6);
        items.put("tf8jbc", 3);
        items.put("i8t6dg", 3);
        items.put("gdnt3e", 10);
        items.put("3eatfr", 5);
        items.put("5h1564", 5);
        items.put("oo83s6", 1);
        items.put("nols0f", 5);
        items.put("63lnds", 9);
        items.put("t73qf4", 1);
        items.put("4vlr0r", 6);
        items.put("ak7b5", 3);
        items.put("7eu3k", 5);
        items.put("ssahil", 9);
        items.put("tqfvhr", 9);
        items.put("tri5bc", 8);
    }


    @Override
    public int getCost(ArrayList<String> list) {
        int sum = 0;
        for(String item : list) {
            if(items.containsKey(item)) {
                sum += items.get(item);
            }
        }
        return sum;
    }

}