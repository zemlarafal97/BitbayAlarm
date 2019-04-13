package pl.rzemla.bitbayalarm.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.activities.MainActivity;
import pl.rzemla.bitbayalarm.other.Resources;
import pl.rzemla.bitbayalarm.services.UpdateService;


public class WidgetTicker extends AppWidgetProvider {


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {


        Log.d("UPDATE APP WIDGET","+++++++++++++++++++++++");

        String cryptocurrency = WidgetTickerConfigureActivity.loadCryptocurrenctPreference(context, appWidgetId);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ticker);
        views.setTextViewText(R.id.widget_value_TV, " - ");
        views.setTextViewText(R.id.widget_date_value_TV, "-");

        switch (cryptocurrency) {
            case "BTC":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.btc_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_btc_background);
                break;
            case "BCC":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.bcc_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_bcc_background);
                break;
            case "BTG":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.btg_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_btg_background);
                break;
            case "LTC":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.ltc_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_ltc_background);
                break;
            case "ETH":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.eth_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_eth_background);
                break;
            case "LSK":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.lsk_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_lsk_background);
                break;
            case "DASH":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.dash_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_dash_background);
                break;
            case "GAME":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.game_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_game_background);
                break;
            case "XIN":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.xin_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_xin_background);
                break;
            case "XRP":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.xrp_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_xrp_background);
                break;
            case "KZC":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.kzc_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_kzc_background);
                break;
            case "XMR":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.xmr_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_xmr_background);
                break;
            case "ZEC":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.zec_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_zec_background);
                break;
            case "GNT":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.gnt_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_gnt_background);
                break;
            case "FTO":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.fto_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_fto_background);
                break;
            case "OMG":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.omg_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_omg_background);
                break;
            case "PAY":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.pay_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_pay_background);
                break;
            case "REP":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.rep_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_rep_background);
                break;
            case "ZRX":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.zrx_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_zrx_background);
                break;
            case "BAT":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.bat_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_bat_background);
                break;
            case "NEU":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.neu_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_neu_background);
                break;
            case "TRX":
                views.setInt(R.id.widget_icon_IV, "setImageResource", R.drawable.trx_icon_24dp);
                views.setInt(R.id.widget_icon_IV, "setBackgroundResource", R.drawable.widget_trx_background);
                break;
        }


        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_ticker, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void setWidgetValues(Context context, AppWidgetManager appWidgetManager, int appWidgetId, double value, String currency) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        df.setMaximumFractionDigits(10);

        String currencySymbol = Resources.getCurrencySymbol(currency);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ticker);
        views.setTextViewText(R.id.widget_value_TV, String.valueOf(df.format(value)) + currencySymbol);
        views.setTextViewText(R.id.widget_date_value_TV, date);
        appWidgetManager.updateAppWidget(appWidgetId,views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        Log.d("onUpdate","WidgetTicker");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onReceive", "Received intent " + intent);


        if(intent.getAction().equals("android.appwidget.action.APPWIDGET_DISABLED")) {
            Log.d("onReceive","DISABLED");

            Intent serviceIntent = new Intent(context,UpdateService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context,0,serviceIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            context.stopService(new Intent(context, UpdateService.class));


        }

    }


}


