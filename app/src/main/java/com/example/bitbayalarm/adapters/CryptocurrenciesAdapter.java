package com.example.bitbayalarm.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bitbayalarm.R;
import com.example.bitbayalarm.other.Cryptocurrency;

import java.util.ArrayList;


public class CryptocurrenciesAdapter extends RecyclerView.Adapter<CryptocurrenciesAdapter.MyViewHolder> {
    private ArrayList<Cryptocurrency> cryptocurrenciesList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cryptocurrencyLayout;
        TextView cryptocurrencyNameTV;
        ImageView cryptocurrencyIV;

        public MyViewHolder(View view) {
            super(view);
            cryptocurrencyNameTV = view.findViewById(R.id.cryptoTextView);
            cryptocurrencyIV = view.findViewById(R.id.cryptoImageView);
            cryptocurrencyLayout = view.findViewById(R.id.crypto_layout);
        }
    }

    public CryptocurrenciesAdapter(ArrayList<Cryptocurrency> cryptocurrenciesList) {
        this.cryptocurrenciesList = cryptocurrenciesList;
    }


    @Override
    public CryptocurrenciesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocurrency_row, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cryptocurrency cryptocurrency = cryptocurrenciesList.get(position);


        holder.cryptocurrencyLayout.setBackgroundResource(cryptocurrency.getLayoutBackgroundRes());
        holder.cryptocurrencyNameTV.setText(cryptocurrenciesList.get(position).getName());
        holder.cryptocurrencyIV.setImageResource(cryptocurrenciesList.get(position).getImageSourceRes());

    }

    @Override
    public int getItemCount() {
        return cryptocurrenciesList.size();
    }


}
