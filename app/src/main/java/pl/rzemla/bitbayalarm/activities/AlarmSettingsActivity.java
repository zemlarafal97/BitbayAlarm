package pl.rzemla.bitbayalarm.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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


import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.other.FileMetaData;
import pl.rzemla.bitbayalarm.other.Resources;
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

    private ArrayAdapter<String> currencyAdapter;
    private ArrayAdapter<String> cryptocurrencyAdapter;
    private ArrayAdapter<String> alarmTypeAdapter;

    private Alarm alarm;
    private Song song;

    private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 99;

    private static final String PREFS_NAME = "pl.rzemla.bitbayalarm.AlarmSettingsActivity";
    private static final String PREF_REFRESH_FREQ = "refreshFrequency";


    private void saveRefreshFreqPreference(Context context, int refreshFreq) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putInt(PREF_REFRESH_FREQ, refreshFreq);
        prefs.apply();
    }

    public static int loadRefreshFreqPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(PREF_REFRESH_FREQ, 30);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);

        alarmSettingsMode = (AlarmSettingsMode) getIntent().getSerializableExtra("alarmSettingsMode");
        alarm = (Alarm) getIntent().getSerializableExtra("alarm");

        initializeViewElements();
        setOnClickListeners();
        setViewElementsValues();

        if(alarmSettingsMode == AlarmSettingsMode.ADD_MODE) {
            song = new Song();
        } else {
            song = alarm.getSong();
        }


        Log.d("Alarm mode", String.valueOf(alarmSettingsMode));

    }

    private boolean isAlarmSettingsInEditMode(AlarmSettingsMode alarmSettingsMode) {
        return alarmSettingsMode == AlarmSettingsMode.EDIT_MODE;
    }

    private void setToolbarTitle(Toolbar toolbar) {
        if (alarmSettingsMode == AlarmSettingsMode.EDIT_MODE) {
            toolbar.setTitle(getResources().getString(R.string.Edit_alarm));
        } else if (alarmSettingsMode == AlarmSettingsMode.ADD_MODE) {
            toolbar.setTitle(getResources().getString(R.string.Add_alarm));
        }
    }

    private void initializeViewElements() {

        Toolbar toolbar = findViewById(R.id.alarm_settings_activity_toolbar);
        setSupportActionBar(toolbar);
        setToolbarTitle(toolbar);

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

        currencyAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Resources.getCurrencies());
        currencySpinner.setAdapter(currencyAdapter);

        cryptocurrencyAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Resources.getCryptocurrencies());
        cryptocurrencySpinner.setAdapter(cryptocurrencyAdapter);

        String[] alarmTypes = {getResources().getString(R.string.will_rise_above), getResources().getString(R.string.will_fall_below), getResources().getString(R.string.track_exchange)};
        alarmTypeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, alarmTypes);
        alarmTypeSpinner.setAdapter(alarmTypeAdapter);



    }

    private void setViewElementsValues() {

        if(isAlarmSettingsInEditMode(alarmSettingsMode)) {

            System.out.println(alarm.toString());

            switch(alarm.getAlarmMode()) {
                case RISE_ABOVE:
                    alarmTypeSpinner.setSelection(alarmTypeAdapter.getPosition(getResources().getString(R.string.will_rise_above)));
                    break;
                case FALL_BELOW:
                    alarmTypeSpinner.setSelection(alarmTypeAdapter.getPosition(getResources().getString(R.string.will_fall_below)));
                    break;
                case TRACK_RATE:
                    alarmTypeSpinner.setSelection(alarmTypeAdapter.getPosition(getResources().getString(R.string.track_exchange)));
                    break;
            }


            if(alarm.isAdditionalTracking()) {
                trackCheckBox.setChecked(true);
            } else {
                trackCheckBox.setChecked(false);
            }

            if(alarm.getSong().isVibration()) {
                vibrationCheckBox.setChecked(true);
            } else {
                vibrationCheckBox.setChecked(false);
            }
            cryptocurrencySpinner.setSelection(cryptocurrencyAdapter.getPosition(alarm.getCryptoCurrency()));
            currencySpinner.setSelection(currencyAdapter.getPosition(alarm.getCurrency()));
            limitValueET.setText(String.valueOf(alarm.getValue()));
            refreshFrequencyET.setText(String.valueOf(loadRefreshFreqPreference(AlarmSettingsActivity.this)));
            alarmSoundTitleTV.setText(alarm.getSong().getSongTitle());
        } else {
            refreshFrequencyET.setText(String.valueOf(loadRefreshFreqPreference(AlarmSettingsActivity.this)));

        }

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


        if(alarmTypeSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.track_exchange)) && isRefreshFrequencyValid(Integer.valueOf(refreshFrequencyET.getText().toString()))) {
            return true;
        }

        if(!isLimitValueEmpty()&& isRefreshFrequencyValid(Integer.valueOf(refreshFrequencyET.getText().toString()))) return true;

        return false;
    }

    private void setOnClickListeners() {

        saveAndExitBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInputCorrect() && !cryptocurrencySpinner.getSelectedItem().toString().equals(currencySpinner.getSelectedItem().toString())) {

                    String currency = currencySpinner.getSelectedItem().toString();
                    String cryptoCurrency = cryptocurrencySpinner.getSelectedItem().toString();
                    double value = Double.parseDouble(limitValueET.getText().toString());
                    int refreshTime = Integer.parseInt(refreshFrequencyET.getText().toString());
                    song.setVibration(hasVibrationBeenSet());

                    Alarm alarm = new Alarm(currency, cryptoCurrency, false, value, song, getAlarmMode(), isAdditionalTracking());

                    if (alarmSettingsMode == AlarmSettingsMode.ADD_MODE) {
                        AlarmsSingleton.getInstance(AlarmSettingsActivity.this).getAlarmsList().add(alarm);

                    } else if (alarmSettingsMode == AlarmSettingsMode.EDIT_MODE) {
                        AlarmsSingleton.getInstance(AlarmSettingsActivity.this).getAlarmsList().set(getIntent().getIntExtra("position", -1), alarm);

                    }
                    saveRefreshFreqPreference(AlarmSettingsActivity.this,refreshTime);

                    finish();
                } else {
                    Toast.makeText(AlarmSettingsActivity.this, getResources().getText(R.string.Correct_input), Toast.LENGTH_SHORT).show();
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
                    vibrationCheckBox.setVisibility(View.VISIBLE);
                    limitValueET.setEnabled(true);
                } else if (alarmTypeSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.will_fall_below))) {
                    trackCheckBox.setVisibility(View.VISIBLE);
                    vibrationCheckBox.setVisibility(View.VISIBLE);
                    limitValueET.setEnabled(true);
                } else {
                    trackCheckBox.setVisibility(View.GONE);
                    vibrationCheckBox.setVisibility(View.GONE);
                    trackCheckBox.setChecked(false);
                    limitValueET.setText("0.0");
                    limitValueET.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private boolean isRefreshFrequencyValid(int refreshFrequency) {
        return (30 <= refreshFrequency && refreshFrequency <= 3600);
    }

    private boolean hasVibrationBeenSet() {
        return vibrationCheckBox.isChecked();
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

    private boolean canReadExternalStorage() {
        int permissionCheck = ContextCompat.checkSelfPermission(AlarmSettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void selectAlarmSong() {

        if(canReadExternalStorage()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        } else {
            ActivityCompat.requestPermissions(AlarmSettingsActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode != RESULT_CANCELED) {
            try {

                Uri uri = intent.getData();
                String title = FileMetaData.getFileMetaData(AlarmSettingsActivity.this, uri).getDisplayName();

                if (uri.toString().contains("content://media")) {
                    song.setSongTitle(title);
                    song.setSongUri(uri.toString());
                } else {
                    Toast.makeText(AlarmSettingsActivity.this, R.string.use_different_app_to_play_song, Toast.LENGTH_SHORT).show();
                    song.setSongTitle("default");
                    song.setSongUri("");
                }

                alarmSoundTitleTV.setText(title);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }

        }



    }
}
