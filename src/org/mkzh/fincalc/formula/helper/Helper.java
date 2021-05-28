package org.mkzh.fincalc.formula.helper;

import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Hashtable;
import java.math.BigDecimal;

public class Helper {
    public static void collect(String request, String symbol, Hashtable<String, BigDecimal> args) {
        Scanner in = new Scanner(System.in);
        System.out.print(request);
        args.put(symbol, in.nextBigDecimal());
    }

    public static BigDecimal makePercentage(BigDecimal decimal) {
        return decimal.multiply(BigDecimal.valueOf(100));
    }

    public static BigDecimal round(BigDecimal exact, int dp) {
        return exact.setScale(dp, RoundingMode.HALF_UP);
    }
}
