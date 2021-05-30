package org.mkzh.fincalc.formula.helper;

import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Hashtable;
import java.math.BigDecimal;

public class Helper {
    public static String invalidMsg = """
            That's not a valid input! Please try again.
            >\s""";

    public static void collect(String request, String symbol, Hashtable<String, BigDecimal> args) {
        Scanner in = new Scanner(System.in);
        System.out.print(request);
        args.put(symbol, in.nextBigDecimal());
    }

    public static void collectOneOrTwo(String request, String symbol, String altSym0, String altSym1, Hashtable<String, BigDecimal> args) {
        Scanner in = new Scanner(System.in);
        System.out.print(request);
        String[] ln = in.nextLine().split(" ");
        while (ln.length != 1 && ln.length != 2) {
            System.out.println(invalidMsg);
            ln = in.nextLine().split(" ");
        }

        if (ln.length == 1) {
            args.put(symbol, new BigDecimal(ln[0]));
        } else {
            args.put(altSym0, new BigDecimal(ln[0]));
            args.put(altSym1, new BigDecimal(ln[1]));
        }
    }

    public static BigDecimal makePercentage(BigDecimal decimal) {
        return decimal.multiply(BigDecimal.valueOf(100));
    }

    public static BigDecimal round(BigDecimal exact, int dp) {
        return exact.setScale(dp, RoundingMode.HALF_UP);
    }
}
