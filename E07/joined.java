/**
 * joined.java
 * @author Chris^2
 * September 2015
 */
import java.util.*;

public class joined {

    private static HashMap<String, ArrayList<String>> prefixes = new HashMap<String, ArrayList<String>>();
    private static ArrayList<String> words = new ArrayList<String>();
    private static HashSet<String> visited;
    private static ArrayList<Pair<String, Integer>> fringe = new ArrayList<Pair<String, Integer>>();

    public static void main(String[] args) {
        String start = null, goal = null;
        
        try {
            start = args[0];
            goal = args[1];
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Bad Input!\n" + e);
        }

        if(start != null && goal != null) {
            Scanner input = new Scanner(System.in);
        
            while(input.hasNext()) {
                words.add(input.next());
            }
            Collections.sort(words);
            visited = new HashSet<String>(words.size()/2);

            ArrayList<String> solution1 = singlyJoined(start, goal);
            ArrayList<String> solution2 = doublyJoined(start, goal);
            System.out.println(output(solution1));
            System.out.println(output(solution2));
        }
    }

    //singlyJoined
    public static ArrayList<String> singlyJoined(String start, String goal) {
        String current = start;
        visited.add(current);
        String suffix = null;
        ArrayList<String> joined = new ArrayList<String>();
        int index = 0;
        ArrayList<String> wantedWords = new ArrayList<String>();
        fringe.add(new joined.Pair<String, Integer>(current, index++));
        int parent_index = 0;
        do {
            for(int i = (current.length() - 1); i >= 0; i--) {
                suffix = current.substring(i);
                if(prefixes.containsKey(suffix)) {
                    wantedWords = prefixes.get(suffix);
                } else {
                    wantedWords = findWords(suffix);
                }
                for(String s : wantedWords) {
                    if(!visited.contains(s) && (atLeastHalf(s, suffix) || atLeastHalf(current, suffix))) {
                        fringe.add(new joined.Pair<String, Integer>(s, index - parent_index));
                        index++;
                        visited.add(s);    
                    }
                }
            }
            ++parent_index;
            if (parent_index == index) {
                return joined;
            } else {
                current = fringe.get(parent_index).getLeft();
            }
        } while(!current.equals(goal));
        int backtrack = 0;
        while(parent_index != 0) {
            joined.add(fringe.get(parent_index).getLeft());
            backtrack = fringe.get(parent_index).getRight();
            parent_index -= backtrack;
        }
        joined.add(fringe.get(parent_index).getLeft());
        fringe.clear();
        visited.clear();
        return joined;
    }

    //doublyJoined
    public static ArrayList<String> doublyJoined(String start, String goal) {
        String current = start;
        visited.add(current);
        String suffix = null;
        ArrayList<String> joined = new ArrayList<String>();
        ArrayList<String> wantedWords = new ArrayList<String>();
        int index = 0;
        fringe.add(new joined.Pair<String, Integer>(current, index++));
        int parent_index = 0;
        do {
            for(int i = current.length()/2; i >= 0; i--) {
                suffix = current.substring(i);
                if(prefixes.containsKey(suffix)) {
                    wantedWords = prefixes.get(suffix);
                } else {
                    wantedWords = findWords(suffix);
                }
                for(String s : wantedWords) {
                    if(!visited.contains(s) && (atLeastHalf(s, suffix))) {
                        fringe.add(new joined.Pair<String, Integer>(s, index - parent_index));
                        index++;
                        visited.add(s); 
                    }                 
                }
            }
            ++parent_index;
            if (parent_index == index) {
                return joined;
            } else {
                current = fringe.get(parent_index).getLeft();
            }
        } while(!current.equals(goal));
        int backtrack = 0;
        while(parent_index != 0) {
            joined.add(fringe.get(parent_index).getLeft());
            backtrack = fringe.get(parent_index).getRight();
            parent_index -= backtrack;
        }
        joined.add(fringe.get(parent_index).getLeft());
        fringe.clear();
        visited.clear();
        return joined;
    }

    public static boolean atLeastHalf(String word, String substring) {
        int word_length = word.length();
        int substring_length = substring.length();
        if((word_length + 1) / 2 <= substring_length) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<String> findWords(String suffix) {
        int i = 0;
        int k = words.size();
        int j = (k + i)/2;
        int l;
        int prev_j = 0;
        String prefix = "";
        ArrayList<String> wantedWords = new ArrayList<String>();
        while(true) {
            if (prev_j == j) {
              return new ArrayList<String>();
              }
            prefix = findPrefix(words.get(j), suffix.length());
            if (prefix.equals("")) {
                j++;
                if (j == k) {
                    return new ArrayList<String>();
                }
            } else {
                if(suffix.equals(prefix)) {
                    break;
                } else if(suffix.compareTo(prefix) < 0) {
                    k = j;
                    prev_j = j;
                    j = (k + i) / 2; 
                } else if(suffix.compareTo(prefix) > 0) {
                    i = j;
                    prev_j = j;
                    j = (k + i) / 2;
                } 
            }
        }
        l = j;
        while(l >= 0 && findPrefix(words.get(l), suffix.length()).equals(prefix)) {
            wantedWords.add(words.get(l));
            l--;
        }
        l = j+1;
        while(l < words.size() && findPrefix(words.get(l), suffix.length()).equals(prefix)) {
            wantedWords.add(words.get(l));
            l++;
        }
        prefixes.put(prefix, wantedWords);
        return wantedWords;
    }

    public static String findPrefix(String word, int pref_length) {
        if(word.length() < pref_length) {
            return "";
        } else {
            return word.substring(0, pref_length);
        }
    }

    public static String output(ArrayList<String> sol) {
        StringBuilder sb = new StringBuilder();
        sb.append(sol.size());
        for(int i = sol.size() - 1; i >= 0; i--) {
            sb.append(" " + sol.get(i));
        }
        return sb.toString();
    }
    
    private static class Pair<L, R> {
        private final L left;
        private final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() { return left; }
        public R getRight() { return right; }

    }

    private static class prefixList {

        private String prefix;
        private ArrayList<String> wordList;

        public prefixList(String prefix,
                          ArrayList<String> wordList) {
            this.prefix = prefix;
            this.wordList = wordList;
        }

        public String getPrefix() {
            return prefix;
        }

        public ArrayList<String> getList() {
            return wordList;
        }

    }
}