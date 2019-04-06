package pl.rzemla.bitbayalarm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitbayalarm.R;

import java.util.ArrayList;
import java.util.List;

import pl.rzemla.bitbayalarm.adapters.AlarmsAdapter;
import pl.rzemla.bitbayalarm.enums.AlarmMode;
import pl.rzemla.bitbayalarm.other.Alarm;


public class Tab2Alarms extends Fragment {

    private RecyclerView recyclerView;
    private AlarmsAdapter alarmsAdapter;
    private LinearLayoutManager layoutManager;
    private List<Alarm> alarmList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);

        alarmList = new ArrayList<>();
        alarmList.add(new Alarm("PLN","BTC",false,0,null, AlarmMode.FALL_BELOW));
        alarmList.add(new Alarm("PLN","BTC",false,0,null, AlarmMode.FALL_BELOW));
        alarmList.add(new Alarm("PLN","BTC",false,0,null, AlarmMode.FALL_BELOW));
        alarmList.add(new Alarm("PLN","BTC",false,0,null, AlarmMode.FALL_BELOW));

        initializeRecyclerView(rootView);

        return rootView;
    }



    private void initializeRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.alarms_recycler_view);
        alarmsAdapter = new AlarmsAdapter(getActivity(),alarmList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(alarmsAdapter);
    }


}

