package pl.rzemla.bitbayalarm.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pl.rzemla.bitbayalarm.BitbayRequest;
import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.other.Currency;
import pl.rzemla.bitbayalarm.other.Resources;
import pl.rzemla.bitbayalarm.interfaces.AdapterClicker;
import pl.rzemla.bitbayalarm.interfaces.ServerCallback;
import pl.rzemla.bitbayalarm.singletons.VolleySingleton;
import pl.rzemla.bitbayalarm.adapters.CurrenciesAdapter;


import com.android.volley.RequestQueue;


import pl.rzemla.bitbayalarm.other.Bitbay;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;


public class Tab1ExchangeRate extends Fragment {

    private Button refreshBtt;
    private TextView lastValueTV;
    private TextView volumeValueTV;
    private TextView maxValueTV;
    private TextView minValueTV;
    private TextView refreshTimeValueTV;
    private TextView exchangeDescrTV;

    private String currentCryptocurrency = "BTC";
    private String currentCurrency = "PLN";

    private RequestQueue mQueue;

    private static final String PREFS_NAME = "pl.rzemla.bitbayalarm.Tab1ExchangeRate";
    private static final String PREF_CRYPTOCURRENCY = "cryptocurrency";
    private static final String PREF_CURRENCY = "currency";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        currentCurrency = loadCurrencyPreference(getActivity());
        currentCryptocurrency = loadCryptocurrencyPreference(getActivity());

        initializeViewElements(rootView);
        initializeCryptocurrenciesRecyclerView(rootView);
        initializeCurrenciesRecyclerView(rootView);

        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        setViewElementsClickListeners();

        updateRatesViews();

