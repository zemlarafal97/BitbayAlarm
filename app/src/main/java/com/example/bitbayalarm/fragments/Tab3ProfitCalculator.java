package com.example.bitbayalarm.fragments;


import android.graphics.Color;
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


import com.example.bitbayalarm.Configuration;
import com.example.bitbayalarm.R;
import com.example.bitbayalarm.calculators.ProfitCalculator;


public class Tab3ProfitCalculator extends Fragment {

    private EditText buyForValueET;
    private EditText buyExchangeRatingValueET;
    private EditText sellExchangeRatingValueET;
    private EditText dealProvisionValueET;

    private Button countProfitBtt;

    private TextView youWillGainValueTV;
    private TextView profitValueTV;
    private TextView noLossRateValueTV;

    private Spinner currencySpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3, container, false);
        initializeViewElements(rootView);


        countProfitBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double buyFor = Double.parseDouble(buyForValueET.getText().toString());
                double buyRate = Double.parseDouble(buyExchangeRatingValueET.getText().toString());
                double sellRate = Double.parseDouble(sellExchangeRatingValueET.getText().toString());
                double provision = Double.parseDouble(dealProvisionValueET.getText().toString());

                double profit = ProfitCalculator.calculateProfit(buyFor, buyRate, sellRate, provision);
                double noLossRate = ProfitCalculator.calculateNoLossRate(buyFor, buyRate, provision);

                /*
                    Toast.makeText(getActivity(),String.valueOf(profit),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),String.valueOf(noLossRate),Toast.LENGTH_SHORT).show();
                */

                youWillGainValueTV.setText(String.valueOf(buyFor + profit));
                profitValueTV.setText(String.valueOf(profit));
                noLossRateValueTV.setText(String.valueOf(noLossRate));

            }
        });


        return rootView;
    }

    private void initializeViewElements(View rootView) {
        buyForValueET = rootView.findViewById(R.id.buyForValueET);
        buyExchangeRatingValueET = rootView.findViewById(R.id.buyExchangeRatingValueET);
        sellExchangeRatingValueET = rootView.findViewById(R.id.sellExchangeRatingValueET);
        dealProvisionValueET = rootView.findViewById(R.id.dealProvisionValueET);
        countProfitBtt = rootView.findViewById(R.id.countProfitBtt);
        youWillGainValueTV = rootView.findViewById(R.id.youWillGainValueTV);
        profitValueTV = rootView.findViewById(R.id.profitValueTV);
        currencySpinner = rootView.findViewById(R.id.currencySpinner);
        noLossRateValueTV = rootView.findViewById(R.id.noLossRateValueTV);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, Configuration.getCurrencies());
        currencySpinner.setAdapter(adapter);
    }



}
