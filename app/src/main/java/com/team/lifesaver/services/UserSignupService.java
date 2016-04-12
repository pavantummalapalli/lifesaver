package com.team.lifesaver.services;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.team.lifesaver.handlers.UserSignUpHandler;
import com.team.lifesaver.representations.User;
import com.team.lifesaver.utils.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by pavan.t on 10/03/16.
 */
public class UserSignupService {

    private static String url = "http://172.20.216.165:25916/services/lifesaver/signup";
    public static void signUpService(final UserSignUpHandler handler) throws JSONException{
        handler.startProgessBar();

        Gson gson = new Gson();
        String userString = gson.toJson(handler.getUser());
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(userString.getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RestClient.post(handler.getContext(),url,entity,"application/json",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                handler.stopProgressBar();
                try {
                    handler.signUp(statusCode,headers,response);
                    //user.setUserId(obj.getInt("userid"));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(handler.getContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                handler.stopProgressBar();
                if (statusCode == 404) {
                    Toast.makeText(handler.getContext(), "404 - Nie odnaleziono serwera!", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(handler.getContext(), "500 - Coś poszło nie tak po stronie serwera!", Toast.LENGTH_LONG).show();
                } else if (statusCode == 403) {
                    Toast.makeText(handler.getContext(), "Podano niepoprawne dane!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(handler.getContext(), throwable.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        }



}
