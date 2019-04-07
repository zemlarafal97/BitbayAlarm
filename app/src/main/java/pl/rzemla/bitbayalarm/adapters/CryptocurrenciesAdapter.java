package pl.rzemla.bitbayalarm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bitbayalarm.R;

import pl.rzemla.bitbayalarm.interfaces.AdapterClicker;
import pl.rzemla.bitbayalarm.other.Cryptocurrency;

import java.util.LinkedList;
import java.util.List;


public class CryptocurrenciesAdapter extends RecyclerView.Adapter<CryptocurrenciesAdapter.MyViewHolder> {
    private List<Cryptocurrency> cryptocurrenciesList;
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

    public CryptocurrenciesAdapter(LinkedList<Cryptocurrency> cryptocurrenciesList, Context context, final AdapterClicker adapterClicker, String currentCryptocurrencyClicked) {
        this.cryptocurrenciesList = cryptocurrenciesList;
        this.mContext = context;
        this.adapterClicker = adapterClicker;
        this.currentCryptocurrencyClicked = currentCryptocurrencyClicked;
    }


    @Override
    public CryptocurrenciesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptocurrency_row, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Cryptocurrency cryptocurrency = cryptocurrenciesList.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCryptocurrencyClicked = cryptocurrenciesList.get(holder.getAdapterPosition()).getName();
                notifyDataSetChanged();
                adapterClicker.onClick(currentCryptocurrencyClicked);
            }
        });

        if(cryptocurrency.getName().equals(currentCryptocurrencyClicked)) {
            holder.cryptocurrencyLayout.setBackgroundResource(cryptocurrency.getLayoutBackgroundRes());
            holder.cryptocurrencyNameTV.setText(cryptocurrenciesList.get(position).getName());
            holder.cryptocurrencyIV.setImageResource(cryptocurrenciesList.get(position).getImageSourceRes());
            double dpToPx = mContext.getResources().getDisplayMetrics().density;
            holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 18));
        } else {
            holder.cryptocurrencyLayout.setBackgroundResource(cryptocurrency.getLayoutBackgroundRes());
            holder.cryptocurrencyNameTV.setText(cryptocurrenciesList.get(position).getName());
            holder.cryptocurrencyIV.setImageResource(cryptocurrenciesList.get(position).getImageSourceRes());
            double dpToPx = mContext.getResources().getDisplayMetrics().density;
            holder.cryptocurrencyNameTV.setPadding(0, 0, 0, (int) (dpToPx * 6));
        }




    }


    @Override
    public int getItemCount() {
        return cryptocurrenciesList.size();
    }


}
