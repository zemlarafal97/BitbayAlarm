package pl.rzemla.bitbayalarm;

public class Resources {

    private final static String[] currencies = {"PLN", "USD", "EUR", "BTC"};
    private final static String[] cryptocurrencies = {"BTC", "BCC", "BTG", "LTC", "ETH", "LSK", "DASH", "GAME", "XIN", "XRP", "KZC", "XMR", "ZEC", "GNT", "FTO", "OMG", "PAY", "REP", "ZRX", "BAT", "NEU", "TRX"};


    public static String[] getCryptocurrencies() {
        return cryptocurrencies;
    }

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
