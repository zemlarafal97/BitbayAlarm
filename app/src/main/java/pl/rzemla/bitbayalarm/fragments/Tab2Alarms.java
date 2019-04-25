package pl.rzemla.bitbayalarm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.activities.AlarmSettingsActivity;
import pl.rzemla.bitbayalarm.adapters.AlarmsAdapter;
import pl.rzemla.bitbayalarm.enums.AlarmSettingsMode;
import pl.rzemla.bitbayalarm.other.Alarm;
import pl.rzemla.bitbayalarm.services.AlarmTrackingService;
import pl.rzemla.bitbayalarm.singletons.AlarmsSingleton;


public class Tab2Alarms extends Fragment {

    private AlarmsAdapter alarmsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);


        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.add_floating_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getActivity(), AlarmSettingsActivity.class);
                settingsIntent.putExtra("alarmSettingsMode", AlarmSettingsMode.ADD_MODE);
                startActivity(settingsIntent);
            }
        });


        initializeRecyclerView(rootView);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AlarmsSingleton.getInstance(getActivity()).isAnyAlarmRunning()) {
            getActivity().startService(new Intent(getActivity(), AlarmTrackingService.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        alarmsAdapter.notifyDataSetChanged();
    }

    private void initializeRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.alarms_recycler_view);

        List<Alarm> alarmList = AlarmsSingleton.getInstance(getActivity()).getAlarmsList();

        alarmsAdapter = new AlarmsAdapter(getActivity(), alarmList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(alarmsAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();
        AlarmsSingleton.getInstance(getActivity()).saveAlarmsToSharedPrefs(getActivity());
    }
}

