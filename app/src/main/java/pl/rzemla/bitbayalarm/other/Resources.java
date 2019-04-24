package pl.rzemla.bitbayalarm.other;

public class Resources {

    private final static String[] currencies = {"PLN", "USD", "EUR", "BTC","USDC"};
    private final static String[] cryptocurrencies = {"BTC", "BCC", "BTG", "LTC", "ETH", "LSK", "DASH", "GAME", "XIN", "XRP", "KZC", "XMR", "ZEC", "GNT", "FTO", "OMG", "PAY", "REP", "ZRX", "BAT", "NEU", "TRX","EXY","AMLT","BOB","BSV","BCP","XBX","XLM"};
    private final static Integer[] intervals = {1, 2, 3, 4, 5, 10, 15, 20, 30, 60};

    public static String[] getCryptocurrencies() {
        return cryptocurrencies;
    }

    public static String[] getCurrencies() {
        return currencies;
    }

    public static Integer[] getIntervals() {
        return intervals;
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
            case "USDC":
                return "($)";
            default:
                return "unknown";
        }
    }



}
