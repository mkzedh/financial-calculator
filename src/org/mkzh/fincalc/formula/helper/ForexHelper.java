package org.mkzh.fincalc.formula.helper;

import java.math.BigDecimal;
import java.util.Hashtable;

public class ForexHelper extends Helper {
    public static void collectValue(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter the value to convert.
                >\s""", "v", args);
    }

    public static void collectExchangeRate(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter the rate of the current currency.
                >\s""", "r0", args);
        collect("""
                Enter the rate of the currency to convert to.
                >\s""", "r1", args);
    }

    public static void collectBidAsk(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter the bid price.
                >\s""", "bid", args);
        collect("""
                Enter the ask price.
                >\s""", "ask", args);
    }

    public static void collectCrossBaseTerms(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter the bid price for the base currency in the cross rate.
                >\s""", "bid0", args);
        collect("""
                Enter the ask price for the base currency in the cross rate.
                >\s""", "ask0", args);
        collect("""
                Enter the bid price for the terms currency in the cross rate.
                >\s""", "bid1", args);
        collect("""
                Enter the ask price for the terms currency in the cross rate.
                >\s""", "ask1", args);
    }

    public static void collectCrossIndirectDirect(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter the bid price of the direct quote.
                >\s""", "bid0", args);
        collect("""
                Enter the ask price of the direct quote.
                >\s""", "ask0", args);
        collect("""
                Enter the bid price of the indirect quote.
                >\s""", "bid1", args);
        collect("""
                Enter the ask price of the indirect quote.
                >\s""", "ask1", args);
    }

    public static void collectSpot(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter spot rate.
                >\s""", "S", args);
    }

    public static void collectForwardDays(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter number of forward days
                >\s""", "f", args);
    }

    public static void collectNumDaysInYear(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter number of days in a year (ex: 360 for US and European markets)
                >\s""", "n", args);
    }

    public static void collectInterestRateBaseTerms(Hashtable<String, BigDecimal> args) {
        collect("""
                Enter interest rates of base currency.
                >\s""", "Ib", args);
        collect("""
                Enter interest rate of terms currency.
                >\s""", "It", args);
    }
}
