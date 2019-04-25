package pl.rzemla.bitbayalarm.widget;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import pl.rzemla.bitbayalarm.BitbayRequest;
import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.other.Resources;
import pl.rzemla.bitbayalarm.services.UpdateService;

public class WidgetTickerConfigureActivity extends Activity {

    private Spinner currencySpinner;
    private Spinner cryptocurrencySpinner;
    private Spinner intervalSpinner;

    ArrayAdapter<String> spinnerCryptocurrencyArrayAdapter;
    ArrayAdapter<String> spinnerCurrencyArrayAdapter;
    ArrayAdapter<Integer> spinnerIntervalArrayAdapter;

    private static final String PREFS_NAME = "pl.rzemla_dev.bitbayalarm.TickerWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static final String PREF_CRYPTOCURRENCY = "cryptocurrency";
    private static final String PREF_CURRENCY = "currency";
    private static final String PREF_URL = "url";
    private static final String PREF_INTERVAL = "interval";

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    public WidgetTickerConfigureActivity() {
        super();
    }

    public static void saveCryptocurrencyPreference(Context context, int appWidgetId, String cryptocurrency) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId + PREF_CRYPTOCURRENCY, cryptocurrency);
        prefs.apply();
    }

    public static void saveCurrencyPreference(Context context, int appWidgetId, String currency) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId + PREF_CURRENCY, currency);
        prefs.apply();
    }

    public static String loadCryptocurrenctPreference(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String cryptoValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId + PREF_CRYPTOCURRENCY, null);
        if (cryptoValue != null) {
            return cryptoValue;
        } else {
            return "-";
        }

    }

    public static String loadCurrencyPreference(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String currencyValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId + PREF_CURRENCY, null);
        if (currencyValue != null) {
            return currencyValue;
        } else {
            return "-";
        }
    }

    public static void saveUrlPref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId + PREF_URL, text);
        prefs.apply();
    }

    public static String loadUrlPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String urlValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId + PREF_URL, null);
        if (urlValue != null) {
            return urlValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }


    public static void saveIntervalPref(Context context, int value) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        prefs.putInt(PREF_PREFIX_KEY + PREF_INTERVAL, value);
        prefs.apply();
    }

    public static int loadIntervalPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int value = prefs.getInt(PREF_PREFIX_KEY + PREF_INTERVAL, 1);
        System.out.println(value);


        return value;
    }

    private void initializeViewElements() {
        currencySpinner = findViewById(R.id.currencySpinner);
        cryptocurrencySpinner = findViewById(R.id.cryptocurrencySpinner);
        intervalSpinner = findViewById(R.id.intervalSpinner);

        spinnerCryptocurrencyArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Resources.getCryptocurrencies());
        spinnerCryptocurrencyArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        cryptocurrencySpinner.setAdapter(spinnerCryptocurrencyArrayAdapter);

        spinnerCurrencyArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Resources.getCurrencies());
        spinnerCurrencyArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        currencySpinner.setAdapter(spinnerCurrencyArrayAdapter);

        spinnerIntervalArrayAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_item, Resources.getIntervals());
        spinnerIntervalArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        intervalSpinner.setAdapter(spinnerIntervalArrayAdapter);
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);


        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.widget_ticker_configure);
        initializeViewElements();

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            Log.d("WidgetConfigur ID:", String.valueOf(mAppWidgetId));

            int interval = loadIntervalPref(WidgetTickerConfigureActivity.this);

            int intervalPosition = spinnerIntervalArrayAdapter.getPosition(interval);
            intervalSpinner.setSelection(intervalPosition);

        }


        findViewById(R.id.widgetSaveAndExitBtt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Save clicked: ", "TRUE");

                if (cryptocurrencySpinner.getSelectedItem().equals("BTC") && currencySpinner.getSelectedItem().equals("BTC")) {
                    Toast.makeText(WidgetTickerConfigureActivity.this, getString(R.string.currency_not_supported), Toast.LENGTH_SHORT).show();
                } else {


                    String url = BitbayRequest.getTickerUrl(cryptocurrencySpinner.getSelectedItem().toString(),currencySpinner.getSelectedItem().toString());

                    Log.d("Url:", url);

                    // When the button is clicked, store the string locally
                    saveUrlPref(WidgetTickerConfigureActivity.this, mAppWidgetId, url);
                    saveCryptocurrencyPreference(WidgetTickerConfigureActivity.this, mAppWidgetId, cryptocurrencySpinner.getSelectedItem().toString());
                    saveCurrencyPreference(WidgetTickerConfigureActivity.this, mAppWidgetId, currencySpinner.getSelectedItem().toString());
                    saveIntervalPref(WidgetTickerConfigureActivity.this, Integer.parseInt(intervalSpinner.getSelectedItem().toString()));

                    // It is the responsibility of the configuration activity to update the app widget
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetTickerConfigureActivity.this);
                    WidgetTicker.updateAppWidget(WidgetTickerConfigureActivity.this, appWidgetManager, mAppWidgetId);

                    // Make sure we pass back the original appWidgetId
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_OK, resultValue);

                    Intent serviceIntent = new Intent(WidgetTickerConfigureActivity.this, UpdateService.class);
                    PendingIntent pi = PendingIntent.getService(WidgetTickerConfigureActivity.this, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager am = (AlarmManager) WidgetTickerConfigureActivity.this.getSystemService(Context.ALARM_SERVICE);
                    am.cancel(pi);


                    int interval = Integer.parseInt(intervalSpinner.getSelectedItem().toString());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        am.setExact(AlarmManager.RTC, System.currentTimeMillis() + interval * 60 * 1000, pi);
                    } else {
                        am.set(AlarmManager.RTC, System.currentTimeMillis() + interval * 60 * 1000, pi);
                    }

                    finish();
                }

            }
        });


        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

    }
}