        return rootView;
    }

    private void initializeViewElements(View rootView) {
        refreshBtt = rootView.findViewById(R.id.refreshBtt);
        lastValueTV = rootView.findViewById(R.id.lastValueTV);
        volumeValueTV = rootView.findViewById(R.id.volumeValueTV);
        maxValueTV = rootView.findViewById(R.id.maxValueTV);
        minValueTV = rootView.findViewById(R.id.minValueTV);
        refreshTimeValueTV = rootView.findViewById(R.id.refreshTimeValueTV);
        exchangeDescrTV = rootView.findViewById(R.id.exchangeDescrTV);
    }

    private void initializeCurrenciesRecyclerView(View rootView) {
        LinkedList<Currency> currencyList = new LinkedList<>();
        currencyList.add(new Currency(R.drawable.shadow_pln, R.drawable.zl_icon_24dp, "PLN"));
        currencyList.add(new Currency(R.drawable.shadow_usd, R.drawable.dollar_icon_24dp, "USD"));
        currencyList.add(new Currency(R.drawable.shadow_eur, R.drawable.eur_icon_24dp, "EUR"));
        currencyList.add(new Currency(R.drawable.shadow_btc, R.drawable.btc_icon_24dp, "BTC"));
        currencyList.add(new Currency(R.drawable.shadow_usdc, R.drawable.usdc_icon_24dp, "USDC"));

        RecyclerView recyclerView = rootView.findViewById(R.id.currency_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new CurrenciesAdapter(currencyList, getActivity(), new AdapterClicker() {
            @Override
            public void onClick(String currency) {
                currentCurrency = currency;
                updateRatesViews();
            }
        },currentCurrency);
        recyclerView.setAdapter(mAdapter);

    }

    private void initializeCryptocurrenciesRecyclerView(View rootView) {
        LinkedList<Currency> currencyList = new LinkedList<>();
        currencyList.add(new Currency(R.drawable.shadow_btc, R.drawable.btc_icon_24dp, "BTC"));
        currencyList.add(new Currency(R.drawable.shadow_bcc, R.drawable.bcc_icon_24dp, "BCC"));
        currencyList.add(new Currency(R.drawable.shadow_btg, R.drawable.btg_icon_24dp, "BTG"));
        currencyList.add(new Currency(R.drawable.shadow_ltc, R.drawable.ltc_icon_24dp, "LTC"));
        currencyList.add(new Currency(R.drawable.shadow_eth, R.drawable.eth_icon_24dp, "ETH"));
        currencyList.add(new Currency(R.drawable.shadow_lsk, R.drawable.lsk_icon_24dp, "LSK"));
        currencyList.add(new Currency(R.drawable.shadow_dash, R.drawable.dash_icon_24dp, "DASH"));
        currencyList.add(new Currency(R.drawable.shadow_game, R.drawable.game_icon_24dp, "GAME"));
        currencyList.add(new Currency(R.drawable.shadow_xin, R.drawable.xin_icon_24dp, "XIN"));
        currencyList.add(new Currency(R.drawable.shadow_xrp,R.drawable.xrp_icon_24dp,"XRP"));
        currencyList.add(new Currency(R.drawable.shadow_kzc, R.drawable.kzc_icon_24dp, "KZC"));
        currencyList.add(new Currency(R.drawable.shadow_xmr, R.drawable.xmr_icon_24dp, "XMR"));
        currencyList.add(new Currency(R.drawable.shadow_zec, R.drawable.zec_icon_24dp, "ZEC"));
        currencyList.add(new Currency(R.drawable.shadow_gnt,R.drawable.gnt_icon_24dp,"GNT"));
        currencyList.add(new Currency(R.drawable.shadow_fto,R.drawable.fto_icon_24dp,"FTO"));
        currencyList.add(new Currency(R.drawable.shadow_omg,R.drawable.omg_icon_24dp,"OMG"));
        currencyList.add(new Currency(R.drawable.shadow_pay,R.drawable.pay_icon_24dp,"PAY"));
        currencyList.add(new Currency(R.drawable.shadow_rep,R.drawable.rep_icon_24dp,"REP"));
        currencyList.add(new Currency(R.drawable.shadow_zrx,R.drawable.zrx_icon_24dp,"ZRX"));
        currencyList.add(new Currency(R.drawable.shadow_bat,R.drawable.bat_icon_24dp,"BAT"));
        currencyList.add(new Currency(R.drawable.shadow_neu,R.drawable.neu_icon_24dp,"NEU"));
        currencyList.add(new Currency(R.drawable.shadow_trx,R.drawable.trx_icon_24dp,"TRX"));
        currencyList.add(new Currency(R.drawable.shadow_amlt,R.drawable.amlt_icon_24dp,"AMLT"));
        currencyList.add(new Currency(R.drawable.shadow_exy,R.drawable.exy_icon_24dp,"EXY"));
        currencyList.add(new Currency(R.drawable.shadow_bob,R.drawable.bob_icon_24dp,"BOB"));
        currencyList.add(new Currency(R.drawable.shadow_lml,R.drawable.lml_icon_24dp,"LML"));
        currencyList.add(new Currency(R.drawable.shadow_bsv,R.drawable.bsv_icon_24dp,"BSV"));
        currencyList.add(new Currency(R.drawable.shadow_bcp,R.drawable.bcp_icon_24dp,"BCP"));
        currencyList.add(new Currency(R.drawable.shadow_xbx,R.drawable.xbx_icon_24dp,"XBX"));
        currencyList.add(new Currency(R.drawable.shadow_xlm,R.drawable.xlm_icon_24dp,"XLM"));




        RecyclerView recyclerView = rootView.findViewById(R.id.cryptocurrency_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new CurrenciesAdapter(currencyList, getActivity(), new AdapterClicker() {
            @Override
            public void onClick(String cryptocurrency) {
                currentCryptocurrency = cryptocurrency;
                updateRatesViews();
            }
        },currentCryptocurrency);
        recyclerView.setAdapter(mAdapter);
    }

    private void setViewElementsClickListeners() {
        refreshBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRatesViews();
            }
        });
    }

    private void updateRatesViews() {


        if (!currentCurrency.equals(currentCryptocurrency)) {
            BitbayRequest.makeBitbayTickerRequest(mQueue, currentCryptocurrency, currentCurrency, new ServerCallback() {

                @Override
                public void onSuccess(Bitbay bitbay) {
                    String currencySymbol = Resources.getCurrencySymbol(currentCurrency);
                    DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
                    df.setMaximumFractionDigits(10);

                    lastValueTV.setText(String.valueOf(df.format(bitbay.getLast()) + currencySymbol));
                    maxValueTV.setText(String.valueOf(df.format(bitbay.getMax24h()) + currencySymbol));
                    minValueTV.setText(String.valueOf(df.format(bitbay.getMin24h()) + currencySymbol));
                    df.setMaximumFractionDigits(4);
                    volumeValueTV.setText(String.valueOf(df.format(bitbay.getVolume())));
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
                    String date = dateFormat.format(Calendar.getInstance().getTime());
                    refreshTimeValueTV.setText(date);

                    String stringBuilder = getString(R.string.Exchange_rate) +
                            " " +
                            currentCryptocurrency +
                            " " +
                            getString(R.string.dash) +
                            " " +
                            currentCurrency;
                    exchangeDescrTV.setText(stringBuilder);


                }
            });
        } else {
            Toast.makeText(getActivity(),R.string.Cryptocurrency_must_be_different_than_currency,Toast.LENGTH_SHORT).show();
            lastValueTV.setText("-");
            maxValueTV.setText("-");
            minValueTV.setText("-");
            volumeValueTV.setText("-");
            refreshTimeValueTV.setText("-");
            exchangeDescrTV.setText("-");
        }
    }

    private void saveCryptocurrencyPreference(Context context, String cryptocurrency) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(PREF_CRYPTOCURRENCY, cryptocurrency);
        prefs.apply();
    }

    private void saveCurrencyPreference(Context context, String currency) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(PREF_CURRENCY, currency);
        prefs.apply();
    }

    private String loadCryptocurrencyPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_CRYPTOCURRENCY, "BTC");
    }

    private String loadCurrencyPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_CURRENCY, "PLN");
    }

    @Override
    public void onPause() {
        super.onPause();

        saveCurrencyPreference(getActivity(), currentCurrency);
        saveCryptocurrencyPreference(getActivity(), currentCryptocurrency);
    }
}
