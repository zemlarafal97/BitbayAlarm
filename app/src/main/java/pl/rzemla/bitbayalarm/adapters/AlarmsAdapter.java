package pl.rzemla.bitbayalarm.adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.activities.AlarmSettingsActivity;
import pl.rzemla.bitbayalarm.enums.AlarmMode;
import pl.rzemla.bitbayalarm.enums.AlarmSettingsMode;
import pl.rzemla.bitbayalarm.other.Alarm;
import pl.rzemla.bitbayalarm.services.AlarmTrackingService;
import pl.rzemla.bitbayalarm.singletons.AlarmsSingleton;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Alarm> alarmList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView alarmTV;
        TextView alarmTypeValueTV;
        TextView currenciesTV;
        TextView valueTV;
        SwitchCompat alarmSwitch;
        ImageButton deleteAlarmBtt;
        ImageView vibrationImageView;

        public MyViewHolder(View view) {
            super(view);
            alarmTV = view.findViewById(R.id.alarmTV);
            alarmTypeValueTV = view.findViewById(R.id.alarmTypeValueTV);
            currenciesTV = view.findViewById(R.id.currenciesTV);
            valueTV = view.findViewById(R.id.valueTV);
            alarmSwitch = view.findViewById(R.id.alarmSwitch);
            deleteAlarmBtt = view.findViewById(R.id.imageButtonDelete);
            vibrationImageView = view.findViewById(R.id.vibrationImageView);
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
    public void onBindViewHolder(@NonNull final AlarmsAdapter.MyViewHolder holder, final int position) {

        setHolderAlarmCard(holder, holder.getAdapterPosition());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Alarm clicked", "Yes!");
                Intent settingsIntent = new Intent(mContext, AlarmSettingsActivity.class);
                settingsIntent.putExtra("alarm", alarmList.get(position));
                settingsIntent.putExtra("position",position);
                settingsIntent.putExtra("alarmSettingsMode", AlarmSettingsMode.EDIT_MODE);
                mContext.startActivity(settingsIntent);
            }
        });

        holder.deleteAlarmBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(canDeleteAlarm(alarmList.get(position))) {
                    alarmList.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(mContext,R.string.Turn_off_the_alarm_to_delete,Toast.LENGTH_SHORT).show();
                }

            }
        });




        holder.alarmSwitch.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                alarmList.get(holder.getAdapterPosition()).setRunning(isChecked);

                final Intent serviceIntent = new Intent(mContext, AlarmTrackingService.class);

                if (AlarmsSingleton.getInstance(mContext).isAnyAlarmRunning()) {
                    mContext.startService(serviceIntent);

                } else {

                    PendingIntent pendingIntent = PendingIntent.getService(mContext,0,serviceIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);

                    mContext.stopService(serviceIntent);
                    Log.d("Service stop", "true");
                }
            }
        });

    }

    private boolean canDeleteAlarm(Alarm a) {
        if(a.isRunning()) return false;
        return true;
    }

    private void setHolderAlarmCard(AlarmsAdapter.MyViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        String currencies = alarm.getCryptoCurrency() + "/" + alarm.getCurrency();
        String alarmTitle = "Alarm " + position;
        holder.alarmTV.setText(alarmTitle);
        holder.currenciesTV.setText(currencies);
        holder.valueTV.setText(String.valueOf(alarm.getValue()));

        if (alarm.getSong().isVibration()) {
            holder.vibrationImageView.setVisibility(View.VISIBLE);
        } else {
            holder.vibrationImageView.setVisibility(View.GONE);
        }

        if (alarm.getAlarmMode() == AlarmMode.FALL_BELOW) {
            holder.alarmTypeValueTV.setText(mContext.getString(R.string.will_fall_below));
        } else if (alarm.getAlarmMode() == AlarmMode.RISE_ABOVE) {
            holder.alarmTypeValueTV.setText(mContext.getString(R.string.will_rise_above));
        } else if (alarm.getAlarmMode() == AlarmMode.TRACK_RATE) {
            holder.alarmTypeValueTV.setText(mContext.getString(R.string.track_exchange));
        }

        if (alarm.isRunning()) {
            holder.alarmSwitch.setChecked(true);
        } else {
            holder.alarmSwitch.setChecked(false);
        }
    }


    @Override
    public int getItemCount() {
        return alarmList.size();
    }
}
