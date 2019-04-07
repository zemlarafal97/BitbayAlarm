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
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.rzemla.bitbayalarm.BitbayRequest;
import pl.rzemla.bitbayalarm.Resources;
import pl.rzemla.bitbayalarm.interfaces.AdapterClicker;
import pl.rzemla.bitbayalarm.interfaces.ServerCallback;
import pl.rzemla.bitbayalarm.VolleySingleton;
import pl.rzemla.bitbayalarm.adapters.CryptocurrenciesAdapter;


import com.android.volley.RequestQueue;
import com.example.bitbayalarm.R;

import pl.rzemla.bitbayalarm.other.Bitbay;
import pl.rzemla.bitbayalarm.other.Cryptocurrency;

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

    private LinearLayout plnLayout;
    private LinearLayout eurLayout;
    private LinearLayout usdLayout;
    private LinearLayout btc2Layout;
    private TextView plnTV;
    private TextView usdTV;
    private TextView eurTV;
    private TextView btc2TV;

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

        initializeViewElements(rootView);
        initializeCryptocurrenciesRecyclerView(rootView);

        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        setViewElementsClickListeners();

        currentCurrency = loadCurrencyPreference(getActivity());
        currentCryptocurrency = loadCryptocurrencyPreference(getActivity());

        switch (currentCurrency) {
            case "PLN":
                setCurrencyClicked(plnTV);
                break;
            case "USD":
                setCurrencyClicked(usdTV);
                break;
            case "BTC":
                setCurrencyClicked(btc2TV);
                break;
            case "EUR":
                setCurrencyClicked(eurTV);
                break;
            default:
                setCurrencyClicked(plnTV);
        }


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
        plnLayout = rootView.findViewById(R.id.pln_layout);
        eurLayout = rootView.findViewById(R.id.eur_layout);
        usdLayout = rootView.findViewById(R.id.usd_layout);
        btc2Layout = rootView.findViewById(R.id.btc_layout2);
        plnTV = rootView.findViewById(R.id.plnTextView);
        usdTV = rootView.findViewById(R.id.usdTextView);
        eurTV = rootView.findViewById(R.id.eurTextView);
        btc2TV = rootView.findViewById(R.id.btc2TextView);
    }

    private void initializeCryptocurrenciesRecyclerView(View rootView) {
        LinkedList<Cryptocurrency> cryptocurrencyList = new LinkedList<>();
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_btc, R.drawable.btc_icon_24dp, "BTC"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_bcc, R.drawable.bcc_icon_24dp, "BCC"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_btg, R.drawable.btg_icon_24dp, "BTG"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_ltc, R.drawable.ltc_icon_24dp, "LTC"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_eth, R.drawable.eth_icon_24dp, "ETH"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_lsk, R.drawable.lsk_icon_24dp, "LSK"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_dash, R.drawable.dash_icon_24dp, "DASH"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_game, R.drawable.game_icon_24dp, "GAME"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_xin, R.drawable.xin_icon_24dp, "XIN"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_xrp,R.drawable.xrp_icon_24dp,"XRP"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_kzc, R.drawable.kzc_icon_24dp, "KZC"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_xmr, R.drawable.xmr_icon_24dp, "XMR"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_zec, R.drawable.zec_icon_24dp, "ZEC"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_gnt,R.drawable.gnt_icon_24dp,"GNT"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_fto,R.drawable.fto_icon_24dp,"FTO"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_omg,R.drawable.omg_icon_24dp,"OMG"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_pay,R.drawable.pay_icon_24dp,"PAY"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_rep,R.drawable.rep_icon_24dp,"REP"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_zrx,R.drawable.zrx_icon_24dp,"ZRX"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_bat,R.drawable.bat_icon_24dp,"BAT"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_neu,R.drawable.neu_icon_24dp,"NEU"));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_trx,R.drawable.trx_icon_24dp,"TRX"));


        RecyclerView recyclerView = rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new CryptocurrenciesAdapter(cryptocurrencyList, getActivity(), new AdapterClicker() {
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

        plnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrencyClicked(plnTV);
                currentCurrency = "PLN";
                updateRatesViews();
            }
        });

        usdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrencyClicked(usdTV);
                currentCurrency = "USD";
                updateRatesViews();
            }
        });

        eurLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrencyClicked(eurTV);
                currentCurrency = "EUR";
                updateRatesViews();
            }
        });

        btc2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrencyClicked(btc2TV);
                currentCurrency = "BTC";
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
        }
    }

    private void unclickCurrenciesLayouts() {
        double dpToPx = getActivity().getResources().getDisplayMetrics().density;
        plnTV.setPadding(0, 0, 0, (int) (dpToPx * 6));
        usdTV.setPadding(0, 0, 0, (int) (dpToPx * 6));
        eurTV.setPadding(0, 0, 0, (int) (dpToPx * 6));
        btc2TV.setPadding(0, 0, 0, (int) (dpToPx * 6));
    }

    private void setCurrencyClicked(View view) {
        unclickCurrenciesLayouts();
        double dpToPx = getActivity().getResources().getDisplayMetrics().density;
        view.setPadding(0, 0, 0, (int) (dpToPx * 18));
        updateRatesViews();
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
