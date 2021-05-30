package org.mkzh.fincalc.formula;

import ch.obermuhlner.math.big.BigDecimalMath;
import org.mkzh.fincalc.formula.helper.DebtHelper;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Hashtable;

public class Debt {
    public static class Simple {
        // FORMULAE
        // simple interest (i = A * n * i)
        public static void interest(int dp) {
            // collect arguments A, n, i
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectPrice(args);
            DebtHelper.collectPeriod(args);
            DebtHelper.collectYield(args);

            // calculations (i = A * n * i; S = i + A)
            BigDecimal res;
            if (args.get("n") != null) { // use number of periods
                res = interestCalc(args.get("A"), args.get("n"), args.get("i"));
            } else { // use days elapsed and days per period
                res = interestCalc(args.get("A"), args.get("nd"), args.get("d"), args.get("i"), dp);
            }

            // display results
            System.out.println("Total interest (simple) = " + DebtHelper.round(res, dp));
        }
        private static BigDecimal interestCalc(BigDecimal A, BigDecimal n, BigDecimal i) {
            return A.multiply(n).multiply(i);
        }
        private static BigDecimal interestCalc(BigDecimal A, BigDecimal nd, BigDecimal d, BigDecimal i, int dp) {
            MathContext mathContext = new MathContext(dp+2, RoundingMode.HALF_UP);
            return A.multiply(d.divide(nd, mathContext)).multiply(i);
        }

        // present value with simple interest (A = S / (1 + (n * i))
        public static void price(int dp) {
            // collect arguments S, n, i
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectFaceValue(args);
            DebtHelper.collectPeriod(args);
            DebtHelper.collectYield(args);

            // calculations
            BigDecimal res;
            if (args.get("n") != null) { // use number of periods
                res = priceCalc(args.get("S"), args.get("n"), args.get("i"), dp);
            } else { // use days elapsed and days per period
                res = priceCalc(args.get("S"), args.get("nd"), args.get("d"), args.get("i"), dp);
            }

            // display results
            System.out.println("Price or Present value with simple interest = " + DebtHelper.round(res, dp));
        }
        private static BigDecimal priceCalc(BigDecimal S, BigDecimal n, BigDecimal i, int dp) {
            MathContext mathContext = new MathContext(dp+4, RoundingMode.HALF_UP);
            return S.divide(n.multiply(i).add(BigDecimal.valueOf(1)), mathContext);
        }
        private static BigDecimal priceCalc(BigDecimal S, BigDecimal nd, BigDecimal d, BigDecimal i, int dp) {
            MathContext mathContext = new MathContext(dp+4, RoundingMode.HALF_UP);
            return S.divide(d.divide(nd, mathContext).multiply(i).add(BigDecimal.valueOf(1)), mathContext);
        }

        // yield with simple interest (i = n * I/A)
        public static void yield(int dp) {
            // collect arguments n, I, A
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectPrice(args);
            DebtHelper.collectReturn(args);
            DebtHelper.collectPeriod(args);

            // calculations
            BigDecimal res;
            if (args.get("n") != null) { // use number of periods
                res = yieldCalc(args.get("n"), args.get("I"), args.get("A"), dp);
            } else { // use days elapsed and days per period
                res = yieldCalc(args.get("nd"), args.get("d"), args.get("I"), args.get("A"), dp);
            }

            // display results
            System.out.println("Yield or interest rate with simple interest in decimal = " + DebtHelper.round(res, dp));
            System.out.println("Yield or interest rate with simple interest in % = " + DebtHelper.round(DebtHelper.makePercentage(res), dp));
        }
        private static BigDecimal yieldCalc(BigDecimal n, BigDecimal I, BigDecimal A, int dp) {
            MathContext mathContext = new MathContext(dp+2, RoundingMode.HALF_UP);
            return n.multiply(I.divide(A, mathContext));
        }
        private static BigDecimal yieldCalc(BigDecimal nd, BigDecimal d, BigDecimal I, BigDecimal A, int dp) {
            MathContext mathContext = new MathContext(dp+4, RoundingMode.HALF_UP);
            return nd.divide(d,mathContext).multiply(I.divide(A, mathContext));
        }

        // S = A + I
        // face value with simple interest
        public static void faceValue(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectPrice(args);
            DebtHelper.collectYield(args);
            DebtHelper.collectPeriod(args);

            // calculations
            BigDecimal res;
            if (args.get("n") != null) { // use number of periods
                res = faceValueCalc(args.get("A"), args.get("n"), args.get("i"));
            } else { // use days elapsed and days per period
                res = faceValueCalc(args.get("A"), args.get("nd"), args.get("d"), args.get("i"), dp);
            }

            // display results
            System.out.println("Face value or Future value or Accumulated value with simple interest = " + DebtHelper.round(res, dp));
        }
        private static BigDecimal faceValueCalc(BigDecimal A, BigDecimal n, BigDecimal i) {
            return A.add(interestCalc(A, n, i));
        }
        private static BigDecimal faceValueCalc(BigDecimal A, BigDecimal nd, BigDecimal d, BigDecimal i, int dp) {
            return A.add(interestCalc(A, nd, d, i, dp));
        }

