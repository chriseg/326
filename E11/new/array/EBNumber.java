package arithmetic;

import java.util.*;

public class EBNumber {

    private static final BNumber ZERO = new BNumber();
    private static final BNumber ONE = new BNumber("1");

    private final boolean neg;
    private final BNumber bnum;
    
    public EBNumber() {
        this.neg = false;
        this.bnum = new BNumber();
    }
    
    public EBNumber(String bnum) {
        if(bnum.charAt(0) == '-') {
            this.neg = true;
            this.bnum = new BNumber(bnum.substring(1));
        } else {
            this.neg = false;
            this.bnum = new BNumber(bnum);
        }
    }

    public EBNumber(String bnum, boolean neg) {
        this.neg = neg;
        this.bnum = new BNumber(bnum);
    }

    private EBNumber(BNumber bnum, boolean neg) {
        this.neg = neg;
        this.bnum = bnum;
    }

    public static EBNumber add(EBNumber lhs, EBNumber rhs) {
        if(lhs.neg && !rhs.neg) {
            EBNumber b = new EBNumber(lhs.bnum, false);
            return EBNumber.subtract(rhs, b);
        } else if(!lhs.neg && rhs.neg) {
            EBNumber b = new EBNumber(rhs.bnum, false);
            return EBNumber.subtract(lhs, b);
        } else if(lhs.neg && rhs.neg) {
            return new EBNumber(BNumber.add(lhs.bnum, rhs.bnum), true);
        } else {
            return new EBNumber(BNumber.add(lhs.bnum, rhs.bnum), false);
        }
    }

    public static EBNumber subtract(EBNumber lhs, EBNumber rhs) {
        if(lhs.neg && !rhs.neg) {
            return new EBNumber(BNumber.add(lhs.bnum, rhs.bnum), true);
        } else if(!lhs.neg && rhs.neg) {
            return new EBNumber(BNumber.add(lhs.bnum, rhs.bnum), false);
        } else if(lhs.neg && rhs.neg) {
            if(lhs.bnum.compareTo(rhs.bnum) == -1) {
                return new EBNumber(BNumber.subtract(rhs.bnum, lhs.bnum),
                                    false);
            } else {
                return new EBNumber(BNumber.subtract(lhs.bnum, rhs.bnum),
                                    true);
            }
        } else {
            if(lhs.bnum.compareTo(rhs.bnum) == -1) {
                return new EBNumber(BNumber.subtract(rhs.bnum, lhs.bnum),
                                    true);
            } else {
                return new EBNumber(BNumber.subtract(lhs.bnum, rhs.bnum),
                                    false);
            }
        }
    }

    private static BNumber douuble(BNumber n) {
        return BNumber.add(n, n);
    }

    private static boolean isEven(BNumber m) {
        BNumber h = m.half();
        BNumber i = BNumber.add(m, ONE).half();
        return h.compareTo(i) == 0;
    }

    public static EBNumber multiply(EBNumber lhs, EBNumber rhs) {
        BNumber m = lhs.bnum;
        BNumber n = rhs.bnum;
        BNumber rslt = new BNumber();
        do {
            // System.err.println("m: " + m + " n: " + n);
            if(!EBNumber.isEven(m)) {
                // System.err.println(m + " isn't even -> adding " + n
                //                    + " to rslt");
                rslt = BNumber.add(n, rslt);
                // System.err.println("rslt = " + rslt);
            }
            m = m.half();
            n = EBNumber.douuble(n);
        } while(m.compareTo(ZERO) != 0);
        
        if(lhs.neg == rhs.neg) {
            return new EBNumber(rslt, false);
        } else {
            return new EBNumber(rslt, true);
        }
    }

    public static EBNumber[] divide(EBNumber lhs, EBNumber rhs) {
        if(rhs.bnum.compareTo(ZERO) == 0) {
            return new EBNumber[]{new EBNumber(ZERO, false),
                                  new EBNumber(ZERO, false)};
        }
        if(lhs.bnum.compareTo(ZERO) == 0) {
            return new EBNumber[]{lhs, rhs};
        }
        BNumber m = rhs.bnum;
        BNumber n = lhs.bnum;
        BNumber rslt = new BNumber();
        do {
            BNumber e = new BNumber();
            BNumber g = n;
            do {
                g = g.half();
                if(g.compareTo(ZERO) == 0) { g = ONE; }
                e = multiply(new EBNumber(m, false),
                             new EBNumber(g, false)).bnum;
                // System.err.println("g: " + g + " e: " + e);
            } while(e.compareTo(n) == 1);
            n = BNumber.subtract(n, e);
            rslt = BNumber.add(rslt, g);
            // System.err.println("--> m: " + m + " n: " + n
            //                    + " rslt: " + rslt);
        } while(m.compareTo(n) != 1);

        if(lhs.neg == rhs.neg) {
            return new EBNumber[]{new EBNumber(rslt, false),
                                  new EBNumber(n, false)};
        } else {
            return new EBNumber[]{new EBNumber(rslt, true),
                                  new EBNumber(n, false)};
        }
    }

    // need to consider case where one of the inputs is 0
    public static EBNumber gcd(EBNumber lhs, EBNumber rhs) {
        EBNumber[] q_r;
        EBNumber divisor = new EBNumber();
        if(lhs.lesser(rhs)) {
            q_r = divide(rhs, lhs);
            divisor = lhs;
            if(lhs.bnum.compareTo(ZERO) == 0) { return rhs; }
        } else {
            q_r = divide(lhs, rhs);
            divisor = rhs;
            if(rhs.bnum.compareTo(ZERO) == 0) { return lhs; }
        }
        EBNumber remainder = q_r[1];
        while(remainder.bnum.compareTo(ZERO) != 0) {
            q_r = divide(divisor, q_r[1]);
            divisor = remainder;
            remainder = q_r[1];  
        }
        return divisor;
    }

    public boolean equals(EBNumber o) {
        if(this.neg != o.neg) {
            return false;
        } else {
            return this.bnum.compareTo(o.bnum) == 0;
        }
    }

    public boolean greater(EBNumber o) {
        if(this.neg && !o.neg) {
            return false;
        } else if(!this.neg && o.neg) {
            return true;
        } else if(this.neg) {
            return this.bnum.compareTo(o.bnum) == -1;
        } else {
            return this.bnum.compareTo(o.bnum) == 1;
        }
    }

    public boolean lesser(EBNumber o) {
        if(this.neg && !o.neg) {
            return true;
        } else if(!this.neg && o.neg) {
            return false;
        } else if(this.neg) {
            return this.bnum.compareTo(o.bnum) == 1;
        } else {
            return this.bnum.compareTo(o.bnum) == -1;
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(this.neg) { sb.append("-"); }
        sb.append(this.bnum.toString());
        return sb.toString();
    }
}
