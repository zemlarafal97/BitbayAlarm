package pl.rzemla.bitbayalarm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.enums.CurrenciesType;
import pl.rzemla.bitbayalarm.interfaces.AdapterClicker;
import pl.rzemla.bitbayalarm.other.Currency;

import java.util.LinkedList;
import java.util.List;


public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.MyViewHolder> {
    private List<Currency> currenciesList;
    private Context mContext;
    private AdapterClicker adapterClicker;
    private String currentCryptocurrencyClicked;
    private CurrenciesType currenciesType;

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

    public CurrenciesAdapter(LinkedList<Currency> currenciesList, Context context, CurrenciesType currenciesType, final AdapterClicker adapterClicker, String currentCryptocurrencyClicked) {
        this.currenciesList = currenciesList;
        this.mContext = context;
        this.adapterClicker = adapterClicker;
        this.currentCryptocurrencyClicked = currentCryptocurrencyClicked;
        this.currenciesType = currenciesType;
    }


    @Override
    public CurrenciesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocurrency_row, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Currency currency = currenciesList.get(position);

        Log.d("OnBind:", currency.getName());

        double dpToPx = mContext.getResources().getDisplayMetrics().density;
        holder.cryptocurrencyLayout.setBackgroundResource(currency.getLayoutBackgroundRes());
        holder.cryptocurrencyNameTV.setText(currenciesList.get(position).getName());
        holder.cryptocurrencyIV.setImageResource(currenciesList.get(position).getImageSourceRes());
        holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 16));

        if(currenciesType == CurrenciesType.CURRENCIES) {
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int singleWidth = width / currenciesList.size();
            Log.d("Single width",String.valueOf(singleWidth));
            holder.cryptocurrencyLayout.setMinimumWidth(singleWidth);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double dpToPx = mContext.getResources().getDisplayMetrics().density;
                currentCryptocurrencyClicked = currenciesList.get(holder.getAdapterPosition()).getName();
                adapterClicker.onClick(currentCryptocurrencyClicked);
                holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 4));
                notifyDataSetChanged();
            }
        });

        if (currency.getName().equals(currentCryptocurrencyClicked)) {
           holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 4));

        } else {
            holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 16));
        }

    }


    @Override
    public int getItemCount() {
        return currenciesList.size();
    }


}
