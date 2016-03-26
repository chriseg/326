/**
 * Ants.java
 * @author Chris Eathorne-Gould
 * @authr Chris Lynch 
 * 15th July 2015
 */

import java.util.*;

/**
 * A class that will read input from stdin and move the ant in the direction
 * specifed by the DNA.
 */
public class ants {

    /**
     * Main method
     */
    public static void main(String [] args) {
        Scanner input = new Scanner(System.in);
        int x, y, i;
        ArrayList<String> dna = new ArrayList<String>();
        String line = "";
        int steps = 0;
        char prev_dir = 'N';
        char def_colour;
        /* HashMap to hold the coordinates and colours of the squares that
           the prog. visits */
        HashMap<String, String> visited = new HashMap<String, String>();

        while(input.hasNextLine()) {
            line = input.nextLine();

            if(line.length() == 0) {
                System.out.println();
                dna.clear();
                
                x = 0; y = 0; i = 0; 
                steps = 0;
            } else if(line.charAt(0) != '#') {
                try {
                    steps = Integer.parseInt(line);
                } catch(NumberFormatException e) {
                    dna.add(line);
                }
                
                if(steps > 0 && dna.size() > 0) {
                    def_colour = dna.get(0).charAt(0);
                
                    /* updating the coords after each scenario */
                    int k = 0, z = 0;
                    i = 0; x = 0; y = 0;
                    char current_sq;
                    while (i < steps) {
                        String coords = Integer.toString(x) + " "
                            + Integer.toString(y);
                        current_sq = visited.get(coords) == null ?
                            def_colour : visited.get(coords).charAt(0);
                    
                        k = pickLine(dna, current_sq);

                    
                        Scanner tokens = new Scanner(dna.get(k));
                        tokens.next();
                        String directions = tokens.next();
                        String update = tokens.next();
                        prev_dir = directions.charAt(z);
                        
                        visited.remove(coords);
                    
                        switch(prev_dir) {
                            case 'N':
                                visited.put(coords,
                                            String.valueOf(update.charAt(z)));
                                ++y;
                                ++i;
                                z = 0;
                                break;
                            case 'E':
                                visited.put(coords,
                                            String.valueOf(update.charAt(z)));
                                ++x;
                                ++i;
                                z = 1;
                                break;
                            case 'S':
                                visited.put(coords,
                                            String.valueOf(update.charAt(z)));
                                --y;
                                ++i;
                                z = 2;
                                break;
                            case 'W':
                                visited.put(coords,
                                            String.valueOf(update.charAt(z)));
                                --x;
                                ++i;
                                z = 3;
                                break;
                        }
                    
                    
                    }
                
                    for(int g = 0; g < dna.size(); g++) {
                        System.out.println(dna.get(g));
                    }
                    System.out.println(steps);
                    
                    System.out.println("# " + x + " " + y);
                    dna.clear();
                    visited.clear();
                    steps = 0;
                } else {
                    steps = 0;
                }
            }
        }
    }

    public static int pickLine(ArrayList<String> dna, char current_sq) {
        int k = 0;
        for(String s : dna) {
            if(s.charAt(0) == current_sq) {
                k = dna.indexOf(s);
                return k;
            }
        }
        return k;
    }
    
}