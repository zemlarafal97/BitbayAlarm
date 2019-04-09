package pl.rzemla.bitbayalarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitbayalarm.R;

import pl.rzemla.bitbayalarm.Resources;
import pl.rzemla.bitbayalarm.enums.AlarmMode;
import pl.rzemla.bitbayalarm.enums.AlarmSettingsMode;
import pl.rzemla.bitbayalarm.other.Alarm;
import pl.rzemla.bitbayalarm.other.Song;
import pl.rzemla.bitbayalarm.singletons.AlarmsSingleton;

public class AlarmSettingsActivity extends AppCompatActivity {

    private Spinner alarmTypeSpinner;
    private Spinner currencySpinner;
    private Spinner cryptocurrencySpinner;

    private EditText limitValueET;
    private EditText refreshFrequencyET;

    private Button saveAndExitBtt;
    private Button chooseAlarmSoundBtt;

    private CheckBox trackCheckBox;
    private CheckBox vibrationCheckBox;

    private TextView alarmSoundTitleTV;

    private AlarmSettingsMode alarmSettingsMode;
    private Song song;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);

        initializeViewElements();
        setOnClickListeners();

        Toolbar toolbar = findViewById(R.id.alarm_settings_activity_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("AAAAAAAAAAAAAAAAAAa");


        alarmSettingsMode = (AlarmSettingsMode) getIntent().getSerializableExtra("alarmSettingsMode");

        setToolbarTitle(toolbar);
        song = new Song();

        Log.d("Alarm mode", String.valueOf(alarmSettingsMode));

    }

    private void setToolbarTitle(Toolbar toolbar) {
        if (alarmSettingsMode == AlarmSettingsMode.EDIT_MODE) {
            toolbar.setTitle(getResources().getString(R.string.Edit_alarm));
        } else if (alarmSettingsMode == AlarmSettingsMode.ADD_MODE) {
            toolbar.setTitle(getResources().getString(R.string.Add_alarm));
        }
    }

    private void initializeViewElements() {

        limitValueET = findViewById(R.id.limitValueET);
        refreshFrequencyET = findViewById(R.id.refreshFrequencyValueET);
        saveAndExitBtt = findViewById(R.id.saveAndExitBtt);
        chooseAlarmSoundBtt = findViewById(R.id.chooseAlarmSoundBtt);
        alarmTypeSpinner = findViewById(R.id.alarmTypeSpinner);
        currencySpinner = findViewById(R.id.currencySpinner);
        cryptocurrencySpinner = findViewById(R.id.cryptocurrencySpinner);
        trackCheckBox = findViewById(R.id.trackCheckBox);
        vibrationCheckBox = findViewById(R.id.vibrationCheckBox);
        alarmSoundTitleTV = findViewById(R.id.alarmSoundValueTV);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Resources.getCurrencies());
        currencySpinner.setAdapter(currencyAdapter);

        ArrayAdapter<String> cryptocurrencyAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Resources.getCryptocurrencies());
        cryptocurrencySpinner.setAdapter(cryptocurrencyAdapter);

        String[] alarmTypes = {getResources().getString(R.string.will_rise_above), getResources().getString(R.string.will_fall_below), getResources().getString(R.string.track_exchange)};
        ArrayAdapter<String> alarmTypeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, alarmTypes);
        alarmTypeSpinner.setAdapter(alarmTypeAdapter);
    }

    private boolean isAdditionalTracking() {
        return trackCheckBox.isChecked();
    }

    private boolean isRefreshRateEmpty() {
        return TextUtils.isEmpty(refreshFrequencyET.getText().toString());
    }

    private boolean isLimitValueEmpty() {
        return TextUtils.isEmpty(limitValueET.getText().toString());
    }

    private boolean isInputCorrect() {
        return !isRefreshRateEmpty() && !isLimitValueEmpty();
    }

    private void setOnClickListeners() {

        saveAndExitBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInputCorrect()) {
                    String currency = currencySpinner.getSelectedItem().toString();
                    String cryptoCurrency = cryptocurrencySpinner.getSelectedItem().toString();
                    double value = Double.parseDouble(limitValueET.getText().toString());
                    Alarm alarm = new Alarm(currency, cryptoCurrency, false, value, song, getAlarmMode(), isAdditionalTracking());

                    if (alarmSettingsMode == AlarmSettingsMode.ADD_MODE) {
                        AlarmsSingleton.getInstance(AlarmSettingsActivity.this).getAlarmsList().add(alarm);
                    } else if (alarmSettingsMode == AlarmSettingsMode.EDIT_MODE) {
                        AlarmsSingleton.getInstance(AlarmSettingsActivity.this).getAlarmsList().set(getIntent().getIntExtra("position", -1), alarm);

                    }

                    finish();
                } else {
                    Toast.makeText(AlarmSettingsActivity.this,getResources().getText(R.string.Correct_input),Toast.LENGTH_SHORT).show();
                }

            }
        });

        chooseAlarmSoundBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAlarmSong();

            }
        });

        alarmTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (alarmTypeSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.will_rise_above))) {
                    trackCheckBox.setVisibility(View.VISIBLE);
                } else if (alarmTypeSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.will_fall_below))) {
                    trackCheckBox.setVisibility(View.VISIBLE);
                } else {
                    trackCheckBox.setVisibility(View.GONE);
                    trackCheckBox.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private AlarmMode getAlarmMode() {

        if (alarmTypeSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.will_rise_above))) {
            return AlarmMode.RISE_ABOVE;
        } else if (alarmTypeSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.will_fall_below))) {
            return AlarmMode.FALL_BELOW;
        } else {
            return AlarmMode.TRACK_RATE;
        }

    }

    private void selectAlarmSong() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
