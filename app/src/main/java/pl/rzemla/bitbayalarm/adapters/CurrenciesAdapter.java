package pl.rzemla.bitbayalarm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.interfaces.AdapterClicker;
import pl.rzemla.bitbayalarm.other.Currency;

import java.util.LinkedList;
import java.util.List;


public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.MyViewHolder> {
    private List<Currency> cryptocurrenciesList;
    private Context mContext;
    private AdapterClicker adapterClicker;
    private String currentCryptocurrencyClicked;

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

    public CurrenciesAdapter(LinkedList<Currency> cryptocurrenciesList, Context context, final AdapterClicker adapterClicker, String currentCryptocurrencyClicked) {
        this.cryptocurrenciesList = cryptocurrenciesList;
        this.mContext = context;
        this.adapterClicker = adapterClicker;
        this.currentCryptocurrencyClicked = currentCryptocurrencyClicked;
    }


    @Override
    public CurrenciesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocurrency_row, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Currency currency = cryptocurrenciesList.get(position);

        Log.d("OnBind:", currency.getName());

        double dpToPx = mContext.getResources().getDisplayMetrics().density;
        holder.cryptocurrencyLayout.setBackgroundResource(currency.getLayoutBackgroundRes());
        holder.cryptocurrencyNameTV.setText(cryptocurrenciesList.get(position).getName());
        holder.cryptocurrencyIV.setImageResource(cryptocurrenciesList.get(position).getImageSourceRes());
        holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 14));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double dpToPx = mContext.getResources().getDisplayMetrics().density;
                currentCryptocurrencyClicked = cryptocurrenciesList.get(holder.getAdapterPosition()).getName();
                adapterClicker.onClick(currentCryptocurrencyClicked);
                holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 6));
                notifyDataSetChanged();
            }
        });

        if (currency.getName().equals(currentCryptocurrencyClicked)) {
           holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 6));

        } else {
            holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 14));
        }

    }


    @Override
    public int getItemCount() {
        return cryptocurrenciesList.size();
    }


}
