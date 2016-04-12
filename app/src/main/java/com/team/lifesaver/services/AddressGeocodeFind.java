package com.team.lifesaver.services;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team.lifesaver.R;
import com.team.lifesaver.handlers.AddressLongLatHandler;
import com.team.lifesaver.utils.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pavan.t on 09/03/16.
 */
public class AddressGeocodeFind {
    private static String googleGeoURL="https://maps.googleapis.com/maps/api/geocode/json";
    private static String key="AIzaSyD98Ofph8Ck36-bCPTPEzZ3N3IR8SjZKMw";

    public static void getGeoCodeForAddress(String address, final AddressLongLatHandler handler) throws JSONException {
        RequestParams params = new RequestParams();
        params.put("address", address);
        params.put("key", key);
        RestClient.get(googleGeoURL, params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    double latitude = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    double longitude = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                    handler.displayLongLat(longitude,latitude);
                } catch (JSONException e) {
                    Log.d("blood", e.toString());
                }
            }
        });

    }
}