        // holding period yield with simple interest (hpy = n/h * (sale price - purchase price)/purchase price)
        public static void hpy(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectFaceValue(args);
            DebtHelper.collectPeriod(args);
            DebtHelper.collectYieldInitialFinal(args);
            DebtHelper.collectHoldingPeriod(args);

            // calculations
            BigDecimal res = hpyCalc(args.get("S"), args.get("nd"), args.get("d"), args.get("i0"), args.get("i1"), args.get("h"), dp);

            // display results
            System.out.println("Holding period yield with simple interest = " + DebtHelper.round(res, dp));
        }
        private static BigDecimal hpyCalc(BigDecimal S, BigDecimal nd, BigDecimal d, BigDecimal i0, BigDecimal i1, BigDecimal h, int dp) {
            MathContext mathContext = new MathContext(dp+2, RoundingMode.HALF_UP);
            BigDecimal buyPrice = priceCalc(S, nd, d, i0, dp);
            BigDecimal sellPrice = priceCalc(S, nd, d.subtract(h), i1, dp);
            return nd.divide(h, mathContext).multiply(sellPrice.subtract(buyPrice).divide(buyPrice, mathContext));
        }

        // discount rate = (S - A)/S * n/d * 100%
        public static void discountRate(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectFaceValue(args);
            DebtHelper.collectPrice(args);
            DebtHelper.collectPeriod(args);

            // calculations
            BigDecimal res = discountRateCalc(args.get("S"), args.get("A"), args.get("n"), args.get("d"), dp);

            // display results
            System.out.println("Discount rate with simple interest in decimal = " + DebtHelper.round(res, dp));
            System.out.println("Discount rate with simple interest in % = " + DebtHelper.round(DebtHelper.makePercentage(res), dp));
        }
        private static BigDecimal discountRateCalc(BigDecimal S, BigDecimal A, BigDecimal n, BigDecimal d, int dp) {
            MathContext mathContext = new MathContext(dp+4, RoundingMode.HALF_UP);
            return S.subtract(A).divide(S, mathContext).multiply(n).divide(d, mathContext);
        }

        // price when discount rate is known = S * (1 - (d/n * dr))
        public static void priceFromDiscountRate(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectDiscountRate(args);
            DebtHelper.collectFaceValue(args);
            DebtHelper.collectPeriod(args);

            // calculations
            BigDecimal res = priceFromDiscountRateCalc(args.get("dr"), args.get("S"), args.get("n"), args.get("d"), dp);

            //display results
            System.out.println("Price = " + DebtHelper.round(res, dp));
        }
        private static BigDecimal priceFromDiscountRateCalc(BigDecimal dr, BigDecimal S, BigDecimal n, BigDecimal d, int dp) {
            MathContext mathContext = new MathContext(dp+2, RoundingMode.HALF_UP);
            return S.multiply(BigDecimal.valueOf(1).subtract(d.divide(n, mathContext).multiply(dr)));
        }
    }

    public static class Compound {
        // compound interest
        public static void interest(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            DebtHelper.collectPrice(args);
            DebtHelper.collectYield(args);
            DebtHelper.collectPeriod(args);

            // calculations
            BigDecimal res;
            if (args.get("n") != null) { // use number of periods
                res = compoundInterestCalc(args.get("A"), args.get("i"), args.get("n"), dp);
            } else { // use days elapsed and days per period
                res = compoundInterestCalc(args.get("A"), args.get("i"), args.get("nd"), args.get("d"), dp);
            }

            //display results
            System.out.println("Price = " + DebtHelper.round(res, dp));
        }
        private static BigDecimal compoundInterestCalc(BigDecimal A, BigDecimal i, BigDecimal n, int dp) {
            MathContext mathContext = new MathContext(dp+2);
            return A.multiply(BigDecimalMath.pow(BigDecimal.valueOf(1).add(i), n, mathContext));
        }
        private static BigDecimal compoundInterestCalc(BigDecimal A, BigDecimal i, BigDecimal nd, BigDecimal d, int dp) {
            MathContext mathContext = new MathContext(dp+4, RoundingMode.HALF_UP);
            return A.multiply(BigDecimalMath.pow(BigDecimal.valueOf(1).add(i), d.divide(nd, mathContext), mathContext));
        }

        // present value with compound interest
        public static void presentValue(int dp) {

        }

        // future value with compound interest
        public static void futureValue(int dp) {

        }
    }
}
