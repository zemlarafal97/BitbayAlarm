package pl.rzemla.bitbayalarm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitbayalarm.R;

import java.util.List;

import pl.rzemla.bitbayalarm.other.Alarm;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Alarm> alarmList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }


    public AlarmsAdapter(Context mContext, List<Alarm> alarmList) {
        this.mContext = mContext;
        this.alarmList = alarmList;
    }


    @NonNull
    @Override
    public AlarmsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmsAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }
}
