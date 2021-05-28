package org.mkzh.fincalc.formula;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;

// import helper
import org.mkzh.fincalc.formula.helper.DebtHelper;

public class Debt {
    public static class SimpleInterest {
        // FORMULAE
        // simple interest (i = A * d/n * i)
        public static void interest(int dp) {
            // collect arguments A, n, i
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectPrice(args);
            DebtHelper.collectInterestPeriod(args);
            DebtHelper.collectYield(args);

            // calculations (i = A * n * i; S = i + A)
            BigDecimal simpleInterest = interestCalc(args.get("A"), args.get("n"), args.get("d"), args.get("i"), dp);

            // display results
            System.out.println("Total interest (simple) = " + DebtHelper.round(simpleInterest, dp));
        }
        private static BigDecimal interestCalc(BigDecimal A, BigDecimal n, BigDecimal d, BigDecimal i, int dp) {
            return A.multiply(d.divide(n, dp+2, RoundingMode.HALF_UP)).multiply(i);
        }

        // present value with simple interest (A = S / (1 + (d/n * i))
        public static void price(int dp) {
            // collect arguments S, n, i
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectFaceValue(args);
            DebtHelper.collectInterestPeriod(args);
            DebtHelper.collectYield(args);

            // calculations
            BigDecimal price = priceCalc(args.get("S"), args.get("n"), args.get("d"), args.get("i"), dp);

            // display results
            System.out.println("Price or Present value with simple interest = " + DebtHelper.round(price, dp));
        }
        private static BigDecimal priceCalc(BigDecimal S, BigDecimal n, BigDecimal d, BigDecimal i, int dp) {
            return S.divide(d.divide(n, dp+4, RoundingMode.HALF_UP).multiply(i).add(BigDecimal.valueOf(1)), dp+2, RoundingMode.HALF_UP);
        }

        // yield with simple interest (i = n/d * I/A)
        public static void yield(int dp) {
            // collect arguments n, I, A
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectPrice(args);
            DebtHelper.collectReturn(args);
            DebtHelper.collectInterestPeriod(args);

            // calculations
            BigDecimal yield = yieldCalc(args.get("n"), args.get("d"), args.get("I"), args.get("A"), dp);

            // display results
            System.out.println("Yield or interest rate with simple interest in decimal = " + DebtHelper.round(yield, dp));
            System.out.println("Yield or interest rate with simple interest in % = " + DebtHelper.round(DebtHelper.makePercentage(yield), dp));
        }
        private static BigDecimal yieldCalc(BigDecimal n, BigDecimal d, BigDecimal I, BigDecimal A, int dp) {
            return n.divide(d,dp+4, RoundingMode.HALF_UP).multiply(I.divide(A, dp+4, RoundingMode.HALF_UP));
        }

        // S = A + I
        // face value with simple interest
        public static void faceValue(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectPrice(args);
            DebtHelper.collectYield(args);
            DebtHelper.collectInterestPeriod(args);

            // calculations
            BigDecimal faceValue = faceValueCalc(args.get("A"), args.get("n"), args.get("d"), args.get("i"), dp);

            // display results
            System.out.println("Face value or Future value or Accumulated value with simple interest = " + DebtHelper.round(faceValue, dp));
        }
        private static BigDecimal faceValueCalc(BigDecimal A, BigDecimal n, BigDecimal d, BigDecimal i, int dp) {
            return A.add(interestCalc(A, n, d, i, dp));
        }

        // holding period yield with simple interest (hpy = n/h * (sale price - purchase price)/purchase price)
        public static void hpy(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectFaceValue(args);
            DebtHelper.collectInterestPeriod(args);
            DebtHelper.collectYieldInitialFinal(args);
            DebtHelper.collectHoldingPeriod(args);

            // calculations
            BigDecimal hpy = hpyCalc(args.get("S"), args.get("n"), args.get("d"), args.get("i0"), args.get("i1"), args.get("h"), dp);

            // display results
            System.out.println("Holding period yield with simple interest = " + DebtHelper.round(hpy, dp));
        }
        private static BigDecimal hpyCalc(BigDecimal S, BigDecimal n, BigDecimal d, BigDecimal i0, BigDecimal i1, BigDecimal h, int dp) {
            BigDecimal buyPrice = priceCalc(S, n, d, i0, dp);
            BigDecimal sellPrice = priceCalc(S, n, d.subtract(h), i1, dp);
            return n.divide(h, dp+2, RoundingMode.HALF_UP).multiply(sellPrice.subtract(buyPrice).divide(buyPrice, dp+2, RoundingMode.HALF_UP));
        }

        // discount rate = (S - A)/S * n/d * 100%
        public static void discountRate(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectFaceValue(args);
            DebtHelper.collectPrice(args);
            DebtHelper.collectInterestPeriod(args);

            // calculations
            BigDecimal discountRate = discountRateCalc(args.get("S"), args.get("A"), args.get("n"), args.get("d"), dp);

            // display results
            System.out.println("Discount rate with simple interest in decimal = " + DebtHelper.round(discountRate, dp));
            System.out.println("Discount rate with simple interest in % = " + DebtHelper.round(DebtHelper.makePercentage(discountRate), dp));
        }
        private static BigDecimal discountRateCalc(BigDecimal S, BigDecimal A, BigDecimal n, BigDecimal d, int dp) {
            return S.subtract(A).divide(S, dp+4, RoundingMode.HALF_UP).multiply(n).divide(d, dp+4, RoundingMode.HALF_UP);
        }

        // price when discount rate is known = S * (1 - (d/n * dr))
        public static void priceFromDiscountRate(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectDiscountRate(args);
            DebtHelper.collectFaceValue(args);
            DebtHelper.collectInterestPeriod(args);

            // calculations
            BigDecimal price = priceFromDiscountRateCalc(args.get("dr"), args.get("S"), args.get("n"), args.get("d"), dp);

            //display results
            System.out.println("Price = " + DebtHelper.round(price, dp));
        }
        private static BigDecimal priceFromDiscountRateCalc(BigDecimal dr, BigDecimal S, BigDecimal n, BigDecimal d, int dp) {
            return S.multiply(BigDecimal.valueOf(1).subtract(d.divide(n, dp+2, RoundingMode.HALF_UP).multiply(dr)));
        }
    }
}
