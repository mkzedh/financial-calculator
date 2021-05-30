package org.mkzh.fincalc;
import java.util.Scanner;

// formulae
import org.mkzh.fincalc.formula.Debt;
import org.mkzh.fincalc.formula.Forex;

public class Main {

    public static void main(String[] args) {
        // choose number of decimal places to display
        int dp = choosePrecisionDP();
        // choose which formula to calculate
        chooseOptions(dp);
    }

    private static int choosePrecisionDP() {
        String options = """
                Enter the number of decimal places to display:
                """;

        System.out.print(options + "> ");
        Scanner in = new Scanner(System.in);

        return in.nextInt();
    }


    private static void chooseOptions(int dp) {
        String options = """
                Choose from the following options (type the number and hit Enter):
                SIMPLE
                    1.1 Simple interest | Return
                    1.2 Price | Present value | Principal
                    1.3 Yield | Interest rate
                    1.4 Face value | Future value | Accumulated value
                    1.5 Holding period yield (HPY)
                    1.6 Discount rate
                    1.7 Price with known discount rate
                COMPOUND (IMPLEMENTATION INCOMPLETE)
                    2.1 Compound interest | Return
                    2.2 Present value | Principal
                    2.3 Future value | Accumulated value
                FOREIGN EXCHANGE
                    3.1 Currency conversion
                    3.2 Transpose spot quotations
                    3.3 Percentage spread
                    3.4 Cross rate (direct x direct)
                    3.5 Cross rate (direct x indirect)
                    3.6 Cross rate (indirect x indirect)
                    3.7 Forward points and exchange rates
                """;

        System.out.print(options);

        Scanner in = new Scanner(System.in);

        // take only valid input
        boolean isValid;
        do {
            System.out.print("> ");
            // check for valid input
            isValid = true;
            switch (in.nextLine()) {
                case "1.1" -> Debt.Simple.interest(dp);
                case "1.2" -> Debt.Simple.price(dp);
                case "1.3" -> Debt.Simple.yield(dp);
                case "1.4" -> Debt.Simple.faceValue(dp);
                case "1.5" -> Debt.Simple.hpy(dp);
                case "1.6" -> Debt.Simple.discountRate(dp);
                case "1.7" -> Debt.Simple.priceFromDiscountRate(dp);

                case "2.1" -> Debt.Compound.interest(dp);
                case "2.2" -> Debt.Compound.presentValue(dp);
                case "2.3" -> Debt.Compound.futureValue(dp);

                case "3.1" -> Forex.CurrencyConversion.convert(dp);
                case "3.2" -> Forex.SpotQuotation.transpose(dp);
                case "3.3" -> Forex.SpotQuotation.percentageSpread(dp);
                case "3.4" -> Forex.CrossRate.directDirect(dp);
                case "3.5" -> Forex.CrossRate.indirectDirect(dp);
                case "3.6" -> Forex.CrossRate.indirectIndirect(dp);
                case "3.7" -> Forex.ForwardRate.forwardPointsAndRate(dp);

                default -> {
                    System.out.println("That's not a valid option");
                    isValid = false;
                }
            }
        } while (!isValid);
    }
}
