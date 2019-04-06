package pl.rzemla.bitbayalarm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.rzemla.bitbayalarm.BitbayRequest;
import pl.rzemla.bitbayalarm.interfaces.AdapterClicker;
import pl.rzemla.bitbayalarm.interfaces.ServerCallback;
import pl.rzemla.bitbayalarm.VolleySingleton;
import pl.rzemla.bitbayalarm.adapters.CryptocurrenciesAdapter;

import com.android.volley.RequestQueue;
import com.example.bitbayalarm.R;

import pl.rzemla.bitbayalarm.other.Bitbay;
import pl.rzemla.bitbayalarm.other.Cryptocurrency;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;


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

    private RequestQueue mQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);

        initializeViewElements(rootView);
        initializeCryptocurrenciesRecyclerView(rootView);

        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        refreshBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BitbayRequest.makeBitbayTickerRequest(mQueue, "BTC", "PLN", new ServerCallback() {

                    @Override
                    public void onSuccess(Bitbay bitbay) {
                        lastValueTV.setText(String.valueOf(bitbay.getLast()));
                        minValueTV.setText(String.valueOf(bitbay.getMin24h()));
                        maxValueTV.setText(String.valueOf(bitbay.getMax24h()));
                        volumeValueTV.setText(String.valueOf(bitbay.getVolume()));
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
                        String date = dateFormat.format(Calendar.getInstance().getTime());
                        refreshTimeValueTV.setText(date);

                    }
                });


            }
        });


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
            public void onClick(String cryptoname) {
                System.out.println(cryptoname);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }


}
