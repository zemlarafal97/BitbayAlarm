package pl.rzemla.bitbayalarm;

public class Resources {

    private final static String[] currencies = {"PLN", "USD", "EUR", "BTC"};

    public static String[] getCurrencies() {
        return currencies;
    }

    public static String getCurrencySymbol(String currency) {

        switch (currency) {
            case "PLN":
                return "zł";
            case "USD":
                return "$";
            case "EUR":
                return "€";
            case "BTC":
                return "B";
            default:
                return "unknown";
        }
    }


}
