package com.example.bitbayalarm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitbayalarm.adapters.CryptocurrenciesAdapter;
import com.example.bitbayalarm.R;
import com.example.bitbayalarm.other.Cryptocurrency;

import java.util.ArrayList;


public class Tab1ExchangeRate extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);


        ArrayList<Cryptocurrency> cryptocurrencyArrayList = new ArrayList<>();
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_btc,R.drawable.btc_icon_24dp,"BTC"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_bcc,R.drawable.bcc_icon_24dp,"BCC"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_btg,R.drawable.btg_icon_24dp,"BTG"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_ltc,R.drawable.ltc_icon_24dp,"LTC"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_eth,R.drawable.eth_icon_24dp,"ETH"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_game,R.drawable.game_icon_24dp,"GAME"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_dash,R.drawable.dash_icon_24dp,"DASH"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_lsk,R.drawable.lsk_icon_24dp,"LSK"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_inr,R.drawable.inr_icon_24dp,"INR"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_kzc,R.drawable.kzc_icon_24dp,"KZC"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_xin,R.drawable.xin_icon_24dp,"XIN"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_xmr,R.drawable.xmr_icon_24dp,"XMR"));
        cryptocurrencyArrayList.add(new Cryptocurrency(R.drawable.shadow_zec,R.drawable.zec_icon_24dp,"ZEC"));


        recyclerView = rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new CryptocurrenciesAdapter(cryptocurrencyArrayList);
        recyclerView.setAdapter(mAdapter);



        return rootView;
    }

}
