package pl.rzemla.bitbayalarm;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import pl.rzemla.bitbayalarm.interfaces.ServerCallback;
import pl.rzemla.bitbayalarm.other.Bitbay;

public class BitbayRequest {


    public static void makeBitbayTickerRequest(RequestQueue requestQueue, String cryptocurrencyCode, String currencyCode, final ServerCallback callback) {

        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });


        String query = "https://bitbay.net/API/Public/" + cryptocurrencyCode + currencyCode + "/ticker.json";


        System.out.println(query);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, query, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Bitbay bitbay = new Bitbay();
                try {
                    bitbay.setLast(response.getDouble("last"));
                    bitbay.setMin24h(response.getDouble("min"));
                    bitbay.setMax24h(response.getDouble("max"));
                    bitbay.setVolume(response.getDouble("volume"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callback.onSuccess(bitbay);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

}
