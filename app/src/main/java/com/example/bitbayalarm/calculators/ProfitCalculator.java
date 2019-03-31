package com.example.bitbayalarm.calculators;

public class ProfitCalculator {


    public static double calculateProfit(double buyFor, double buyRate, double sellRate, double provision) {

        double youBuy = buyFor / buyRate;

        double buyProvision = youBuy * provision / 100.0;

        double youWillReceive = youBuy - buyProvision;

        double youSell = youWillReceive * sellRate;

        double sellProvision = youSell * provision / 100.0;

        return youSell - sellProvision;

    }

    public static double calculateNoLossRate(double buyFor, double buyRate, double provision) {

        double youBuy = buyFor / buyRate;

        double buyProvision = youBuy * provision / 100.0;

        double youWillReceive = youBuy - buyProvision;

        return buyFor / (youWillReceive - youWillReceive * provision / 100.0);
    }


}
