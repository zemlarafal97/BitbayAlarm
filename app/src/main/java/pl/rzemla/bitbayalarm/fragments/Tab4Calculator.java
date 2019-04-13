package pl.rzemla.bitbayalarm.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.other.Resources;
import pl.rzemla.bitbayalarm.other.Calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class Tab4Calculator extends Fragment {

    private EditText buyForValueET;
    private EditText buyExchangeRatingValueET;
    private EditText iWantToEarnValueET;
    private EditText dealProvisionValueET;

    private Button countBtt;

    private TextView youWillGainValueTV;
    private TextView sellRateValueTV;
    private TextView noLossRateValueTV;

    private Spinner currencySpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab4, container, false);
        initializeViewElements(rootView);

        countBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    calculateAndShowResults();
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getString(R.string.Fill_empty_fields), Toast.LENGTH_SHORT).show();
                }


            }
        });


        return rootView;
    }


    private void initializeViewElements(View rootView) {
        buyForValueET = rootView.findViewById(R.id.buyForValueET);
        buyExchangeRatingValueET = rootView.findViewById(R.id.buyExchangeRatingValueET);
        iWantToEarnValueET = rootView.findViewById(R.id.iWantToEarnValueET);
        dealProvisionValueET = rootView.findViewById(R.id.dealProvisionValueET);
        countBtt = rootView.findViewById(R.id.countProfitBtt);
        youWillGainValueTV = rootView.findViewById(R.id.youWillGainValueTV);
        sellRateValueTV = rootView.findViewById(R.id.sellRateValueTV);
        noLossRateValueTV = rootView.findViewById(R.id.noLossRateValueTV);

        currencySpinner = rootView.findViewById(R.id.currencySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, Resources.getCurrencies());
        currencySpinner.setAdapter(adapter);
    }

    private void showResultsInCards(double youWillReceive, double sellRate, double noLossRate) {

        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        df.setMaximumFractionDigits(10);
        String symbol = Resources.getCurrencySymbol(currencySpinner.getSelectedItem().toString());

        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(df.format(youWillReceive)));
        sb.append(symbol);
        youWillGainValueTV.setText(sb);


        sb = new StringBuilder();
        sb.append(String.valueOf(df.format(sellRate)));
        sb.append(symbol);
        sellRateValueTV.setText(sb);

        sb = new StringBuilder();
        sb.append(String.valueOf(df.format(noLossRate)));
        sb.append(symbol);
        noLossRateValueTV.setText(sb);

    }

    private void calculateAndShowResults() {

        double buyFor = Double.parseDouble(buyForValueET.getText().toString());
        double buyRate = Double.parseDouble(buyExchangeRatingValueET.getText().toString());
        double iWantEarn = Double.parseDouble(iWantToEarnValueET.getText().toString());
        double provision = Double.parseDouble(dealProvisionValueET.getText().toString());

        double youWillReceive = buyFor + iWantEarn;
        double sellRate = Calculator.calculateSellRate(buyFor, buyRate, iWantEarn, provision);
        double noLossRate = Calculator.calculateNoLossRate(buyFor, buyRate, provision);

        if (currencySpinner.getSelectedItem().toString().equals("BTC")) {
            showResultsInCards(youWillReceive, sellRate, noLossRate);
        } else {
            showResultsInCards(Math.round(youWillReceive * 100.0) / 100.0, Math.round((sellRate) * 100.0) / 100.0, Math.round(noLossRate * 100.0) / 100.0);
        }
    }


}
