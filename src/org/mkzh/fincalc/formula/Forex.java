package org.mkzh.fincalc.formula;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;

// import helper
import org.mkzh.fincalc.formula.helper.ForexHelper;

public class Forex {
    public static class CurrencyConversion {
        public static void convert(int dp) {
            // collect arguments
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            ForexHelper.collectValue(args);
            ForexHelper.collectExchangeRate(args);

            // calculate
            BigDecimal res = convertCalc(args.get("v"), args.get("r0"), args.get("r1"), dp);

            // display
            System.out.println("Converted value = " + ForexHelper.round(res, dp));
        }
        private static BigDecimal convertCalc(BigDecimal v, BigDecimal r0, BigDecimal r1, int dp) {
            return v.multiply(r1.divide(r0, dp+2, RoundingMode.HALF_UP));
        }
    }

    public static class SpotQuotation {
        public static void transpose(int dp) {
            // collect arguments Value,
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            ForexHelper.collectBidAsk(args);

            // calculate
            BigDecimal[] res = transposeCalc(args.get("bid"), args.get("ask"), dp);

            // display
            System.out.println("Transposed bid price = " + ForexHelper.round(res[0], dp));
            System.out.println("Transposed ask price = " + ForexHelper.round(res[1], dp));
        }
        private static BigDecimal[] transposeCalc(BigDecimal bid, BigDecimal ask, int dp) {
            BigDecimal[] transposed = new BigDecimal[2];
            transposed[0] = BigDecimal.valueOf(1).divide(ask, dp+2, RoundingMode.HALF_UP);
            transposed[1] = BigDecimal.valueOf(1).divide(bid, dp+2, RoundingMode.HALF_UP);

            return transposed;
        }

        public static void percentageSpread(int dp) {
            // collect arguments Value,
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            ForexHelper.collectBidAsk(args);

            // calculate
            BigDecimal res = percentageSpreadCalc(args.get("bid"), args.get("ask"), dp);

            // display
            System.out.println("Percentage spread = " + ForexHelper.round(res, dp));
        }
        private static BigDecimal percentageSpreadCalc(BigDecimal bid, BigDecimal ask, int dp) {
            return ask.subtract(bid).divide(bid, dp+2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        }
    }

    public static class CrossRate {
        public static void directDirect(int dp) {
            // collect arguments Value,
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            ForexHelper.collectCrossBaseTerms(args);

            // calculate
            BigDecimal[] crossRate = directDirectCalc(args.get("bid0"), args.get("ask0"), args.get("bid1"), args.get("ask1"), dp);

            // display
            System.out.println("Cross rate bid price = " + ForexHelper.round(crossRate[0], dp));
            System.out.println("Cross rate ask price = " + ForexHelper.round(crossRate[1], dp));
        }
        private static BigDecimal[] directDirectCalc(BigDecimal bid0, BigDecimal ask0, BigDecimal bid1, BigDecimal ask1, int dp) {
            BigDecimal[] crossRate = new BigDecimal[2];
            crossRate[0] = bid1.divide(ask0, dp+2, RoundingMode.HALF_UP);
            crossRate[1] = ask1.divide(bid0, dp+2, RoundingMode.HALF_UP);
            return crossRate;
        }

        public static void indirectDirect(int dp) {
            // collect arguments Value,
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            ForexHelper.collectCrossIndirectDirect(args);

            // calculate
            BigDecimal[] res = directIndirectCalc(args.get("bid0"), args.get("ask0"), args.get("bid1"), args.get("ask1"));

            // display
            System.out.println("Cross rate is in Ci/Cd (Ci = currency in indirect quote; Cd = currency in direct quote");
            System.out.println("Cross rate bid price = " + ForexHelper.round(res[0], dp));
            System.out.println("Cross rate ask price = " + ForexHelper.round(res[1], dp));
        }
        private static BigDecimal[] directIndirectCalc(BigDecimal bid0, BigDecimal ask0, BigDecimal bid1, BigDecimal ask1) {
            BigDecimal[] crossRate = new BigDecimal[2];
            crossRate[0] = bid0.multiply(bid1);
            crossRate[1] = ask0.multiply(ask1);
            return crossRate;
        }

        public static void indirectIndirect(int dp) {
            // collect arguments Value,
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            ForexHelper.collectCrossBaseTerms(args);

            // calculate
            BigDecimal[] res = indirectIndirectCalc(args.get("bid0"), args.get("ask0"), args.get("bid1"), args.get("ask1"), dp);

            // display
            System.out.println("Cross rate bid price = " + ForexHelper.round(res[0], dp));
            System.out.println("Cross rate ask price = " + ForexHelper.round(res[1], dp));
        }
        private static BigDecimal[] indirectIndirectCalc(BigDecimal bid0, BigDecimal ask0, BigDecimal bid1, BigDecimal ask1, int dp) {
            BigDecimal[] crossRate = new BigDecimal[2];
            crossRate[0] = bid0.divide(ask1, dp+2, RoundingMode.HALF_UP);
            crossRate[1] = ask0.divide(bid1, dp+2, RoundingMode.HALF_UP);
            return crossRate;
        }

    }

    public static class ForwardRate {
        public static void forwardPointsAndRate(int dp) {
            // collect arguments Value,
            Hashtable<String, BigDecimal> args = new Hashtable<>();
            ForexHelper.collectSpot(args);
            ForexHelper.collectForwardDays(args);
            ForexHelper.collectNumDaysInYear(args);
            ForexHelper.collectInterestRateBaseTerms(args);

            // calculate
            BigDecimal[] res = forwardPointsAndRateCalc(args.get("S"), args.get("f"), args.get("n"), args.get("Ib"), args.get("It"), dp);

            // display
            System.out.println("Forward points = " + ForexHelper.round(res[0], dp));
            System.out.println("Forward rate = " + ForexHelper.round(res[1], dp));
        }
        private static BigDecimal[] forwardPointsAndRateCalc(BigDecimal S, BigDecimal f, BigDecimal n, BigDecimal Ib, BigDecimal It, int dp) {
            BigDecimal[] pointsAndRate = new BigDecimal[2];
            BigDecimal forwardPointsDecimal = S.multiply(BigDecimal.valueOf(1).add(It.multiply(f.divide(n, dp+8, RoundingMode.HALF_UP))).divide(BigDecimal.valueOf(1).add(Ib.multiply(f.divide(n, dp+8, RoundingMode.HALF_UP))), dp+6, RoundingMode.HALF_UP).subtract(BigDecimal.valueOf(1)));
            pointsAndRate[0] = forwardPointsDecimal.multiply(BigDecimal.valueOf(10000));
            pointsAndRate[1] = S.add(forwardPointsDecimal);
            return pointsAndRate;
        }
    }



}
