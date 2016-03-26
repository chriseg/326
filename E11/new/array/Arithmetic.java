package arithmetic;

import java.util.*;

public class Arithmetic {

    private static final EBNumber ZERO = new EBNumber();
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String line, lhs, rhs, op;
        
        while(input.hasNextLine()) {
            line = input.nextLine();
            if(line.length() != 0) {
                if(line.charAt(0) != '#') {
                    System.out.println(line);
                    Scanner exprssn = new Scanner(line);
                    try {
                        lhs = exprssn.next();
                        op = exprssn.next();
                        rhs = exprssn.next();
                        if(!exprssn.hasNext()) {
                            try {
                                EBNumber l = new EBNumber(lhs);
                                EBNumber r = new EBNumber(rhs);
                                handleOperation(l, op, r);
                            } catch(NumberFormatException e) {
                                System.err.println("# Syntax error");
                            }
                        } else {
                            System.err.println("# Syntax error");
                        }
                    } catch(NoSuchElementException e) {
                        System.err.println("# Syntax error");
                    }
                }
            } else {
                System.out.println();
            }
        }
    }

    public static void handleOperation(EBNumber lhs, String op, EBNumber rhs) {
        EBNumber rslt = null;
        EBNumber rem = null;
        boolean b = false;
        boolean update = false;
        switch(op) {
            case "+":
                rslt = EBNumber.add(lhs, rhs);
                break;
            case "-":
                rslt = EBNumber.subtract(lhs, rhs);
                break;
            case "*":
                rslt = EBNumber.multiply(lhs, rhs);
                break;
            case "/":
                EBNumber[] div = EBNumber.divide(lhs, rhs);
                rslt = div[0];
                rem = div[1];
                break;
            case "gcd":
                rslt = EBNumber.gcd(lhs, rhs);
                break;
            case "<":
                b = lhs.lesser(rhs);
                update = true;
                break;
            case ">":
                b = lhs.greater(rhs);
                update = true;
                break;
            case "=":
                b = lhs.equals(rhs);
                update = true;
                break;
            default:
                System.err.println("# Syntax error");
        }
        if(rslt != null) {
            if(rslt.equals(ZERO) && rem != null && rem.equals(ZERO)) {
                System.err.println("# Syntax error");
            } else {
                System.out.print("# " + rslt);
                if(rem != null) {
                    System.out.println(" " + rem);
                } else {
                    System.out.println();
                }
            }
        } else {
            if(update) { System.out.println("# " + b); }
        }
    }
}
