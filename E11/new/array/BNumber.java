package arithmetic;

import java.util.*;

/* Class which handles integer operations without overflow - similar but more
   basic than BigInteger */
// Doesn't handle negatives currently - add in extension
// should this class be private?
public class BNumber implements Comparable<BNumber> {

    /* internal representation of the number: the number is indexed from least
       significant digit to most significant digit */
    private final int[] bnum;

    /* Default constructor - creates an int array containing a single 0 */
    public BNumber() {
        this(new int[]{0});
    }

    /* Designated constructor - takes String input and converts it to an int
       array to be stored in global var. bnum */
    public BNumber(String bnum) {
        int l = bnum.length() - 1;
        int[] num_array = new int[bnum.length()];
        for(int i = 0; i < num_array.length; ++i) {
            String s = "" + bnum.charAt(l-i);
            num_array[i] = Integer.parseInt(s);
        }
        this.bnum = num_array;
    }

    /* Private constructor - only use of this constructor will be internal */
    // could this stay public? or be private for forced modularity?
    private BNumber(int[] bnum) {
        this.bnum = bnum;
    }

    private boolean inBounds(int i) {
        return 0 <= i && i < this.bnum.length;
    }

    /* returns the digit at the specified position, or 0 if the index is out
       of bounds */
    private int getDigit(int i) {
        if(inBounds(i)) {
            return this.bnum[i];
        } else {
            return 0;
        }
    }

    public static BNumber add(BNumber lhs, BNumber rhs) {
        ArrayList<Integer> sum = new ArrayList<Integer>();
        int[] rslt;
        int m, n, c, s, l;

        if(lhs.bnum.length < rhs.bnum.length) {
            l = rhs.bnum.length;
        } else {
            l = lhs.bnum.length;
        }

        c = 0;
        for(int i = 0; i < l; ++i) {
            m = lhs.getDigit(i);
            n = rhs.getDigit(i);
            // System.err.print(m + " + " + n + " + " + c + " = ");
            s = m + n + c;
            if(s >= 10) {
                c = 1;
                s -= 10;
            } else {
                c = 0;
            }
            // System.err.println(s + " with carry: " + c);
            sum.add(s);
        }
        if(c > 0) {
            sum.add(c);
        }

        // loop to convert ArrayList<Integer> into int[]
        rslt = new int[sum.size()];
        for(int i = 0; i < sum.size(); ++i) {
            rslt[i] = sum.get(i).intValue();
        }

        return new BNumber(rslt);
    }

    public static BNumber subtract(BNumber lhs, BNumber rhs) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int[] rslt;
        int m, n, b, r, l;

        if(lhs.bnum.length < rhs.bnum.length) {
            l = rhs.bnum.length;
        } else {
            l = lhs.bnum.length;
        }

        b = 0;
        for(int i = 0; i < l; ++i) {
            m = lhs.getDigit(i);
            n = rhs.getDigit(i);
            // System.err.print("(" + m + " - " + b + ") - " + n + " = ");
            r = (m - b) - n;
            if(r < 0) {
                b = 1;
                r += 10;
            } else {
                b = 0;
            }
            // System.err.println(r + " with borrow: " + b);
            result.add(r);
        }
        if(b > 0) {
            System.err.println("OVERFLOW ERROR: " +
                               "CANNOT HANDLE NEGATIVE RESULTS");
            return new BNumber();
        }

        // loop to remove redundant zeroes
        l = result.size() - 1;
        while(l > 0 && result.get(l) == 0) {
            result.remove(l);
            --l;
        }
        
        // loop to convert ArrayList<Integer> into int[]
        rslt = new int[result.size()];
        for(int i = 0; i < result.size(); ++i) {
            rslt[i] = result.get(i).intValue();
        }

        return new BNumber(rslt);
    }

    public BNumber half() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int[] rslt;
        int m, r, q, l;

        r = 0;
        for(int i = this.bnum.length - 1; i >= 0; --i) {
            m = ((r * 10) + this.getDigit(i));
            // System.err.print(m + " / " + 2 + " = ");
            q = m / 2;
            r = m % 2;
            // System.err.println(q + " with remainder: " + r);
            result.add(q);
        }
        
        // loop to remove redundant zeroes
        while(result.size() > 1 && result.get(0) == 0) {
            result.remove(0);
        }

        // loop to convert ArrayList<Integer> into int[]
        rslt = new int[result.size()];
        l = result.size() - 1;
        for(int i = 0; i < rslt.length; ++i) {
            rslt[i] = result.get(l-i).intValue();
        }

        return new BNumber(rslt);
    }
    
    public int compareTo(BNumber other) {
        int l;
        
        if(this.bnum.length < other.bnum.length) {
            l = other.bnum.length - 1;
        } else {
            l = this.bnum.length - 1;
        }

        for(int i = l; i >= 0; --i) {
            if(this.getDigit(i) > other.getDigit(i)) {
                return 1;
            } else if(this.getDigit(i) < other.getDigit(i)) {
                return -1;
            }
        }
        return 0;
    }

    /* Debugging method - to check that integers in the array are between 0
       and 9, ie., none with more than 1 digit */
    public String outputArray() {
        int end = this.bnum.length - 1;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < end; ++i) {
            sb.append(this.getDigit(i) + " ");
        }
        sb.append(this.bnum[end]);
        return sb.toString();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = this.bnum.length - 1; i >= 0; --i) {
            sb.append(this.getDigit(i));
        }
        return sb.toString();
    }
}
