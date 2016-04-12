package com.team.lifesaver.utils;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

public class RestClient {

  private static AsyncHttpClient client = new AsyncHttpClient();


  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

      client.get(url, params, responseHandler);
  }

  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.post(url, params, responseHandler);
  }

    public static void post(Context context,String url,HttpEntity entity,String ContentType,AsyncHttpResponseHandler responseHandler) {
        client.post(context,url,entity,ContentType,responseHandler);
    }



}