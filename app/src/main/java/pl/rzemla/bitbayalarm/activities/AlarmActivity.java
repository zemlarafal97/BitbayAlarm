package pl.rzemla.bitbayalarm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.other.Resources;
import pl.rzemla.bitbayalarm.enums.AlarmMode;
import pl.rzemla.bitbayalarm.services.AlarmSoundService;

public class AlarmActivity extends Activity {

    private Button turnOffAlarmBtt;
    private TextView cryptocurrencyTV;
    private TextView cryptocurrencyModeTV;
    private TextView exchangeTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        initializeViewElements();

        String cryptocurrency = getIntent().getStringExtra("cryptocurrency");
        String currency = getIntent().getStringExtra("currency");
        double value = getIntent().getDoubleExtra("value", 0.0);
        AlarmMode mode = (AlarmMode) getIntent().getSerializableExtra("alarmMode");

        String exchangeValue = String.valueOf(value) + Resources.getCurrencySymbol(currency);

        cryptocurrencyTV.setText(cryptocurrency);
        setAlarmModeText(mode);
        exchangeTV.setText(exchangeValue);

        turnOffAlarmBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, AlarmSoundService.class);
                stopService(intent);

                finish();
            }
        });

    }

    private void setAlarmModeText(AlarmMode mode) {
        if(mode == AlarmMode.FALL_BELOW) {
            cryptocurrencyModeTV.setText(getResources().getString(R.string.has_fallen_below));
        } else if(mode == AlarmMode.RISE_ABOVE) {
            cryptocurrencyModeTV.setText(getResources().getString(R.string.has_risen_above));
        }
    }

    private void initializeViewElements() {
        cryptocurrencyTV = findViewById(R.id.cryptoCurrencyTypeTV);
        cryptocurrencyModeTV = findViewById(R.id.cryptoAlarmTypeTV);
        exchangeTV = findViewById(R.id.exchangeRateValueTV);
        turnOffAlarmBtt = findViewById(R.id.turnOffAlarmBtt);
    }
}
