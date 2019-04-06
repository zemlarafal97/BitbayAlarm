package pl.rzemla.bitbayalarm.fragments;

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

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        initializeViewElements(rootView);
        initializeCryptocurrenciesRecyclerView(rootView);

        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        setViewElementsClickListeners();

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
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_btc, R.drawable.btc_icon_24dp, "BTC", true));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_bcc, R.drawable.bcc_icon_24dp, "BCC", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_btg, R.drawable.btg_icon_24dp, "BTG", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_ltc, R.drawable.ltc_icon_24dp, "LTC", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_eth, R.drawable.eth_icon_24dp, "ETH", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_game, R.drawable.game_icon_24dp, "GAME", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_dash, R.drawable.dash_icon_24dp, "DASH", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_lsk, R.drawable.lsk_icon_24dp, "LSK", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_inr, R.drawable.inr_icon_24dp, "INR", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_kzc, R.drawable.kzc_icon_24dp, "KZC", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_xin, R.drawable.xin_icon_24dp, "XIN", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_xmr, R.drawable.xmr_icon_24dp, "XMR", false));
        cryptocurrencyList.add(new Cryptocurrency(R.drawable.shadow_zec, R.drawable.zec_icon_24dp, "ZEC", false));


        recyclerView = rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new CryptocurrenciesAdapter(cryptocurrencyList, getActivity(), new AdapterClicker() {
            @Override
            public void onClick(String cryptocurrency) {
                currentCryptocurrency = cryptocurrency;
                updateRatesViews();
            }
        });
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
                unclickCurrenciesLayouts();
                setCurrencyClicked(plnTV);
                currentCurrency = "PLN";
            }
        });

        usdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickCurrenciesLayouts();
                setCurrencyClicked(usdTV);
                currentCurrency = "USD";
            }
        });

        eurLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickCurrenciesLayouts();
                setCurrencyClicked(eurTV);
                currentCurrency = "EUR";
            }
        });

        btc2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unclickCurrenciesLayouts();
                setCurrencyClicked(btc2TV);
                currentCurrency = "BTC";
            }
        });
    }

    private void updateRatesViews() {
        if (currentCurrency != currentCryptocurrency) {
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
        double dpToPx = getActivity().getResources().getDisplayMetrics().density;
        view.setPadding(0, 0, 0, (int) (dpToPx * 18));
        updateRatesViews();
    }


}
