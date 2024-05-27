import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
class CurrencyConverter {

    private static final Map<String, Map<String, Double>> exchangeRates =new HashMap<>();

    static {

        Map<String, Double> exchangeRatesINR = new HashMap<>();

        exchangeRatesINR.put("USD", 0.012030455);
        exchangeRatesINR.put("EUR", 0.011009769);
        exchangeRatesINR.put("CAD", 0.01620713);
        exchangeRatesINR.put("YEN", 0.094086563);
        exchangeRatesINR.put("INR", 1.00);

        Map<String, Double> exchangeRatesUSD = new HashMap<>();
        exchangeRatesUSD.put("INR", 83.123277);
        exchangeRatesUSD.put("EUR", 0.91513789);
        exchangeRatesUSD.put("CAD", 1.347224);
        exchangeRatesUSD.put("YEN", 7.8207076);
        exchangeRatesUSD.put("USD", 1.00);

        Map<String, Double> exchangeRatesCAD = new HashMap<>();
        exchangeRatesCAD.put("INR", 61.69684);
        exchangeRatesCAD.put("EUR", 0.67928079);
        exchangeRatesCAD.put("USD", 8.74216798);
        exchangeRatesCAD.put("YEN", 5.8041596);
        exchangeRatesCAD.put("CAD", 1.00);


        Map<String, Double> exchangeRatesEUR = new HashMap<>();
        exchangeRatesEUR.put("INR", 90.823935);
        exchangeRatesEUR.put("CAD", 1.4722727);
        exchangeRatesEUR.put("USD", 1.8926778);
        exchangeRatesEUR.put("YEN", 8.5454306);
        exchangeRatesEUR.put("EUR", 1.00);

        Map<String, Double> exchangeRatesHKD = new HashMap<>();
        exchangeRatesHKD.put("INR", 10.628342);
        exchangeRatesHKD.put("CAD", 0.17227875);
        exchangeRatesHKD.put("USD", 0.12786675);
        exchangeRatesHKD.put("EUR", 0.11701191);
        exchangeRatesHKD.put("YEN", 1.00);

        exchangeRates.put("INR", exchangeRatesINR);
        exchangeRates.put("USD", exchangeRatesUSD);
        exchangeRates.put("CAD", exchangeRatesCAD);
        exchangeRates.put("EUR", exchangeRatesEUR);
        exchangeRates.put("YEN", exchangeRatesHKD);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("-------*----------------------------------Currency converter----------------------------------*-----------------");
        System.out.println("1.INR(India Rupees) \t 2.USO (US Dollars) \t 3.CAD(Canadian Dollars) \t 4. EUR(Euros) \t 5.YEN(Japanese YEN)");
        System.out.print("Put currency to convert from (Options to choose from INR, USD, CAD, EUR, YEN): ");
        String fromCurrency = scanner.next().toUpperCase();
        convertCurrency(fromCurrency);
        scanner.close();

    }

    private static void convertCurrency(String fromCurrency) {

        Map<String, Double> rate = exchangeRates.get(fromCurrency);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Put currency to convert to (Options to choose from INR, USD, CAD, EUR, YEN): ");
        String toCurrency = scanner.next().toUpperCase();
        while (!rate.containsKey(toCurrency)) {
            System.out.print("Invalid currency. Enter again: ");
            toCurrency = scanner.next().toUpperCase();
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Amount that you want to Convert");
        double amount = sc.nextDouble();
        double convertedAmount = amount * (rate.get(toCurrency));
        System.out.println("Amount in" + toCurrency + ":" + convertedAmount);
        System.out.println("-----------------------------------------------------------------------------------");
        sc.close();
        scanner.close();
    }
}