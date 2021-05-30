package org.mkzh.fincalc.formula.helper;

import java.math.BigDecimal;
import java.util.Hashtable;

public class DebtHelper extends Helper {
    // collect A
    public static void collectPrice(Hashtable<String, BigDecimal> args) {
        collect("""
                    Enter price | present value | principal.
                    >\s""", "A", args);
    }

    // collect S
    public static void collectFaceValue(Hashtable<String, BigDecimal> args) {
        collect("""
                    Enter face value | future value | accumulated value.
                    >\s""", "S", args);
    }

    // collect I
    public static void collectReturn(Hashtable<String, BigDecimal> args) {
        collect("""
                    Enter nominal return | total interest earned.
                    >\s""", "I", args);
    }

    // collect i
    public static void collectYield(Hashtable<String, BigDecimal> args) {
        collect("""
                    Enter yield | interest rate per period expressed as a decimal.
                    >\s""", "i", args);
    }

    // collect i0 and i1
    public static void collectYieldInitialFinal(Hashtable<String, BigDecimal> args) {
        collect("""
                    Enter initial yield expressed as a decimal.
                    >\s""", "i0", args);
        collect("""
                    Enter final yield expressed as a decimal.
                    >\s""", "i1", args);
    }

    // collect n
    public static void collectPeriod(Hashtable<String, BigDecimal> args) {
        collectOneOrTwo("""
                Enter the number of periods OR
                Enter the number of days per period (nd) and number of days elapsed/to maturity (d) in the form (nd d).
                >\s""", "n", "nd", "d", args);
    }

    // collect h
    public static void collectHoldingPeriod(Hashtable<String, BigDecimal> args) {
        collect("""
                    Enter holding period in days.
                    >\s""", "h", args);
    }

    // collect dr
    public static void collectDiscountRate(Hashtable<String, BigDecimal> args) {
        collect("""
                    Enter discount rate in decimal.
                    >\s""", "dr", args);
    }
}

